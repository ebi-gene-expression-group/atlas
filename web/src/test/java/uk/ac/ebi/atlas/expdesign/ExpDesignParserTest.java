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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.MatcherAssertionErrors;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.expdesign.ExpDesignParser.SAMPLE_COLUMN_HEADER_PATTERN;
import static uk.ac.ebi.atlas.expdesign.ExpDesignParser.extractMatchingContent;

@RunWith(MockitoJUnitRunner.class)
public class ExpDesignParserTest {

    private static final String EXPERIMENT_ACCESSION = "ACCESSION";
    private static final String FIRST_ASSAY = "C1";
    private static final String RD_INSTAR_LARVA = "3rd instar larva";
    private static final String LAST_ASSAY = "WT3";
    private static final String DUMMY = "dummy";
    private static final String DEVELOPMENTAL_STAGE = "DevelopmentalStage";
    private static final String STRAIN_OR_LINE = "StrainOrLine";
    private static final String GENOTYPE = "GENOTYPE";
    private static final String OREGON_R = "Oregon R";
    private static final String CYC_C_MUTANT = "cycC mutant";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String ASSAY = "Assay";
    private static final String ARRAY = "Array";

    private static final String[] HEADER_LINE = new String[]{ASSAY, ARRAY, "Sample Characteristics[DevelopmentalStage]", "Sample Characteristics[Genotype]", "Sample Characteristics[Organism]", "Sample Characteristics[StrainOrLine]", "Factor Values[GENOTYPE]"};
    private static final String[] FIRST_LINE = new String[]{FIRST_ASSAY, A_AFFY_35, RD_INSTAR_LARVA, "w1118; +; cycCY5", "Drosophila melanogaster", "", CYC_C_MUTANT};
    private static final String[] LAST_LINE = new String[]{LAST_ASSAY, A_AFFY_35, RD_INSTAR_LARVA, "wild_type", "Drosophila melanogaster", OREGON_R, "wild_type"};
    private static final List<String[]> DATA = Lists.newArrayList(HEADER_LINE, FIRST_LINE, LAST_LINE);

    @Mock
    private TsvReaderBuilder tsvReaderBuilderMock;

    @Mock
    private TsvReader tsvReaderMock;

    private ExpDesignParser subject;

    @Before
    public void setUp() throws Exception {
        when(tsvReaderBuilderMock.forTsvFilePathTemplate(anyString())).thenReturn(tsvReaderBuilderMock);
        when(tsvReaderBuilderMock.build()).thenReturn(tsvReaderMock);

        when(tsvReaderMock.readAll(EXPERIMENT_ACCESSION)).thenReturn(DATA);

        subject = new ExpDesignParser();
        subject.setTsvReaderBuilder(tsvReaderBuilderMock);
    }

    @Test
    public void testParseHeaders() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorHeaders(), contains(GENOTYPE));
        assertThat(experimentDesign.getSampleHeaders(), contains(DEVELOPMENTAL_STAGE, "Genotype", "Organism", STRAIN_OR_LINE));
    }

    @Test
    public void testParseFactors() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorValue(FIRST_ASSAY, GENOTYPE), is(CYC_C_MUTANT));
        assertThat(experimentDesign.getFactorValue(FIRST_ASSAY, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getFactorValue(DUMMY, GENOTYPE), is(nullValue()));
    }

    @Test
    public void testParseSamples() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getSampleValue(FIRST_ASSAY, DEVELOPMENTAL_STAGE), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleValue(LAST_ASSAY, DEVELOPMENTAL_STAGE), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleValue(FIRST_ASSAY, STRAIN_OR_LINE), is(""));
        assertThat(experimentDesign.getSampleValue(LAST_ASSAY, STRAIN_OR_LINE), is(OREGON_R));
        assertThat(experimentDesign.getSampleValue(FIRST_ASSAY, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getSampleValue(DUMMY, DEVELOPMENTAL_STAGE), is(nullValue()));
    }

    @Test
    public void testAssays() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getArrayDesign(FIRST_ASSAY), is(A_AFFY_35));
        assertThat(experimentDesign.getArrayDesign(LAST_ASSAY), is(A_AFFY_35));
    }

    @Test
    public void testAssayHeaders() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getAssayHeaders(), arrayContaining(ASSAY, ARRAY));
    }

    @Test
    public void testGetAllRunOrAssay() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getAllRunOrAssay(), contains(FIRST_ASSAY, LAST_ASSAY));
    }

    @Test
    public void testExtractGroup1Match() throws Exception {
        String matchingString = extractMatchingContent("Assay Bello", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, is(nullValue()));

        matchingString = extractMatchingContent("Assay Bello[assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, is(nullValue()));

        matchingString = extractMatchingContent(" Sample Characteristics[bello assai] ", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, is("bello assai"));

        matchingString = extractMatchingContent("Sample Characteristics[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, is("bello assai"));

        matchingString = extractMatchingContent("Sample  Characteristics[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, is(nullValue()));
    }
}