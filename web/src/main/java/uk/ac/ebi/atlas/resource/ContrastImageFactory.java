package uk.ac.ebi.atlas.resource;


import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.ContrastImage;
import uk.ac.ebi.atlas.model.resource.MicroarrayContrastImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;
import uk.ac.ebi.atlas.model.resource.RnaSeqContrastImage;

import javax.inject.Named;
import java.util.Collection;

@Named
public class ContrastImageFactory {

    @Value("#{configuration['experiment.gsea-plot.path.template']}")
    String gseaPathTemplate;

    @Value("#{configuration['experiment.rnaseq.ma-plot.path.template']}")
    String rnaSeqPathTemplate;

    @Value("#{configuration['experiment.microarray.ma-plot.path.template']}")
    String microarrayPathTemplate;


    ContrastImage getContrastImage(ResourceType resourceType,String experimentAccession, Optional<String>
            arrayDesign,
            String contrastId ){
        String pathTemplate = "";
        switch (resourceType) {
            case PLOT_GSEA_INTERPRO:
                pathTemplate = gseaPathTemplate.replace("{2}", "interpro");
                break;
            case PLOT_GSEA_GO:
                pathTemplate = gseaPathTemplate.replace("{2}", "go");
                break;
            case PLOT_GSEA_REACTOME:
                pathTemplate = gseaPathTemplate.replace("{2}", "reactome");
                break;
            case PLOT_MA:
                pathTemplate = arrayDesign.isPresent()? microarrayPathTemplate : rnaSeqPathTemplate;
                break;
        }

        String uriTemplate = (arrayDesign.isPresent()
                ? "/external-resources/{0}/{1}/{2}/"
                :"/external-resources/{0}/{1}/")+resourceType.fileName;


        return arrayDesign.isPresent()
        ?   new MicroarrayContrastImage(resourceType,pathTemplate,uriTemplate,
                experimentAccession,arrayDesign.get(), contrastId)
                :new RnaSeqContrastImage(resourceType,pathTemplate,uriTemplate,
                experimentAccession, contrastId);
    }

    ContrastImage getContrastImage(ResourceType resourceType,String experimentAccession,
                                           String contrastId ){
        return getContrastImage(resourceType, experimentAccession, Optional.<String>absent(), contrastId);
    }

    public JsonObject createJsonByContrastIdForTheOldHeatmap(
            String experimentAccession, Collection<Contrast> contrasts) {
        JsonObject result = new JsonObject();
        for (Contrast contrast : contrasts) {
            JsonObject valuesForThisContrast = new JsonObject();
            valuesForThisContrast.addProperty("go",
                    getContrastImage(ResourceType.PLOT_GSEA_GO, experimentAccession, contrast.getId()).exists() );
            valuesForThisContrast.addProperty("interpro",
                    getContrastImage(ResourceType.PLOT_GSEA_INTERPRO, experimentAccession, contrast.getId()).exists() );
            valuesForThisContrast.addProperty("reactome",
                    getContrastImage(ResourceType.PLOT_GSEA_REACTOME, experimentAccession, contrast.getId()).exists() );
            result.add(contrast.getId(), valuesForThisContrast);
        }
        return result;
    }


    public JsonObject resourcesPerContrast(DifferentialExperiment differentialExperiment){
        JsonObject result = new JsonObject();
        for(Contrast contrast : differentialExperiment.getContrasts()){
            Optional<String> arrayDesign =
                    differentialExperiment instanceof MicroarrayExperiment
                            ? Optional.of(contrast.getArrayDesignAccession())
                            : Optional.<String>absent();
            JsonArray resultsForThisContrast = new JsonArray();
            for( ResourceType resourceType :
                    arrayDesign.isPresent()
                            ? MicroarrayContrastImage.RESOURCE_TYPES
                            : RnaSeqContrastImage.RESOURCE_TYPES){
                ContrastImage contrastImage =
                        getContrastImage(
                                resourceType,
                                differentialExperiment.getAccession(),
                                arrayDesign,
                                contrast.getId());
                if(contrastImage.exists()) {
                    resultsForThisContrast.add(contrastImage.toJson());
                }
            }

            result.add(contrast.getId(), resultsForThisContrast);
        }
        return result;
    }

}
