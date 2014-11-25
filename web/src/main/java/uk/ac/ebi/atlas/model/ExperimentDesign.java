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

import com.google.common.collect.*;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *  ExperimentDesign stores factors and characteristics per _assay_ and other information
 *  needed to render the experiment design page. On experiment import, it is created from
 *  the magetab sdrf and persisted into ExpDesign files. The ExpDesign files act as a cache of
 *  relevant information in the sdrf, because parsing the sdrf is an expensive operation.
 *
 *  ExperimentalFactors also has factor information, but per _assay group_.
 *  ExperimentalFactors is used to render the experiment page.
 *
 *  An important distinction note: factor headers are NOT the same as factor types.
 *  Factor headers are not normalized (see Factor.normalize), where as factor types (ie: Factor.getType()) are.
 *
 */
public class ExperimentDesign implements Serializable {

    private SortedSet<String> sampleHeaders = Sets.newTreeSet();

    private SortedSet<String> factorHeaders = Sets.newTreeSet();

    // assay, SampleCharacteristics
    private Map<String, SampleCharacteristics> samples = Maps.newHashMap();

    // header, value
    private class SampleCharacteristics extends HashMap<String, SampleCharacteristic> { }

    // assay, factors
    private Map<String, FactorSet> factorSetMap = Maps.newHashMap();

    private Map<String, String> arrayDesigns = Maps.newHashMap();

    private List<String> assayHeaders = Lists.newArrayList();

    public void putSampleCharacteristic(String runOrAssay, String sampleCharacteristicHeader, String sampleCharacteristicValue) {
        SampleCharacteristic sampleCharacteristic = SampleCharacteristic.create(sampleCharacteristicHeader, sampleCharacteristicValue);
        putSampleCharacteristic(runOrAssay, sampleCharacteristicHeader, sampleCharacteristic);
    }

    public void putSampleCharacteristic(String runOrAssay, String sampleHeader, SampleCharacteristic sampleCharacteristic) {
        if (!samples.containsKey(runOrAssay)) {
            samples.put(runOrAssay, new SampleCharacteristics());
        }
        samples.get(runOrAssay).put(sampleHeader, sampleCharacteristic);
        sampleHeaders.add(sampleHeader);
    }

    public void putFactor(String runOrAssay, String factorHeader, String factorValue) {
        putFactor(runOrAssay, factorHeader, factorValue, new OntologyTerm[0]);
    }

    public void putFactor(String runOrAssay, String factorHeader, String factorValue, OntologyTerm ... factorOntologyTerms) {
        Factor factor = new Factor(factorHeader, factorValue, factorOntologyTerms);
        if(!factorSetMap.containsKey(runOrAssay)){
            factorSetMap.put(runOrAssay, new FactorSet());
        }
        factorSetMap.get(runOrAssay).add(factor);
        factorHeaders.add(factorHeader);
    }

    public void putArrayDesign(String runOrAssay, String arrayDesign) {
        arrayDesigns.put(runOrAssay, arrayDesign);
    }

    public String getArrayDesign(String runOrAssay) {
        return arrayDesigns.get(runOrAssay);
    }

    public void addAssayHeader(String assayHeader) {
        assayHeaders.add(assayHeader);
    }

    public List<String> getAssayHeaders() {
        return assayHeaders;
    }

    public SortedSet<String> getSampleHeaders() {
        return Collections.unmodifiableSortedSet(sampleHeaders);
    }

    //NB: factor headers are not normalized (see Factor.normalize), unlike factor type !
    public SortedSet<String> getFactorHeaders() {
        return Collections.unmodifiableSortedSet(factorHeaders);
    }

    public @Nullable String getSampleCharacteristicValue(String runOrAssay, String sampleHeader) {
        SampleCharacteristic sampleCharacteristic = getSampleCharacteristic(runOrAssay, sampleHeader);
        return sampleCharacteristic == null ? null : sampleCharacteristic.value();
    }

    public @Nullable SampleCharacteristic getSampleCharacteristic(String runOrAssay, String sampleHeader) {
        SampleCharacteristics sampleCharacteristics = this.samples.get(runOrAssay);
        return (sampleCharacteristics == null) ? null :  sampleCharacteristics.get(sampleHeader);
    }

    public @Nullable Factor getFactor(String runOrAssay, String factorHeader) {
        FactorSet factorSet = factorSetMap.get(runOrAssay);
        if (factorSet == null) {
            return null;
        }
        return factorSet.getFactorByType(Factor.normalize(factorHeader));
    }

