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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml", "classpath:oracleUcpContext.xml"})
public class BaselineProfilesTSVWriterIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String MULTIDIMENSIONAL_EXPERIMENT_ACCESSION = "E-GEOD-26284";

    /*/in factor value(s)*/

    @Inject
    private BaselineProfilesTSVWriter subject;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    private BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext requestContext;

    @Before
    public void setUp() throws Exception {

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        requestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences).build();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void headerTextShouldContainThreeRows(){

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows.length, is(3));

    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));

    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));

    }

    @Test
    public void secondHeaderLineShouldDescribeQuery(){

        requestPreferences.setExactMatch(false);

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding', specifically expressed in any Organism part above the expression level cutoff: 0.5 in experiment " + EXPERIMENT_ACCESSION));

    }


    @Test
    public void secondHeaderLineShouldDescribeExactMatchQuery(){

        requestPreferences.setExactMatch(true);
        requestPreferences.setSpecific(false);

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding' exactly, expressed in any Organism part above the expression level cutoff: 0.5 in experiment " + EXPERIMENT_ACCESSION));
        assertThat(headerRows[2], startsWith("# Timestamp: "));

    }

    @Test
    public void secondHeaderLineShouldDescribeExactMatchQueryAlsoForMultidimensionalExperiments(){
        BaselineExperiment multidimensionalExperiment = baselineExperimentsCache.getExperiment(MULTIDIMENSIONAL_EXPERIMENT_ACCESSION);

        requestPreferences.setSerializedFilterFactors("RNA:total RNA,CELLULAR_COMPONENT:whole cell");

        requestPreferences.setQueryFactorType("CELL_LINE");
        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("HPC-PL cell line", "Mickey Mouse")));

        baselineRequestContextBuilder.forExperiment(multidimensionalExperiment)
                                                              .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding' exactly, specifically expressed " +
                "in Cell lines: 'HPC-PL cell line, Mickey Mouse' above the expression level cutoff: 0.5 " +
                "in experiment E-GEOD-26284, filtered by RNA: total RNA and Cellular component: whole cell"));

    }

    @Test
    public void secondHeaderLineShouldDescribeExactMatchQueryAlsoForTwodimensionalExperiments(){
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment("E-GEOD-41338");

        requestPreferences.setSerializedFilterFactors("ORGANISM:Gallus gallus");

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("brain")));

        baselineRequestContextBuilder.forExperiment(experiment)
                                                              .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead(requestContext).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding' exactly, specifically expressed " +
                "in Organism part: 'brain' above the expression level cutoff: 0.5 " +
                "in experiment E-GEOD-41338, filtered by Organism: Gallus gallus"));

    }
}
