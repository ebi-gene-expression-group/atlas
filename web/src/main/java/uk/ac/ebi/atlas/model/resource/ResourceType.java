package uk.ac.ebi.atlas.model.resource;

import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

public enum ResourceType {
    PLOT_GSEA_INTERPRO("gsea_interpro.png"),
    PLOT_GSEA_GO("gsea_go.png"),
    PLOT_GSEA_REACTOME("gsea_reactome.png"),
    PLOT_MA("ma-plot.png");

    ResourceType(String fileName){
        this.fileName = fileName;
    }

    public String fileName;


    public static ResourceType forFileName(String fileName){
        for(ResourceType type : values()){
            if(type.fileName.equals(fileName)){
                return type;
            }
        }
        throw new ResourceNotFoundException("no matching resource for file: "+fileName);
    }
}
