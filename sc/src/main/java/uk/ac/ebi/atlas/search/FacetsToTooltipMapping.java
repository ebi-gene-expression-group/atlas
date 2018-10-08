package uk.ac.ebi.atlas.search;

public enum FacetsToTooltipMapping {
    CHARACTERISTIC_INFERRED_CELL_TYPE("Tooltip for inferred cell type"),
    CHARACTERISTIC_ORGANISM_PART("Tooltip for organism part");

    String tooltip;


     FacetsToTooltipMapping(String tooltip){
        this.tooltip = tooltip;
    }

    public String getTooltip(){
         return this.tooltip;
    }
}
