package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.ArrayDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MicroarrayExperiment extends DifferentialExperiment {

    private final List<ArrayDesign> arrayDesigns;

    public MicroarrayExperiment(ExperimentType type, String accession, Date lastUpdate,
                                List<Pair<Contrast, Boolean>> contrasts,
                                String description, Species species,
                                ExperimentDesign experimentDesign,
                                Set<String> pubMedIds, List<ArrayDesign> arrayDesigns) {
        super(type, accession, lastUpdate, contrasts, description, species, pubMedIds, experimentDesign);
        this.arrayDesigns = arrayDesigns;
    }

    public List<String> getArrayDesignAccessions() {
        return arrayDesigns.stream().map(a -> a.accession()).collect(Collectors.toList());
    }

    public List<String> getArrayDesignNames() {
        return arrayDesigns.stream().map(a -> a.name()).collect(Collectors.toList());
    }


    @Override
    public HashMap<String, Object> getAttributes() {
        HashMap<String, Object> result = new HashMap<>();
        result.putAll(super.getAttributes());
        result.put("arrayDesignAccessions", getArrayDesignAccessions());
        result.put("arrayDesignNames", getArrayDesignNames());
        return result;
    }

    @Override
    public ExperimentInfo buildExperimentInfo() {
        ExperimentInfo experimentInfo = super.buildExperimentInfo();
        experimentInfo.setArrayDesigns(getArrayDesignAccessions());
        experimentInfo.setArrayDesignNames(getArrayDesignNames());
        return experimentInfo;
    }
}
