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

package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.math.util.MathUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsQueue;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsQueueBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfilesInputStream;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankBaselineProfilesCommandTest {

    private static final String SPECIES = "Species 1";
    public static final String E_MTAB_513 = "E-MTAB-513";

    GeneProfileInputStreamEMTab513React71 eMTab513react71InputStream = new GeneProfileInputStreamEMTab513React71(0.5);

    @Mock
    private BaselineRequestContext requestContextMockSpecificNoGeneQuery;

    @Mock
    private BaselineRequestContext requestContextMockEMTab513;

    @Mock
    private InputStreamFactory inputStreamFactoryMock;

    private BaselineProfileBuilder baselineProfileBuilder = new BaselineProfileBuilder();
    private GeneSetFactory geneSetFactory = new GeneSetFactory(baselineProfileBuilder);
    private GeneSetProfilesBuilder geneSetProfilesBuilder = new GeneSetProfilesBuilder(geneSetFactory);

    private ObjectInputStream<BaselineProfile> largeInputStream;

    private ObjectInputStream<BaselineProfile> smallInputStream;

    private RankBaselineProfilesCommand subject;

    @Mock
    private GeneQueryResponse geneQueryResponseMock;

    public RankBaselineProfilesCommandTest() {
    }

    @Before
    public void initializeSubject() throws Exception {

        ImmutableSet<Factor> eMtab513AllFactors = eMTab513react71InputStream.getOrderedFactorGroups().extractFactors();

        when(requestContextMockEMTab513.getHeatmapMatrixSize()).thenReturn(50);
        when(requestContextMockEMTab513.getSelectedFilterFactors()).thenReturn(Collections.EMPTY_SET);
        when(requestContextMockEMTab513.getAllQueryFactors()).thenReturn(eMtab513AllFactors);
        when(requestContextMockEMTab513.getCutoff()).thenReturn(0.5D);

        when(requestContextMockSpecificNoGeneQuery.getGeneQuery()).thenReturn("");
        when(requestContextMockSpecificNoGeneQuery.getFilteredBySpecies()).thenReturn(SPECIES);
        when(requestContextMockSpecificNoGeneQuery.getHeatmapMatrixSize()).thenReturn(100);
        when(requestContextMockSpecificNoGeneQuery.isSpecific()).thenReturn(true);

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);

        subject = new RankBaselineProfilesCommand(requestContextMockSpecificNoGeneQuery, inputStreamFactoryMock, geneSetProfilesBuilder);

    }

    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //when
        List<BaselineProfile> top3Objects = subject.execute(smallInputStream, requestContextMockSpecificNoGeneQuery);

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {

        //given
        given(requestContextMockSpecificNoGeneQuery.getHeatmapMatrixSize()).willReturn(3);

        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMockSpecificNoGeneQuery);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenEmptyConditions() throws Exception {
        //given
        when(requestContextMockSpecificNoGeneQuery.isSpecific()).thenReturn(false);
        //when
        subject.execute(largeInputStream, requestContextMockSpecificNoGeneQuery);
    }

        @Test
    public void rankedObjectsShouldBeInAscendingOrder() throws Exception {

        //given
        given(requestContextMockSpecificNoGeneQuery.isSpecific()).willReturn(false);
        //and
        given(requestContextMockSpecificNoGeneQuery.getSelectedQueryFactors()).willReturn(Sets.newHashSet(mock(Factor.class)));


        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMockSpecificNoGeneQuery);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(5));
        //and
        assertThat(top3Objects.get(0).getMaxExpressionLevel(), is(5D));
        //then
        assertThat(top3Objects.get(0).getId(), is("5"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(2).getMaxExpressionLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getId(), is("3"));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {

        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMockSpecificNoGeneQuery);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(1));
        //and
        assertThat(top3Objects.get(0).getMaxExpressionLevel(), is(1D));
        //then
        assertThat(top3Objects.get(0).getId(), is("1"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(2).getMaxExpressionLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getId(), is("3"));

    }

    public static final double POLR2B_LEUKOCYTE = 47D;
    public static final double SNRPA_LEUKOCYTE = 19D;
    public static final double CCNT2_LEUKOCYTE = 9D;
    public static final double ZKSCAN5_LEUKOCYTE = 5D;
    private static final double NUMBER_OF_GENES_IN_GENE_SET = 7;
    private static final int NUMBER_OF_FRACTIONAL_DIGITS = 0;
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true
    @Test
    public void eMTab513react71_Specific() {
        mockSpecific();
        BaselineProfilesList profiles = subject.execute(eMTab513react71InputStream, requestContextMockEMTab513);

        assertThat(profiles.extractGeneNames(), contains("SRSF2", "ZNF713", "ZFP2", "POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        BaselineProfile srsf2 = profiles.get(0);
        BaselineProfile znf713 = profiles.get(1);
        BaselineProfile zfp2 = profiles.get(2);
        BaselineProfile polr2b = profiles.get(3);
        BaselineProfile snrpa = profiles.get(4);
        BaselineProfile ccnt2 = profiles.get(5);
        BaselineProfile zkscan5 = profiles.get(6);
        assertThat(srsf2.getName(), is("SRSF2"));
        assertThat(znf713.getName(), is("ZNF713"));
        assertThat(zfp2.getName(), is("ZFP2"));
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(srsf2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on
    @Test
    public void eMTab513react71_NotSpecific() {
        BaselineProfilesList profiles = subject.execute(eMTab513react71InputStream, requestContextMockEMTab513);

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5", "ZFP2", "ZNF713", "SRSF2"));

        BaselineProfile srsf2 = profiles.get(6);
        BaselineProfile znf713 = profiles.get(5);
        BaselineProfile zfp2 = profiles.get(4);
        BaselineProfile polr2b = profiles.get(0);
        BaselineProfile snrpa = profiles.get(1);
        BaselineProfile ccnt2 = profiles.get(2);
        BaselineProfile zkscan5 = profiles.get(3);
        assertThat(srsf2.getName(), is("SRSF2"));
        assertThat(znf713.getName(), is("ZNF713"));
        assertThat(zfp2.getName(), is("ZFP2"));
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(srsf2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet() {
        mockSpecific();
        mockGeneSetMatchTrue();

        BaselineProfilesList profiles = subject.execute(eMTab513react71InputStream, requestContextMockEMTab513);

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);

    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet() {
        mockGeneSetMatchTrue();

        BaselineProfilesList profiles = subject.execute(eMTab513react71InputStream, requestContextMockEMTab513);

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_Specific_QueryFactorLeukocyte() throws GenesNotFoundException {
        mockSpecific();
        mockSelectOrganismPart(FACTOR_LEUKOCYTE);

        mockInputStreamFromEMTab513SourceString();
        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        assertThat(profiles.extractGeneNames(), contains("POLR2B"));

        BaselineProfile polr2b = profiles.get(0);

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException {
        mockSelectOrganismPart(FACTOR_LEUKOCYTE);

        mockInputStreamFromEMTab513SourceString();
        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        BaselineProfile polr2b = profiles.get(0);
        BaselineProfile snrpa = profiles.get(1);
        BaselineProfile ccnt2 = profiles.get(2);
        BaselineProfile zkscan5 = profiles.get(3);
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }


    private void mockSpecific() {
        when(requestContextMockEMTab513.isSpecific()).thenReturn(true);
    }

    private void mockInputStreamFromEMTab513SourceString() {
        BaselineProfilesInputStream inputStream = inputStreamFromSourceString(eMTab513react71InputStream.getSourceString());
        when(inputStreamFactoryMock.createBaselineProfileInputStream(E_MTAB_513)).thenReturn(inputStream);
        subject = new RankBaselineProfilesCommand(requestContextMockEMTab513, inputStreamFactoryMock, geneSetProfilesBuilder);
    }

    private void mockSelectOrganismPart(Factor factor) {
        when(requestContextMockEMTab513.getQueryFactorType()).thenReturn("ORGANISM_PART");
        when(requestContextMockEMTab513.getSelectedQueryFactors()).thenReturn(Collections.singleton(factor));
    }

    private void mockGeneSetMatchTrue() {
        when(requestContextMockEMTab513.isGeneSetMatch()).thenReturn(true);
        when(geneQueryResponseMock.getRelatedQueryTerms(anyString())).thenReturn(Collections.singleton("react_71"));
        when(geneQueryResponseMock.getQueryTerms()).thenReturn(Collections.singleton("react_71"));
        when(requestContextMockEMTab513.getGeneQueryResponse()).thenReturn(geneQueryResponseMock);
    }

    @Mock
    private BaselineExpressionsQueueBuilder baselineExpressionsQueueBuilder;

    private BaselineProfilesInputStream inputStreamFromSourceString(String sourceString) {
        // Create input stream from source string. This is needed when you need to exercise the
        // specificity filtering in the BaselineProfilePrecondition, which is called from inside the TsvInputStream
        // So you need to setup a real TsvInputStream, but from a mocked source string
        // TODO: refactor production code, way too much to setup here.
        StringReader source = new StringReader(sourceString);
        CSVReader csvReader = CsvReaderFactory.createTsvReader(source);
        BaselineProfilePreconditionBackedBuilder baselineProfilePreconditionBackedBuilder = new BaselineProfilePreconditionBackedBuilder(requestContextMockEMTab513, new BaselineExpressionPrecondition(), new BaselineProfilePrecondition());
        when(baselineExpressionsQueueBuilder.forExperiment(E_MTAB_513)).thenReturn(baselineExpressionsQueueBuilder);
        when(baselineExpressionsQueueBuilder.withHeaders((String)anyVararg())).thenReturn(baselineExpressionsQueueBuilder);

        when(baselineExpressionsQueueBuilder.build()).thenReturn(new BaselineExpressionsQueue(eMTab513react71InputStream.getOrderedFactorGroups()));
        return new BaselineProfilesInputStream(csvReader, E_MTAB_513, baselineExpressionsQueueBuilder, baselineProfilePreconditionBackedBuilder);
    }

    private void checkAllPolr2bExpressionLevels(BaselineProfile polr2b) {
        assertThat(polr2b.getExpressionLevel(factor("adipose")), is(16D));
        assertThat(polr2b.getExpressionLevel(factor("adrenal gland")), is(30D));
        assertThat(polr2b.getExpressionLevel(factor("brain")), is(24D));
        assertThat(polr2b.getExpressionLevel(factor("breast")), is(18D));
        assertThat(polr2b.getExpressionLevel(factor("colon")), is(20D));
        assertThat(polr2b.getExpressionLevel(factor("heart")), is(26D));
        assertThat(polr2b.getExpressionLevel(factor("kidney")), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(factor("leukocyte")), is(47D));
        assertThat(polr2b.getExpressionLevel(factor("liver")), is(12D));
        assertThat(polr2b.getExpressionLevel(factor("lung")), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(factor("lymph node")), is(21D));
        assertThat(polr2b.getExpressionLevel(factor("ovary")), is(25D));
        assertThat(polr2b.getExpressionLevel(factor("prostate")), is(24D));
        assertThat(polr2b.getExpressionLevel(factor("skeletal muscle")), is(28D));
        assertThat(polr2b.getExpressionLevel(factor("testis")), is(33D));
        assertThat(polr2b.getExpressionLevel(factor("thyroid")), is(38D));
    }


    private void checkAllReact71ExpressionLevels(BaselineProfile react71) {
        assertThat(react71.getExpressionLevel(factor("adipose")), is(7D));
        assertThat(react71.getExpressionLevel(factor("adrenal gland")), is(10D));
        assertThat(react71.getExpressionLevel(factor("brain")), is(6D));
        assertThat(react71.getExpressionLevel(factor("breast")), is(6D));
        assertThat(react71.getExpressionLevel(factor("colon")), is(8D));
        assertThat(react71.getExpressionLevel(factor("heart")), is(6D));
        assertThat(react71.getExpressionLevel(factor("kidney")), is(4D));
        assertThat(react71.getExpressionLevel(FACTOR_LEUKOCYTE), is(MathUtils.round((POLR2B_LEUKOCYTE + SNRPA_LEUKOCYTE + CCNT2_LEUKOCYTE + ZKSCAN5_LEUKOCYTE) / NUMBER_OF_GENES_IN_GENE_SET, NUMBER_OF_FRACTIONAL_DIGITS)));
        assertThat(react71.getExpressionLevel(factor("liver")), is(4D));
        assertThat(react71.getExpressionLevel(factor("lung")), is(6D));
        assertThat(react71.getExpressionLevel(factor("lymph node")), is(9D));
        assertThat(react71.getExpressionLevel(factor("ovary")), is(9D));
        assertThat(react71.getExpressionLevel(factor("prostate")), is(17D));
        assertThat(react71.getExpressionLevel(factor("skeletal muscle")), is(11D));
        assertThat(react71.getExpressionLevel(factor("testis")), is(11D));
        assertThat(react71.getExpressionLevel(factor("thyroid")), is(8D));
    }

    private Factor factor(String factorValue) {
        return new Factor("ORGANISM_PART", factorValue);
    }

}
