/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.*;
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
        given(experimentDesignMock.getFactors("a1")).willReturn(ImmutableMap.of("ORGANISM_PART", "brain"));
        given(experimentDesignMock.getSamples("a1")).willReturn(ImmutableMap.of("sex", "female"));

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
        given(experimentDesignMock.getFactors("a1")).willReturn(ImmutableMap.of("ORGANISM_PART", "brain"));
        given(experimentDesignMock.getSamples("a1")).willReturn(ImmutableMap.of("sex", "female"));

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
