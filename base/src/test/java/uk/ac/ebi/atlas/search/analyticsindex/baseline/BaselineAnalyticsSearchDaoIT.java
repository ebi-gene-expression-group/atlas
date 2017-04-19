package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class BaselineAnalyticsSearchDaoIT {

    @Inject
    BaselineAnalyticsSearchDao subject;

    //if fails check http://lime.ebi.ac.uk:8983/solr/analytics/query?q=identifierSearch:(%22kinase%22)%20AND%20defaultQueryFactorType:ORGANISM_PART&rows=0&omitHeader=true&fq=experimentType:(rnaseq_mrna_baseline%20OR%20proteomics_baseline)&json.facet=%7B%22experimentType%22:%7B%22terms%22:%7B%22field%22:%22experimentType%22,%22facet%22:%7B%22species%22:%7B%22terms%22:%7B%22field%22:%22species%22,%22facet%22:%7B%22defaultQueryFactorType%22:%7B%22terms%22:%7B%22field%22:%22defaultQueryFactorType%22,%22facet%22:%7B%22experimentAccession%22:%7B%22terms%22:%7B%22field%22:%22experimentAccession%22,%22facet%22:%7B%22assayGroupId%22:%7B%22terms%22:%7B%22field%22:%22assayGroupId%22,%22limit%22:1000,%22facet%22:%7B%22sumExpressionLevel%22:%22sum(expressionLevel)%22%7D%7D%7D,%22uniqueIdentifiers%22:%22unique(bioentityIdentifier)%22%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D
    //kinase is a popular kind of protein and at least one experiment should have it
    @Test
    public void fetchExpressionLevelFaceted(){
        String species = "homo sapiens";
        String defaultQueryFactorType = "ORGANISM_PART";
        List<Map<String, Object>> result =
                subject.fetchExpressionLevelFaceted(SemanticQuery.create("kinase"), SemanticQuery.create(), species, defaultQueryFactorType);

        assertThat(result.size(), greaterThan(0));

        for(Map<String, Object> m : result){
            for(String key: ImmutableList.of("val","uniqueIdentifiers","assayGroupId")){
                assertNotNull(m.get(key));
            }
        }
    }

}