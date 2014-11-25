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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqExperimentMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";

    private static final String EXPERIMENT_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";

    @Inject
    private MageTabLimpopoUtils mageTabLimpopoUtils;

    @Inject
    private ValueAndUnitJoiner valueAndUnitJoiner;

    private RnaSeqExperimentMageTabParser subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqExperimentMageTabParser();
        subject.setMageTabLimpopoUtils(mageTabLimpopoUtils);
        subject.setValueAndUnitJoiner(valueAndUnitJoiner);
    }

    @Test
    public void testExtractCharacteristics513() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513).getExperimentDesign();
        assertThat(experimentDesign.getSampleHeaders(), containsInAnyOrder("sex", "age", "organism part", "Organism", "ethnic group"));
    }

    @Test
    public void testExtractCharacteristics26284() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284).getExperimentDesign();
        assertThat(experimentDesign.getSampleHeaders(), containsInAnyOrder("sex", "biosource provider", "cell line", "cellular component", "organism part", "karyotype", "disease state", "cell type", "Organism"));
    }

    @Test
    public void characteristicHeaderAndValueTerm() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284).getExperimentDesign();

        SampleCharacteristic sampleCharacteristic = experimentDesign.getSampleCharacteristic("SRR089334", "karyotype");
        assert sampleCharacteristic != null;

        assertThat(sampleCharacteristic.header(), is("karyotype"));
        assertThat(sampleCharacteristic.value(), is("cancer"));

    }

    @Test
    public void characteristicOntologyTerm() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284).getExperimentDesign();

        SampleCharacteristic sampleCharacteristic = experimentDesign.getSampleCharacteristic("SRR089334", "karyotype");
        assert sampleCharacteristic != null;

        Set<OntologyTerm> ontologyTermOptional = sampleCharacteristic.valueOntologyTerms();

        assertThat(ontologyTermOptional.isEmpty(), is(false));
        assertThat(OntologyTermUtils.joinIds(ontologyTermOptional), is("EFO_0000616"));
        assertThat(OntologyTermUtils.joinSources(ontologyTermOptional), is("EFO"));
    }

    @Test
    public void allOntologyTerms() throws IOException {
        ExperimentDesign experimentDesign = subject.parse("E-GEOD-30352").getExperimentDesign();

        ImmutableSetMultimap<String, String> allOntologyTermIdsByAssayAccession = experimentDesign.getAllOntologyTermIdsByAssayAccession();
        String characteristicOntologyTermIdSex = "NCBITaxon:13616";
        String characteristicOntologyTermIdOrganismPart = "EFO:0001265";
        String factorOntologyTermIdOrganismPart = "UBERON:0000948";

        assertThat(allOntologyTermIdsByAssayAccession.get("SRR306747"), containsInAnyOrder(characteristicOntologyTermIdSex, characteristicOntologyTermIdOrganismPart, factorOntologyTermIdOrganismPart));

    }


    @Test
    public void testGetSpeciesForAssays() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513).getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("ERR030886", "ERR030883"));
        assertThat(species, containsInAnyOrder("Homo sapiens"));

    }

    @Test
    public void testGetSpeciesForAssaysOnMultispeciesExperiment() throws IOException {
        ExperimentDesign experimentDesign = subject.parse("E-GEOD-30352").getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("SRR306848", "SRR306747"));
        assertThat(species, containsInAnyOrder("Homo sapiens", "Monodelphis domestica"));

    }

    @Test
    public void testGetFactors() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513).getExperimentDesign();

        Factor factor = new Factor("organism part", "adipose");

        FactorSet err030880 = experimentDesign.getFactors("ERR030880");
        assertThat(err030880, contains(factor));

        Factor organismPart = err030880.iterator().next();
        Set<OntologyTerm> valueOntologyTerm = organismPart.getValueOntologyTerms();
        assertThat(valueOntologyTerm.isEmpty(), is(false));
        assertThat(OntologyTermUtils.joinIds(valueOntologyTerm), is("UBERON:0001013"));
        assertThat(OntologyTermUtils.joinSources(valueOntologyTerm), is("UBERON"));
    }

    @Test
    public void asTableDataShouldReturnTheRightStuff() throws IOException {
        MageTabParserOutput mageTabParserOutput = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513);
        ExperimentDesign experimentDesign = mageTabParserOutput.getExperimentDesign();
        SetMultimap<String, String> ontologyTerms = experimentDesign.getAllOntologyTermIdsByAssayAccession();

        assertThat(experimentDesign.asTableData().size(), is(48));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("ERR030856","Homo sapiens",null,null,"16 tissues mixture",null,"16 tissues mixture"));
        assertThat(experimentDesign.asTableData().get(47), arrayContaining("ERR030903","Homo sapiens","60 years","Caucasian","thyroid","female","thyroid"));

        assertThat(ontologyTerms.entries().size(), is(142));
        assertThat(ontologyTerms.keySet().size(), is(48));
        assertThat(ontologyTerms.get("ERR030856"), containsInAnyOrder("NCBITaxon:9606"));
        assertThat(ontologyTerms.get("ERR030887"), containsInAnyOrder("NCBITaxon:9606", "UBERON:0002107", "EFO:0001266", "EFO:0003156"));

    }

    @Test
    public void asTableDataForMultifactorShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284).getExperimentDesign();

        assertThat(experimentDesign.asTableData().size(), is(171));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("SRR089332", "Homo sapiens", "Coriell Cell Repositories http://ccr.coriell.org/Sections/Search/Search.aspx?PgId=165&q=GM12878", "GM12878", "B cell", "whole cell", null, "relatively normal", null, "female", "total RNA", "GM12878", "whole cell"));
        assertThat(experimentDesign.asTableData().get(170), arrayContaining("SRR534335","Homo sapiens","PromoCell","hMSC-AT cell line","human mesenchymal stem cell from adipose tissue (hMSC-AT)","whole cell",null,null,"adipose",null,"total RNA","hMSC-AT cell line","whole cell"));
    }

}