package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.SolrInputDocumentInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

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
import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.GO;
import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.GOTERM;
import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.ORTHOLOG;

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
            BaselineExperimentTest.mockExperiment(),
            new BaselineAnalytics(bioentityIdentifier, "column_name", 13.37),
            "condition search");

    private SolrInputDocumentInputStream subject;

    @Before
    public void setUp() {
        when(baselineExperimentDataPointStream.readNext()).thenReturn(baselineExperimentDataPoint).thenReturn(null);
        subject = new SolrInputDocumentInputStream(baselineExperimentDataPointStream, bioentityPropertyNames);
    }

    @Test
    public void testSingleBaselineRecord(){
        SolrInputDocument result = subject.readNext();

        //Generic fields for any record
        assertThat(result.keySet(),
                hasItems("bioentityIdentifier", "species", "kingdom",  "experimentAccession", "experimentType"));

        //Baseline specific
        assertThat(result.keySet(), hasItems("defaultQueryFactorType", "expressionLevel"));
        assertThat(result.get("expressionLevel").getValue(), is((Object) 13.37));

        //identifier search
        assertThat(result.keySet(), hasItems(GO.asAnalyticsIndexKeyword(), "identifierSearch"));
        assertThat(result.get("identifierSearch").toString(), containsString("pancake"));

        //we do not index everything
        assertThat(result.keySet(), not(hasItem(ORTHOLOG.asAnalyticsIndexKeyword())));

        assertThat(subject.readNext(), is(nullValue()));
    }

}