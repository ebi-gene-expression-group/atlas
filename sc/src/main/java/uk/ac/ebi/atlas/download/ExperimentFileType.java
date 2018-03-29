package uk.ac.ebi.atlas.download;

import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

public enum ExperimentFileType {

    // Could include icon name (similar to Description class in ExternallyAvailableContent)
    EXPERIMENT_DESIGN ("experiment-design", "Experiment Design", "A file outlying the experimental design");

    // IDs should be used when generating URLs
    private final String id;
    private final String name;
    private final String description;

    ExperimentFileType(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public static ExperimentFileType fromId(String id) {
        for(ExperimentFileType fileType : ExperimentFileType.values()) {
            if(fileType.id.equalsIgnoreCase(id)) {
                return fileType;
            }
        }

        throw new ResourceNotFoundException("No experiment file type with ID " + id + " was found");
    }
}
