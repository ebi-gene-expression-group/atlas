package uk.ac.ebi.atlas.solr.cloud.collections;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ENSGENE;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ENTREZGENE;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.FLYBASE_GENE_ID;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.HGNC_SYMBOL;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.MGI_ID;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.MGI_SYMBOL;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.WBPSGENE;
import static uk.ac.ebi.atlas.utils.StringUtil.escapeDoubleQuotes;

public class BioentitiesCollectionProxy extends CollectionProxy {
    // Where, and in what order, should we search in case of a free text query (without category)
    public static final ImmutableList<BioentityPropertyName> ID_PROPERTY_NAMES =
            ImmutableList.of(ENSGENE, SYMBOL, ENTREZGENE, HGNC_SYMBOL, MGI_ID, MGI_SYMBOL, FLYBASE_GENE_ID, WBPSGENE);

    // These are species-specific property names that will ignore a species argument when searching. We assume that if
    // the user chooses something like ENSG000001234 from the drop-down and Mus musculus in the species select, it is
    // because she didn’t notice and her intent is clear (or even that she chose a species but when typing and saw the
    // suggestions she changed her mind). After all, she’s the geneticist/biologist/bioinformatician!
    public static final ImmutableList<BioentityPropertyName> SPECIES_OVERRIDE_PROPERTY_NAMES =
            ImmutableList.of(ENSGENE, ENTREZGENE, HGNC_SYMBOL, MGI_ID, MGI_SYMBOL, FLYBASE_GENE_ID, WBPSGENE);

    public static final class BioentitiesSchemaField extends SchemaField<BioentitiesCollectionProxy> {
        private BioentitiesSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final BioentitiesSchemaField BIOENTITY_IDENTIFIER =
            new BioentitiesSchemaField("bioentity_identifier");
    public static final BioentitiesSchemaField SPECIES =
            new BioentitiesSchemaField("species");
    public static final BioentitiesSchemaField PROPERTY_NAME =
            new BioentitiesSchemaField("property_name");
    public static final BioentitiesSchemaField PROPERTY_VALUE =
            new BioentitiesSchemaField("property_value");

    public BioentitiesCollectionProxy(SolrClient solrClient) {
        super(solrClient, "bioentities");
    }

    public static String asBioentitiesCollectionQuery(SemanticQueryTerm geneQuery) {
        return geneQuery.category()
                .map(
                        category ->
                                String.format(
                                        PROPERTY_NAME.name() + ":\"%s\" AND " + PROPERTY_VALUE.name() + ":\"%s\"",
                                        escapeDoubleQuotes(category),
                                        escapeDoubleQuotes(geneQuery.value())))
                .orElse(String.format(PROPERTY_VALUE.name() + ":\"%s\"", escapeDoubleQuotes(geneQuery.value())));
    }

    public QueryResponse query(SolrQueryBuilder<BioentitiesCollectionProxy> solrQueryBuilder) {
        return rawQuery(solrQueryBuilder.build());
    }
}
