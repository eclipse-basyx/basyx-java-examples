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

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.HashMap;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import org.eclipse.basyx.examples.timeseries.TimeSeriesSubmodel.IQueryContainer;

/*
 * Implements annotations to act as a container for AirSensor data in an influxDB database
 * 
 * author Al-Obaidi
 */
@Measurement(name = "AirSensors")
public class AirSensor implements IQueryContainer {
    @Column(name = "humidity")
    public double humidity;

    @Column(name = "temperature")
    public double temperature;

    @Column(name = "co")
    public double co;

    @Column(timestamp = true)
    public Instant time;

    // get time
    public Instant getTime() {
        return time;
    }

    // gets all _fields as keys and set their values
    public HashMap<String, Object> getFieldsMap() throws IllegalArgumentException, IllegalAccessException {
        HashMap<String, Object> fieldsMap = new HashMap<String, Object>();
        Field[] fList = AirSensor.class.getFields();
        for (Field f : fList) {
            if (!f.getName().equals("time")) {
                fieldsMap.put(f.getName(), f.get(this));
            }
        }
        return fieldsMap;
    }
}
