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

package uk.ac.ebi.atlas.streams.differential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentsCache;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DifferentialProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";

    private static final String GENE_ID_1 = "ENSMUSG00000030105";
    private static final String GENE_ID_2 = "ENSMUSG00000042428";
    private static final String GENE_ID_3 = "ENSMUSG00000084215";

    @Inject
    private InputStreamFactory inputStreamFactory;

    @Inject
    private DifferentialExperimentsCache differentialExperimentsCache;

    private DifferentialProfilesInputStream subject;
    private Contrast contrast;


    @Before
    public void initSubject() throws Exception {
        subject = inputStreamFactory.createDifferentialProfileInputStream(EXPERIMENT_ACCESSION, 0.05D, Regulation.UP_DOWN);
        DifferentialExperiment differentialExperiment = differentialExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        contrast = differentialExperiment.getContrasts().first();
    }

    @Test
    public void readNextShouldReturnNextExpression() throws IOException {
        //given
        DifferentialProfile differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getGeneId(), is(GENE_ID_1));
        assertThat(differentialProfile.getSpecificity(), is(1));
        double expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(1.0));
        DifferentialExpression differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(0.474360080385946));

        //given we poll again
        differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getGeneId(), is(GENE_ID_2));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(-0.0177584280774704));

        differentialProfile = subject.readNext();

        //given we poll again
        assertThat(differentialProfile.getGeneId(), is(GENE_ID_3));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(1.0));
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(1.74803957316317));

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
