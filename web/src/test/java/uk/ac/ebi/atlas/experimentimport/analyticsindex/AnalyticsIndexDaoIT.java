package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.baseline.IsBaselineExpressionAboveCutoffAndForFilterFactors;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexDaoIT {

    private static class AnalyticsDocumentsGenerator {
        private static ImmutableList<AnalyticsDocument> generate(String genePrefix, String species, String kingdom, String experimentAccession, ExperimentType experimentType, int count) {
            ImmutableList.Builder<AnalyticsDocument> builder = new ImmutableList.Builder<>();

            for (int i = 0 ; i < count ; i++) {
                AnalyticsDocument.Builder analyticsDocumentBuilder =
                        AnalyticsDocument.builder()
                                .bioentityIdentifier(genePrefix + String.format("%011d", i))
                                .identifierSearch("foo")
                                .species(species)
                                .kingdom(kingdom)
                                .experimentAccession(experimentAccession)
                                .experimentType(experimentType)
                                .conditionsSearch("bar");

                if (experimentType.isBaseline()) {
                    analyticsDocumentBuilder
                            .assayGroupId("blah")
                            .expressionLevel(randomDouble(0.0, 1.0));
                } else {
                    analyticsDocumentBuilder
                            .contrastId("blah")
                            .factors(Collections.singleton("time"))
                            .numReplicates(3)
                            .foldChange(randomDouble(-10.0, 10.0))
                            .pValue(randomDouble(0.0, 0.5));
                }

                builder.add(analyticsDocumentBuilder.build());
            }

            return builder.build();
        }

        private static double randomDouble(double min, double max) {
            return Math.random() * (max - min) + min;
        }
    }

    private static final int BATCH_SIZE = 1024;

    private int countAboveExpressionLevel(Iterable<AnalyticsDocument> analyticsDocumentList, double cutoff) {
        Function<AnalyticsDocument, BaselineExpression> analyticsDocumentToBaselineExpression =
                new Function<AnalyticsDocument, BaselineExpression>() {
                    public BaselineExpression apply(AnalyticsDocument analyticsDocument) {
                        return new BaselineExpression(analyticsDocument.getExpressionLevel());
                    }
                };

        return FluentIterable.from(analyticsDocumentList)
                .transform(analyticsDocumentToBaselineExpression)
                .filter(new IsBaselineExpressionAboveCutoffAndForFilterFactors().setCutoff(cutoff))
                .size();
    }

    private int countAboveFoldChangeAndBelowPValue(Iterable<AnalyticsDocument> analyticsDocumentList, double foldChangeCutoff, double pValueCutoff) {
        Function<AnalyticsDocument, DifferentialExpression> analyticsDocumentToDifferentialExpression =
                new Function<AnalyticsDocument, DifferentialExpression>() {
                    public DifferentialExpression apply(AnalyticsDocument analyticsDocument) {
                        return new DifferentialExpression(analyticsDocument.getPValue(), analyticsDocument.getFoldChange());
                    }
                };

        return FluentIterable.from(analyticsDocumentList)
                .transform(analyticsDocumentToDifferentialExpression)
                .filter(new IsDifferentialExpressionAboveCutOff().setFoldChangeCutOff(foldChangeCutoff).setPValueCutoff(pValueCutoff))
                .size();
    }

    @Mock
    private SolrClient solrClientMock;

    private AnalyticsIndexDAO subject;

    @Before
    public void setUp() throws Exception {
        when(solrClientMock.addBeans(Matchers.<Collection<AnalyticsDocument>>any(), anyInt())).thenReturn(new UpdateResponse());
        when(solrClientMock.commit(anyBoolean(), anyBoolean())).thenReturn(new UpdateResponse());
        when(solrClientMock.deleteByQuery(anyString())).thenReturn(new UpdateResponse());
        when(solrClientMock.commit()).thenReturn(new UpdateResponse());
        when(solrClientMock.rollback()).thenReturn(new UpdateResponse());
        when(solrClientMock.optimize()).thenReturn(new UpdateResponse());
        subject = new AnalyticsIndexDAO(solrClientMock);
    }

    @Test
    public void add10000RnaSeqBaselineDocuments() throws Exception {
        ImmutableList<AnalyticsDocument> rnaSeqBaselineDocuments =
                AnalyticsDocumentsGenerator.generate(
                        "ENSFOO", "foo sapiens", "barimals", "E-FOOBAR-1", ExperimentType.RNASEQ_MRNA_BASELINE, 10000);

        int count = countAboveExpressionLevel(rnaSeqBaselineDocuments, AnalyticsDocument.RNA_SEQ_BASELINE_EXPRESSION_CUTOFF);

        assertThat(subject.addDocuments(rnaSeqBaselineDocuments, BATCH_SIZE), is(count));
        verify(solrClientMock, times(count / BATCH_SIZE + 1)).addBeans(Matchers.<Collection<AnalyticsDocument>>any(), anyInt());
        verify(solrClientMock, times(1)).commit(false, false);
    }

    @Test
    public void add10000ProteomicsBaselineDocuments() throws Exception {
        ImmutableList<AnalyticsDocument> rnaSeqBaselineDocuments =
                AnalyticsDocumentsGenerator.generate(
                        "ENSFOO", "foo sapiens", "barimals", "E-FOOBAR-1", ExperimentType.PROTEOMICS_BASELINE, 10000);

        int count = countAboveExpressionLevel(rnaSeqBaselineDocuments, AnalyticsDocument.PROTEOMICS_BASELINE_EXPRESSION_CUTOFF);

        assertThat(subject.addDocuments(rnaSeqBaselineDocuments, BATCH_SIZE), is(count));
        verify(solrClientMock, times(count / BATCH_SIZE + 1)).addBeans(Matchers.<Collection<AnalyticsDocument>>any(), anyInt());
        verify(solrClientMock, times(1)).commit(false, false);
    }

    @Test
    public void add10000DifferentialDocuments() throws Exception {
        ImmutableList<AnalyticsDocument> differentialDocuments =
                AnalyticsDocumentsGenerator.generate(
                        "ENSFOO", "foo sapiens", "barimals", "E-FOOBAR-1", ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL, 10000);

        int count = countAboveFoldChangeAndBelowPValue(
                differentialDocuments, AnalyticsDocument.DIFFERENTIAL_FOLD_CHANGE_CUTOFF, AnalyticsDocument.DIFFERENTIAL_P_VALUE_CUTOFF);

        assertThat(subject.addDocuments(differentialDocuments, BATCH_SIZE), is(count));
        verify(solrClientMock, times(count / BATCH_SIZE + 1)).addBeans(Matchers.<Collection<AnalyticsDocument>>any(), anyInt());
        verify(solrClientMock, times(1)).commit(false, false);
    }

}