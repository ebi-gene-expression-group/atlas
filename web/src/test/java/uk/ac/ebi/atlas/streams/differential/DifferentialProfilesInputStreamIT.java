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

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import java.io.IOException;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";

    private static final String GENE_ID_UPDOWN_1 = "ENSMUSG00000030105";
    private static final String GENE_ID_UPDOWN_2 = "ENSMUSG00000050370";
    private static final String GENE_ID_UPDOWN_3 = "ENSMUSG00000024799";
    private static final String GENE_ID_UPDOWN_4 = "ENSMUSG00000018930";

    private static final String GENE_ID_UP_1 = "ENSMUSG00000030105";
    private static final String GENE_ID_UP_2 = "ENSMUSG00000050370";
    private static final String GENE_ID_UP_3 = "ENSMUSG00000018930";
    private static final String GENE_ID_UP_4 = "ENSMUSG00000036896";

    private static final String GENE_ID_DOWN_1 = "ENSMUSG00000024799";
    private static final String GENE_ID_DOWN_2 = "ENSMUSG00000002100";
    private static final String GENE_ID_DOWN_3 = "ENSMUSG00000052468";

    @Inject
    private InputStreamFactory inputStreamFactory;

    @Inject
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private RnaSeqRequestContext rnaSeqRequestContext;

    private ObjectInputStream<RnaSeqProfile> subject;

    private Contrast contrast;

    private DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();


    @Before
    public void setUp() throws Exception {

        subject = inputStreamFactory.createDifferentialProfileInputStream(EXPERIMENT_ACCESSION);

        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);

        contrast = differentialExperiment.getContrasts().iterator().next();

        TreeSet<Contrast> allQueryFactors = Sets.newTreeSet();
        allQueryFactors.add(contrast);

        rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(differentialRequestPreferences);

        rnaSeqRequestContext = rnaSeqRequestContextBuilder.build();

    }


    @Test
    public void readNextWithUpDownRegulation() throws IOException {
        differentialRequestPreferences.setRegulation(Regulation.UP_DOWN);

        //given
        RnaSeqProfile differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_UPDOWN_1));
        assertThat(differentialProfile.getSpecificity(), is(1));
        double expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0D));
        DifferentialExpression differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(0.474360080385946));

        //given we poll again
        differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_UPDOWN_2));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(1.70428798138445E-6));
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(3.01033089730209));

        //given we poll again
        differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_UPDOWN_3));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.0137444998099392));

        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(-1.15843751459071));

        differentialProfile = subject.readNext();

        //given we poll again
        assertThat(differentialProfile.getId(), is(GENE_ID_UPDOWN_4));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.00188954906204623));
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(3.2943463328998));

    }

    @Test
    public void readNextWithUpRegulation() throws IOException {
        differentialRequestPreferences.setRegulation(Regulation.UP);

        //given
        RnaSeqProfile differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_UP_1));
        assertThat(differentialProfile.getSpecificity(), is(1));
        double expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0D));
        DifferentialExpression differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(0.474360080385946));

        //given we poll again
        differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_UP_2));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(1.70428798138445E-6));
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(3.01033089730209));

        //given we poll again
        differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_UP_3));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.00188954906204623));

        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(3.2943463328998));

        differentialProfile = subject.readNext();

        //given we poll again
        assertThat(differentialProfile.getId(), is(GENE_ID_UP_4));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(1.72296964769093E-4));
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(1.38380288897256));

    }


    @Test
    public void readNextWithDownRegulation() throws IOException {
        differentialRequestPreferences.setRegulation(Regulation.DOWN);


        //given
        RnaSeqProfile differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_DOWN_1));
        assertThat(differentialProfile.getSpecificity(), is(1));
        double expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.0137444998099392));
        DifferentialExpression differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(-1.15843751459071));

        //given we poll again
        differentialProfile = subject.readNext();
        //then
        assertThat(differentialProfile.getId(), is(GENE_ID_DOWN_2));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.0206187013724948));

        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(-2.37961321555458));

        differentialProfile = subject.readNext();

        //given we poll again
        assertThat(differentialProfile.getId(), is(GENE_ID_DOWN_3));
        assertThat(differentialProfile.getSpecificity(), is(1));
        expressionLevel = differentialProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.00304596230892978));
        differentialExpression = differentialProfile.getExpression(contrast);
        assertThat(differentialExpression.getFoldChange(), is(-1.46618405734343));

    }

    @Test
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        differentialRequestPreferences.setRegulation(Regulation.UP_DOWN);


        long countProfiles = 0;
        while (subject.readNext() != null) {
            ++countProfiles;
        }

        assertThat(countProfiles, is(50L));
    }


}
