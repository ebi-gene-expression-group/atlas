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
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
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

    private BaselineRequestContext baselineRequestContext;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();


    @Before
    public void setUp() throws Exception {

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        baselineRequestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences).build();

    }

    @After
    public void tearDown() throws Exception {

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

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding', specifically expressed in any Organism Part above the expression level cutoff: 0.5 in experiment " + EXPERIMENT_ACCESSION));

    }


    @Test
    public void secondHeaderLineShouldDescribeExactMatchQuery(){

        requestPreferences.setExactMatch(true);
        requestPreferences.setSpecific(false);

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding' exactly, expressed in any Organism Part above the expression level cutoff: 0.5 in experiment " + EXPERIMENT_ACCESSION));
        assertThat(headerRows[2], startsWith("# Timestamp: "));

    }

    @Test
    public void secondHeaderLineShouldDescribeExactMatchQueryAlsoForMultidimensionalExperiments(){
        BaselineExperiment multidimensionalExperiment = baselineExperimentsCache.getExperiment(MULTIDIMENSIONAL_EXPERIMENT_ACCESSION);

        requestPreferences.setSerializedFilterFactors("RNA:total RNA,CELLULAR_COMPONENT:whole cell");

        requestPreferences.setQueryFactorType("CELL_LINE");
        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("HPC-PL cell line", "Mickey Mouse")));

        baselineRequestContext = baselineRequestContextBuilder.forExperiment(multidimensionalExperiment)
                                                              .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding' exactly, specifically expressed " +
                "in Cell Lines: 'HPC-PL cell line, Mickey Mouse' above the expression level cutoff: 0.5 " +
                "in experiment E-GEOD-26284, filtered by RNA: total RNA and Cellular Component: whole cell"));

    }

    @Test
    public void secondHeaderLineShouldDescribeExactMatchQueryAlsoForTwodimensionalExperiments(){
        BaselineExperiment multidimensionalExperiment = baselineExperimentsCache.getExperiment(MULTIDIMENSIONAL_EXPERIMENT_ACCESSION);

        requestPreferences.setSerializedFilterFactors("CELL_LINE:HPC-PL cell line");

        requestPreferences.setQueryFactorType("RNA");
        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("Mickey Mouse")));

        baselineRequestContext = baselineRequestContextBuilder.forExperiment(multidimensionalExperiment)
                                                              .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead().split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding' exactly, specifically expressed " +
                "in RNA: 'Mickey Mouse' above the expression level cutoff: 0.5 " +
                "in experiment E-GEOD-26284, filtered by Cell Line: HPC-PL cell line"));

    }
}
