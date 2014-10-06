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

package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentDesignParserWithOntologyTermsTest {

    private static final String EXPERIMENT_ACCESSION = "ACCESSION";
    private static final String ASSAY_ACCESSION_1 = "C1";
    private static final String RD_INSTAR_LARVA = "3rd instar larva";
    private static final String ASSAY_ACCESSION_2 = "WT3";
    private static final String DUMMY = "dummy";
    private static final String SAMPLE_NAME_1 = "DevelopmentalStage";
    private static final String SAMPLE_NAME_2 = "StrainOrLine";
    private static final String GENOTYPE = "GENOTYPE";
    private static final String OREGON_R = "Oregon R";
    private static final String CYC_C_MUTANT = "cycC mutant";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String ASSAY = "Assay";
    private static final String ARRAY = "Array";
    private static final String SPECIES_1 = "Drosophila melanogaster";
    private static final String SPECIES_2 = "Rabbit";

    private static final String ONTOLOGY_TERM_1 = "ONTOLOGY TERM 1";
    private static final String ONTOLOGY_TERM_2 = "ONTOLOGY TERM 2";

    private static final String[] HEADER_LINE = new String[]{ASSAY, ARRAY, "Sample Characteristics[DevelopmentalStage]", "Sample Characteristics[Genotype]", "Sample Characteristics[Organism]", "Sample Characteristics[StrainOrLine]", "Factor Values[GENOTYPE]", "Factor Value Ontology Term ID[GENOTYPE]"};
    private static final String[] FIRST_LINE = new String[]{ASSAY_ACCESSION_1, A_AFFY_35, RD_INSTAR_LARVA, "w1118; +; cycCY5", SPECIES_1, "", CYC_C_MUTANT, ONTOLOGY_TERM_1};
    private static final String[] LAST_LINE = new String[]{ASSAY_ACCESSION_2, A_AFFY_35, RD_INSTAR_LARVA, "wild_type", SPECIES_2, OREGON_R, "wild_type", ONTOLOGY_TERM_2};
    private static final List<String[]> DATA = Lists.newArrayList(HEADER_LINE, FIRST_LINE, LAST_LINE);
    private static final Factor FACTOR1 = new Factor(GENOTYPE, CYC_C_MUTANT, OntologyTerm.create(ONTOLOGY_TERM_1));
    private static final Factor FACTOR2 = new Factor(GENOTYPE, "wild_type", OntologyTerm.create(ONTOLOGY_TERM_2));

    @Mock
    private TsvReaderBuilder tsvReaderBuilderMock;

    @Mock
    private TsvReader tsvReaderMock;

    private ExperimentDesignParser subject;

    @Before
    public void setUp() throws Exception {
        when(tsvReaderBuilderMock.forTsvFilePathTemplate(anyString())).thenReturn(tsvReaderBuilderMock);
        when(tsvReaderBuilderMock.withExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(tsvReaderBuilderMock);
        when(tsvReaderBuilderMock.build()).thenReturn(tsvReaderMock);

        when(tsvReaderMock.readAll()).thenReturn(DATA);

        subject = new ExperimentDesignParser();
        subject.setTsvReaderBuilder(tsvReaderBuilderMock);
    }

    @Test
    public void testParseHeaders() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorHeaders(), contains(GENOTYPE));
        assertThat(experimentDesign.getSampleHeaders(), contains(SAMPLE_NAME_1, "Genotype", "Organism", SAMPLE_NAME_2));
    }

    @Test
    public void testParseFactors() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, GENOTYPE), is(CYC_C_MUTANT));
        assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getFactorValue(DUMMY, GENOTYPE), is(nullValue()));
        assertThat(experimentDesign.getFactors(ASSAY_ACCESSION_1), contains(FACTOR1));
        assertThat(experimentDesign.getFactors(ASSAY_ACCESSION_2), contains(FACTOR2));
    }

    @Test
    public void testParseSamples() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getSampleValue(ASSAY_ACCESSION_1, SAMPLE_NAME_1), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleValue(ASSAY_ACCESSION_2, SAMPLE_NAME_1), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleValue(ASSAY_ACCESSION_1, SAMPLE_NAME_2), is(""));
        assertThat(experimentDesign.getSampleValue(ASSAY_ACCESSION_2, SAMPLE_NAME_2), is(OREGON_R));
        assertThat(experimentDesign.getSampleValue(ASSAY_ACCESSION_1, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getSampleValue(DUMMY, SAMPLE_NAME_1), is(nullValue()));
    }

    @Test
    public void testAssays() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getArrayDesign(ASSAY_ACCESSION_1), is(A_AFFY_35));
        assertThat(experimentDesign.getArrayDesign(ASSAY_ACCESSION_2), is(A_AFFY_35));
    }

    @Test
    public void testAssayHeaders() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getAssayHeaders(), contains(ASSAY, ARRAY));
    }

    @Test
    public void testGetAllRunOrAssay() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getAllRunOrAssay(), contains(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
    }

    @Test
    public void testGetSpeciesForAssays(){
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
        assertThat(species, Matchers.containsInAnyOrder(SPECIES_1, SPECIES_2));
    }

}