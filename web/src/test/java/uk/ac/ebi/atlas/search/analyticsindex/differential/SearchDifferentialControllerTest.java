package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDao;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.GeneQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchDifferentialControllerTest {

    SearchDifferentialController subject;

    @Mock
    EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    @Mock
    AnalyticsSearchDao analyticsSearchDao;

    @Mock
    DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao;


    @Test
    public void test() {

        ImmutableMultimap<String, NameValue> facets = ImmutableMultimap.<String, NameValue>builder()
                .putAll("species", ImmutableList.of(NameValue.create("homo sapiens", "Homo sapiens"), NameValue.create("arabidopsis thaliana")))
                .put("experimentType", NameValue.create("rnaseq_mrna_differential"))
                .put("factors", NameValue.create("genotype"))
                .put("numReplicates", NameValue.create("3"))
                .put("regulation", NameValue.create("UP"))
                .build();

        when(differentialAnalyticsSearchDao.fetchFacets(any(GeneQuery.class))).thenReturn(facets);
        subject = new SearchDifferentialController(ebiGlobalSearchQueryBuilder, analyticsSearchDao, differentialAnalyticsSearchDao);

        String expected = "{\n" +
                "  \"species\": [\n" +
                "    {\n" +
                "      \"name\": \"homo sapiens\",\n" +
                "      \"value\": \"Homo sapiens\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"arabidopsis thaliana\",\n" +
                "      \"value\": \"arabidopsis thaliana\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"experimentType\": [\n" +
                "    {\n" +
                "      \"name\": \"rnaseq_mrna_differential\",\n" +
                "      \"value\": \"rnaseq_mrna_differential\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"factors\": [\n" +
                "    {\n" +
                "      \"name\": \"genotype\",\n" +
                "      \"value\": \"genotype\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"numReplicates\": [\n" +
                "    {\n" +
                "      \"name\": \"3\",\n" +
                "      \"value\": \"3\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"regulation\": [\n" +
                "    {\n" +
                "      \"name\": \"UP\",\n" +
                "      \"value\": \"UP\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        assertThat(subject.fetchFacetsAsJson(GeneQuery.create("dummy"), gson), is(expected));
    }


}