package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.*;

public class MicroarrayExperiment extends DifferentialExperiment {

    private SortedSet<String> arrayDesignAccessions;
    private SortedSet<String> arrayDesignNames;

    public MicroarrayExperiment(ExperimentType type, String accession, Date lastUpdate, List<Contrast> contrasts,
                                String description, boolean hasRData, Species species,
                                SortedSet<String> arrayDesignAccessions, SortedSet<String> arrayDesignNames,
                                ExperimentDesign experimentDesign, Set<String> pubMedIds) {

        super(type, accession, lastUpdate, contrasts, description, hasRData, species, pubMedIds, experimentDesign);
        this.arrayDesignAccessions = arrayDesignAccessions;
        this.arrayDesignNames = arrayDesignNames;
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }

    public SortedSet<String> getArrayDesignNames() {return arrayDesignNames;}


    @Override
    public Map<String, Object> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.putAll(super.getAttributes());
        //For showing the QC REPORTS button in the header
        result.put("qcArrayDesigns", getArrayDesignAccessions());
        result.put("allArrayDesigns", getArrayDesignNames());
        return result;
    }

    @Override
    public ExperimentInfo buildExperimentInfo(){
        ExperimentInfo experimentInfo = super.buildExperimentInfo();
        experimentInfo.setArrayDesigns(arrayDesignAccessions);
        experimentInfo.setArrayDesignNames(arrayDesignNames);
        return experimentInfo;
    }
}
