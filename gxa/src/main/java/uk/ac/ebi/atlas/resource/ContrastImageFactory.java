package uk.ac.ebi.atlas.resource;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.resource.ContrastImage;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import java.nio.file.Path;
import java.util.Optional;

@Component
public class ContrastImageFactory {
    private final String gseaPathTemplate;
    private final String rnaSeqPathTemplate;
    private final String microarrayPathTemplate;

    public ContrastImageFactory(DataFileHub dataFileHub) {
        Path experimentPathTemplate = dataFileHub.getExperimentMageTabDirLocation().resolve("{0}");

        gseaPathTemplate = experimentPathTemplate.resolve("{0}.{1}.{2}.gsea_class_non_dir_both.png").toString();
        rnaSeqPathTemplate = experimentPathTemplate.resolve("{0}-{1}-mvaPlot.png").toString();
        microarrayPathTemplate = experimentPathTemplate.resolve("{0}_{1}-{2}-mvaPlot.png").toString();
    }

    ExternalImage getContrastImage(ResourceType resourceType,
                                   String experimentAccession,
                                   Optional<String> arrayDesign,
                                   String contrastId) {
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
                pathTemplate = arrayDesign.isPresent() ? microarrayPathTemplate : rnaSeqPathTemplate;
                break;
            default:
                throw new IllegalArgumentException("Unknown resourece type" + resourceType.fileName());
        }

        if (arrayDesign.isPresent() && resourceType.equals(ResourceType.PLOT_MA)) {
            return new ContrastImage(
                    resourceType, pathTemplate, "external-resources/{0}/{1}/{2}/" + resourceType.fileName(),
                    experimentAccession, arrayDesign.get(), contrastId);
        } else {
            return new ContrastImage(
                    resourceType, pathTemplate, "external-resources/{0}/{1}/" + resourceType.fileName(),
                    experimentAccession, contrastId);
        }
    }

    ExternalImage getContrastImage(ResourceType resourceType, String experimentAccession, String contrastId) {
        return getContrastImage(resourceType, experimentAccession, Optional.empty(), contrastId);
    }
}
