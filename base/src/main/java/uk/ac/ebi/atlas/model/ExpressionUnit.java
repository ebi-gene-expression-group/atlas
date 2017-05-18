package uk.ac.ebi.atlas.model;

public interface ExpressionUnit {

    interface Absolute extends ExpressionUnit {
        enum Rna implements Absolute {
            FPKM, TPM
        }

         class Protein implements Absolute { }
    }

    // interface Relative extends ExpressionUnit {}
}
