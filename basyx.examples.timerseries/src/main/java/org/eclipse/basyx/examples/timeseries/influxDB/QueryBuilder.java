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

package org.eclipse.basyx.examples.timeseries.influxDB;

/*
 * Builds an influxDB query
 * and provides methods for adding filters, and pivots
 * author Al-Obaidi
 */
public class QueryBuilder {
    private String query;

    public QueryBuilder(String dbName) {
        this.query = "from(bucket: \"" + dbName + "\")";
    }

    public void timeFilter(String startTime, String endTime) {
        this.query = this.query + "|> range(start: " + startTime + ", stop: " + endTime + ")";
    }

    public void addFilter(String filter) {
        String filterFunction = " |> filter(" + filter + ") ";
        this.query = this.query + filterFunction;
    }

    public void addFilters(String filter) {
        this.query = this.query + filter;
    }

    public void addPivot(String filter) {
        String filterFunction = " |> pivot(" + filter + ") ";
        this.query = this.query + filterFunction;
    }

    public String buildQuery() {
        return this.toString();
    }

    @Override
    public String toString() {
        return this.query;
    }
}
