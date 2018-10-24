package uk.ac.ebi.atlas.model.experiment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

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

    // Headers retrieved from the condensed sdrf file
    private Set<String> sampleHeaders = new LinkedHashSet<>();
    private Set<String> factorHeaders = new LinkedHashSet<>();

    // Headers retrieved from the sdrf file, which maintain a curated order
    private Set<String> orderedSampleHeaders;
    private Set<String> orderedFactorHeaders;

    // assay, SampleCharacteristics
    private Map<String, SampleCharacteristics> samples = new HashMap<>();

    // header, value
    private class SampleCharacteristics extends HashMap<String, SampleCharacteristic> { }

    // assay, factors
    private Map<String, FactorSet> factorSetMap = new HashMap<>();
    private Map<String, String> arrayDesigns = new HashMap<>();
    private List<String> assayHeaders = new ArrayList<>();

    public void putSampleCharacteristic(String runOrAssay,
                                        String sampleCharacteristicHeader,
                                        String sampleCharacteristicValue) {
        SampleCharacteristic sampleCharacteristic =
                SampleCharacteristic.create(sampleCharacteristicHeader, sampleCharacteristicValue);
        putSampleCharacteristic(runOrAssay, sampleCharacteristicHeader, sampleCharacteristic);
    }

    public void putSampleCharacteristic(String runOrAssay,
                                        String sampleHeader,
                                        SampleCharacteristic sampleCharacteristic) {
        if (!samples.containsKey(runOrAssay)) {
            samples.put(runOrAssay, new SampleCharacteristics());
        }
        samples.get(runOrAssay).put(sampleHeader, sampleCharacteristic);
        sampleHeaders.add(sampleHeader);
    }

    public void putFactor(String runOrAssay, String factorHeader, String factorValue) {
        putFactor(runOrAssay, factorHeader, factorValue, new OntologyTerm[0]);
    }

    public void putFactor(String runOrAssay,
                          String factorHeader,
                          String factorValue,
                          OntologyTerm... factorOntologyTerms) {
        Factor factor = new Factor(factorHeader, factorValue, factorOntologyTerms);
        if (!factorSetMap.containsKey(runOrAssay)) {
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

    public void setOrderedSampleHeaders(Set<String> orderedSampleHeaders) {
        this.orderedSampleHeaders = orderedSampleHeaders;
    }

    public void setOrderedFactorHeaders(Set<String> orderedFactorHeaders) {
        this.orderedFactorHeaders = orderedFactorHeaders;
    }

    public List<String> getAssayHeaders() {
        return assayHeaders;
    }

    public Set<String> getSampleHeaders() {
        if (!CollectionUtils.isEmpty(orderedSampleHeaders)) {
            return Collections.unmodifiableSet(orderedSampleHeaders);
        } else {
            return Collections.unmodifiableSet(sampleHeaders);
        }
    }


    //NB: factor headers are not normalized (see Factor.normalize), unlike factor type !
    public Set<String> getFactorHeaders() {
        if (!CollectionUtils.isEmpty(orderedFactorHeaders)) {
            return Collections.unmodifiableSet(orderedFactorHeaders);
        } else {
            return Collections.unmodifiableSet(factorHeaders);
        }
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

        getFactorOntologyTerms().forEach(builder::putAll);
        getCharacteristicOntologyTerms().forEach(builder::putAll);

        return builder.build();
    }

    private Map<String, Set<String>> getFactorOntologyTerms() {
        return factorSetMap.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry ->
                                stream(entry.getValue().spliterator(), false)
                                        .flatMap(factor ->
                                                factor.getValueOntologyTerms().stream())
                                        .map(OntologyTerm::accession)
                                        .collect(toSet())));
    }

    private Map<String, Set<String>> getCharacteristicOntologyTerms() {
        return samples.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry ->
                                entry.getValue().values().stream()
                                        .flatMap(sampleCharacteristic ->
                                                sampleCharacteristic.valueOntologyTerms().stream())
                                        .map(OntologyTerm::accession)
                                        .collect(toSet())));
    }

    /**
     *
     * @param runOrAssay run or assay id
     * @return  map of {factorHeader, factorValue}
     */
    public Map<String, String> getFactorValues(String runOrAssay) {
        FactorSet factorSet = factorSetMap.get(runOrAssay);

        if (factorSet == null) {
            return ImmutableMap.of();
        }
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (Factor factor : factorSet) {
            builder.put(factor.getHeader(), factor.getValue());
        }

        return builder.build();
    }

    public FactorSet getFactors(String runOrAssay) {
        if (factorSetMap.containsKey(runOrAssay)) {
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
        return samples.getOrDefault(runOrAssay, new SampleCharacteristics()).entrySet().stream()
                .collect(toImmutableMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().value()));
    }

    public SortedSet<String> getAllRunOrAssay() {
        return Sets.newTreeSet(samples.keySet());
    }

    public String getSpeciesForAssays(Set<String> assayAccessions) {
        for (String assayAccession: assayAccessions) {
            Map<String, String> assaySamples = getSampleCharacteristicsValues(assayAccession);

            for (String sampleName : assaySamples.keySet()) {
                if ("organism".equalsIgnoreCase(sampleName)) {
                    return assaySamples.get(sampleName);
                }
            }
        }

        return "";
    }
}
