
package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class DifferentialExperiment extends Experiment {

    private static final Gson gson = new Gson();

    private LinkedHashMap<String, Contrast> contrastsById = Maps.newLinkedHashMap();

    public DifferentialExperiment(String accession, Date lastUpdate, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, boolean hasRData, String species, String kingdom, String ensemblDB, Set<String> pubMedIds, ExperimentDesign experimentDesign) {
        this(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, accession, lastUpdate, contrasts, description, hasExtraInfoFile, hasRData, species, kingdom, ensemblDB, pubMedIds, experimentDesign);
    }

    protected DifferentialExperiment(ExperimentType experimentType, String accession, Date lastUpdate, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, boolean hasRData, String species, String kingdom, String ensemblDB, Set<String> pubMedIds, ExperimentDesign experimentDesign) {
        super(experimentType, accession, lastUpdate,null, description, hasExtraInfoFile, hasRData, species, kingdom,
                ensemblDB, null, pubMedIds, experimentDesign,Collections.<String>emptyList(), Collections.<String>emptyList(),Collections.<String>emptyList());
        for (Contrast contrast : contrasts) {
            contrastsById.put(contrast.getId(), contrast);
        }
    }

    public Set<Contrast> getContrasts() {
        return Sets.newLinkedHashSet(contrastsById.values());
    }

    public Contrast getContrast(String contrastId) {
        Contrast contrast = contrastsById.get(contrastId);
        checkArgument(contrast != null, this.getAccession() + ": cannot find a contrast with contrastId: " + contrastId);
        return contrast;
    }

    public Set<String> getContrastIds() {
        return Collections.unmodifiableSet(contrastsById.keySet());
    }

    public Set<String> getAssayAccessions() {
        Set<String> assayAccessions = Sets.newHashSet();
        for (Contrast contrast : getContrasts()) {
            for (String assayAccession : contrast.getReferenceAssayGroup()) {
                assayAccessions.add(assayAccession);
            }
            for (String assayAccession : contrast.getTestAssayGroup()) {
                assayAccessions.add(assayAccession);
            }
        }

        return assayAccessions;
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions() {
        return getAssayAccessions();
    }

    @Override
    public Map<String, ?> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.putAll(super.getAttributes());
        result.put("regulationValues", Regulation.values());
        result.put("isFortLauderdale", false);
        result.put("contrasts", this.getContrasts());

        return result;
    }

    public Map<String, ?> getDifferentialAttributes(){
        //you only have a selected contrast when you're on an experiment design page
        return getDifferentialAttributes("");
    }

    public Map<String, ?> getDifferentialAttributes(String selectedContrast){
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(selectedContrast)) {
            selectedContrast = getContrasts().iterator().next().getId();
        }

        Contrast contrast = getContrast(selectedContrast);
        result.put("referenceAssays", gson.toJson(Sets.newHashSet(contrast.getReferenceAssayGroup())));
        result.put("testAssays", gson.toJson(Sets.newHashSet(contrast.getTestAssayGroup())));

        return result;

    }

}
