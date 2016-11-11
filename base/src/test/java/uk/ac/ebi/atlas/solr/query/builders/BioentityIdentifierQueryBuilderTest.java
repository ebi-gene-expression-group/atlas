package uk.ac.ebi.atlas.solr.query.builders;

import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BioentityIdentifierQueryBuilderTest {

    private static final String QUERY_STRING = "A QUERY STRING";
    private static final String PROPERTY_NAME_1 = "PROPERTY_NAME_1";
    private static final String PROPERTY_NAME_2 = "PROPERTY_NAME_2";
    private static final String SPECIES = "SPECIES";
    private static final Set<String> BIOENTITY_TYPES = Sets.newTreeSet(Sets.newHashSet("BIOENTITY_TYPE_1", "BIOENTITY_TYPE_2"));

    private static final String EXPECTED_QUERY_STRING =
                    "property_value_search:\"A QUERY STRING\" " +
                    "AND (property_name:\"PROPERTY_NAME_1\" OR property_name:\"PROPERTY_NAME_2\") " +
                    "AND species:\"SPECIES\" " +
                    "AND (bioentity_type:\"BIOENTITY_TYPE_1\" OR bioentity_type:\"BIOENTITY_TYPE_2\")";

    private static final String EXPECTED_QUERY_STRING_WITH_OR_OPERATOR_APPLIED =
                    "{!lucene q.op=OR df=property_value_search}" +
                    "(property_value_search:\"A QUERY STRING\") " +
                    "AND (property_name:\"PROPERTY_NAME_1\" OR property_name:\"PROPERTY_NAME_2\") " +
                    "AND species:\"SPECIES\" " +
                    "AND (bioentity_type:\"BIOENTITY_TYPE_1\" OR bioentity_type:\"BIOENTITY_TYPE_2\")";


    private BioentityIdentifierQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new BioentityIdentifierQueryBuilder();
    }

    @Test
    public void shouldApplyOrOperatorOnQueryStringContent(){
        SolrQuery solrQuery = subject.forTerm(SemanticQueryTerm.create(QUERY_STRING))
                .withPropertyNames(PROPERTY_NAME_1, PROPERTY_NAME_2)
                .withSpecies(SPECIES)
                .withBioentityTypes(BIOENTITY_TYPES)
                .build();

        assertThat(solrQuery.getQuery(), is(EXPECTED_QUERY_STRING_WITH_OR_OPERATOR_APPLIED));

    }

}
