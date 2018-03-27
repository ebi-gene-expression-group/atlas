package uk.ac.ebi.atlas.solr.cloud;

public abstract class SchemaField<T extends CollectionProxy> {
    protected final String fieldName;

    public SchemaField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String name() {
         return fieldName;
    }
}
