package uk.ac.ebi.atlas.web.model.rest;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

public class AssayPropertiesBuilder {

    private ExperimentDesign experimentDesign;

    private AssayGroup assayGroup;

    public AssayPropertiesBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public AssayPropertiesBuilder withAssayGroup(AssayGroup assayGroup) {
        this.assayGroup = assayGroup;
        return this;
    }

    public AssayProperties build() {
        checkState(assayGroup != null && experimentDesign != null);

        AssayProperties assayProperties = new AssayProperties();

        Multimap<String, String> allFactorValues = HashMultimap.create();
        Multimap<String, String> allSampleValues = HashMultimap.create();
        for (String assay : assayGroup) {
            extractAllValues(experimentDesign.getFactors(assay), allFactorValues);
            extractAllValues(experimentDesign.getSamples(assay), allSampleValues);
        }

        addAssayProperties(assayProperties, allFactorValues, ContrastPropertyType.FACTOR);
        addAssayProperties(assayProperties, allSampleValues, ContrastPropertyType.SAMPLE);

        return assayProperties;

    }

    private void addAssayProperties(AssayProperties assayProperties, Multimap<String, String> allValues, ContrastPropertyType contrastPropertyType) {
        for (String name : allValues.keySet()) {

            AssayProperty property = new AssayProperty(name,
                            Joiner.on(",").skipNulls().join(allValues.get(name)),
                            contrastPropertyType);

            assayProperties.add(property);
        }
    }

    private void extractAllValues(Map<String, String> samples, Multimap<String, String> allValues) {
        for (String name : samples.keySet()) {
            allValues.put(name, samples.get(name));
        }

    }
}

