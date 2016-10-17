package uk.ac.ebi.atlas.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ExperimentType {

    RNASEQ_MRNA_BASELINE("rnaseq_mrna_baseline")
    ,RNASEQ_MRNA_DIFFERENTIAL("rnaseq_mrna_differential")
    ,MICROARRAY_ANY("microarray parent type")
    ,MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_1colour_mrna_differential")
    ,MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_2colour_mrna_differential")
    ,MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_1colour_microrna_differential")
    ,PROTEOMICS_BASELINE("proteomics_baseline");

    private ExperimentType parent;
    private String description;

    ExperimentType(String description) {
        this.description = description;
    }

    ExperimentType(ExperimentType parent, String description) {
        this(description);
        this.parent = parent;
    }

    public boolean isMicroarray() {
        return equals(MICROARRAY_ANY) || getParent().equals(MICROARRAY_ANY);
    }

    public boolean isBaseline() {
        return equals(RNASEQ_MRNA_BASELINE) || equals(PROTEOMICS_BASELINE);
    }

    public boolean isProteomicsBaseline() {
        return equals(PROTEOMICS_BASELINE);
    }

    public boolean isRnaSeqDifferential() {
        return equals(RNASEQ_MRNA_DIFFERENTIAL);
    }

    public boolean isDifferential() {
        return equals(RNASEQ_MRNA_DIFFERENTIAL) || isMicroarray();
    }

    public ExperimentType getParent() {
        return parent == null ? this : parent;
    }

    public String getDescription() {
        return description;
    }

    private static final Map<String,ExperimentType> TYPE_BY_DESCRIPTION = new HashMap<>();

    static {
        for(ExperimentType experimentType : EnumSet.allOf(ExperimentType.class)) {
            TYPE_BY_DESCRIPTION.put(experimentType.getDescription(), experimentType);
        }
    }

    public static ExperimentType get(String experimentTypeDescription) {
        return TYPE_BY_DESCRIPTION.get(experimentTypeDescription.toLowerCase());
    }

    public static boolean containsBaseline(Set<String> experimentTypes) {
        return experimentTypes.contains(RNASEQ_MRNA_BASELINE.getDescription()) || experimentTypes.contains(PROTEOMICS_BASELINE.getDescription());
    }

    public static boolean containsDifferential(Set<String> experimentTypes) {
        return experimentTypes.contains(RNASEQ_MRNA_DIFFERENTIAL.getDescription()) || experimentTypes.contains(MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL.getDescription()) || experimentTypes.contains(MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL.getDescription()) || experimentTypes.contains(MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL.getDescription());
    }


    // Do not remove: the following three methods are used in experiment-header.jsp
    public boolean isTwoColour() {
        return equals(MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
    }

    public boolean isMicroRna() {
        return equals(MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
    }

    public boolean isRnaSeqBaseline() {
        return equals(RNASEQ_MRNA_BASELINE);
    }

    public static ExperimentType[] all(){
        return new ExperimentType[] {
                ExperimentType.MICROARRAY_ANY,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_BASELINE,
                ExperimentType.PROTEOMICS_BASELINE
        };
    }
}
