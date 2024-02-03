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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import org.eclipse.basyx.examples.timeseries.influxDB.QueryBuilder;
import org.eclipse.basyx.examples.timeseries.influxDB.InfluxDBConnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Creates invokables for TimeSeries query operation
 * 
 * author Al-Obaidi
 */

public class InfluxDBInvokables {
    static final String INFLUXDB_TOKEN = "d28e8bebb73e59c0cd88e8a1ac7855aa";
    static final String INFLUXDB_BUCKET = "airsensors";
    static final String INFLUXDB_ORG = "basyx";
    static final String INFLUXDB_URL = "http://influxdb:8086";
    static final String PIVOT_RULE = "rowKey: [\"_time\"], columnKey: [\"_field\"],valueColumn: \"_value\"";
    static final String FILTER_RULE = "fn: (r) => r._measurement == \"%s\"";

    private InfluxDBConnection inConn;
    private String recordName;
    private SubmodelElementCollection recordMetadata;
    private java.lang.Class<IQueryContainer> queryContainer;

    public InfluxDBInvokables(String recordName,
            SubmodelElementCollection recordMetadata, java.lang.Class<IQueryContainer> queryContainer) {
        this.recordName = recordName;
        this.recordMetadata = recordMetadata;
        this.queryContainer = queryContainer;
        inConn = new InfluxDBConnection(INFLUXDB_URL,
                INFLUXDB_TOKEN, INFLUXDB_TOKEN, INFLUXDB_ORG);
    }

    public Function<Object[], Object> setupReadRecordsInvokable() {
        Function<Object[], Object> readRecordsInvokable = (timeSpan) -> {
            List<IQueryContainer> dataContainers = queryData(timeSpan);
            try {
                return influxDbQueryToRecordsSMC(dataContainers);
            } catch (IllegalArgumentException | IllegalAccessException | JsonProcessingException e) {
                e.printStackTrace();
            }
            return new SubmodelElementCollection();
        };
        return readRecordsInvokable;
    }

    public Function<Object[], Object> setupReadRecordsShortInvokable() {
        Function<Object[], Object> readRecordsInvokable = (timeSpan) -> {
            List<IQueryContainer> dataContainers = queryData(timeSpan);
            try {
                return influxDbQueryToRecordsProperty(dataContainers);
            } catch (IllegalArgumentException | IllegalAccessException | JsonProcessingException e) {
                e.printStackTrace();
            }
            return "";
        };
        return readRecordsInvokable;
    }

    private List<IQueryContainer> queryData(Object[] timeSpan) {
        // temporary fix... api gives us a string in the format { min = ###, max = ###}
        // can't parse to RangeValue
        String[] timeSpanAsArray = timeSpan[0].toString().split(",", 0);
        String from = timeSpanAsArray[0].split("=")[1].strip();
        String to = timeSpanAsArray[1].replace("}", "").split("=")[1].strip();

        // This doesn't seem to work
        // RangeValue timeRangeValue = RangeValue.createAsFacade(timeSpan)
        // timeSpan[0]);
        // String from = timeRangeValue.getMin().toString();
        // String to = timeRangeValue.getMax().toString();

        QueryBuilder fluxQueryBuilder = new QueryBuilder(INFLUXDB_BUCKET);
        fluxQueryBuilder.timeFilter(from,
                to);
        fluxQueryBuilder.addFilter(String.format(FILTER_RULE, recordName));
        // get all fields of the measurement
        fluxQueryBuilder.addPivot(PIVOT_RULE);
        return inConn.queryData(fluxQueryBuilder.buildQuery(),
                queryContainer);
    }

    private Map<String, ISubmodelElement> influxDbQueryToRecordsSMC(List<IQueryContainer> dataContainers)
            throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
        SubmodelElementCollection outputRecords = new SubmodelElementCollection("Records");
        long c = 0;
        for (IQueryContainer dataContainer : dataContainers) {
            String recordName = "rec" + Long.toString(c);
            outputRecords.addSubmodelElement(createDataRecordSMC(dataContainer, recordName));
            c++;
        }
        return outputRecords.getSubmodelElements();
    }

    private String influxDbQueryToRecordsProperty(List<IQueryContainer> dataContainers)
            throws IllegalArgumentException, JsonProcessingException, IllegalAccessException {
        List<Map<String, Object>> queryResult = new ArrayList<>();
        for (IQueryContainer dataContainer : dataContainers) {
            Map<String, Object> dataContainerMap = dataContainer.getFieldsMap();
            dataContainerMap.put("time", dataContainer.getTime().toString());
            queryResult.add(dataContainerMap);
        }
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.writeValueAsString(queryResult);
    }

    private SubmodelElementCollection createDataRecordSMC(IQueryContainer dataContainer, String recordName)
            throws IllegalAccessException {
        SubmodelElementCollection record = new SubmodelElementCollection();
        Property time = (Property) recordMetadata.getSubmodelElement("Time").getLocalCopy();
        time.setKind(ModelingKind.INSTANCE);
        time.setValue(dataContainer.getTime().toString());
        record.addSubmodelElement(time);

        // time is not included in this map
        HashMap<String, Object> dataContainerFields = dataContainer.getFieldsMap();

        recordMetadata.getSubmodelElements().forEach((name, variable) -> {
            if (!name.equals("Time")) {
                Property element = (Property) variable.getLocalCopy();
                element.setValue(dataContainerFields.get(name));
                record.addSubmodelElement(element);
            }
        });
        return record;
    }
}
