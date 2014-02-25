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

package uk.ac.ebi.atlas.streams.differential.rnaseq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.streams.differential.IsDifferentialExpressionAboveCutOff;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqProfileBuilderTest {

    public static final String CONTRAST_NAME1 = "a";
    public static final String CONTRAST_NAME2 = "b";
    public static final String GENE_ID = "geneId";
    private static final String GENE_NAME = "aGeneName";

    @Mock
    Contrast contrastMock1;

    @Mock
    Contrast contrastMock2;

    @Mock
    DifferentialExpression expressionMock;

    RnaSeqProfileReusableBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_NAME1);
        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_NAME2);
        SortedSet<Contrast> sortedSet = new TreeSet();
        sortedSet.add(contrastMock1);
        sortedSet.add(contrastMock2);

        when(expressionMock.isUnderExpressed()).thenReturn(true);

        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(0.05);
        expressionFilter.setRegulation(Regulation.UP_DOWN);

        subject = new RnaSeqProfileReusableBuilder(expressionFilter);
    }

    @Test
    public void testCreate() throws Exception {
        RnaSeqProfileReusableBuilder builder = subject.beginNewInstance(GENE_ID, GENE_NAME);
        builder.addExpression(expressionMock);
        DifferentialProfile profile = builder.create();
        assertThat(profile.getId(), is(GENE_ID));
    }
}