    public @Nullable String getFactorValue(String runOrAssay, String factorHeader) {
        FactorSet factorSet = factorSetMap.get(runOrAssay);
        if (factorSet != null) {

            Factor factor = factorSet.getFactorByType(Factor.normalize(factorHeader));
            return factor == null ? null : factor.getValue();
        }
        return null;
    }


    public ImmutableSetMultimap<String, String> getAllOntologyTermIdsByAssayAccession() {
        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        addFactorOntologyTerms(builder);
        addCharacteristicOntologyTerms(builder);

        return builder.build();
    }

    private void addCharacteristicOntologyTerms(ImmutableSetMultimap.Builder<String, String> builder) {
        for (Map.Entry<String, SampleCharacteristics> sampleEntry : samples.entrySet()) {
            String runOrAssay = sampleEntry.getKey();
            SampleCharacteristics sampleCharacteristics = sampleEntry.getValue();

            for (SampleCharacteristic sampleCharacteristic : sampleCharacteristics.values()) {
                Set<OntologyTerm> valueOntologyTerms = sampleCharacteristic.valueOntologyTerms();
                if (!valueOntologyTerms.isEmpty()) {
                    builder.put(runOrAssay, OntologyTermUtils.joinIds(valueOntologyTerms));
                }
            }

        }
    }

    private void addFactorOntologyTerms(ImmutableSetMultimap.Builder<String, String> builder) {
        for (Map.Entry<String, FactorSet> factorSetEntry : factorSetMap.entrySet()) {
            String runOrAssay = factorSetEntry.getKey();
            FactorSet factorSet = factorSetEntry.getValue();

            for (Factor factor : factorSet) {
                Set<OntologyTerm> valueOntologyTerms = factor.getValueOntologyTerms();
                if (!valueOntologyTerms.isEmpty()) {
                    builder.put(runOrAssay, OntologyTermUtils.joinIds(valueOntologyTerms));
                }
            }
        }
    }

    /**
     *
     * @param runOrAssay run or assay id
     * @return  map of {factorHeader, factorValue}
     */
    public Map<String, String> getFactorValues(String runOrAssay) {
        Map<String, String> valueByHeader = Maps.newHashMap();
        FactorSet factorSet = factorSetMap.get(runOrAssay);

        if (factorSet == null){
            return null;
        }
        for (Factor factor : factorSet){
            valueByHeader.put(factor.getHeader(), factor.getValue());
        }

        return valueByHeader;
    }

    public FactorSet getFactors(String runOrAssay){
        if(factorSetMap.containsKey(runOrAssay)){
            return factorSetMap.get(runOrAssay);
        }
        return null;
    }

    public Collection<SampleCharacteristic> getSampleCharacteristics(String runOrAssay) {
        return this.samples.get(runOrAssay).values();
    }


    // returns header, value
    public Map<String, String> getSampleCharacteristicsValues(String runOrAssay) {
        SampleCharacteristics sampleCharacteristics = this.samples.get(runOrAssay);

        checkNotNull(sampleCharacteristics, "No sample characteristics for run or assay " + runOrAssay + ". Check configuration.xml matches ExpDesign/SDRF.");

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        for (Map.Entry<String, SampleCharacteristic> sampleCharacteristic : sampleCharacteristics.entrySet()) {
            String header = sampleCharacteristic.getKey();
            String value = sampleCharacteristic.getValue().value();
            builder.put(header, value);
        }

        return builder.build();
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
            row.add(getSampleCharacteristicValue(runOrAssay, sampleHeader));
        }

        for (String factorHeader : getFactorHeaders()) {
            row.add(getFactorValue(runOrAssay, factorHeader));
        }

        return row.toArray(new String[row.size()]);
    }

    public Set<String> getSpeciesForAssays(Set<String> assayAccessions) {
        Set<String> species = Sets.newHashSet();
        for (String assayAccession: assayAccessions){
            Map<String, String> assaySamples = getSampleCharacteristicsValues(assayAccession);

            checkNotNull(assaySamples, String.format("Assay accession %s does not exist or has no samples", assayAccession));

            for (String sampleName : assaySamples.keySet()){
                if ("organism".equalsIgnoreCase(sampleName)){
                    species.add(assaySamples.get(sampleName));
                }
            }
        }
        return species;
    }

}