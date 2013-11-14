package uk.ac.ebi.atlas.dto.tooltip;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("request")
public class AssayGroupSummaryBuilder {

    private ExperimentDesign experimentDesign;

    private AssayGroup assayGroup;

    public AssayGroupSummaryBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public AssayGroupSummaryBuilder forAssayGroup(AssayGroup assayGroup) {
        this.assayGroup = assayGroup;
        return this;
    }

    public AssayGroupSummary build() {
        checkState(assayGroup != null && experimentDesign != null);

        AssayGroupSummary assayGroupSummary = new AssayGroupSummary();

        Multimap<String, String> allFactorValues = HashMultimap.create();
        Multimap<String, String> allSampleValues = HashMultimap.create();
        for (String assay : assayGroup) {
            extractAllValues(experimentDesign.getFactors(assay), allFactorValues);
            extractAllValues(experimentDesign.getSamples(assay), allSampleValues);
        }

        addAssayProperties(assayGroupSummary, allFactorValues, ContrastPropertyType.FACTOR);
        addAssayProperties(assayGroupSummary, allSampleValues, ContrastPropertyType.SAMPLE);

        return assayGroupSummary;

    }

    protected void addAssayProperties(AssayGroupSummary assayGroupSummary, Multimap<String, String> allValues, ContrastPropertyType contrastPropertyType) {
        for (String name : allValues.keySet()) {

            AssayProperty property = new AssayProperty(name,
                    Joiner.on(",").skipNulls().join(allValues.get(name)),
                    contrastPropertyType);

            assayGroupSummary.add(property);
        }
    }

    protected void extractAllValues(Map<String, String> samples, Multimap<String, String> allValues) {
        for (String name : samples.keySet()) {
            allValues.put(name, samples.get(name));
        }

    }
}

