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

package org.eclipse.basyx.examples.timeseries.TimeSeriesSubmodel;

import java.util.function.Function;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

import com.influxdb.annotations.Measurement;

import org.eclipse.basyx.examples.timeseries.AASXImporter;

/*
 * Creates ReadRecords operation for a TimeSeries Submodel that follows the IDTA standard
 * (https://github.com/admin-shell-io/submodel-templates). The operation takes a Range SubmodelElement as input, which
 * is timerange in ISO UTC format. The output is a SubmodelElementCollection consisting of records
 * (https://admin-shell.io/idta/TimeSeries/Record/1/1) as found under TimeSeries->Metadata->Record.
 * Creates another experimental operation ReadRecordsShort which has a similar input as ReadRecords, but
 * returns the query results as a string in a value of a property (reduces overhead).
 * 
 * author Al-Obaidi
 */
public class TimeSeriesSubmodel {

    private static final String TIMESERIES_SUBMODEL_IDSHORT = "TimeSeries";
    private static final String METADATA_SME_IDSHORT = "Metadata";
    private static final String RECORD_SME_IDSHORT = "Record";
    private static final String NAME_SME_IDSHORT = "Name";
    private static final String READRECORDS_OPERATION_IDSHORT = "ReadRecords";
    private static final String READRECORDSSHORT_OPERATION_IDSHORT = "ReadRecordsShort";

    public enum Database {
        influxDB,
    }

    private ISubmodel timeSeriesSm;
    private Database database;
    private java.lang.Class<IQueryContainer> queryContainer;

    public TimeSeriesSubmodel(Database db, String pathToAas, String AasIdShort)
            throws Exception {
        AASBundle submodelSpecAAS = AASXImporter.getAasFromAasx(pathToAas,
                AasIdShort);
        for (ISubmodel sms : submodelSpecAAS.getSubmodels()) {
            if (sms.getIdShort().equals(TIMESERIES_SUBMODEL_IDSHORT)) {
                timeSeriesSm = sms;
            }
        }

        database = db;
    }

    @SuppressWarnings("unchecked")
    private <T extends IQueryContainer> void setAnnotatedClass(java.lang.Class<T> c) throws Exception {
        if (!c.isAnnotationPresent(Measurement.class)) {
            throw new Exception("Annotated on class not valid for influxDB query");
        }
        System.out.println("Annotation is present on " + c.getName());
        queryContainer = (Class<IQueryContainer>) c;
    }

    public <T extends IQueryContainer> Submodel instantiateTimeSeriesSubmodel(java.lang.Class<T> c) throws Exception {
        switch (database) {
            case influxDB:
                setAnnotatedClass(c);
                return instantiateInfluxDBTimeSeriesSubmodel();
            default:
                throw new Exception("Query operations not implemented for " + database.toString());
        }
    }

    private Submodel instantiateInfluxDBTimeSeriesSubmodel() throws Exception {
        if (!queryContainer.isAnnotationPresent(Measurement.class)) {
            throw new Exception(
                    "You have to use setAnnotatedClass method to provide a class annotated with com.influxdb.annotations.Measurement");
        }
        // Get the records of the timeseries
        SubmodelElementCollection recordMetadata = (SubmodelElementCollection) ((SubmodelElementCollection) timeSeriesSm
                .getSubmodelElement(METADATA_SME_IDSHORT)).getSubmodelElement(RECORD_SME_IDSHORT);
        // Name of segment should be the same as the measurement in influxdb
        String recordName = ((MultiLanguageProperty) ((SubmodelElementCollection) timeSeriesSm
                .getSubmodelElement(METADATA_SME_IDSHORT)).getSubmodelElement(NAME_SME_IDSHORT)).getValue().get("en");

        // Expects range of time in ISO UTC, ex: '2011-12-03T10:15:30Z'
        InfluxDBInvokables dbInvokables = new InfluxDBInvokables(recordName,
                recordMetadata,
                queryContainer);
        Function<Object[], Object> readRecordsInvokable = dbInvokables.setupReadRecordsInvokable();
        ((Operation) timeSeriesSm.getOperations().get(READRECORDS_OPERATION_IDSHORT))
                .setInvokable(readRecordsInvokable);

        // Expects range of time in ISO UTC, ex: '2011-12-03T10:15:30Z'
        Function<Object[], Object> readRecordsShortInvokable = dbInvokables.setupReadRecordsShortInvokable();
        ((Operation) timeSeriesSm.getOperations().get(READRECORDSSHORT_OPERATION_IDSHORT))
                .setInvokable(readRecordsShortInvokable);
        return (Submodel) timeSeriesSm;
    }

}
