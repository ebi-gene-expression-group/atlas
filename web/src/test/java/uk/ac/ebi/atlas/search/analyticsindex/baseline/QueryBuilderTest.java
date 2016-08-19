package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueryBuilderTest {

    QueryBuilder subject = new QueryBuilder();

    @Test
    public void doQuery(){
        String result = subject.buildSolrQuery(ImmutableList.of(Pair.of("identifierSearch", SemanticQuery.create
                ("zinc " +
                "finger")), Pair.of("conditionSearch",SemanticQuery.create("lung"))));

        assertThat(result, is("identifierSearch:(\"zinc finger\") AND conditionSearch:(\"lung\")"));
    }

}