package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HistogramServiceTest {
    MockDataFileHub dataFileHub;

    RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory;

    @Mock
    ExperimentTrader experimentTrader;

    @Mock
    BaselineExperiment experiment;

    CutoffScale.Scaled cutoffScale = new CutoffScale.Scaled();

    HistogramService<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>, BaselineExperiment> subject;

    @Before
    public void setUp() throws Exception {
        dataFileHub = MockDataFileHub.create();
        rnaSeqBaselineProfileStreamFactory = Mockito.spy(new RnaSeqBaselineProfileStreamFactory(dataFileHub));

        subject = new HistogramService<>(rnaSeqBaselineProfileStreamFactory, experimentTrader, cutoffScale.get());

        when(experiment.getAccession()).thenReturn("accession");
        when(experiment.getDisplayDefaults()).thenReturn(ExperimentDisplayDefaults.simpleDefaults());

        when(experimentTrader.getExperiment("accession", "")).thenReturn(experiment);

        dataFileHub.addTpmsExpressionFile("accession", ImmutableList.of( new String[]{"Gene ID", "Gene name"},
                new String[]{"id_1", "name_1"}));
    }

    @Test
    public void testGet() throws Exception {
        BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> baselineProfileStreamOptions =
                new BaselineRequestContext<>(new RnaSeqBaselineRequestPreferences(), experiment);
        JsonObject result = subject.get("accession", "", baselineProfileStreamOptions).asJson();

        assertThat(result.has("bins"), is(true));
        Mockito.verify(rnaSeqBaselineProfileStreamFactory)
                .histogram(experiment, baselineProfileStreamOptions, cutoffScale.get());
    }

    @Test
    public void cachingWorks() throws Exception {
        subject.get(
                "accession", "",
                new BaselineRequestContext<>(new RnaSeqBaselineRequestPreferences(), experiment)).asJson();
        subject.get(
                "accession", "",
                new BaselineRequestContext<>(new RnaSeqBaselineRequestPreferences(), experiment)).asJson();

        Mockito.verify(rnaSeqBaselineProfileStreamFactory, times(1))
                .histogram(eq(experiment), ArgumentMatchers.any(), eq(cutoffScale.get()));
    }
}