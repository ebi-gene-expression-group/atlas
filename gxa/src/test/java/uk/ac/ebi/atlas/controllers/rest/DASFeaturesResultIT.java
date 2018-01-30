package uk.ac.ebi.atlas.controllers.rest;

import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class DASFeaturesResultIT {

    @Inject
    DifferentialAnalyticsSearchService differentialAnalyticsSearchService;

    @Inject
    DiffAnalyticsSearchService diffAnalyticsSearchService;

    @Inject
    DifferentialJsonResultsParser differentialJsonResultsParser;


    private String geneId = "ENSG00000005469";
    private String conditionQuery = "";


    @Test
    public void compareSizeOfDiffAnalyticListOfBothControllers() throws Exception {

        List<DiffAnalytics> differentialAnalyticsList,diffAnalyticsList;

        JsonObject result = differentialAnalyticsSearchService.fetchResults(SemanticQuery.create(geneId), SemanticQuery.create(conditionQuery));

        differentialAnalyticsList = differentialJsonResultsParser.parseDifferentialResults(result);

        diffAnalyticsList = diffAnalyticsSearchService.fetchTopWithoutCountAnySpecies(geneId);

        assertThat(differentialAnalyticsList.size(), equalTo(diffAnalyticsList.size()));

    }

    @Test
    public void secondElementOfDiffAnalyticListOfBothControllersIsSame() throws Exception {

        List<DiffAnalytics> differentialAnalyticsList,diffAnalyticsList;

        JsonObject result = differentialAnalyticsSearchService.fetchResults(SemanticQuery.create(geneId), SemanticQuery.create(conditionQuery));

        differentialAnalyticsList = differentialJsonResultsParser.parseDifferentialResults(result);

        diffAnalyticsList = diffAnalyticsSearchService.fetchTopWithoutCountAnySpecies(geneId);

        for (int i=0; i < differentialAnalyticsList.size(); i++) {

            assertThat(differentialAnalyticsList.get(i), equalTo(diffAnalyticsList.get(i)));
        }

    }

}