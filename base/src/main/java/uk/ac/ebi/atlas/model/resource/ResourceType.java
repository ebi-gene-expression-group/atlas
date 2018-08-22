package uk.ac.ebi.atlas.model.resource;

import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

public enum ResourceType {
    PLOT_MA("ma-plot"), //accessed as ma-plot by old heatmap and otherwise ma-plot.png
    PLOT_GSEA_INTERPRO("gsea_interpro"),
    PLOT_GSEA_GO("gsea_go"),
    PLOT_GSEA_REACTOME("gsea_reactome"),
    EXTRA_INFO("extra-info");

    public final String resourceName;

    ResourceType(String resourceName) {
        this.resourceName = resourceName;
    }

    public String fileName() {
        return resourceName + ".png";
    }

    public static ResourceType forFileName(String fileName) {
        for (ResourceType type : values()) {
            if (type.fileName().contains(fileName)) {
                return type;
            }
        }
        throw new ResourceNotFoundException("no matching resource for file: " + fileName);
    }
}
