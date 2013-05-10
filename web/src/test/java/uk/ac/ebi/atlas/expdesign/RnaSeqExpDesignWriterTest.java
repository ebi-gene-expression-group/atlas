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

package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqExpDesignWriterTest {

    private static final String ACCESSION = "ACCESSION";
    private static final String RUN = "RUN";
    private static final String SEX = "sex";
    private static final String AGE = "age";
    private static final String ORGANISM = "organism";
    private static final String RNA = "RNA";

    RnaSeqExpDesignWriter subject;

    @Mock
    MageTabLimpopoExpDesignParser mageTabLimpopoExpDesignParserMock;

    @Mock
    ScanNode scanNodeMock;

    Set<String> characteristics = Sets.newHashSet(SEX, AGE, ORGANISM);

    Set<String> factors = Sets.newHashSet(RNA, AGE);

    List<String> characteristicsList = Lists.newArrayList(characteristics);

    List<String> factorsList = Lists.newArrayList(factors);

    @Before
    public void setUp() throws Exception {
        Collections.sort(characteristicsList);
        Collections.sort(factorsList);

        when(mageTabLimpopoExpDesignParserMock.forExperimentAccession(ACCESSION)).thenReturn(mageTabLimpopoExpDesignParserMock);
        when(mageTabLimpopoExpDesignParserMock.build()).thenReturn(mageTabLimpopoExpDesignParserMock);
        when(mageTabLimpopoExpDesignParserMock.extractCharacteristics()).thenReturn(characteristics);
        when(mageTabLimpopoExpDesignParserMock.extractFactors()).thenReturn(factors);
        when(mageTabLimpopoExpDesignParserMock.getScanNodeForRunAccession(RUN)).thenReturn(scanNodeMock);
        when(mageTabLimpopoExpDesignParserMock.findCharacteristicValueForScanNode(scanNodeMock, SEX)).thenReturn(new String[]{"male"});
        when(mageTabLimpopoExpDesignParserMock.findCharacteristicValueForScanNode(scanNodeMock, AGE)).thenReturn(new String[]{"60"});
        when(mageTabLimpopoExpDesignParserMock.findCharacteristicValueForScanNode(scanNodeMock, ORGANISM)).thenReturn(new String[]{"Homo sapiens"});
        when(mageTabLimpopoExpDesignParserMock.findFactorValueForScanNode(scanNodeMock, RNA)).thenReturn(new String[]{"total RNA"});
        when(mageTabLimpopoExpDesignParserMock.findFactorValueForScanNode(scanNodeMock, AGE)).thenReturn(new String[]{"60"});
        when(mageTabLimpopoExpDesignParserMock.extractRunAccessions()).thenReturn(Sets.newHashSet(RUN));

        subject = new RnaSeqExpDesignWriter(mageTabLimpopoExpDesignParserMock);
    }

    @Test
    public void testForExperimentAccession() throws Exception {
        subject.forExperimentAccession(ACCESSION);

        verify(mageTabLimpopoExpDesignParserMock).forExperimentAccession(ACCESSION);
        verify(mageTabLimpopoExpDesignParserMock).build();
        verify(mageTabLimpopoExpDesignParserMock).extractCharacteristics();
        verify(mageTabLimpopoExpDesignParserMock).extractFactors();
        verify(mageTabLimpopoExpDesignParserMock).extractRunAccessions();
        verify(mageTabLimpopoExpDesignParserMock).getScanNodeForRunAccession(RUN);
        verify(mageTabLimpopoExpDesignParserMock).findCharacteristicValueForScanNode(scanNodeMock, SEX);
        verify(mageTabLimpopoExpDesignParserMock).findCharacteristicValueForScanNode(scanNodeMock, AGE);
        verify(mageTabLimpopoExpDesignParserMock).findCharacteristicValueForScanNode(scanNodeMock, ORGANISM);
        verify(mageTabLimpopoExpDesignParserMock).findFactorValueForScanNode(scanNodeMock, RNA);
        verify(mageTabLimpopoExpDesignParserMock).findFactorValueForScanNode(scanNodeMock, AGE);
    }

    @Test
    public void testComposeHeader() {
        assertThat(subject.composeHeader(characteristicsList, factorsList), is("Run\tSample Characteristics[age]\tSample Characteristics[organism]\tSample Characteristics[sex]\tFactor Values[RNA]\tFactor Values[age]"));
    }

    @Test
    public void testComposeExperimentRun() {
        assertThat(subject.composeExperimentRun(RUN, characteristicsList, factorsList), is("RUN\t60\tHomo sapiens\tmale\ttotal RNA\t60"));
    }
}