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

package uk.ac.ebi.atlas.streams.differential.microarray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class MicroarrayProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-MTAB-1066";

    private static final String GENE_ID_UPDOWN_1 = "FBgn0038465";
    private static final String GENE_ID_UPDOWN_2 = "FBgn0053054";
    private static final String GENE_ID_UPDOWN_3 = "FBgn0029134";

    private static final String GENE_ID_UP_1 = "FBgn0038465";
    private static final String GENE_ID_UP_2 = "FBgn0053054";
    private static final String GENE_ID_UP_3 = "FBgn0040393";

    private static final String GENE_ID_DOWN_1 = "FBgn0029134";
    private static final String GENE_ID_DOWN_2 = "FBgn0033770";
    private static final String GENE_ID_DOWN_3 = "FBgn0032636";
    private static final String ARRAY_DESIGN_ACCESSION = "A-AFFY-35";

    @Inject
    private InputStreamFactory inputStreamFactory;

    @Inject
    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    @Inject
    private ExperimentDAO experimentDAO;

    @Inject
    private DesignElementMappingLoader designElementLoader;

    private MicroarrayRequestContext microarrayRequestContext;

    private ObjectInputStream<MicroarrayProfile> subject;

    private Contrast contrast;

    private MicroarrayRequestPreferences microarrayRequestPreferences = new MicroarrayRequestPreferences();

    @Before
    public void initSubject() throws Exception {

        experimentDAO.addExperiment(EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY, false);

        designElementLoader.loadMappings(ARRAY_DESIGN_ACCESSION, ArrayDesignType.MICRO_ARRAY);

        subject = inputStreamFactory.createMicroarrayProfileInputStream(EXPERIMENT_ACCESSION, ARRAY_DESIGN_ACCESSION);

        MicroarrayExperiment microarrayExperiment = microarrayExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);

        contrast = microarrayExperiment.getContrasts().toArray(new Contrast[0])[1];

        microarrayRequestPreferences.setArrayDesignAccession(ARRAY_DESIGN_ACCESSION);
        microarrayRequestContext = microarrayRequestContextBuilder.forExperiment(microarrayExperiment)
                .withPreferences(microarrayRequestPreferences).build();

    }

    @After
    public void tearDown() throws Exception {
        experimentDAO.deleteExperiment(EXPERIMENT_ACCESSION);
    }

    @Test
    public void readNextWithUpDownRegulation() throws IOException {
        microarrayRequestPreferences.setRegulation(Regulation.UP_DOWN);

        //given
        MicroarrayProfile microarrayProfile = subject.readNext();
        //then
        assertThat(microarrayProfile.getId(), is(GENE_ID_UPDOWN_1));
        assertThat(microarrayProfile.getSpecificity(), is(1));
        Double expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(nullValue()));

        //given we poll again
        microarrayProfile = subject.readNext();
        //then
        assertThat(microarrayProfile.getId(), is(GENE_ID_UPDOWN_2));
        assertThat(microarrayProfile.getSpecificity(), is(2));
        expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.0103104291771865));

        MicroarrayExpression microarrayExpression = microarrayProfile.getExpression(contrast);
        assertThat(microarrayExpression.getFoldChange(), is(0.386948166666667));
        assertThat(microarrayExpression.getLevel(), is(0.0103104291771865));
        assertThat(microarrayExpression.getTstatistic(), is(4.61680601729325));

        microarrayProfile = subject.readNext();

        //given we poll again
        assertThat(microarrayProfile.getId(), is(GENE_ID_UPDOWN_3));
        assertThat(microarrayProfile.getSpecificity(), is(2));
        expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.00146761846818228));
        microarrayExpression = microarrayProfile.getExpression(contrast);
        assertThat(microarrayExpression.getFoldChange(), is(-0.672350666666665));
        assertThat(microarrayExpression.getLevel(), is(0.00146761846818228));
        assertThat(microarrayExpression.getTstatistic(), is(-7.07897827772072));

    }

    @Test
    public void readNextWithUpRegulation() throws IOException {
        microarrayRequestPreferences.setRegulation(Regulation.UP);

        //given
        MicroarrayProfile microarrayProfile = subject.readNext();
        //then
        assertThat(microarrayProfile.getId(), is(GENE_ID_UP_1));
        assertThat(microarrayProfile.getSpecificity(), is(1));
        Double expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(nullValue()));

        //given we poll again
        microarrayProfile = subject.readNext();
        //then
        assertThat(microarrayProfile.getId(), is(GENE_ID_UP_2));
        assertThat(microarrayProfile.getSpecificity(), is(2));
        expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.0103104291771865));

        MicroarrayExpression microarrayExpression = microarrayProfile.getExpression(contrast);
        assertThat(microarrayExpression.getFoldChange(), is(0.386948166666667));
        assertThat(microarrayExpression.getLevel(), is(0.0103104291771865));
        assertThat(microarrayExpression.getTstatistic(), is(4.61680601729325));

        microarrayProfile = subject.readNext();

        //given we poll again
        assertThat(microarrayProfile.getId(), is(GENE_ID_UP_3));
        assertThat(microarrayProfile.getSpecificity(), is(2));
        expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(1.46552279262956E-5));
        microarrayExpression = microarrayProfile.getExpression(contrast);
        assertThat(microarrayExpression.getFoldChange(), is(2.858022));
        assertThat(microarrayExpression.getLevel(), is(1.46552279262956E-5));
        assertThat(microarrayExpression.getTstatistic(), is(19.8487440997079));

    }


    @Test
    public void readNextWithDownRegulation() throws IOException {
        microarrayRequestPreferences.setRegulation(Regulation.DOWN);

        //given
        MicroarrayProfile microarrayProfile = subject.readNext();
        //then
        assertThat(microarrayProfile.getId(), is(GENE_ID_DOWN_1));
        assertThat(microarrayProfile.getSpecificity(), is(2));
        Double expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.00146761846818228));
        MicroarrayExpression microarrayExpression = microarrayProfile.getExpression(contrast);
        assertThat(microarrayExpression.getFoldChange(), is(-0.672350666666665));
        assertThat(microarrayExpression.getLevel(), is(0.00146761846818228));
        assertThat(microarrayExpression.getTstatistic(), is(-7.07897827772072));

        //given we poll again
        microarrayProfile = subject.readNext();
        //then
        assertThat(microarrayProfile.getId(), is(GENE_ID_DOWN_2));
        assertThat(microarrayProfile.getSpecificity(), is(1));
        expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(nullValue()));

        microarrayProfile = subject.readNext();

        //given we poll again
        assertThat(microarrayProfile.getId(), is(GENE_ID_DOWN_3));
        assertThat(microarrayProfile.getSpecificity(), is(2));
        expressionLevel = microarrayProfile.getExpressionLevel(contrast);
        assertThat(expressionLevel, is(0.00844182480286779));
        microarrayExpression = microarrayProfile.getExpression(contrast);
        assertThat(microarrayExpression.getFoldChange(), is(-0.6649263));
        assertThat(microarrayExpression.getLevel(), is(0.00844182480286779));
        assertThat(microarrayExpression.getTstatistic(), is(-4.82652565702941));

    }

    @Test
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        microarrayRequestPreferences.setRegulation(Regulation.UP_DOWN);


        long countProfiles = 0;
        while (subject.readNext() != null) {
            ++countProfiles;
        }

        assertThat(countProfiles, is(175L));
    }


}
