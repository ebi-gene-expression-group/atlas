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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqBaselineExperimentMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";
    private static final String EXPERIMENT_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";
    private static final String EXPERIMENT_ACCESSION_E_GEOD_30352 = "E-GEOD-30352";

    @Inject
    private CondensedSdrfParser subject;

    @Test
    public void testExtractCharacteristics513() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();
        assertThat(experimentDesign.getSampleHeaders(), containsInAnyOrder("sex", "age", "organism part", "organism", "ethnic group"));
    }

    @Test
    public void testExtractCharacteristics26284() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();
        System.out.println("\"" + Joiner.on("\", \"").join(experimentDesign.getSampleHeaders()) + "\"");
        assertThat(experimentDesign.getSampleHeaders(), containsInAnyOrder("biosource provider", "cell line", "cell type", "cellular component", "disease", "karyotype", "organism", "organism part", "sex"));
    }

    @Test
    public void characteristicHeaderAndValueTerm() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();

        SampleCharacteristic sampleCharacteristic = experimentDesign.getSampleCharacteristic("SRR089334", "karyotype");
        assert sampleCharacteristic != null;

        assertThat(sampleCharacteristic.header(), is("karyotype"));
        assertThat(sampleCharacteristic.value(), is("cancer"));

    }

    @Test
    public void characteristicOntologyTerm() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();

        SampleCharacteristic sampleCharacteristic = experimentDesign.getSampleCharacteristic("SRR089334", "karyotype");
        assert sampleCharacteristic != null;

        assertThat(sampleCharacteristic.valueOntologyTerms().isEmpty(), is(false));

        OntologyTerm ontologyTerm = sampleCharacteristic.valueOntologyTerms().iterator().next();
        assertThat(ontologyTerm.id(), is("EFO_0000311"));
        assertThat(ontologyTerm.source(), is("http://www.ebi.ac.uk/efo/"));
    }

    @Test
    public void allOntologyTerms() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_30352, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();

        ImmutableSetMultimap<String, String> allOntologyTermIdsByAssayAccession = experimentDesign.getAllOntologyTermIdsByAssayAccession();
        String characteristicOntologyTermIdSex = "NCBITaxon_13616";
        String characteristicOntologyTermIdOrganismPart = "EFO_0001265";
        String factorOntologyTermIdOrganismPart = "UBERON_0000948";

        assertThat(allOntologyTermIdsByAssayAccession.get("SRR306747"), containsInAnyOrder(characteristicOntologyTermIdSex, characteristicOntologyTermIdOrganismPart, factorOntologyTermIdOrganismPart));

    }


    @Test
    public void testGetSpeciesForAssays() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("ERR030886", "ERR030883"));
        assertThat(species, containsInAnyOrder("Homo sapiens"));

    }

    @Test
    public void testGetSpeciesForAssaysOnMultispeciesExperiment() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_30352, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("SRR306848", "SRR306747"));
        assertThat(species, containsInAnyOrder("Homo sapiens", "Monodelphis domestica"));

    }

    @Test
    public void testGetFactors() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();

        Factor factor = new Factor("organism part", "adipose tissue");

        FactorSet err030880 = experimentDesign.getFactors("ERR030880");
        assertThat(err030880, contains(factor));

        Factor organismPart = err030880.iterator().next();
        assertThat(organismPart.getValueOntologyTerms().isEmpty(), is(false));

        OntologyTerm ontologyTerm = organismPart.getValueOntologyTerms().iterator().next();
        assertThat(ontologyTerm.id(), is("UBERON_0001013"));
        assertThat(ontologyTerm.source(), is("http://purl.obolibrary.org/obo/"));
    }

    @Test
    public void asTableDataShouldReturnTheRightStuff() throws IOException {
        CondensedSdrfParserOutput condensedSdrfParserOutput = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513, ExperimentType.RNASEQ_MRNA_BASELINE);
        ExperimentDesign experimentDesign = condensedSdrfParserOutput.getExperimentDesign();
        SetMultimap<String, String> ontologyTerms = experimentDesign.getAllOntologyTermIdsByAssayAccession();

        assertThat(experimentDesign.asTableData().size(), is(48));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("ERR030856", null, null, "Homo sapiens", "16 tissues mixture", null, "16 tissues mixture"));
        // TODO https://www.pivotaltracker.com/story/show/100371514
        assertThat(experimentDesign.asTableData().get(47), arrayContaining("ERR030903", "60 year", "Caucasian", "Homo sapiens", "thyroid", "female", "thyroid"));

        assertThat(ontologyTerms.entries().size(), is(142));
        assertThat(ontologyTerms.keySet().size(), is(48));
        assertThat(ontologyTerms.get("ERR030856"), containsInAnyOrder("NCBITaxon_9606"));
        assertThat(ontologyTerms.get("ERR030887"), containsInAnyOrder("NCBITaxon_9606", "UBERON_0002107", "EFO_0001266", "EFO_0003156"));

    }

    @Test
    public void asTableDataForMultifactorShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284, ExperimentType.RNASEQ_MRNA_BASELINE).getExperimentDesign();

        assertThat(experimentDesign.asTableData().size(), is(171));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("SRR089332", "Coriell Cell Repositories http://ccr.coriell.org/Sections/Search/Search.aspx?PgId=165&q=GM12878", "GM12878", "B cell", "whole cell", null, "relatively normal", "Homo sapiens", null, "female", "total RNA", "GM12878", "whole cell"));
        assertThat(experimentDesign.asTableData().get(170), arrayContaining("SRR534335", "PromoCell", "hMSC-AT cell line", "human mesenchymal stem cell from adipose tissue (hMSC-AT)", "whole cell", null, null, "Homo sapiens", "adipose tissue",null, "total RNA","hMSC-AT cell line","whole cell"));
    }

}