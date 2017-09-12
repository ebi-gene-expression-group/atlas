package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ArrayDesign {

    public abstract String accession();
    public abstract String name();

    public static ArrayDesign create(String accession, String name){
        return new AutoValue_ArrayDesign(accession, name);
    }

    public static ArrayDesign createForUnknownName(String accession){
        return create(accession, accession);
    }
}
