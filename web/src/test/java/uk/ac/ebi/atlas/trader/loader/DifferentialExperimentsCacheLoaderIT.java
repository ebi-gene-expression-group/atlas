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

package uk.ac.ebi.atlas.trader.loader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialExperimentsCacheLoaderIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";

    @Inject
    private DifferentialExperimentsCacheLoader subject;


    @Test
    public void shouldHaveExactlyOneSpecies() throws IOException {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getOrganisms(), contains("Mus musculus"));
    }

    @Test
    public void shouldContainOneContrast() throws IOException {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getContrasts().size(), is(1));
        assertThat(experiment.getContrasts().iterator().next().getDisplayName(), startsWith("genotype:'expressing"));
    }

    @Test
    public void shouldContainGivenContrast() throws IOException {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //when
        Contrast contrast = experiment.getContrast("g1_g2");

        //then
        assertThat(contrast.getId(), is("g1_g2"));
        assertThat(contrast.getDisplayName(), startsWith("genotype:'expressing"));

        AssayGroup expectedAssayGroup = new AssayGroup("g1", "SRR057596", "SRR057597", "SRR057598");
        assertThat(contrast.getReferenceAssayGroup(), is(expectedAssayGroup));

        expectedAssayGroup = new AssayGroup("g2", "SRR057599", "SRR057600", "SRR057601");
        assertThat(contrast.getTestAssayGroup(), is(expectedAssayGroup));
    }

    @Test
    public void shouldHaveDisplayNameEqualsToAccession() throws IOException {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getDisplayName(), is(EXPERIMENT_ACCESSION));
        assertThat(experiment.hasExtraInfoFile(), is(false));
        assertThat(experiment.getDescription(), startsWith(""));
    }

}
