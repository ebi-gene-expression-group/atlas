package uk.ac.ebi.atlas.model;

public interface ExpressionUnit {

    interface Absolute extends ExpressionUnit {
        enum Rna implements Absolute {
            FPKM, TPM
        }


        enum Protein implements Absolute {
            ANY("");

            String name;

            Protein(String name){
                this.name = name;
            }

            @Override
            public String toString(){
                return "";
            }

        }
    }

    enum Relative implements ExpressionUnit {
        FOLD_CHANGE;

        @Override
        public String toString(){
            return "Log2-fold change";
        }
    }

}
