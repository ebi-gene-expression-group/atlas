package uk.ac.ebi.atlas.model.experiment.baseline;

import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class BaselineExperiment extends Experiment {

    private ExperimentalFactors experimentalFactors;
    private AssayGroups assayGroups;

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate,
                       ExperimentalFactors experimentalFactors, String description, String displayName,
                       String disclaimer, String species, boolean hasRData, Collection<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups, List<String> dataProviderURL,
                       List<String> dataProviderDescription, List<String> alternativeViews,
                       List<String> alternativeViewDescriptions) {

        super(experimentType, accession, lastUpdate, displayName, description, disclaimer, hasRData, species,
              pubMedIds, experimentDesign, dataProviderURL, dataProviderDescription,
              alternativeViews, alternativeViewDescriptions);

        this.experimentalFactors = experimentalFactors;
        this.assayGroups = assayGroups;
    }

    public Set<String> getExperimentRunAccessions() {
        return assayGroups.getAssayAccessions();
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public AssayGroups getAssayGroups() {
        return assayGroups;
    }

    public SortedSet<Factor> getAssayGroupFactors(Collection<String> assayGroupIds, String factorType) {
        return getExperimentalFactors().getFactors(assayGroupIds, factorType);
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions() {
        return getExperimentRunAccessions();
    }

    @Override
    public ExperimentInfo getExperimentInfo(){
        ExperimentInfo experimentInfo = super.getExperimentInfo();
        experimentInfo.setNumberOfAssays(getExperimentRunAccessions().size());
        return experimentInfo;
    }
}
