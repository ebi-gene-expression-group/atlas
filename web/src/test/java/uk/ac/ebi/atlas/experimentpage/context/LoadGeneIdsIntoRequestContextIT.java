package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class LoadGeneIdsIntoRequestContextIT {

    @Inject
    private SolrQueryService subject;

    @Inject
    BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext baselineRequestContext;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;


    public void populateRequestContext(String experimentAccession) throws ExecutionException {
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(experimentAccession);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        baselineRequestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences)
                .build();
    }

    @Test
    public void mirbaseGeneIdsAreExpanded() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setGeneQuery(GeneQuery.create("hsa-mir-636"));
        populateRequestContext("E-MTAB-1733");
        Optional<GeneQueryResponse> geneQueryResponse = subject.fetchResponseBasedOnRequestContext
                (baselineRequestContext, "homo sapiens");

        assertTrue(geneQueryResponse.isPresent());
        assertThat(geneQueryResponse.get().getAllGeneIds(), contains("ENSG00000207556", "MIMAT0003306"));
    }


}