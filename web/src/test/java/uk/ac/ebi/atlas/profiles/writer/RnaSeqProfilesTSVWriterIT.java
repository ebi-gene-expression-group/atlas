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

package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesTSVWriterIT {

    private static final String RNA_SEQ_EXPERIMENT_ACCESSION = "E-GEOD-22351";

    @Inject
    private RnaSeqProfilesTSVWriter subject;

    @Inject
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

    private DifferentialExperiment differentialExperiment;

    private RnaSeqRequestContext requestContext;

    @Before
    public void setUp() throws Exception {

        differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(RNA_SEQ_EXPERIMENT_ACCESSION);
        requestContext = rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(requestPreferences).build();

    }

    @Test
    public void headerTextShouldContainThreeRows(){

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows.length, is(3));

    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));

    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));

    }

    @Test
    public void secondHeaderLineShouldDescribeQuery(){

        requestPreferences.setExactMatch(false);

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: '', specifically up/down differentially expressed in " +
                "any contrast given the p-value cutoff 0.05 and log2-fold change cutoff 1 in experiment "+RNA_SEQ_EXPERIMENT_ACCESSION));

    }

    @Test
    public void secondHeaderLineShouldDescribeExactMatchQuery(){

        requestPreferences.setExactMatch(true);
        requestPreferences.setSpecific(false);

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: '' exactly, up/down differentially expressed in any " +
                "contrast given the p-value cutoff 0.05 and log2-fold change cutoff 1 in experiment "+RNA_SEQ_EXPERIMENT_ACCESSION));
        assertThat(headerRows[2], startsWith("# Timestamp: "));

    }

    @Test
    public void secondHeaderLineShouldDescribeQueryAlsoWhenSelectingContrasts(){

        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("g1_g2")));

        rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertTrue("Get a vaguely correct line back",
                Pattern.matches("# Query: Genes.*differentially.*"+RNA_SEQ_EXPERIMENT_ACCESSION,headerRows[1]));
        assertThat(headerRows[2], startsWith("# Timestamp: "));
    }
}
