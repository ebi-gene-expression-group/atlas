package uk.ac.ebi.atlas.dto.tooltip;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Named;
import java.util.*;

@Named
@Scope("request")
public class ContrastSummaryBuilder {

    protected static final String ARRAY_DESIGN = "array design";

    private Contrast contrast;

    private ExperimentDesign experimentDesign;

    private String experimentDescription;

    private Set<AssayProperty> properties = new HashSet<>();


    public ContrastSummaryBuilder forContrast(Contrast contrast) {
        this.contrast = contrast;
        return this;
    }

    public ContrastSummaryBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public ContrastSummaryBuilder withExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
        return this;
    }
    public ContrastSummary build() {

        Multimap<String, String> allRefFactorValues = HashMultimap.create();
        Multimap<String, String> allRefSampleValues = HashMultimap.create();
        for (String assay : contrast.getReferenceAssayGroup()) {
            extractAllValues(experimentDesign.getFactors(assay), allRefFactorValues);
            extractAllValues(experimentDesign.getSamples(assay), allRefSampleValues);
            allRefSampleValues.put(ARRAY_DESIGN, experimentDesign.getArrayDesign(assay));
        }


        Multimap<String, String> allTestFactorValues = HashMultimap.create();
        Multimap<String, String> allTestSampleValues = HashMultimap.create();
        for (String assay : contrast.getTestAssayGroup()) {
            extractAllValues(experimentDesign.getFactors(assay), allTestFactorValues);
            extractAllValues(experimentDesign.getSamples(assay), allTestSampleValues);
            allTestSampleValues.put(ARRAY_DESIGN, experimentDesign.getArrayDesign(assay));

        }


        for (String factorHeader : experimentDesign.getFactorHeaders()) {
            ContrastProperty property = composeContrastProperty(allTestFactorValues, allRefFactorValues, factorHeader, ContrastPropertyType.FACTOR);
            properties.add(property);
        }

        // array design row should be sorted within samples category
        SortedSet<String> sampleHeaders = Sets.newTreeSet(experimentDesign.getSampleHeaders());
        sampleHeaders.add(ARRAY_DESIGN);
        for (String sampleHeader : sampleHeaders) {
            ContrastProperty property = composeContrastProperty(allTestSampleValues, allRefSampleValues, sampleHeader, ContrastPropertyType.SAMPLE);
            properties.add(property);
        }

        ContrastSummary contrastSummary = new ContrastSummary(experimentDescription, contrast.getDisplayName(), Sets.newTreeSet(properties));

        return contrastSummary;
    }

    private void extractAllValues(Map<String, String> samples, Multimap<String, String> allValues) {
        for (String name : samples.keySet()) {
            allValues.put(name, samples.get(name));
        }

    }

    private ContrastProperty composeContrastProperty(Multimap<String, String> allTestValues, Multimap<String, String> allReferenceValues,
                                                     String header, ContrastPropertyType contrastPropertyType) {
        Collection<String> testValues = allTestValues.get(header);
        Collection<String> referenceValues = allReferenceValues.get(header);
        ContrastProperty property = new ContrastProperty(header,
                Joiner.on(",").skipNulls().join(testValues),
                Joiner.on(",").skipNulls().join(referenceValues),
                contrastPropertyType);
        return property;
    }
}
