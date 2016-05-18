
package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
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
    private SolrServer solrServerMock;

    @Mock
    private DifferentialConditionsBuilder conditionsPropertiesBuilderMock;

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
    }

    @Test
    public void updateConditionsShouldReindexDiffExperiment() throws Exception {
        given(conditionsPropertiesBuilderMock.buildProperties(Mockito.eq(differentialExperimentMock), Mockito.any(SetMultimap.class))).willReturn(CollectionUtils.EMPTY_COLLECTION);
        DifferentialConditionsIndex subject = new DifferentialConditionsIndex(solrServerMock, conditionsPropertiesBuilderMock);

        subject.updateConditions(differentialExperimentMock, null);

        ArgumentCaptor<String> deleteQueryCaptor = ArgumentCaptor.forClass(String.class);

        verify(solrServerMock).deleteByQuery(deleteQueryCaptor.capture());
        assertThat(deleteQueryCaptor.getValue(), endsWith(EXPERIMENT_ACCESSION));

        verify(conditionsPropertiesBuilderMock).buildProperties(Mockito.eq(differentialExperimentMock), Mockito.any(SetMultimap.class));

        verify(solrServerMock).addBeans(CollectionUtils.EMPTY_COLLECTION);
    }

    @Test
    public void addConditionsBaselineExtractsFactorAndSampleValues() throws Exception {
        given(baselineExperimentMock.getAssayGroups()).willReturn(new AssayGroups(Collections.singleton(new AssayGroup("g1","a1"))));
        given(baselineExperimentMock.getExperimentDesign()).willReturn(experimentDesignMock);
        given(experimentDesignMock.getFactorValues("a1")).willReturn(ImmutableMap.of("ORGANISM_PART", "brain"));
        given(experimentDesignMock.getSampleCharacteristicsValues("a1")).willReturn(ImmutableMap.of("sex", "female"));

        BaselineConditionsIndex subject = new BaselineConditionsIndex(solrServerMock, new BaselineConditionsBuilder());

        SetMultimap<String, String> emptyOntologyTerms = ImmutableSetMultimap.of();
        subject.addConditions(baselineExperimentMock, emptyOntologyTerms);

        ArgumentCaptor<Collection> addBeansQueryCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(solrServerMock).addBeans(addBeansQueryCaptor.capture());

        Collection<Condition> beans = addBeansQueryCaptor.getValue();

        assertThat(beans, containsInAnyOrder(new Condition(EXPERIMENT_ACCESSION, "g1", ImmutableList.of("female", "brain"))));

    }

    @Test
    public void addConditionsBaselineIncludesOntologyTerms() throws Exception {
        given(baselineExperimentMock.getAssayGroups()).willReturn(new AssayGroups(Collections.singleton(new AssayGroup("g1","a1"))));
        given(baselineExperimentMock.getExperimentDesign()).willReturn(experimentDesignMock);
        given(experimentDesignMock.getFactorValues("a1")).willReturn(ImmutableMap.of("ORGANISM_PART", "brain"));
        given(experimentDesignMock.getSampleCharacteristicsValues("a1")).willReturn(ImmutableMap.of("sex", "female"));

        SetMultimap<String, String> ontologyTerms =
                new ImmutableSetMultimap.Builder<String, String>()
                        .putAll("a1", "obo", "efo")
                        .build();

        BaselineConditionsIndex subject = new BaselineConditionsIndex(solrServerMock, new BaselineConditionsBuilder());

        subject.addConditions(baselineExperimentMock, ontologyTerms);

        ArgumentCaptor<Collection> addBeansQueryCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(solrServerMock).addBeans(addBeansQueryCaptor.capture());

        Collection<Condition> beans = addBeansQueryCaptor.getValue();
        Condition condition = beans.iterator().next();

        assertThat(beans, hasSize(1));
        assertThat(condition.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(condition.getAssayGroupId(), is("g1"));
        assertThat(condition.getValues(), containsInAnyOrder("brain", "female", "obo", "efo"));

    }

}
