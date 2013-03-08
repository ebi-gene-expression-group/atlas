/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.streams.differential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.streams.TsvInputStreamBuilder;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DifferentialProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";

    private static final String GENE_ID_1 = "ENSMUSG00000030105";
    private static final String GENE_ID_2 = "ENSMUSG00000042428";
    private static final String GENE_ID_3 = "ENSMUSG00000084215";

    @Inject
    private TsvInputStreamBuilder geneInputStreamBuilder;

    @Inject
    private DifferentialExperimentsCache experimentsCache;

    private DifferentialProfilesInputStream subject;

    RequestPreferences requestPreferences = new RequestPreferences();

    @Before
    public void initSubject() throws Exception {
        requestPreferences.setCutoff(0.5d);
        requestPreferences.setQueryFactorType("ORGANISM_PART");

        DifferentialExperiment experiment = experimentsCache.getExperiment(EXPERIMENT_ACCESSION);

        subject = geneInputStreamBuilder.createDifferentialProfileInputStream(EXPERIMENT_ACCESSION);

    }

    @Test
    public void readNextShouldReturnNextExpression() throws IOException {
        //given
        DifferentialProfile geneProfile = subject.readNext();
        //then
        assertThat(geneProfile.getGeneId(), is(GENE_ID_1));
        assertThat(geneProfile.iterator().hasNext(), is(true));
        //ToDo: GeneProfile needs a getter for Expressions

        //given we poll twice more
        geneProfile = subject.readNext();
        //then
        assertThat(geneProfile.getGeneId(), is(GENE_ID_2));

        geneProfile = subject.readNext();

        assertThat(geneProfile.getGeneId(), is(GENE_ID_3));
    }


    @Test
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {

        long countProfiles = 0;
        while(subject.readNext() != null){
            ++countProfiles;
        }

        assertThat(countProfiles, is(24869L));
    }


}
