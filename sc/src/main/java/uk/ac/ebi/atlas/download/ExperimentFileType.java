package uk.ac.ebi.atlas.download;

import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import java.util.Arrays;

public enum ExperimentFileType {
    // Could include icon name (similar to Description class in ExternallyAvailableContent)
    EXPERIMENT_DESIGN(
            "experiment-design", "Experiment design file (.tsv)", IconType.EXPERIMENT_DESIGN, false),
    SDRF(
            "sdrf", "SDRF file (.txt)", IconType.TSV, false),
    IDF(
            "idf", "IDF file (.txt)", IconType.TSV, false),
    CLUSTERING(
            "cluster", "Clustering file (.tsv)", IconType.TSV, false),
    QUANTIFICATION_RAW(
            "quantification-raw", "Raw quantification files (MatrixMarket)", IconType.TSV, true),
    QUANTIFICATION_FILTERED(
            "quantification-filtered", "Filtered quantification files (MatrixMarket)", IconType.TSV, true),
    NORMALISED(
            "normalised", "Normalised counts files (MatrixMarket)", IconType.TSV, true),
    MARKER_GENES(
            "marker-genes", "Marker gene files (.tsv)", IconType.TSV, true);

    // IDs should be used when generating URLs
    private final String id;
    private final String description;
    private final IconType iconType;
    // Indicates if the file type has more than one associated files, which should be served as an archive
    private final boolean isArchive;

    ExperimentFileType(String id, String description, IconType iconType, boolean isArchive) {
        this.id = id;
        this.description = description;
        this.iconType = iconType;
        this.isArchive = isArchive;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public IconType getIconType() {
        return iconType;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public static ExperimentFileType fromId(String id) {
        return Arrays.stream(ExperimentFileType.values())
                .filter(value -> value.id.equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException("No experiment file type with ID " + id + " was found"));
    }
}
