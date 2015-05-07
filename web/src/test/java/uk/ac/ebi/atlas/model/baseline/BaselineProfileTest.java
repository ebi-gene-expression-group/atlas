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

//ToDo: this test is too complex. It's not really unit test on GeneProfile because it tests all the chain of builder , preconditions , etc....

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileTest {

    private static final String QUERY_FACTOR_TYPE = "ORGANISM_PART";

    private static final String GENE_ID = "geneId_1";
    private static final String GENE_NAME = "geneName_1";

    private Factor factor1 = new Factor(QUERY_FACTOR_TYPE, "nose");
    private Factor factor2 = new Factor(QUERY_FACTOR_TYPE, "trunk");
    private Factor factor3 = new Factor(QUERY_FACTOR_TYPE, "head");
    private Factor factor4 = new Factor(QUERY_FACTOR_TYPE, "billabong");

    private BaselineExpression expression_1 = new BaselineExpression(2.2D, new FactorSet().add(factor1));
    private BaselineExpression expression_2 = new BaselineExpression(3D, new FactorSet().add(factor2));
    private BaselineExpression expression_3 = new BaselineExpression(3.001D, new FactorSet().add(factor3));

    private BaselineProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineProfile(GENE_ID, GENE_NAME);
        subject.add(QUERY_FACTOR_TYPE, expression_1).add(QUERY_FACTOR_TYPE, expression_2).add(QUERY_FACTOR_TYPE, expression_3);
    }

    @Test
    public void testGetGeneSpecificity() throws Exception {
        assertThat(subject.getSpecificity(), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenQueryFactorsIsEmpty() {
        subject.getAverageExpressionLevelOn(new HashSet<Factor>());
    }

    @Test
    public void averageExpressionLevelOnEmptyCollection() {
        //given
        Set<Factor> queryFactors = Sets.newHashSet(mock(Factor.class));
        //when
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(queryFactors);
        //then
        assertThat(averageExpressionLevel, is(0D));
    }

    @Test
    public void averageExpressionLevel() {
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(factor1, factor3));
        assertThat(averageExpressionLevel, is(2.6005000000000003D));

        averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(factor1, factor3,
                new Factor(QUERY_FACTOR_TYPE, "leg")));
        assertThat(averageExpressionLevel, is(1.733666666666667D));
    }

    @Test
    public void testSumProfile(){
        subject.sumProfile(buildOtherProfile());
        assertThat(subject.getId(), is(subject.getId()));
        assertThat(subject.getKnownExpressionLevel(factor1), is(2.2D + 1D));
        assertThat(subject.getKnownExpressionLevel(factor2), is(3D + 2D));
        assertThat(subject.getKnownExpressionLevel(factor3), is(3.001D + 3D));
        assertThat(subject.getKnownExpressionLevel(factor4), is(300D));
    }

    @Test
    public void sumProfileShouldPreserveLevelsThatAreNotExpressedInOtherProfile(){
        BaselineProfile otherProfile = new BaselineProfile("other profile", "other name").add(QUERY_FACTOR_TYPE, expression_2);

        subject.sumProfile(otherProfile);
        assertThat(subject.getId(), is(GENE_ID));
        assertThat(subject.getKnownExpressionLevel(factor1), is(subject.getKnownExpressionLevel(factor1)));
        assertThat(subject.getKnownExpressionLevel(factor2), is(6D));
        assertThat(subject.getKnownExpressionLevel(factor3), is(subject.getKnownExpressionLevel(factor3)));
        assertThat(subject.getKnownExpressionLevel(factor4), is(nullValue()));
    }

    @Test
    public void nonTissueExpressionsDoNotAffectMinOrMax() {
        assertThat(subject.getMinExpressionLevel(), is(2.2));
        assertThat(subject.getMaxExpressionLevel(), is(3.001));

        subject.add(QUERY_FACTOR_TYPE, new BaselineExpression("NT", new FactorSet().add(factor1)));

        assertThat(subject.getMinExpressionLevel(), is(2.2));
        assertThat(subject.getMaxExpressionLevel(), is(3.001));
    }

    @Test
    public void sumOfUnknownExpressionAndKnownExpressionIsUnknownExpression() {

        BaselineProfile profileWithUnknownExpression = buildProfileWithUnknownExpression();

        assertThat(subject.getExpression(factor1).isKnown(), is(true));
        assertThat(subject.getExpression(factor2).isKnown(), is(true));
        subject.sumProfile(profileWithUnknownExpression);
        assertThat(subject.getExpression(factor1).isKnown(), is(false));
        assertThat(subject.getExpression(factor2).isKnown(), is(true));

    }

    private BaselineProfile buildProfileWithUnknownExpression() {
        BaselineExpression unknownExpression1 = new BaselineExpression("NT", new FactorSet().add(factor1));
        BaselineProfile profileWithUnknownExpression = new BaselineProfile("OTHER_ID", "OTHER_NAME");
        profileWithUnknownExpression.add(QUERY_FACTOR_TYPE, unknownExpression1);
        return profileWithUnknownExpression;
    }

    @Test
    public void testFold(){
        BaselineProfile sumProfile = subject.foldProfile(3);
        assertThat(sumProfile.getId(), is(subject.getId()));
        assertThat(sumProfile.getKnownExpressionLevel(factor1), is(0.7D));
        assertThat(sumProfile.getKnownExpressionLevel(factor2), is(1.0D));
        assertThat(sumProfile.getKnownExpressionLevel(factor3), is(1.0D));
        assertThat(sumProfile.getKnownExpressionLevel(factor4), is(nullValue()));
    }

    @Test
    public void foldUnknownExpressionIsUnknownExpression(){
        BaselineProfile profileWithUnknownExpression = buildProfileWithUnknownExpression();

        BaselineProfile sumProfile = profileWithUnknownExpression.foldProfile(3);
        assertThat(sumProfile.getExpression(factor1).isKnown(), is(false));
    }

    BaselineProfile buildOtherProfile(){
        BaselineExpression expression_1 = new BaselineExpression(1D, new FactorSet().add(factor1));
        BaselineExpression expression_2 = new BaselineExpression(2D, new FactorSet().add(factor2));
        BaselineExpression expression_3 = new BaselineExpression(3D, new FactorSet().add(factor3));
        BaselineExpression expression_4 = new BaselineExpression(300D, new FactorSet().add(factor4));

        BaselineProfile baselineProfile = new BaselineProfile("OTHER_ID", "OTHER_NAME");

        return baselineProfile.add(QUERY_FACTOR_TYPE, expression_1)
                              .add(QUERY_FACTOR_TYPE, expression_2)
                              .add(QUERY_FACTOR_TYPE, expression_3)
                              .add(QUERY_FACTOR_TYPE, expression_4);
    }

}