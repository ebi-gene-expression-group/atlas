package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.*;

public abstract class ConditionsBuilder<T extends Experiment> {

    public abstract Collection buildProperties(T experiment);

    protected Set<String> collectAssayProperties(ExperimentDesign experimentDesign, String assayAccession) {

        Map<String, String> factors = experimentDesign.getFactors(assayAccession);

        Map<String, String> properties = new HashMap<>(factors);

        properties.putAll(experimentDesign.getSamples(assayAccession));

        Set<String> values = new HashSet<>();
        for (String name : properties.keySet()) {
            String value = properties.get(name);
            if (StringUtils.isNotBlank(value)) {
                values.add(value);
            }

        }
        return values;
    }


}
