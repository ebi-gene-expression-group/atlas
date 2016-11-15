
package uk.ac.ebi.atlas.trader;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

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

    private static final String SPECIES_1_ONTOLOGY_ID = "DROS";
    private static final String SPECIES_2_ONTOLOGY_ID = "RABBIT";
    private static final String HTTP_OBO = "http://purl.obolibrary.org/obo/";
    private static final String SPECIES_1_ONTOLOGY_TERM_SOURCEID = HTTP_OBO + SPECIES_1_ONTOLOGY_ID;
    private static final String SPECIES_2_ONTOLOGY_TERM_SOURCEID = HTTP_OBO + SPECIES_2_ONTOLOGY_ID;
    private static final OntologyTerm SPECIES_1_ONTOLOGY_TERM = OntologyTerm.createFromURI(SPECIES_1_ONTOLOGY_TERM_SOURCEID);

    private static final SampleCharacteristic ASSAY_1_SAMPLE_CHARACTERISTIC_3 = SampleCharacteristic.create("Organism", SPECIES_1, SPECIES_1_ONTOLOGY_TERM);

    private static final String ONTOLOGY_TERM_1 = "ONTOLOGY TERM 1";
    private static final String UBERON_0002107 = "UBERON:0002107";
    private static final String ONTOLOGY_TERM_2 = "http://purl.obolibrary.org/obo/" + UBERON_0002107;

    private static final String[] HEADER_LINE = new String[]{ASSAY, ARRAY, "Sample Characteristic[DevelopmentalStage]", "Sample Characteristic[Genotype]", "Sample Characteristic[Organism]", "Sample Characteristic Ontology Term[Organism]", "Sample Characteristic[StrainOrLine]", "Factor Value[GENOTYPE]", "Factor Value Ontology Term[GENOTYPE]"};
    private static final String[] FIRST_LINE = new String[]{ASSAY_ACCESSION_1, A_AFFY_35, RD_INSTAR_LARVA, "w1118; +; cycCY5", SPECIES_1, SPECIES_1_ONTOLOGY_TERM_SOURCEID, "", CYC_C_MUTANT, ONTOLOGY_TERM_1};
    private static final String CYC = "cyc";
    private static final String[] EMPTY_ONTOLOGY_TERM = new String[]{ASSAY_ACCESSION_3, A_AFFY_35, RD_INSTAR_LARVA, CYC, SPECIES_2, "", OREGON_R, CYC, ""};
    private static final String[] LAST_LINE = new String[]{ASSAY_ACCESSION_2, A_AFFY_35, RD_INSTAR_LARVA, "wild_type", SPECIES_2, SPECIES_2_ONTOLOGY_TERM_SOURCEID, OREGON_R, "wild_type", ONTOLOGY_TERM_2};
    private static final List<String[]> DATA = Lists.newArrayList(HEADER_LINE, FIRST_LINE, EMPTY_ONTOLOGY_TERM, LAST_LINE);
    private static final Factor FACTOR1 = new Factor(GENOTYPE, CYC_C_MUTANT, OntologyTerm.create(ONTOLOGY_TERM_1));
    private static final Factor FACTOR2 = new Factor(GENOTYPE, "wild_type", OntologyTerm.create(ONTOLOGY_TERM_2));
    private static final String ORGANISM = "Organism";
    private static final SampleCharacteristic SC_RABBIT = SampleCharacteristic.create(ORGANISM, SPECIES_2);
    private static final Factor FACTOR_GENOTYPE = new Factor(GENOTYPE, CYC);

    @Mock
    private FileTsvReaderBuilder fileTsvReaderBuilderMock;

    @Mock
    private TsvReader tsvReaderMock;

    MockDataFileHub dataFileHub = MockDataFileHub.get();

    private ExperimentDesignParser subject;

    @Before
    public void setUp() throws Exception {

        dataFileHub.addTemporaryTsv(MessageFormat.format("/expdesign/ExpDesign-{0}.tsv",EXPERIMENT_ACCESSION), DATA);

        subject = new ExperimentDesignParser(dataFileHub);
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

        Factor factor = factors.factorOfType(GENOTYPE);
        assertThat(factor.getValueOntologyTerms().iterator().next().accession(), is(UBERON_0002107));
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
        assertThat(sampleCharacteristic.header().equals(ASSAY_1_SAMPLE_CHARACTERISTIC_3.header()), is (true));
        assertThat(sampleCharacteristic.value().equals(ASSAY_1_SAMPLE_CHARACTERISTIC_3.value()), is (true));
        assertThat(sampleCharacteristic.valueOntologyTerms().isEmpty(), is(false));

        OntologyTerm ontologyTerm = sampleCharacteristic.valueOntologyTerms().iterator().next();
        assertThat(ontologyTerm.accession(), is(SPECIES_1_ONTOLOGY_ID));
        assertThat(ontologyTerm.source(), is(HTTP_OBO));
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
        String species = experimentDesign.getSpeciesForAssays(Sets.newHashSet(ASSAY_ACCESSION_1));
        assertThat(species, is(SPECIES_1));
    }

}