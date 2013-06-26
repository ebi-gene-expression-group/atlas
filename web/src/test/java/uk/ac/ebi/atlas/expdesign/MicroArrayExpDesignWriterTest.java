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

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroArrayExpDesignWriterTest {

    private static final String ACCESSION = "ACCESSION";
    private static final String ARRAY = "ARRAY";
    private static final String ASSAY = "ASSAY";
    private static final String SEX = "sex";
    private static final String AGE = "age";
    private static final String ORGANISM = "organism";
    private static final String RNA = "RNA";
    private static final String[] EXPECTED_ASSAY = new String[]{"ASSAY", "ARRAY", "60", "Homo sapiens", "male", "total RNA", "60"};
    private static final String[] EXPECTED_HEADER = new String[]{"Assay", "Array", "Sample Characteristics[age]", "Sample Characteristics[organism]", "Sample Characteristics[sex]", "Factor Values[RNA]", "Factor Values[age]"};

    MicroArrayExpDesignWriter subject;

    @Mock
    MicroArrayExpDesignMageTabParser mageTabLimpopoExpDesignParserMock;

    @Mock
    HybridizationNode scanNodeMock;

    @Mock
    CSVWriter csvWriterMock;

    Set<String> characteristics = Sets.newHashSet(SEX, AGE, ORGANISM);

    Set<String> factors = Sets.newHashSet(RNA, AGE);

    List<String> characteristicsList = Lists.newArrayList(characteristics);

    List<String> factorsList = Lists.newArrayList(factors);

    @Before
    public void setUp() throws Exception {
        Collections.sort(characteristicsList);
        Collections.sort(factorsList);

        when(mageTabLimpopoExpDesignParserMock.extractCharacteristics()).thenReturn(characteristics);
        when(mageTabLimpopoExpDesignParserMock.extractFactors()).thenReturn(factors);
        when(mageTabLimpopoExpDesignParserMock.getHybridizationNodeForAssay(ASSAY)).thenReturn(scanNodeMock);
        when(mageTabLimpopoExpDesignParserMock.findArrayForHybridizationNode(scanNodeMock)).thenReturn(ARRAY);
        when(mageTabLimpopoExpDesignParserMock.findCharacteristicValueForSDRFNode(scanNodeMock, SEX)).thenReturn(Lists.newArrayList("male"));
        when(mageTabLimpopoExpDesignParserMock.findCharacteristicValueForSDRFNode(scanNodeMock, AGE)).thenReturn(Lists.newArrayList("60"));
        when(mageTabLimpopoExpDesignParserMock.findCharacteristicValueForSDRFNode(scanNodeMock, ORGANISM)).thenReturn(Lists.newArrayList("Homo sapiens"));
        when(mageTabLimpopoExpDesignParserMock.findFactorValueForHybridizationNode(scanNodeMock, RNA)).thenReturn(Lists.newArrayList("total RNA"));
        when(mageTabLimpopoExpDesignParserMock.findFactorValueForHybridizationNode(scanNodeMock, AGE)).thenReturn(Lists.newArrayList("60"));
        when(mageTabLimpopoExpDesignParserMock.extractAssays()).thenReturn(Sets.newHashSet(ASSAY));

        subject = new MicroArrayExpDesignWriter(mageTabLimpopoExpDesignParserMock);
    }

    @Test
    public void testForExperimentAccession() throws Exception {
        subject.forExperimentAccession(ACCESSION, csvWriterMock);

        verify(mageTabLimpopoExpDesignParserMock).init(ACCESSION);
        verify(mageTabLimpopoExpDesignParserMock).extractCharacteristics();
        verify(mageTabLimpopoExpDesignParserMock).extractFactors();
        verify(mageTabLimpopoExpDesignParserMock).extractAssays();
        verify(mageTabLimpopoExpDesignParserMock).findArrayForHybridizationNode(scanNodeMock);
        verify(mageTabLimpopoExpDesignParserMock).getHybridizationNodeForAssay(ASSAY);
        verify(mageTabLimpopoExpDesignParserMock).findCharacteristicValueForSDRFNode(scanNodeMock, SEX);
        verify(mageTabLimpopoExpDesignParserMock).findCharacteristicValueForSDRFNode(scanNodeMock, AGE);
        verify(mageTabLimpopoExpDesignParserMock).findCharacteristicValueForSDRFNode(scanNodeMock, ORGANISM);
        verify(mageTabLimpopoExpDesignParserMock).findFactorValueForHybridizationNode(scanNodeMock, RNA);
        verify(mageTabLimpopoExpDesignParserMock).findFactorValueForHybridizationNode(scanNodeMock, AGE);

        verify(csvWriterMock).writeNext(EXPECTED_HEADER);
        verify(csvWriterMock).writeNext(EXPECTED_ASSAY);
    }

    @Test
    public void testComposeHeader() {
        assertThat(subject.composeHeader(characteristicsList, factorsList), is(EXPECTED_HEADER));
    }

    @Test
    public void testComposeExperimentAssay() {
        assertThat(subject.composeExperimentAssay(ASSAY, characteristicsList, factorsList), is(EXPECTED_ASSAY));
    }
}