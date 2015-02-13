package uk.ac.ebi.atlas.bioentity.go;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 09/02/15.
 */

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GoPoTerm {

    public abstract String accession();
    public abstract String name();
    public abstract int depth();
    //    @Nullable
    //    public abstract String category();


    public static GoPoTerm create(String accession, String name, int depth) {
        return new AutoValue_GoPoTerm(accession, name, depth);
    }
}