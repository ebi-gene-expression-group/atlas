/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileInputStreamFilterTest {

    public static final String GENE_1 = "Gene1";
    public static final String GENE_2 = "Gene2";

    public static final String ORGANISM_PART_1 = "nose";
    public static final String ORGANISM_PART_2 = "hair";

    @Mock
    private ObjectInputStream<GeneProfile> inputStreamMock;

    @Mock
    private GeneProfile gene1ProfileMock;

    @Mock
    private GeneProfile gene3ProfileMock;

    private Set<String> geneIDs = Sets.newHashSet(GENE_1, GENE_2);

    private Set<String> factorValues = Sets.newHashSet(ORGANISM_PART_1, ORGANISM_PART_2);

    private Set<String> EMPTY_GENE_IDS = Sets.newHashSet();

    private Set<FactorValue> EMPTY_FILTER_FACTOR_VALUES = Sets.newHashSet();

    private GeneProfileInputStreamFilter subject;

    @Before
    public void initMocks() {
        when(gene1ProfileMock.getGeneId()).thenReturn(GENE_2);
        when(gene1ProfileMock.isExpressedOnAnyOf(factorValues)).thenReturn(true);
        when(gene1ProfileMock.getAllFactorValues()).thenReturn(Sets.newHashSet(new FactorValue("ORGANISM_PART", "", ORGANISM_PART_1)));
        when(gene3ProfileMock.getGeneId()).thenReturn("UNACCEPTABLE_GENE");
        when(gene3ProfileMock.isExpressedOnAnyOf(factorValues)).thenReturn(true);
        when(gene3ProfileMock.getAllFactorValues()).thenReturn(Sets.newHashSet(new FactorValue("ORGANISM_PART", "", ORGANISM_PART_2)));

    }

    @Before
    public void initSubject() {
        subject = new GeneProfileInputStreamFilter(inputStreamMock, EMPTY_FILTER_FACTOR_VALUES, geneIDs, factorValues);
    }

    @Test
    public void acceptanceCriteriaTestShouldBeBasedOnGeneIDsSet() {
        //given
        Predicate<GeneProfile> acceptancePredicate = subject.getAcceptanceCriteria();

        //then
        boolean apply = acceptancePredicate.apply(gene1ProfileMock);
        assertThat(apply, is(true));
        //and
        assertThat(acceptancePredicate.apply(gene3ProfileMock), is(false));
    }

    @Test
    public void acceptanceCriteriaTestAlwaysSucceedsWhenTheGeneIDsSetIsEmpty() {
        //given
        subject = new GeneProfileInputStreamFilter(inputStreamMock, EMPTY_FILTER_FACTOR_VALUES, EMPTY_GENE_IDS, factorValues);
        //and
        Predicate<GeneProfile> acceptancePredicate = subject.getAcceptanceCriteria();

        //then
        assertThat(acceptancePredicate.apply(gene1ProfileMock), is(true));
        //and
        assertThat(acceptancePredicate.apply(gene3ProfileMock), is(true));
    }

    @Test
    public void acceptanceCriteriaTestWithFilterFactorValueAndGeneIDsSetIsEmpty() {
        //given
        subject = new GeneProfileInputStreamFilter(inputStreamMock,
                Sets.newHashSet(new FactorValue("ORGANISM_PART", "", ORGANISM_PART_1)), EMPTY_GENE_IDS, factorValues);
        //and
        Predicate<GeneProfile> acceptancePredicate = subject.getAcceptanceCriteria();

        //then
        assertThat(acceptancePredicate.apply(gene1ProfileMock), is(true));
        //and
        assertThat(acceptancePredicate.apply(gene3ProfileMock), is(false));
    }
}
