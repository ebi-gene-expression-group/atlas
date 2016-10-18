package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class AssayGroupSummaryBuilder {

    private ExperimentDesign experimentDesign;

    private AssayGroup assayGroup;

    private Set<AssayProperty> properties = new HashSet<>();

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


        Multimap<String, String> allFactorValues = HashMultimap.create();
        Multimap<String, String> allSampleValues = HashMultimap.create();
        for (String assay : assayGroup) {
            extractAllValues(experimentDesign.getFactorValues(assay), allFactorValues);
            extractAllValues(experimentDesign.getSampleCharacteristicsValues(assay), allSampleValues);
        }

        addAssayProperties(allFactorValues, ContrastPropertyType.FACTOR);
        addAssayProperties(allSampleValues, ContrastPropertyType.SAMPLE);

        return new AssayGroupSummary(assayGroup.getReplicates(), Sets.newTreeSet(properties));

    }

    protected void addAssayProperties( Multimap<String, String> allValues, ContrastPropertyType contrastPropertyType) {
        for (String name : allValues.keySet()) {

            AssayProperty property = new AssayProperty(name,
                    Joiner.on(",").skipNulls().join(allValues.get(name)),
                    contrastPropertyType);

            properties.add(property);
        }
    }

    protected void extractAllValues(Map<String, String> samples, Multimap<String, String> allValues) {
        for (String name : samples.keySet()) {
            allValues.put(name, samples.get(name));
        }

    }
}

