/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.commons.berkeley;

import com.sleepycat.collections.TransactionWorker;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

public abstract class ObjectValueTransactionWorker<V> implements TransactionWorker {

    // changed to private by Sonar recommendation
    private String[] row;

    private ConcurrentMap<String, V> map;

    protected ObjectValueTransactionWorker(ConcurrentMap<String, V> map) {
        this.map = map;
    }

    public ObjectValueTransactionWorker setRow(String[] row) {
        // this copy has been suggested by Sonar
        this.row = Arrays.copyOf(row, row.length);
        return this;
    }

    protected String[] getRow() {
        return row;
    }

    @Override
    public void doWork() {
        try {
            map.put(getKey(), getValue());
        } catch (Exception e) {
            throw new IllegalStateException("Error while writing gene annotations DB: " + e.getMessage());
        }
    }

    protected abstract V getValue();

    protected abstract String getKey();
}