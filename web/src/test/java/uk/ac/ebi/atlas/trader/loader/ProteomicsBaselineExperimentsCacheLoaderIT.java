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

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ProteomicsBaselineExperimentsCacheLoaderIT {

    private static final String E_PROT_1 = "E-PROT-1";
    private static final String DEVELOPMENTAL_STAGE = "developmental stage";
    private static final String ORGANISM_PART = "organism part";
    public static final String ORGANISM = "organism";

    @Inject
    private ProteomicsBaselineExperimentsCacheLoader subject;

    @Mock
    private ExperimentDAO experimentDao;

    @Before
    public void mockOutDatabase() {
        MockitoAnnotations.initMocks(this);

        Set<String> pubMedIds = Collections.emptySet();
        ExperimentDTO experimentDTO = new ExperimentDTO(E_PROT_1, ExperimentType.PROTEOMICS_BASELINE,
                ImmutableSet.of("Homo sapiens"), pubMedIds, "title", new Date(), false, UUID.randomUUID().toString());
        when(experimentDao.findExperiment(E_PROT_1, true)).thenReturn(experimentDTO);

        subject.setExperimentDAO(experimentDao);
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

        assertThat(experimentDesign.getSampleCharacteristics("Adult_Ovary"), contains(
                SampleCharacteristic.create(ORGANISM_PART, "ovary"),
                SampleCharacteristic.create(ORGANISM, "Homo sapiens"),
                SampleCharacteristic.create(DEVELOPMENTAL_STAGE, "adult")));

    }

}
