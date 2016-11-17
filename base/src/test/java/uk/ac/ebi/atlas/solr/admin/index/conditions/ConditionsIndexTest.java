package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConditionsIndexTest {

    private static final String EXPERIMENT_ACCESSION = "AN_ACCESSION";

    @Mock
    private SolrClient solrClientMock;

    @Mock
    private DifferentialConditionsBuilder conditionsPropertiesBuilderMock;

    @Mock
    private BaselineConditionsBuilder baselineConditionsBuilderMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Before
    public void setUp() throws Exception {
        given(differentialExperimentMock.getAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(baselineExperimentMock.getAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(baselineConditionsBuilderMock.buildProperties(Mockito.eq(differentialExperimentMock))).willReturn(ImmutableList.<Condition>of());
    }

    @Test
    public void updateConditionsShouldReindexDiffExperiment() throws Exception {
        given(conditionsPropertiesBuilderMock.buildProperties(Mockito.eq(differentialExperimentMock))).willReturn(ImmutableList.<DifferentialCondition>of());
        DifferentialConditionsIndex subject = new DifferentialConditionsIndex(solrClientMock, conditionsPropertiesBuilderMock);

        subject.updateConditions(differentialExperimentMock);

        ArgumentCaptor<String> deleteQueryCaptor = ArgumentCaptor.forClass(String.class);

        verify(solrClientMock).deleteByQuery(deleteQueryCaptor.capture());
        assertThat(deleteQueryCaptor.getValue(), endsWith(EXPERIMENT_ACCESSION));

        verify(conditionsPropertiesBuilderMock).buildProperties(Mockito.eq(differentialExperimentMock));

        verify(solrClientMock).addBeans(CollectionUtils.EMPTY_COLLECTION);
    }

    @Test
    public void addConditionsBaselineExtractsFactorAndSampleValues() throws Exception {
        given(baselineExperimentMock.getAssayGroups()).willReturn(new AssayGroups(Collections.singleton(new AssayGroup("g1","a1"))));
        given(baselineExperimentMock.getExperimentDesign()).willReturn(experimentDesignMock);
        given(experimentDesignMock.getFactorValues("a1")).willReturn(ImmutableMap.of("ORGANISM_PART", "brain"));
        given(experimentDesignMock.getSampleCharacteristicsValues("a1")).willReturn(ImmutableMap.of("sex", "female"));
        given(baselineConditionsBuilderMock.buildProperties(Mockito.any(Experiment.class)))
            .willReturn(ImmutableList.of(new Condition(EXPERIMENT_ACCESSION, "g1", ImmutableList.of("female", "brain"))));

        BaselineConditionsIndex subject = new BaselineConditionsIndex(solrClientMock, baselineConditionsBuilderMock);

        subject.addConditions(baselineExperimentMock);

        ArgumentCaptor<Collection> addBeansQueryCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(solrClientMock).addBeans(addBeansQueryCaptor.capture());

        Collection<Condition> beans = addBeansQueryCaptor.getValue();

        assertThat(beans, containsInAnyOrder(new Condition(EXPERIMENT_ACCESSION, "g1", ImmutableList.of("female", "brain"))));

    }

    @Test
    public void addConditionsBaselineIncludesOntologyTerms() throws Exception {
        given(baselineExperimentMock.getAssayGroups()).willReturn(new AssayGroups(Collections.singleton(new AssayGroup("g1","a1"))));
        given(baselineExperimentMock.getExperimentDesign()).willReturn(experimentDesignMock);
        given(experimentDesignMock.getFactorValues("a1")).willReturn(ImmutableMap.of("ORGANISM_PART", "brain"));
        given(experimentDesignMock.getSampleCharacteristicsValues("a1")).willReturn(ImmutableMap.of("sex", "female"));
        given(baselineConditionsBuilderMock.buildProperties(Mockito.any(Experiment.class)))
                .willReturn(ImmutableList.of(new Condition(EXPERIMENT_ACCESSION, "g1", ImmutableList.of("brain", "female", "obo", "efo"))));

        BaselineConditionsIndex subject = new BaselineConditionsIndex(solrClientMock, baselineConditionsBuilderMock);

        subject.addConditions(baselineExperimentMock);

        ArgumentCaptor<Collection> addBeansQueryCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(solrClientMock).addBeans(addBeansQueryCaptor.capture());

        Collection<Condition> beans = addBeansQueryCaptor.getValue();
        Condition condition = beans.iterator().next();

        assertThat(beans, hasSize(1));
        assertThat(condition.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(condition.getAssayGroupId(), is("g1"));
        assertThat(condition.getValues(), containsInAnyOrder("brain", "female", "obo", "efo"));

    }

}
