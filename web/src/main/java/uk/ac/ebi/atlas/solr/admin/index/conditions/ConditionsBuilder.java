package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ConditionsBuilder {

    private static final String ERROR_MESSAGE_TEMPLATE = "No %s found for assay accession '%s'. Check assays defined in configuration.xml match Assay Name/Scan Name in the SDRF.";

    public abstract Collection buildProperties(Experiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession);

    protected Set<String> collectAssayProperties(ExperimentDesign experimentDesign, String assayAccession, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Map<String, String> factors = experimentDesign.getFactorValues(assayAccession);
        Map<String, String> samples = experimentDesign.getSampleCharacteristicsValues(assayAccession);
        Set<String> terms = ontologyTermIdsByAssayAccession.get(assayAccession);

        checkNotNull(factors, ERROR_MESSAGE_TEMPLATE, "factors", assayAccession);
        checkNotNull(samples, ERROR_MESSAGE_TEMPLATE, "samples", assayAccession);

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
