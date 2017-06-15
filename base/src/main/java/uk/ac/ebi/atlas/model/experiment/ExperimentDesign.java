package uk.ac.ebi.atlas.model.experiment;

import com.google.common.collect.*;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

/**
 *  ExperimentDesign stores factors and characteristics per _assay_ and other information
 *  needed to render the experiment design page. On experiment import, it is created from
 *  the condensedSdrf sdrf and persisted into ExpDesign files. The ExpDesign files act as a cache of
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
        return factorSet.factorOfType(Factor.normalize(factorHeader));
    }


    public @Nullable String getFactorValue(String runOrAssay, String factorHeader) {
        FactorSet factorSet = factorSetMap.get(runOrAssay);
        if (factorSet != null) {

            Factor factor = factorSet.factorOfType(Factor.normalize(factorHeader));
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
                for (OntologyTerm valueOntologyTerm : sampleCharacteristic.valueOntologyTerms()) {
                    builder.put(runOrAssay, valueOntologyTerm.accession());
                }
            }

        }
    }


    private void addFactorOntologyTerms(ImmutableSetMultimap.Builder<String, String> builder) {
        for (Map.Entry<String, FactorSet> factorSetEntry : factorSetMap.entrySet()) {
            String runOrAssay = factorSetEntry.getKey();
            FactorSet factorSet = factorSetEntry.getValue();

            for (Factor factor : factorSet) {
                for (OntologyTerm valueOntologyTerm : factor.getValueOntologyTerms()) {
                    builder.put(runOrAssay, valueOntologyTerm.accession());
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
        FactorSet factorSet = factorSetMap.get(runOrAssay);

        if (factorSet == null){
            return ImmutableMap.of();
        }
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (Factor factor : factorSet){
            builder.put(factor.getHeader(), factor.getValue());
        }

        return builder.build();
    }

    public FactorSet getFactors(String runOrAssay){
        if(factorSetMap.containsKey(runOrAssay)){
            return factorSetMap.get(runOrAssay);
        }
        return null;
    }


    public Collection<SampleCharacteristic> getSampleCharacteristics(String runOrAssay) {
        SampleCharacteristics sampleCharacteristics = this.samples.get(runOrAssay);
        return (sampleCharacteristics == null ? new SampleCharacteristics() : sampleCharacteristics).values();
    }


    // returns header, value
    public Map<String, String> getSampleCharacteristicsValues(String runOrAssay) {
        SampleCharacteristics sampleCharacteristics = this.samples.get(runOrAssay);
        if(sampleCharacteristics == null){
            return ImmutableMap.of();
        }

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        for (Map.Entry<String, SampleCharacteristic> sampleCharacteristic : sampleCharacteristics.entrySet()) {

            builder.put(sampleCharacteristic.getKey(), sampleCharacteristic.getValue().value());
        }

        return builder.build();
    }


    public SortedSet<String> getAllRunOrAssay() {
        return Sets.newTreeSet(samples.keySet());
    }


    public String getSpeciesForAssays(Set<String> assayAccessions) {
        for (String assayAccession: assayAccessions) {
            Map<String, String> assaySamples = getSampleCharacteristicsValues(assayAccession);

            for (String sampleName : assaySamples.keySet()) {
                if ("organism".equalsIgnoreCase(sampleName)){
                    return assaySamples.get(sampleName);
                }
            }
        }

        return "";
    }

}
