package uk.ac.ebi.atlas.resource;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.resource.ContrastImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;
import uk.ac.ebi.atlas.model.resource.RnaSeqContrastImage;

import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;

@Named
public class ContrastImageFactory {

    @Value("#{configuration['experiment.gsea-plot.path.template']}")
    String gseaPathTemplate;

    @Value("#{configuration['experiment.rnaseq.ma-plot.path.template']}")
    String rnaSeqPathTemplate;


    private ContrastImage getContrastImage(ResourceType resourceType,String experimentAccession, String contrastId ){
        String geneSetType = "";
        String fileName = "";
        String pathTemplate = "";
        switch (resourceType) {
            case PLOT_GSEA_INTERPRO:
                geneSetType= "interpro";
                break;
            case PLOT_GSEA_GO:
                geneSetType= "go";
                break;
            case PLOT_GSEA_REACTOME:
                geneSetType= "reactome";
                break;
            case PLOT_MA:
                geneSetType= "ma";
                break;
        }
        switch (resourceType) {
            case PLOT_GSEA_INTERPRO:
            case PLOT_GSEA_GO:
            case PLOT_GSEA_REACTOME:
                fileName= "gsea_"+geneSetType+".png";
                pathTemplate = gseaPathTemplate.replace("{2}", geneSetType);
                break;
            case PLOT_MA:
                fileName= "ma-plot.png";
                pathTemplate = rnaSeqPathTemplate;
                break;
        }

        return new RnaSeqContrastImage(resourceType,pathTemplate,
                "/external-resources/{0}/{1}/"+fileName,
                experimentAccession, contrastId);
    }

    public JsonElement createJsonByContrastIdForTheOldHeatmap(
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
        for(String contrastId : differentialExperiment.getContrastIds()){
            JsonArray resultsForThisContrast = new JsonArray();
            for( ResourceType resourceType :
                    ImmutableList.of(
                            ResourceType.PLOT_GSEA_GO,
                            ResourceType.PLOT_GSEA_INTERPRO,
                            ResourceType.PLOT_GSEA_REACTOME,
                            ResourceType.PLOT_MA)){
                ContrastImage contrastImage =
                        getContrastImage(resourceType, differentialExperiment.getAccession(), contrastId);
                if(contrastImage.exists()) {
                    resultsForThisContrast.add(contrastImage.toJson());
                } else {
                    int x = 0;
                }
            }

            result.add(contrastId, resultsForThisContrast);
        }

        return result;
    }
}
