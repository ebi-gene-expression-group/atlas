package uk.ac.ebi.atlas.model.resource;

import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

/*
TODO rely on enum names everywhere!
 */
public enum ResourceType {
    PLOT_MA("ma-plot"), //accessed as ma-plot by old heatmap and otherwise ma-plot.png
    PLOT_GSEA_INTERPRO("gsea_interpro"),
    PLOT_GSEA_GO("gsea_go"),
    PLOT_GSEA_REACTOME("gsea_reactome"),
    EXTRA_INFO("extra-info");

    ResourceType(String resourceName){
        this.resourceName = resourceName;
        this.resourceKind = Kind.PLOT;
    }

    public final Kind resourceKind;
    final String resourceName;

    public String description(){
        return "TODO I am a resource "+resourceName;
    }

    public String fileName(){
        return resourceName+".png";
    }


    public static ResourceType forFileName(String fileName){
        for(ResourceType type : values()){
            if(type.fileName().contains(fileName)){
                return type;
            }
        }
        throw new ResourceNotFoundException("no matching resource for file: "+fileName);
    }

    public enum Kind {
        PLOT;
    }
}
