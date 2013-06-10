/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.*;

public class ExperimentDesign implements Serializable {

    SortedSet<String> sampleHeaders = Sets.newTreeSet();

    SortedSet<String> factorHeaders = Sets.newTreeSet();

    Map<String, ExpDesignValues> samples = Maps.newHashMap();

    Map<String, ExpDesignValues> factors = Maps.newHashMap();

    Map<String, String> arrayDesigns = Maps.newHashMap();

    String[] assayHeaders;

    public void putSample(String runOrAssay, String sampleHeader, String sampleValue) {
        if (!samples.containsKey(runOrAssay)) {
            samples.put(runOrAssay, new ExpDesignValues());
        }
        samples.get(runOrAssay).put(sampleHeader, sampleValue);
        sampleHeaders.add(sampleHeader);
    }

    public void putFactor(String runOrAssay, String factorHeader, String factorValue) {
        if (!factors.containsKey(runOrAssay)) {
            factors.put(runOrAssay, new ExpDesignValues());
        }
        factors.get(runOrAssay).put(factorHeader, factorValue);
        factorHeaders.add(factorHeader);
    }

    public void putArrayDesign(String runOrAssay, String arrayDesign) {
        arrayDesigns.put(runOrAssay, arrayDesign);
    }

    public String getArrayDesign(String runOrAssay) {
        return arrayDesigns.get(runOrAssay);
    }

    public void setAssayHeaders(String[] assayHeaders) {
        this.assayHeaders = assayHeaders;
    }

    public String[] getAssayHeaders() {
        return assayHeaders;
    }

    public SortedSet<String> getSampleHeaders() {
        return Collections.unmodifiableSortedSet(sampleHeaders);
    }

    public SortedSet<String> getFactorHeaders() {
        return Collections.unmodifiableSortedSet(factorHeaders);
    }

    public String getSampleValue(String runOrAssay, String sampleHeader) {
        ExpDesignValues expDesignValues = samples.get(runOrAssay);
        if (expDesignValues != null) {
            return expDesignValues.get(sampleHeader);
        }
        return null;
    }

    public String getFactorValue(String runOrAssay, String factorHeader) {
        ExpDesignValues expDesignValues = factors.get(runOrAssay);
        if (expDesignValues != null) {
            return expDesignValues.get(factorHeader);
        }
        return null;
    }

    public SortedSet<String> getAllRunOrAssay() {
        return Sets.newTreeSet(samples.keySet());
    }

    public List<String[]> asTableData() {
        List<String[]> tableData = Lists.newArrayList();
        for (String runOrAssay : getAllRunOrAssay()) {
            tableData.add(composeTableRow(runOrAssay));
        }
        return tableData;
    }

    protected String[] composeTableRow(String runOrAssay) {
        List<String> row = Lists.newArrayList(runOrAssay);

        String arrayDesign = getArrayDesign(runOrAssay);
        if (arrayDesign != null) {
            row.add(arrayDesign);
        }

        for (String sampleHeader : getSampleHeaders()) {
            row.add(getSampleValue(runOrAssay, sampleHeader));
        }

        for (String factorHeader : getFactorHeaders()) {
            row.add(getFactorValue(runOrAssay, factorHeader));
        }

        return row.toArray(new String[row.size()]);
    }

    private class ExpDesignValues extends HashMap<String, String> {

    }

}