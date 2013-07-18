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

package uk.ac.ebi.atlas.commands.download;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.experimentloader.ConfigurationDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MicroarrayProfilesTSVWriterIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-MTAB-1066";

    @Inject
    private MicroarrayProfilesTSVWriter subject;

    @Inject
    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    @Inject
    private ConfigurationDao configurationDao;

    private MicroarrayRequestContext microarrayRequestContext;

    private MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

    private MicroarrayExperiment microarrayExperiment;

    @Before
    public void setUp() throws Exception {

        if (configurationDao.getExperimentConfiguration(MICROARRAY_EXPERIMENT_ACCESSION) == null) {
            configurationDao.addExperimentConfiguration(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY);
        }

        microarrayExperiment = microarrayExperimentsCache.getExperiment(MICROARRAY_EXPERIMENT_ACCESSION);
        microarrayRequestContext = microarrayRequestContextBuilder.forExperiment(microarrayExperiment)
                .withPreferences(requestPreferences).build();

    }

    @After
    public void tearDown() throws Exception {
        configurationDao.deleteExperimentConfiguration(MICROARRAY_EXPERIMENT_ACCESSION);
    }

    @Test
    public void secondHeaderLineShouldDescribeQueryAlsoWhenSelectingContrasts() {

        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("g2_g3")));

        microarrayRequestContext = microarrayRequestContextBuilder.forExperiment(microarrayExperiment)
                .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: '' exactly, specifically up/down differentially expressed in contrast: genotype:'cdk8 mutant' vs 'wild type' given the False Discovery Rate cutoff: 0.05 in experiment E-MTAB-1066"));
        assertThat(headerRows[2], startsWith("# Timestamp: "));
    }


}
