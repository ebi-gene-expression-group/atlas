package uk.ac.ebi.atlas.bioentity.go;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GoPoTerm {

    public abstract String accession();
    public abstract String name();
    public abstract int depth();

    public static GoPoTerm create(String accession, String name, int depth) {
        return new AutoValue_GoPoTerm(accession, name, depth);
    }
}