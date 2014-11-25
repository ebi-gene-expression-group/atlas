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

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
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
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

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
    private static final String ASSAY_ACCESSION_2 = "WT3";
    private static final String ASSAY_ACCESSION_3 = "A3";
    private static final String RD_INSTAR_LARVA = "3rd instar larva";
    private static final String DUMMY = "dummy";
    private static final String CHARACTERISTIC_1 = "DevelopmentalStage";
    private static final String CHARACTERISTIC_2 = "StrainOrLine";
    private static final String CHARACTERISTIC_3 = "Organism";
    private static final String GENOTYPE = "GENOTYPE";
    private static final String OREGON_R = "Oregon R";
    private static final String CYC_C_MUTANT = "cycC mutant";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String ASSAY = "Assay";
    private static final String ARRAY = "Array";
    private static final String SPECIES_1 = "Drosophila melanogaster";
    private static final String SPECIES_2 = "Rabbit";

    public static final String SPECIES_1_ONTOLOGY_ID = "DROS";
    public static final String SPECIES_2_ONTOLOGY_ID = "RABBIT";
    public static final String HTTP_OBO = "http://purl.obolibrary.org/obo/";
    private static final String SPECIES_1_ONTOLOGY_TERM_SOURCEID = HTTP_OBO + SPECIES_1_ONTOLOGY_ID;
    private static final String SPECIES_2_ONTOLOGY_TERM_SOURCEID = HTTP_OBO + SPECIES_2_ONTOLOGY_ID;
    private static final OntologyTerm SPECIES_1_ONTOLOGY_TERM = OntologyTerm.createFromUri(SPECIES_1_ONTOLOGY_TERM_SOURCEID);

    private static final SampleCharacteristic ASSAY_1_SAMPLE_CHARACTERISTIC_3 = SampleCharacteristic.create("Organism", SPECIES_1, SPECIES_1_ONTOLOGY_TERM);

    private static final String ONTOLOGY_TERM_1 = "ONTOLOGY TERM 1";
    private static final String UBERON_0002107 = "UBERON:0002107";
    private static final String ONTOLOGY_TERM_2 = "http://purl.obolibrary.org/obo/" + UBERON_0002107;

    private static final String[] HEADER_LINE = new String[]{ASSAY, ARRAY, "Sample Characteristic[DevelopmentalStage]", "Sample Characteristic[Genotype]", "Sample Characteristic[Organism]", "Sample Characteristic Ontology Term[Organism]", "Sample Characteristic[StrainOrLine]", "Factor Value[GENOTYPE]", "Factor Value Ontology Term[GENOTYPE]"};
    private static final String[] FIRST_LINE = new String[]{ASSAY_ACCESSION_1, A_AFFY_35, RD_INSTAR_LARVA, "w1118; +; cycCY5", SPECIES_1, SPECIES_1_ONTOLOGY_TERM_SOURCEID, "", CYC_C_MUTANT, ONTOLOGY_TERM_1};
    public static final String CYC = "cyc";
    private static final String[] EMPTY_ONTOLOGY_TERM = new String[]{ASSAY_ACCESSION_3, A_AFFY_35, RD_INSTAR_LARVA, CYC, SPECIES_2, "", OREGON_R, CYC, ""};
    private static final String[] LAST_LINE = new String[]{ASSAY_ACCESSION_2, A_AFFY_35, RD_INSTAR_LARVA, "wild_type", SPECIES_2, SPECIES_2_ONTOLOGY_TERM_SOURCEID, OREGON_R, "wild_type", ONTOLOGY_TERM_2};
    private static final List<String[]> DATA = Lists.newArrayList(HEADER_LINE, FIRST_LINE, EMPTY_ONTOLOGY_TERM, LAST_LINE);
    private static final Factor FACTOR1 = new Factor(GENOTYPE, CYC_C_MUTANT, new OntologyTerm(ONTOLOGY_TERM_1));
    private static final Factor FACTOR2 = new Factor(GENOTYPE, "wild_type", new OntologyTerm(ONTOLOGY_TERM_2));
    private static final String ORGANISM = "Organism";
    private static final SampleCharacteristic SC_RABBIT = SampleCharacteristic.create(ORGANISM, SPECIES_2);
    private static final Factor FACTOR_GENOTYPE = new Factor(GENOTYPE, CYC);

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
        assertThat(experimentDesign.getSampleHeaders(), contains(CHARACTERISTIC_1, "Genotype", "Organism", CHARACTERISTIC_2));
    }

    @Test
    public void testParseFactors() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, GENOTYPE), is(CYC_C_MUTANT));
        assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getFactorValue(DUMMY, GENOTYPE), is(nullValue()));
        assertThat(experimentDesign.getFactors(ASSAY_ACCESSION_1), contains(FACTOR1));
        FactorSet factors = experimentDesign.getFactors(ASSAY_ACCESSION_2);

        assertThat(factors, contains(FACTOR2));
        Factor factor = factors.getFactorByType(GENOTYPE);

        assertThat(factor.getValueOntologyTermId(), is(UBERON_0002107));
    }

    @Test
    public void testParseSamples() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, CHARACTERISTIC_1), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_2, CHARACTERISTIC_1), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, CHARACTERISTIC_2), is(""));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_2, CHARACTERISTIC_2), is(OREGON_R));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getSampleCharacteristicValue(DUMMY, CHARACTERISTIC_1), is(nullValue()));

        SampleCharacteristic sampleCharacteristic = experimentDesign.getSampleCharacteristic(ASSAY_ACCESSION_1, CHARACTERISTIC_3);
        Set<OntologyTerm> ontologyTermOptional = sampleCharacteristic.valueOntologyTerms();
        assertThat(sampleCharacteristic.header().equals(ASSAY_1_SAMPLE_CHARACTERISTIC_3.header()), is (true));
        assertThat(sampleCharacteristic.value().equals(ASSAY_1_SAMPLE_CHARACTERISTIC_3.value()), is (true));
        assertThat(ontologyTermOptional.isEmpty(), is(false));
        assertThat(OntologyTermUtils.joinIds(ontologyTermOptional), is(SPECIES_1_ONTOLOGY_ID));
        assertThat(OntologyTermUtils.joinSources(ontologyTermOptional), is(HTTP_OBO));
    }

    @Test
    public void parseEmptyOntologyTermInSample() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);

        System.out.println("\"" + Joiner.on("\", \"").join(experimentDesign.getSampleCharacteristics(ASSAY_ACCESSION_3)));
        assertThat(experimentDesign.getSampleCharacteristic(ASSAY_ACCESSION_3, ORGANISM), is(SC_RABBIT));
    }


    @Test
    public void parseEmptyOntologyTermInFactor() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);

        System.out.println("\"" + Joiner.on("\", \"").join(experimentDesign.getFactors(ASSAY_ACCESSION_3)));
        Factor factor = experimentDesign.getFactor(ASSAY_ACCESSION_3, GENOTYPE);
        assertThat(factor, is(FACTOR_GENOTYPE));
        assertThat(factor.getValueOntologyTerms().isEmpty(), is(true));

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
        assertThat(experimentDesign.getAllRunOrAssay(), contains(ASSAY_ACCESSION_3, ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
    }

    @Test
    public void testGetSpeciesForAssays() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
        assertThat(species, Matchers.containsInAnyOrder(SPECIES_1, SPECIES_2));
    }

}