package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

import javax.inject.Inject;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class AnalyticsIndexerServiceIT {

    @Mock
    private SolrClient solrClient;

    @Inject
    private ExperimentTrader experimentTrader;

    @Inject
    private BioentityPropertiesDao bioentityPropertiesDao;

    @Inject
    private ExperimentDataPointStreamFactory experimentDataPointStreamFactory;

    @Inject
    private BioentityIdentifiersReader bioentityIdentifiersReader;

    @Inject
    private AnalyticsQueryClient analyticsQueryClient;

    private AnalyticsIndexerService subject = new AnalyticsIndexerService(solrClient, experimentDataPointStreamFactory);

    @Before
    public void setUp() {
        subject = new AnalyticsIndexerService(solrClient, experimentDataPointStreamFactory);
    }

    @Test
    public void testSomeExperiments() {
        experimentInformationEndsUpInTheIndex("E-MTAB-513");
        experimentInformationEndsUpInTheIndex("E-PROT-1");
        experimentInformationEndsUpInTheIndex("E-GEOD-48549");
        experimentInformationEndsUpInTheIndex("E-GEOD-22351");
    }

    private Iterable<SolrInputDocument> getResults(Experiment experiment){

        return subject.solrInputDocuments(
                experiment,
                bioentityPropertiesDao.getMap(
                        bioentityIdentifiersReader.getBioentityIdsFromExperiment(experiment.getAccession())));

    }

    private void experimentInformationEndsUpInTheIndex(String accession) {
        Experiment experiment = experimentTrader.getPublicExperiment(accession);
        Iterable<SolrInputDocument> result = getResults(experiment);

        int count = 0;
        for(SolrInputDocument solrInputDocument : result) {
            count++;

            assertThat(solrInputDocument.size(), greaterThan(10));
            assertThat(experiment.getType().name().toUpperCase(),
                    is(solrInputDocument.getField("experimentType").getValue()));
            assertThat(experiment.getSpecies().mappedName, is(solrInputDocument.getField("species").getValue()));
        }
        assertThat(count, is(greaterThan(100)));
    }

    @Test
    public void weGenerateDocumentsCompatibleWithIndexContent(){
        weGenerateDocumentsCompatibleWithIndexContent("E-MTAB-2706");
    }

    private void weGenerateDocumentsCompatibleWithIndexContent(String accession){
        Collection<SolrInputDocument> results =
                ImmutableList.copyOf(getResults(experimentTrader.getPublicExperiment(accession)));

        weGenerateDocumentsWithTheSameIdentifiersAsCurrentIndexContent(accession,results);
        weGenerateDocumentsWhoseContentWeCanThenRetrieve(accession,results);
        theSpeciesFieldIsTheEnsemblName(accession, results);
    }

    private void weGenerateDocumentsWithTheSameIdentifiersAsCurrentIndexContent(String accession, Collection<SolrInputDocument> results){
        Collection<String> identifiersForThatExperiment = AnalyticsSearchService.readBuckets(analyticsQueryClient
                .queryBuilder().bioentityIdentifierFacets(-1)
                .inExperiment
                (accession).fetch());

        assertThat(identifiersForThatExperiment, not(empty()));

        for(SolrInputDocument solrInputDocument: results) {
            String bioentityIdentifier = solrInputDocument.getField("bioentityIdentifier").getValue().toString();
            assertThat(identifiersForThatExperiment, hasItem(bioentityIdentifier));
         }

    }

    private void weGenerateDocumentsWhoseContentWeCanThenRetrieve(String accession,Collection<SolrInputDocument> results){
        Map<String,String> keywordFieldsPresent = new HashMap<>();
        for(SolrInputDocument solrInputDocument: results) {
            for(String fieldName: solrInputDocument.getFieldNames()) {
                if(fieldName.startsWith("keyword_")){
                    //we repeatedly put into the same fields but that's okay I just want one example value per field
                    keywordFieldsPresent.put(fieldName.replace("keyword_",""), solrInputDocument.getFieldValue
                            (fieldName).toString());
                }
            }
        }

        //category searches e.g. symbol:PIM1
        for(Map.Entry<String, String> e: keywordFieldsPresent.entrySet()) {
            indexReturnsDataFor(accession, SemanticQuery.create(SemanticQueryTerm.create(e.getValue(), e.getKey())));
        }

        //identifier search searches e.g. symbol:PIM
        for(Map.Entry<String, String> e: keywordFieldsPresent.entrySet()) {
            indexReturnsDataFor(accession, SemanticQuery.create(SemanticQueryTerm.create(e.getValue())));
        }

    }

    private void theSpeciesFieldIsTheEnsemblName(String accession,Collection<SolrInputDocument> results) {
        Set<String> species = new HashSet<>();
        for(SolrInputDocument solrInputDocument: results) {
            species.add(solrInputDocument.getField("species").getValue().toString());
        }

        assertThat(species.size(), is(1));

        assertThat(species.iterator().next(),
                is(experimentTrader.getPublicExperiment(accession).getSpecies().mappedName));

    }

    private void indexReturnsDataFor(String accession, SemanticQuery identifierSearch) {
        Collection<String> identifiersForThatExperiment = AnalyticsSearchService.readBuckets(
                analyticsQueryClient.queryBuilder()
                        .bioentityIdentifierFacets(-1)
                        .queryIdentifierSearch(identifierSearch)
                        .inExperiment(accession)
                        .fetch()
        );

        assertThat(
                MessageFormat.format("Nothing in the index for {0} , {1}", accession, identifierSearch.description()),
                identifiersForThatExperiment, not(Matchers.<String>empty()));
    }

}
