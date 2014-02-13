package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.*;

public abstract class ConditionsBuilder<T extends Experiment> {

    public abstract Collection buildProperties(T experiment, SetMultimap<String, String> ontologyTerms);

    protected Set<String> collectAssayProperties(ExperimentDesign experimentDesign, String assayAccession, SetMultimap<String, String> ontologyTerms) {

        Map<String, String> factors = experimentDesign.getFactorValues(assayAccession);
        Map<String, String> samples = experimentDesign.getSamples(assayAccession);
        Set<String> terms = ontologyTerms.get(assayAccession);

        Iterable<String> properties = Iterables.concat(factors.values(), samples.values(), terms);

        Set<String> values = new HashSet<>();
        for (String property : properties) {
            if (StringUtils.isNotBlank(property)) {
                values.add(property);
            }
        }
        return values;
    }


}
