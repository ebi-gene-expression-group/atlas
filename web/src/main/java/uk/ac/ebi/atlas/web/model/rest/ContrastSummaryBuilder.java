package uk.ac.ebi.atlas.web.model.rest;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

public class ContrastSummaryBuilder {

    protected static final String ARRAY_DESIGN = "array design";

    private AssayPropertiesBuilder assayPropertiesBuilder;

    private Contrast contrast;

    private ExperimentDesign experimentDesign;

    private String experimentDescription;

    public ContrastSummaryBuilder(AssayPropertiesBuilder assayPropertiesBuilder) {
        this.assayPropertiesBuilder = assayPropertiesBuilder;
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

        ContrastSummary contrastSummary = new ContrastSummary(experimentDescription, contrast.getDisplayName());

        for (String factorHeader : experimentDesign.getFactorHeaders()) {
            ContrastProperty property = composeContrastProperty(allTestFactorValues, allRefFactorValues, factorHeader, ContrastPropertyType.FACTOR);
            contrastSummary.addContrastProperty(property);
        }

        // array design row should be sorted within samples category
        SortedSet<String> sampleHeaders = Sets.newTreeSet(experimentDesign.getSampleHeaders());
        sampleHeaders.add(ARRAY_DESIGN);
        for (String sampleHeader : sampleHeaders) {
            ContrastProperty property = composeContrastProperty(allTestSampleValues, allRefSampleValues, sampleHeader, ContrastPropertyType.SAMPLE);
            contrastSummary.addContrastProperty(property);
        }


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
