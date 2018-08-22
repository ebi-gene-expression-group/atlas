
package uk.ac.ebi.atlas.model.experiment.differential;


import static com.google.common.base.Preconditions.checkState;

public enum Regulation {

    UP("up"), DOWN("down"), UP_DOWN("up/down");

    private String label;

    Regulation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Regulation valueOf(double foldChange) {
        checkState(foldChange != 0, "Cannot determine regulation for foldChange of zero");
        return foldChange < 0 ? DOWN : UP;
    }
}
