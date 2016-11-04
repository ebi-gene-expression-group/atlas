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
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

import javax.inject.Inject;

import java.text.MessageFormat;
import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/*
excluded from ITs because it takes about two minutes
TODO make it run fast by stubbing out the EFO tree getting built
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexerServiceEIT {


    @Mock
    SolrClient solrClient;

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    BioentityPropertiesDao bioentityPropertiesDao;

    @Inject
    ExperimentDataPointStreamFactory experimentDataPointStreamFactory;

    @Inject
    BioentityIdentifiersReader bioentityIdentifiersReader;

    @Inject
    AnalyticsQueryClient analyticsQueryClient;

    AnalyticsIndexerService subject;
    
    @Before
    public void setUp(){
        subject = new AnalyticsIndexerService(solrClient,experimentDataPointStreamFactory
        );
    }

    @Test
    public void testSomeExperiments(){
        experimentInformationEndsUpInTheIndex("E-MTAB-513");
        experimentInformationEndsUpInTheIndex("E-PROT-1");
        experimentInformationEndsUpInTheIndex("E-GEOD-48549");
        experimentInformationEndsUpInTheIndex("E-GEOD-22351");
    }

    Iterable<SolrInputDocument> getResults(Experiment experiment){

        return subject.solrInputDocuments(experiment, bioentityPropertiesDao.getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiment
                (experiment.getAccession())));

    }

     void experimentInformationEndsUpInTheIndex(String accession){
        Experiment experiment = experimentTrader.getPublicExperiment(accession);


        Iterable<SolrInputDocument> result = getResults(experiment);

        int count = 0;

        for(SolrInputDocument solrInputDocument: result){
            count++;

            assertThat(solrInputDocument.size(), greaterThan(10));
            assertEquals(experiment.getType().name().toUpperCase(), solrInputDocument.getField("experimentType").getValue());
            assertEquals(experiment.getSpecies().mappedName, solrInputDocument.getField("species").getValue());
        }

        assertTrue(count>100);
    }

    @Test
    public void weGenerateDocumentsCompatibleWithIndexContent(){
        weGenerateDocumentsCompatibleWithIndexContent("E-MTAB-2706");
    }


     void weGenerateDocumentsCompatibleWithIndexContent(String accession){
         Collection<SolrInputDocument> results = ImmutableList.copyOf(getResults(experimentTrader.getPublicExperiment
                 (accession)));

         weGenerateDocumentsWithTheSameIdentifiersAsCurrentIndexContent(accession,results);
        weGenerateDocumentsWhoseContentWeCanThenRetrieve(accession,results);
         theSpeciesFieldIsTheEnsemblName(accession, results);
    }

     void weGenerateDocumentsWithTheSameIdentifiersAsCurrentIndexContent(String accession, Collection<SolrInputDocument> results){
        Collection<String> identfiersForThatExperiment = AnalyticsSearchService.readBuckets(analyticsQueryClient
                .queryBuilder().bioentityIdentifierFacets(-1)
                .inExperiment
                (accession).fetch());

        assertThat(identfiersForThatExperiment, not(Matchers.<String>empty()));


         for(SolrInputDocument solrInputDocument: results){
             String bioentityIdentifier = solrInputDocument.getField("bioentityIdentifier").getValue().toString();
             assertTrue(bioentityIdentifier, identfiersForThatExperiment.contains(bioentityIdentifier));
         }

    }

    void weGenerateDocumentsWhoseContentWeCanThenRetrieve(String accession,Collection<SolrInputDocument> results){
        Map<String,String> keywordFieldsPresent = new HashMap<>();
        for(SolrInputDocument solrInputDocument: results){
            for(String fieldName: solrInputDocument.getFieldNames()){
                if(fieldName.startsWith("keyword_")){
                    //we repeatedly put into the same fields but that's okay I just want one example value per field
                    keywordFieldsPresent.put(fieldName.replace("keyword_",""), solrInputDocument.getFieldValue
                            (fieldName).toString());
                }
            }
        }

        //category searches e.g. symbol:PIM1
        for(Map.Entry<String, String> e: keywordFieldsPresent.entrySet()){
            indexReturnsDataFor(accession, SemanticQuery.create(SemanticQueryTerm.create(e.getValue(), e.getKey())));
        }

        //identifier search searches e.g. symbol:PIM
        for(Map.Entry<String, String> e: keywordFieldsPresent.entrySet()){
            indexReturnsDataFor(accession, SemanticQuery.create(SemanticQueryTerm.create(e.getValue())));
        }

    }

    void theSpeciesFieldIsTheEnsemblName(String accession,Collection<SolrInputDocument> results){
        Set<String> species = new HashSet<>();
        for(SolrInputDocument solrInputDocument: results){
            species.add(solrInputDocument.getField("species").getValue().toString());
        }

        assertThat(species.size(), is(1));

        assertThat(species.iterator().next(), is(experimentTrader.getPublicExperiment(accession).getSpecies()
                .mappedName));

    }

    void indexReturnsDataFor(String accession, SemanticQuery identifierSearch){
        Collection<String> identfiersForThatExperiment = AnalyticsSearchService.readBuckets(
                analyticsQueryClient.queryBuilder()
                        .bioentityIdentifierFacets(-1)
                        .queryIdentifierSearch(identifierSearch)
                        .inExperiment(accession)
                        .fetch()
        );

        assertThat(MessageFormat.format("Nothing in the index for {0} , {1}",accession,identifierSearch.description()),
                identfiersForThatExperiment, not(Matchers.<String>empty()));
    }








}