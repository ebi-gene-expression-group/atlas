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

//ToDo ... this is impossible to test without bootstrapping spring, as most other IT tests


package uk.ac.ebi.atlas.trader.loader;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.ProteomicsBaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ProteomicsBaselineExperimentsCacheLoaderIT {

    private static final String E_PROT_1 = "E-PROT-1";
    private static final String DEVELOPMENTAL_STAGE = "developmental stage";
    private static final String ORGANISM_PART = "organism part";
    private static final String ORGANISM = "organism";

    @Inject
    private ProteomicsBaselineExperimentsCacheLoader subject;

    @Mock
    private ExperimentDAO experimentDao;

    @Mock
    private ArrayExpressClient arrayExpressClient;

    @Before
    public void mockOutDatabaseAndArrayExpress() {
        MockitoAnnotations.initMocks(this);

        Set<String> pubMedIds = Collections.emptySet();
        ExperimentDTO experimentDTO = new ExperimentDTO(E_PROT_1, ExperimentType.PROTEOMICS_BASELINE,
                ImmutableSet.of("Homo sapiens"), pubMedIds, "title", new Date(), false, UUID.randomUUID().toString());
        when(experimentDao.findExperiment(E_PROT_1, true)).thenReturn(experimentDTO);

        when(arrayExpressClient.fetchExperimentName(E_PROT_1)).thenReturn("title");

        subject.setExperimentDAO(experimentDao);
        subject.setArrayExpressClient(arrayExpressClient);
    }

    @Test
    public void correctSpeciesReadFromDatabase() throws Exception {
        //given
        ProteomicsBaselineExperiment experiment = subject.load(E_PROT_1);
        //then
        Set<String> species = experiment.getOrganisms();
        assertThat(species, contains("Homo sapiens"));

    }

    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() throws IOException, ParseException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        assertThat(experiment.getExperimentRunAccessions(), containsInAnyOrder(
                "Adult_Adrenalgland", "Adult_Bcells", "Adult_CD4Tcells", "Adult_CD8Tcells",
                "Adult_Colon", "Adult_Esophagus", "Adult_Frontalcortex", "Adult_Gallbladder",
                "Adult_Heart", "Adult_Kidney", "Adult_Liver", "Adult_Lung",
                "Adult_Monocytes", "Adult_NKcells", "Adult_Ovary", "Adult_Pancreas",
                "Adult_Platelets", "Adult_Prostate", "Adult_Rectum", "Adult_Retina",
                "Adult_Spinalcord", "Adult_Testis", "Adult_Urinarybladder", "Fetal_Brain",
                "Fetal_Gut", "Fetal_Heart", "Fetal_Liver", "Fetal_Ovary",
                "Fetal_Placenta", "Fetal_Testis"
        ));

    }

    @Test
    public void experimentShouldContainAssayGroups() throws IOException, ParseException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        assertThat(experiment.getAssayGroups().getAssayGroupIds(), contains("g10", "g11", "g16", "g17", "g18", "g19",
                "g12", "g13", "g14", "g15", "g30", "g21", "g9", "g22", "g7", "g8", "g20", "g29", "g27", "g28", "g25",
                "g26", "g23", "g24", "g2", "g1", "g6", "g5", "g4", "g3"));
    }

    @Test
    public void experimentalFactors() throws IOException, ParseException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        //ImmutableList<FactorGroup> allFactors = experiment.getExperimentalFactors().getOrderedFactorGroups();
        //System.out.println("\"" + Joiner.on("\", \"").join(allFactors));

        FactorGroup adultAdrenal = new FactorSet(new Factor(DEVELOPMENTAL_STAGE, "adult"), new Factor(ORGANISM_PART, "adrenal gland"));
        FactorGroup fetusTestis = new FactorSet(new Factor(DEVELOPMENTAL_STAGE, "fetus"), new Factor(ORGANISM_PART, "testis"));
        assertThat(experiment.getExperimentalFactors().getFactorGroupByAssayGroupId("g1"), is(adultAdrenal));
        assertThat(experiment.getExperimentalFactors().getFactorGroupByAssayGroupId("g30"), is(fetusTestis));
    }

    @Test
    public void experimentDesign() throws IOException, ParseException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        assertThat(experimentDesign.getFactorHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM_PART));
        assertThat(experimentDesign.getSampleHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM, ORGANISM_PART));

        Iterator<SampleCharacteristic> sampleCharacteristicIterator = experimentDesign.getSampleCharacteristics("Adult_Ovary").iterator();

        SampleCharacteristic sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(ORGANISM_PART));
        assertThat(sampleCharacteristic.value(), is("ovary"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), is("http://www.ebi.ac.uk/efo/EFO_0000973"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(ORGANISM));
        assertThat(sampleCharacteristic.value(), is("Homo sapiens"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), is("http://purl.obolibrary.org/obo/NCBITaxon_9606"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(DEVELOPMENTAL_STAGE));
        assertThat(sampleCharacteristic.value(), is("adult"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), is("http://www.ebi.ac.uk/efo/EFO_0001272"));

        assertThat(experimentDesign.asTableData(), hasSize(30));
        assertThat(experimentDesign.asTableData(), contains(
                arrayContaining("Adult_Adrenalgland", "adult", "Homo sapiens", "adrenal gland", "adult", "adrenal gland"),
                arrayContaining("Adult_Bcells", "adult", "Homo sapiens", "B cell", "adult", "B cell"),
                arrayContaining("Adult_CD4Tcells", "adult", "Homo sapiens", "CD4-positive T cell", "adult", "CD4-positive T cell"),
                arrayContaining("Adult_CD8Tcells", "adult", "Homo sapiens", "CD8-positive T cell", "adult", "CD8-positive T cell"),
                arrayContaining("Adult_Colon", "adult", "Homo sapiens", "colon", "adult", "colon"),
                arrayContaining("Adult_Esophagus", "adult", "Homo sapiens", "esophagus", "adult", "esophagus"),
                arrayContaining("Adult_Frontalcortex", "adult", "Homo sapiens", "frontal cortex", "adult", "frontal cortex"),
                arrayContaining("Adult_Gallbladder", "adult", "Homo sapiens", "gallbladder", "adult", "gallbladder"),
                arrayContaining("Adult_Heart", "adult", "Homo sapiens", "heart", "adult", "heart"),
                arrayContaining("Adult_Kidney", "adult", "Homo sapiens", "kidney", "adult", "kidney"),
                arrayContaining("Adult_Liver", "adult", "Homo sapiens", "liver", "adult", "liver"),
                arrayContaining("Adult_Lung", "adult", "Homo sapiens", "lung", "adult", "lung"),
                arrayContaining("Adult_Monocytes", "adult", "Homo sapiens", "monocyte", "adult", "monocyte"),
                arrayContaining("Adult_NKcells", "adult", "Homo sapiens", "natural killer cell", "adult", "natural killer cell"),
                arrayContaining("Adult_Ovary", "adult", "Homo sapiens", "ovary", "adult", "ovary"),
                arrayContaining("Adult_Pancreas", "adult", "Homo sapiens", "pancreas", "adult", "pancreas"),
                arrayContaining("Adult_Platelets", "adult", "Homo sapiens", "platelet", "adult", "platelet"),
                arrayContaining("Adult_Prostate", "adult", "Homo sapiens", "prostate", "adult", "prostate"),
                arrayContaining("Adult_Rectum", "adult", "Homo sapiens", "rectum", "adult", "rectum"),
                arrayContaining("Adult_Retina", "adult", "Homo sapiens", "retina", "adult", "retina"),
                arrayContaining("Adult_Spinalcord", "adult", "Homo sapiens", "spinal cord", "adult", "spinal cord"),
                arrayContaining("Adult_Testis", "adult", "Homo sapiens", "testis", "adult", "testis"),
                arrayContaining("Adult_Urinarybladder", "adult", "Homo sapiens", "urinary bladder", "adult", "urinary bladder"),
                arrayContaining("Fetal_Brain", "fetus", "Homo sapiens", "brain", "fetus", "brain"),
                arrayContaining("Fetal_Gut", "fetus", "Homo sapiens", "gut", "fetus", "gut"),
                arrayContaining("Fetal_Heart", "fetus", "Homo sapiens", "heart", "fetus", "heart"),
                arrayContaining("Fetal_Liver", "fetus", "Homo sapiens", "liver", "fetus", "liver"),
                arrayContaining("Fetal_Ovary", "fetus", "Homo sapiens", "ovary", "fetus", "ovary"),
                arrayContaining("Fetal_Placenta", "fetus", "Homo sapiens", "placenta", "fetus", "placenta"),
                arrayContaining("Fetal_Testis", "fetus", "Homo sapiens", "testis", "fetus", "testis")));
    }

}
