package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.model.baseline.BioentityPropertyName.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class SolrInputDocumentIterableTest {

    String bioentityIdentifier = "TEST_BIOENTITY_IDENTIFIER";

    Map<String, Map<BioentityPropertyName, Set<String>>> bioentityPropertyNames =
             ImmutableMap.of(
                    bioentityIdentifier,
                     (Map<BioentityPropertyName, Set<String>>)
                             ImmutableMap.of(GO,(Set<String>) ImmutableSet.of("go_id"),
                                     GOTERM,ImmutableSet.of("go term with word pancake"),
                                     ORTHOLOG,
                                     ImmutableSet.of("something_we_dont_put_in_there_because_that_would_be_confusing")
                    ));


    ExperimentDataPoint experimentDataPoint = new BaselineExperimentDataPoint(
            BaselineExperimentTest.mockExperiment(),
            new BaselineAnalytics(bioentityIdentifier, "column_name", 13.37),
            "condition search");



    @Test
    public void testBaselineRecord(){

        Iterator<SolrInputDocument> subject =
                new SolrInputDocumentIterable(ImmutableList.of(experimentDataPoint).iterator(), bioentityPropertyNames).iterator();

        assertEquals(true, subject.hasNext());
        SolrInputDocument result = subject.next();
        assertEquals(false, subject.hasNext());


        //Generic fields for any record
        assertThat(result.keySet(),
                hasItems("bioentityIdentifier", "species", "kingdom",  "experimentAccession", "experimentType"));

        //Baseline specific
        assertThat(result.keySet(),
                hasItems("defaultQueryFactorType", "expressionLevel"));
        assertThat(result.get("expressionLevel").getValue(), is((Object) 13.37));

        //identifier search
        assertThat(result.keySet(),
                hasItems(GO.asAnalyticsIndexKeyword(), "identifierSearch"));
        assertThat(result.get("identifierSearch").toString(), containsString("pancake"));

        //we do not index everything
        assertThat(result.keySet(), not(hasItem(ORTHOLOG.asAnalyticsIndexKeyword())));




    }

}