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

package uk.ac.ebi.atlas.commands.download;

import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqProfilesTSVWriterTest {

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private RnaSeqProfile geneProfileMock;

    @Mock
    private DifferentialExpression expressionMock;

    @Mock
    private RnaSeqRequestContext rnaSeqRequestContextMock;

    private RnaSeqProfilesTSVWriter subject;


    @Before
    public void initMocks() {
        when(geneProfileMock.getExpression(any(Contrast.class))).thenReturn(expressionMock);

        subject = new RnaSeqProfilesTSVWriter();
    }

    @Test
    public void buildConditionExpressionsHeaders() throws Exception {
        Contrast contrast1 = mock(Contrast.class);
        when(contrast1.getDisplayName()).thenReturn("cond1");
        Contrast contrast2 = mock(Contrast.class);
        when(contrast2.getDisplayName()).thenReturn("cond2");

        TreeSet<Contrast> conditions = Sets.newTreeSet();
        conditions.add(contrast1);
        conditions.add(contrast2);
        String[] columnNames = subject.getConditionColumnHeaders(conditions);
        assertThat(columnNames.length, is(4));
        assertThat(columnNames, arrayContaining("cond1.p-value", "cond1.log2foldchange", "cond2.p-value", "cond2.log2foldchange"));
    }

    @Test
    public void testBuildExpressionsRow() throws Exception {

        //given
        when(expressionMock.getFoldChange()).thenReturn(-0.978932452151424);
        when(expressionMock.getLevel()).thenReturn(0.134707651014487);

        SortedSet<Contrast> contrasts = new TreeSet<>();
        contrasts.add(new Contrast("id1", null, null, "name"));

        //when
        String[] expressions = subject.extractConditionLevels(geneProfileMock, contrasts);

        //then
        assertThat(expressions.length, Matchers.is(2));
        assertThat(expressions, is(new String[]{"0.134707651014487", "-0.978932452151424"}));

    }

    @Test
    public void testGetValueToString() throws Exception {
        assertThat(subject.getValueToString(0.134707651014487), is("0.134707651014487"));
        assertThat(subject.getValueToString(Double.POSITIVE_INFINITY), is("NA"));
        assertThat(subject.getValueToString(Double.NEGATIVE_INFINITY), is("NA"));

    }

    @Test
    public void testRemoveTrailingZero(){
        assertThat(subject.removeTrailingZero(1.111111), is("1.1111"));
        assertThat(subject.removeTrailingZero(1.100111), is("1.1001"));
        assertThat(subject.removeTrailingZero(1.111100), is("1.1111"));
        assertThat(subject.removeTrailingZero(1.110000), is("1.11"));
    }
}
