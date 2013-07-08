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

package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.model.differential.*;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayProfileBuilderTest {

    private static final String DESIGN_ELEMENT_NAME = "designElementName";
    private static final String ARRAY_DESIGN = "arraydesign";
    private static final String CONTRAST_NAME1 = "a";
    private static final String CONTRAST_NAME2 = "b";
    private static final String GENE_ID = "geneId";

    @Mock
    Contrast contrastMock1;

    @Mock
    Contrast contrastMock2;

    @Mock
    MicroarrayRequestContext contextMock;

    @Mock
    DifferentialExpression expressionMock;

    @Mock
    private DesignElementMappingProvider designElementMappingProvider;

    MicroarrayProfileBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_NAME1);
        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_NAME2);
        SortedSet<Contrast> sortedSet = new TreeSet();
        sortedSet.add(contrastMock1);
        sortedSet.add(contrastMock2);

        when(contextMock.getCutoff()).thenReturn(0.05);
        when(contextMock.getRegulation()).thenReturn(Regulation.UP_DOWN);
        when(contextMock.getAllQueryFactors()).thenReturn(sortedSet);
        when(contextMock.getSelectedQueryFactors()).thenReturn(Sets.newHashSet(contrastMock1));

        when(expressionMock.isUnderExpressed()).thenReturn(true);

        when(designElementMappingProvider.getEnsGeneId(ARRAY_DESIGN, DESIGN_ELEMENT_NAME)).thenReturn(GENE_ID);

        subject = new MicroarrayProfileBuilder(contextMock, new DifferentialExpressionPrecondition(), new DifferentialProfilePrecondition(), designElementMappingProvider);

    }

    @Test
    public void testCreate() throws Exception {
        MicroarrayProfileBuilder builder = subject.withDesignElementName(DESIGN_ELEMENT_NAME).withArrayDesignAccession(ARRAY_DESIGN);
        builder.addExpression(expressionMock);
        MicroarrayProfile profile = builder.create();
        assertThat(profile.getDesignElementName(), is(DESIGN_ELEMENT_NAME));
        assertThat(profile.getId(), is(GENE_ID));
    }
}