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
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesTSVWriterIT {

    private static final String RNA_SEQ_EXPERIMENT_ACCESSION = "E-GEOD-38400";

    @Inject
    private RnaSeqProfilesTSVWriter subject;

    @Inject
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private RnaSeqRequestContext rnaSeqRequestContext;

    private DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

    private DifferentialExperiment differentialExperiment;

    @Inject
    private ExperimentDAO experimentDAO;

    @Before
    public void setUp() throws Exception {

        experimentDAO.addExperiment(RNA_SEQ_EXPERIMENT_ACCESSION, ExperimentType.DIFFERENTIAL, false);

        differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(RNA_SEQ_EXPERIMENT_ACCESSION);
        rnaSeqRequestContext = rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(requestPreferences).build();

    }

    @After
    public void tearDown() throws Exception {
        experimentDAO.deleteExperiment(RNA_SEQ_EXPERIMENT_ACCESSION);
    }

    @Test
    public void headerTextShouldContainThreeRows(){

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows.length, is(3));

    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));

    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));

    }

    @Test
    public void secondHeaderLineShouldDescribeQuery(){

        requestPreferences.setExactMatch(false);

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: '', specifically up/down differentially expressed in any contrast given the False Discovery Rate cutoff: 0.05 in experiment E-GEOD-38400"));

    }

    @Test
    public void secondHeaderLineShouldDescribeExactMatchQuery(){

        requestPreferences.setExactMatch(true);
        requestPreferences.setSpecific(false);

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: '' exactly, up/down differentially expressed in any contrast given the False Discovery Rate cutoff: 0.05 in experiment E-GEOD-38400"));
        assertThat(headerRows[2], startsWith("# Timestamp: "));

    }

    @Test
    public void secondHeaderLineShouldDescribeQueryAlsoWhenSelectingContrasts(){

        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("g1_g4", "g1_g3")));

        rnaSeqRequestContext = rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: '' exactly, specifically up/down differentially expressed in contrasts: idn2 mutant vs wild type, swi3b mutant vs wild type given the False Discovery Rate cutoff: 0.05 in experiment E-GEOD-38400"));
        assertThat(headerRows[2], startsWith("# Timestamp: "));
    }
}
