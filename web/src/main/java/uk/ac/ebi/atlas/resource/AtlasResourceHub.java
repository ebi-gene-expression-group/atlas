package uk.ac.ebi.atlas.resource;

import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;
import uk.ac.ebi.atlas.model.resource.ContrastImage;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Named
public class AtlasResourceHub {

    private ContrastImageFactory contrastImageFactory;
    private ExtraInfoFactory extraInfoFactory;

    @Inject
    public AtlasResourceHub(ContrastImageFactory contrastImageFactory, ExtraInfoFactory extraInfoFactory){
        this.contrastImageFactory = contrastImageFactory;
        this.extraInfoFactory = extraInfoFactory;
    }


    public JsonObject createJsonByContrastIdForTheOldHeatmap(
            String experimentAccession, Collection<Contrast> contrasts) {
        JsonObject result = new JsonObject();
        for (Contrast contrast : contrasts) {
            JsonObject valuesForThisContrast = new JsonObject();
            valuesForThisContrast.addProperty("go",
                    contrastImageFactory.getContrastImage(ResourceType.PLOT_GSEA_GO, experimentAccession, contrast
                            .getId()).exists() );
            valuesForThisContrast.addProperty("interpro",
                    contrastImageFactory.getContrastImage(ResourceType.PLOT_GSEA_INTERPRO, experimentAccession,
                            contrast.getId()).exists() );
            valuesForThisContrast.addProperty("reactome",
                    contrastImageFactory.getContrastImage(ResourceType.PLOT_GSEA_REACTOME, experimentAccession,
                            contrast.getId()).exists() );
            result.add(contrast.getId(), valuesForThisContrast);
        }
        return result;
    }

    public Map<String,JsonArray> contrastImages(DifferentialExperiment differentialExperiment){
        Map<String,JsonArray>  result = new HashMap<>();
        for(Contrast contrast : differentialExperiment.getContrasts()){
            Optional<String> arrayDesign =
                    differentialExperiment instanceof MicroarrayExperiment
                            ? Optional.of(contrast.getArrayDesignAccession())
                            : Optional.<String>absent();
            JsonArray resultsForThisContrast = new JsonArray();
            for( ResourceType resourceType
                            : ContrastImage.RESOURCE_TYPES){
                ExternalImage externalImage =
                        contrastImageFactory.getContrastImage(
                                resourceType,
                                differentialExperiment.getAccession(),
                                arrayDesign,
                                contrast.getId());
                if(externalImage.exists()) {
                    resultsForThisContrast.add(externalImage.toJson());
                }
            }

            result.put(contrast.getId(), resultsForThisContrast);
        }
        return result;
    }

    public boolean hasExtraInfo(Experiment experiment){
        return extraInfoFactory.getExtraInfo(experiment.getAccession()).exists();
    }

}
