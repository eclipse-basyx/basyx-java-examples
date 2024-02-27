/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package org.eclipse.basyx.examples.timeseries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

import org.eclipse.basyx.examples.timeseries.time_series_submodel.TimeSeriesSubmodelFactory;

/*
 * Builds an AirSensor AAS from the provided AirSensor.aasx
 * Attaches a Submodel provider server with a TimeSeries
 * operation
 * 
 * author Al-Obaidi
 */
public class Executable {
    /**
     * AAS endpoint configs
     */
    // private static final String REGISTRY_ENDPOINT =
    // "http://localhost:4000/registry/";
    // public static final String AASSERVER_ENDPOINT =
    // "http://localhost:4001/aasServer";
    private static final String REGISTRY_ENDPOINT = "http://registry:4000/registry/";
    private static final String AASSERVER_ENDPOINT = "http://aas:4001/aasServer";
    private static final int NUMBER_OF_RETRIES = 5;

    private static final String AASX_PATH = "admin-shells/AirSensor.aasx";
    private static final String AAS_ID_SHORT = "AirSensor";
    private static final String TIMESERIES_SUBMODEL_ENDPOINT = "http://localhost:8002/AirSensor/TimeSeries";

    private static final Logger logger = LoggerFactory.getLogger(Executable.class);

    public static void main(String[] args) throws Exception {

        IAASRegistry registryProxy = new AASRegistryProxy(REGISTRY_ENDPOINT);

        waitUntilRegistryIsReady(registryProxy);
        waitUntilEndpointIsReady(AASSERVER_ENDPOINT + "/shells");

        ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(
                registryProxy);

        AASBundle AirSensorAasxBundle = AASXImporter.getAasFromAasx(AASX_PATH,
                AAS_ID_SHORT);
        AssetAdministrationShell AirSensorAAS = (AssetAdministrationShell) AirSensorAasxBundle.getAAS();
        aasManager.createAAS(AirSensorAAS, AASSERVER_ENDPOINT);

        TimeSeriesSubmodelFactory timeSeriesFactory = new TimeSeriesSubmodelFactory(
                TimeSeriesSubmodelFactory.Database.influxDB,
                AASX_PATH,
                AAS_ID_SHORT);
        Submodel airSensorTimeSeriesSubmodel = timeSeriesFactory.createTimeSeriesSubmodel(AirSensor.class);

        aasManager.retrieveAAS(AirSensorAAS.getIdentification()).addSubmodel(airSensorTimeSeriesSubmodel);
        registryProxy.register(AirSensorAAS.getIdentification(),
                new SubmodelDescriptor(airSensorTimeSeriesSubmodel,
                        TIMESERIES_SUBMODEL_ENDPOINT));
        startTimeSeriesSubmodelServer(airSensorTimeSeriesSubmodel);
    }

    /*
     * Wait until the registry is ready.
     */
    private static void waitUntilRegistryIsReady(IAASRegistry registry) throws InterruptedException {
        for (int i = 1; i <= NUMBER_OF_RETRIES; i++) {
            try {
                logger.info("Trying to connect to registry (..." + i + ")");
                registry.lookupAll();
                logger.info("Registry is up!");
                break;
            } catch (ProviderException e) {
                logger.info("Could not connect to registry!");
                Thread.sleep(5000);
            }
        }
    }

    /*
     * Wait until the endpoint is ready.
     */
    private static void waitUntilEndpointIsReady(String endpoint) throws InterruptedException {
        ModelProxyFactory mpf = new ModelProxyFactory(new HTTPConnectorFactory());
        VABElementProxy smProxy = mpf.createProxy(endpoint);

        for (int i = 1; i <= NUMBER_OF_RETRIES; i++) {
            try {
                logger.info("Trying to connect to AASServer (..." + i + ")");
                smProxy.getValue("");
                logger.info("AASServer is up!");
                break;
            } catch (ProviderException e) {
                logger.info("Could not connect to AASServer!");
                Thread.sleep(5000);
            }
        }
    }

    private static void startTimeSeriesSubmodelServer(Submodel timeSeriesSubmodel) {
        BaSyxContext context = new BaSyxContext("/AirSensor", "", "localhost",
                8002);
        SubmodelProvider smOperationsProvider = new SubmodelProvider(timeSeriesSubmodel);
        HttpServlet smOperationsServlet = new VABHTTPInterface<IModelProvider>(smOperationsProvider);
        context.addServletMapping("/TimeSeries/*", smOperationsServlet);
        context.setAccessControlAllowOrigin("*"); // accessible from anywhere
        BaSyxHTTPServer server = new BaSyxHTTPServer(context);
        server.start();
    }

}
