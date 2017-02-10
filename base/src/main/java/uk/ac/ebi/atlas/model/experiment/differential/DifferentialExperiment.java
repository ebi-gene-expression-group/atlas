package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.*;

public class DifferentialExperiment extends Experiment<Contrast> {

    private static final Gson gson = new Gson();

    public DifferentialExperiment(String accession, Date lastUpdate, List<Contrast> contrasts, String description,
                                  boolean hasRData, Species species, Collection<String> pubMedIds,
                                  ExperimentDesign experimentDesign) {

        this(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, accession, lastUpdate, contrasts, description, hasRData,
                species, pubMedIds, experimentDesign);

    }

    protected DifferentialExperiment(ExperimentType experimentType, String accession, Date lastUpdate,
                                     List<Contrast> contrasts, String description, boolean hasRData, Species species,
                                     Collection<String> pubMedIds, ExperimentDesign experimentDesign) {

        super(experimentType, accession, lastUpdate,null, description, "", hasRData, species, pubMedIds,
                experimentDesign, Collections.<String>emptyList(), Collections.<String>emptyList(),
                Collections.<String>emptyList(), Collections.<String>emptyList(), contrasts);
    }

    @Override
    public Map<String, Object> getAttributes() {

        Map<String, Object> result = new HashMap<>();
        result.putAll(super.getAttributes());
        result.put("regulationValues", Regulation.values());
        result.put("isFortLauderdale", false);
        result.put("contrasts", this.getDataColumnDescriptors());

        return result;
    }

    public Map<String, ?> getDifferentialAttributes() {
        //you only have a selected contrast when you're on an experiment design page
        return getDifferentialAttributes("");
    }

    public Map<String, ?> getDifferentialAttributes(String selectedContrast) {

        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(selectedContrast)) {
            selectedContrast = getDataColumnDescriptors().iterator().next().getId();
        }

        Contrast contrast = getDataColumnDescriptor(selectedContrast);
        result.put("referenceAssays", gson.toJson(Sets.newHashSet(contrast.getReferenceAssayGroup())));
        result.put("testAssays", gson.toJson(Sets.newHashSet(contrast.getTestAssayGroup())));

        return result;

    }

    @Override
    public ExperimentInfo buildExperimentInfo() {
        ExperimentInfo experimentInfo = super.buildExperimentInfo();
        experimentInfo.setNumberOfContrasts(getDataColumnDescriptors().size());
        return  experimentInfo;
    }

}
