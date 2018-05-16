package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.SolrInputDocumentInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.testutils.MockExperiment;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.GO;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.GOTERM;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ORTHOLOG;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.asAnalyticsSchemaField;

@RunWith(MockitoJUnitRunner.class)
public class SolrInputDocumentInputStreamTest {

    @Mock
    private BaselineExperimentDataPointStream baselineExperimentDataPointStream;

    private String bioentityIdentifier = "TEST_BIOENTITY_IDENTIFIER";

    private Map<String, Map<BioentityPropertyName, Set<String>>> bioentityPropertyNames =
             ImmutableMap.of(
                    bioentityIdentifier,
                     (Map<BioentityPropertyName, Set<String>>)
                             ImmutableMap.of(GO,(Set<String>) ImmutableSet.of("go_id"),
                                     GOTERM,ImmutableSet.of("go term with word pancake"),
                                     ORTHOLOG,
                                     ImmutableSet.of("something_we_dont_put_in_there_because_that_would_be_confusing")
                             )
             );

    private BaselineExperimentDataPoint baselineExperimentDataPoint = new BaselineExperimentDataPoint(
            MockExperiment.createBaselineExperiment(),
            BaselineAnalytics.create(bioentityIdentifier, "column_name", 13.37, 0.0),
            "condition search");

    private SolrInputDocumentInputStream subject;

    @Before
    public void setUp() {
        when(baselineExperimentDataPointStream.readNext()).thenReturn(baselineExperimentDataPoint).thenReturn(null);
        subject = new SolrInputDocumentInputStream(baselineExperimentDataPointStream, bioentityPropertyNames);
    }

    @Test
    public void testSingleBaselineRecord() {
        SolrInputDocument result = subject.readNext();

        //Generic fields for any record
        assertThat(result.keySet(),
                hasItems("bioentity_identifier", "species", "kingdom",  "experiment_accession", "experiment_type"));

        //Baseline specific
        assertThat(result.keySet(), hasItems("default_query_factor_type", "expression_level"));
        assertThat(result.get("expression_level").getValue(), is(13.37));

        //identifier search
        assertThat(result.keySet(), hasItems(asAnalyticsSchemaField(GO).name(), "identifier_search"));
        assertThat(result.get("identifier_search").toString(), containsString("pancake"));

        //we do not index everything
        assertThat(result.keySet(), not(hasItem("keyword_ortholog")));

        assertThat(subject.readNext(), is(nullValue()));
    }

}