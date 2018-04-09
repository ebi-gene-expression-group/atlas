package uk.ac.ebi.atlas.download;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

public enum ExperimentFileType {

    // Could include icon name (similar to Description class in ExternallyAvailableContent)
    EXPERIMENT_DESIGN ("experiment-design", "Experiment Design", "Experiment design file", IconType.EXPERIMENT_DESIGN),
    SDRF("sdrf", "SDRF file", "SDRF file", IconType.TSV),
    CLUSTERING("cluster", "Clustering file", "Clustering file", IconType.TSV),
    QUANTIFICATION_RAW("quantification-raw", "Raw Quantification", "Raw quantification files (MatrixMarket format)", IconType.TSV),
    QUANTIFICATION_FILTERED("quantification-filtered", "Filtered Quantification", "Filetered quantification files (MatrixMarket format)", IconType.TSV);

    // IDs should be used when generating URLs
    private final String id;
    private final String name;
    private final String description;
    private final IconType iconType;

    ExperimentFileType(String id, String name, String description, IconType iconType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconType = iconType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public IconType getIconType() {
        return iconType;
    }

    public static ExperimentFileType fromId(String id) {
        for(ExperimentFileType fileType : ExperimentFileType.values()) {
            if(fileType.id.equalsIgnoreCase(id)) {
                return fileType;
            }
        }

        throw new ResourceNotFoundException("No experiment file type with ID " + id + " was found");
    }

    public JsonObject toJson() {
        JsonObject result = new JsonObject();

        result.addProperty("icon", this.iconType.getName());
        result.addProperty("description", this.description);

        return result;
    }
}
