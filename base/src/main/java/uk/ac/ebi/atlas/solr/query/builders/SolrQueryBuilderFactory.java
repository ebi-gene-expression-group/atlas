package uk.ac.ebi.atlas.solr.query.builders;

import javax.inject.Named;

//we need a factory because a client may need to build multiple different queries in one single method (i.e. autocomplete suggestions)
@Named
public class SolrQueryBuilderFactory {

    public FacetedPropertyValueQueryBuilder createFacetedPropertyValueQueryBuilder(){
        return new FacetedPropertyValueQueryBuilder();
    }

    public BioentityIdentifierQueryBuilder createGeneBioentityIdentifierQueryBuilder(){
        return new BioentityIdentifierQueryBuilder();
    }

    public AutocompleteGroupedPropertyValueQueryBuilder createAutocompleteGroupedPropertyValueQueryBuilder() {
        return new AutocompleteGroupedPropertyValueQueryBuilder();
    }


}
