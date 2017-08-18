package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
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
import java.util.SortedSet;


public class MicroarrayExperiment extends DifferentialExperiment {

    private SortedSet<String> arrayDesignAccessions;
    private SortedSet<String> arrayDesignNames;

    public MicroarrayExperiment(ExperimentType type, String accession, Date lastUpdate,
                                List<Pair<Contrast, Boolean>> contrasts,
                                String description, Species species,
                                Set<String> arrayDesignAccessions,
                                Set<String> arrayDesignNames,
                                ExperimentDesign experimentDesign,
                                Set<String> pubMedIds) {
        super(type, accession, lastUpdate, contrasts, description, species, pubMedIds, experimentDesign);
        this.arrayDesignAccessions = Sets.newTreeSet(arrayDesignAccessions);
        this.arrayDesignNames =  Sets.newTreeSet(arrayDesignNames);
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }

    public SortedSet<String> getArrayDesignNames() {return arrayDesignNames;}


    @Override
    public HashMap<String, Object> getAttributes(){
        HashMap<String, Object> result = new HashMap<>();
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
