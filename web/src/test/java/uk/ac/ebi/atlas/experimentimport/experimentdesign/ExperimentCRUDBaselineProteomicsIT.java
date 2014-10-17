package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.*;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTraderFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDBaselineProteomicsIT {


    public static final String E_PROT_1 = "E-PROT-1";

    @Inject
    private ExperimentChecker experimentChecker;

    @Inject
    private AnalyticsLoaderFactory analyticsLoaderFactory;

    @Inject
    private ExperimentMetadataCRUDFactory experimentMetadataCRUDFactory;

    @Inject
    private ConditionsIndexTraderFactory conditionsIndexTraderFactory;

    @Mock
    private AnalyticsDao analyticsDao;

    @Mock
    private ExperimentDAO experimentDao;

    @Mock
    private SolrServer solrServer;

    private ExperimentCRUD subject;

    @Inject
    private ConfigurationTrader configurationTrader;

    @Captor
    ArgumentCaptor<Collection<Condition>> beansCaptor;

    @Captor
    ArgumentCaptor<ExperimentDTO> experimentDTOCaptor;

    @Before
    public void mockOutDatabaseAndSolr() {
        MockitoAnnotations.initMocks(this);

        ExperimentDTO experimentDTO = new ExperimentDTO(E_PROT_1, ExperimentType.PROTEOMICS_BASELINE, Collections.<String>emptySet(), "title", new Date(), false, UUID.randomUUID().toString());
        when(experimentDao.findExperiment(E_PROT_1, true)).thenThrow(new ResourceNotFoundException("")).thenReturn(experimentDTO);
        when(experimentDao.findPublicExperiment(E_PROT_1)).thenReturn(experimentDTO);

        subject = new ExperimentCRUD();
        subject.setAnalyticsDao(analyticsDao);
        subject.setAnalyticsLoaderFactory(analyticsLoaderFactory);
        subject.setExperimentChecker(experimentChecker);
        subject.setConfigurationTrader(configurationTrader);

        ConditionsIndexTrader conditionsIndexTrader = conditionsIndexTraderFactory.create(solrServer);
        ExperimentMetadataCRUD experimentMetadataCRUD = experimentMetadataCRUDFactory.create(experimentDao, conditionsIndexTrader);
        subject.setExperimentMetadataCRUD(experimentMetadataCRUD);
    }

    @Test
    public void importExperiment_AddsConditionsToSolr() throws IOException, SolrServerException {
        subject.importExperiment(E_PROT_1, false);

        verify(solrServer).addBeans(beansCaptor.capture());

        Collection<Condition> beans = beansCaptor.getValue();

        System.out.println(Joiner.on(", ").join(beans));

        assertThat(beans, hasSize(30));
        assertThat(beans, hasItem(new Condition(E_PROT_1,"g10", ImmutableList.of("Homo sapiens", "adult", "kidney"))));
    }

    @Test
    public void importExperiment_AddsToDatabase() throws IOException, SolrServerException {
        subject.importExperiment(E_PROT_1, false);

        verify(experimentDao).addExperiment(experimentDTOCaptor.capture(), any(Optional.class));

        ExperimentDTO experimentDTO = experimentDTOCaptor.getValue();

        //System.out.println(experimentDTO);

        assertThat(experimentDTO.getExperimentAccession(), is(E_PROT_1));
        assertThat(experimentDTO.getExperimentType(), is(ExperimentType.PROTEOMICS_BASELINE));
        assertThat(experimentDTO.getSpecies(), contains("Homo sapiens"));
        assertThat(experimentDTO.getPubmedIds(), contains("24669763", "24870542"));
        assertThat(experimentDTO.getTitle(), is("A draft map of the human proteome"));
        assertThat(experimentDTO.isPrivate(), is(false));

    }

    @Test
    public void importExperiment_WritesExpDesignFile() throws IOException, SolrServerException {
    }

}
