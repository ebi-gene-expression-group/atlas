package uk.ac.ebi.atlas.model.baseline;

import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ProteomicsBaselineExperiment extends BaselineExperiment {

    ProteomicsBaselineExperiment(String accession, Date lastUpdate, ExperimentalFactors experimentalFactors, String description, String displayName, Set<String> species, String kingdom, Map<String, String> speciesMapping, boolean hasExtraInfoFile, Set<String> pubMedIds, ExperimentDesign experimentDesign, AssayGroups assayGroups, String dataProviderURL, String dataProviderDescription) {
        super(ExperimentType.PROTEOMICS_BASELINE, accession, lastUpdate, experimentalFactors, description, displayName, species, kingdom, speciesMapping, hasExtraInfoFile, pubMedIds, experimentDesign, assayGroups, dataProviderURL, dataProviderDescription);
    }
}
