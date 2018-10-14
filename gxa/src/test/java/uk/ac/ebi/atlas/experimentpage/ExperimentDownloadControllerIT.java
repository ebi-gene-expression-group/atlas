package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExperimentDownloadControllerIT {
    private static final int GENE_NAME_INDEX = 1;

    @Inject
    private DataSource dataSource;

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private ExperimentDownloadController subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/small-experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @Test
    void testSomeMicroarrayExperiments() {
        Set<String> rnaSeqExperiments = experimentTrader.getMicroarrayExperimentAccessions();
        assertThat(rnaSeqExperiments, hasSize(greaterThan(0)));

        for (String accession: rnaSeqExperiments) {
            MicroarrayExperiment differentialExperiment = (MicroarrayExperiment)
                    experimentTrader.getPublicExperiment(accession);

            defaultParametersHeader(differentialExperiment);
            weHaveSomeResults(differentialExperiment);
            upDownRegulationWorks(differentialExperiment);
            noDataWithVeryStrictPValueCutoff(differentialExperiment);
            noDataWithVeryLargeFoldChangeCutoff(differentialExperiment);
        }
    }

    private Pair<List<String>, List<String>> headersAndBody(MockHttpServletResponse response) {
        List<String> headers = new ArrayList<>();
        List<String> body = new ArrayList<>();

        try {
            for (String l : response.getContentAsString().split("\n")) {
                if (l.startsWith("#")) {
                    headers.add(l);
                } else {
                    body.add(l);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return Pair.of(headers, body);
    }

    private void defaultParametersHeader(MicroarrayExperiment experiment) {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertThat(
                Pattern.matches(
                        ".*Genes .* up/down differentially expressed.*" + experiment.getAccession(), queryLine),
                is(true));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertThat("Gene ID", is(columnHeaders[0]));
        assertThat("Gene Name", is(columnHeaders[1]));
        assertThat("Design Element", is(columnHeaders[2]));
        assertThat(columnHeaders.length, greaterThan(3));
    }

    private void weHaveSomeResults(MicroarrayExperiment experiment) {
        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        if (Math.random() < 0.5) {
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(1D);
        requestPreferences.setFoldChangeCutoff(0D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneNameMicroarray(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns, hasSize(greaterThan(0)));
        assertThat(dataColumns, hasSize(geneNames.size()));
    }

    private void upDownRegulationWorks(MicroarrayExperiment experiment) {
        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setRegulation(Regulation.UP);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        List<String> geneNamesUp = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.DOWN);

        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesDown = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.UP_DOWN);


        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesUpDown = geneNames(headersAndBody(response).getRight());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertThat(resultsSeparately, is(new HashSet<>(geneNamesUpDown)));
    }


    private void noDataWithVeryStrictPValueCutoff(MicroarrayExperiment experiment) {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setCutoff(1e-100);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertThat(headersAndBody.getRight(), hasSize(1));
    }

    private void noDataWithVeryLargeFoldChangeCutoff(MicroarrayExperiment experiment) {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setFoldChangeCutoff(50000D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertThat(headersAndBody.getRight(), hasSize(1));
    }

    private ImmutableMap<String, String[]> indexByGeneNameMicroarray(List<String> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String l : lines) {
            String[] line = l.split("\t", -1);
            builder.put(
                    line[GENE_NAME_INDEX - 1] + " " + line[GENE_NAME_INDEX] + " " + line[GENE_NAME_INDEX + 1], line);
        }

        return builder.build();
    }

    private List<String> geneNames(List<String> lines) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (String l : lines) {
            String[] line = l.split("\t", -1);
            builder.add(line[GENE_NAME_INDEX]);
        }

        return builder.build();
    }

    @Test
    void testSomeRnaSeqExperiments() throws Exception {
        Set<String> rnaSeqExperiments = experimentTrader.getRnaSeqDifferentialExperimentAccessions();
        assertThat(rnaSeqExperiments, hasSize(greaterThan(0)));

        for (String accession: rnaSeqExperiments) {

            DifferentialExperiment differentialExperiment = (DifferentialExperiment)
                    experimentTrader.getPublicExperiment(accession);

            defaultParametersHeader(differentialExperiment);
            weHaveSomeResults(differentialExperiment);
            upDownRegulationWorks(differentialExperiment);
            noDataWithVeryStrictPValueCutoff(differentialExperiment);
            noDataWithVeryLargeFoldChangeCutoff(differentialExperiment);
        }
    }

    private void defaultParametersHeader(DifferentialExperiment experiment) throws Exception {

        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertThat(
                Pattern.matches(
                        ".*Genes .* up/down differentially expressed.*" + experiment.getAccession(), queryLine),
                is(true));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertThat("Gene ID", is(columnHeaders[0]));
        assertThat("Gene Name", is(columnHeaders[1]));
        assertThat(columnHeaders.length, greaterThan(2));
    }

    private void weHaveSomeResults(DifferentialExperiment experiment) throws Exception {
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

        if (Math.random() < 0.5) {
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(1D);
        requestPreferences.setFoldChangeCutoff(0D);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns, hasSize(greaterThan(0)));
        assertThat(dataColumns, hasSize(geneNames.size()));
    }

    private void upDownRegulationWorks(DifferentialExperiment experiment) throws Exception {
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        requestPreferences.setRegulation(Regulation.UP);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        List<String> geneNamesUp = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.DOWN);

        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesDown = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.UP_DOWN);


        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesUpDown = geneNames(headersAndBody(response).getRight());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertThat(resultsSeparately, is(new HashSet<>(geneNamesUpDown)));
    }

    private void noDataWithVeryStrictPValueCutoff(DifferentialExperiment experiment) throws Exception {
        // Sometimes the pValue is actually 0.0 due to rounding errors e.g. E-MTAB-5633 ENSMUSG00000056569
        // It‘s strictly above cutoff, see IsDifferentialExpressionAboveCutOff
        MockHttpServletResponse response = new MockHttpServletResponse();
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        requestPreferences.setCutoff(0.0);
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        assertThat(headersAndBody(response).getRight(), hasSize(1));
    }

    private void noDataWithVeryLargeFoldChangeCutoff(DifferentialExperiment experiment) throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        requestPreferences.setFoldChangeCutoff(50000D);
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        assertThat(headersAndBody(response).getRight(), hasSize(1));
    }

    private ImmutableMap<String, String[]> indexByGeneName(List<String> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String l : lines) {
            String[] line = l.split("\t", -1);
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.put(line[GENE_NAME_INDEX - 1] + " " + line[GENE_NAME_INDEX], line);
            }
        }

        return builder.build();
    }

    @Test
    void testSomeRnaSeqBaselineExperiments() throws Exception {
        Set<String> rnaSeqExperiments =
                experimentTrader.getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE);
        assertThat(rnaSeqExperiments, hasSize(greaterThan(0)));

        for (String accession: rnaSeqExperiments) {
            for (ExpressionUnit.Absolute.Rna unit:
                    dataFileHub.getRnaSeqBaselineExperimentFiles(accession).dataFiles()) {
                BaselineExperiment experiment = (BaselineExperiment)
                        experimentTrader.getPublicExperiment(accession);

                defaultParametersHeaderRnaSeqBaseline(experiment, unit);
                weHaveSomeResultsRnaSeqBaseline(experiment, unit);
                noDataWithVeryLargeCutoffRnaSeqBaseline(experiment, unit);
            }
        }
    }

    private void defaultParametersHeaderRnaSeqBaseline(BaselineExperiment experiment,
                                                      ExpressionUnit.Absolute.Rna unit) throws Exception {
        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(unit);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqBaselineExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertThat(Pattern.matches(".*Genes .* expressed.*" + experiment.getAccession(), queryLine), is(true));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertThat("Gene ID", is(columnHeaders[0]));
        assertThat("Gene Name", is(columnHeaders[1]));
        assertThat(columnHeaders.length, greaterThan(2));
    }

    private void weHaveSomeResultsRnaSeqBaseline(BaselineExperiment experiment,
                                                ExpressionUnit.Absolute.Rna unit) throws Exception {
        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(unit);

        if (Math.random() < 0.5) {
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(0.0);
        requestPreferences.setGeneQuery(SemanticQuery.create());

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqBaselineExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneNameMicroarray(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns, hasSize(greaterThan(0)));
        assertThat(dataColumns, hasSize(geneNames.size()));
    }

    private void noDataWithVeryLargeCutoffRnaSeqBaseline(BaselineExperiment experiment,
                                                        ExpressionUnit.Absolute.Rna unit) throws Exception {
        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(unit);
        requestPreferences.setGeneQuery(SemanticQuery.create());

        requestPreferences.setCutoff(10000000D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqBaselineExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertThat(headersAndBody.getRight(), hasSize(1));
    }

    @Test
    void testSomeProteomicsBaselineExperiments() throws Exception {
        Set<String> experimentAccessions =
                experimentTrader.getPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE);
        assertThat(experimentAccessions, hasSize(greaterThan(0)));

        for (String accession: experimentAccessions) {
            BaselineExperiment experiment = (BaselineExperiment)
                    experimentTrader.getPublicExperiment(accession);

            defaultParametersHeaderProteomicsBaseline(experiment);
            weHaveSomeResultsProteomicsBaseline(experiment);
            noDataWithVeryLargeCutoffProteomicsBaseline(experiment);
        }
    }

    private void defaultParametersHeaderProteomicsBaseline(BaselineExperiment experiment) throws Exception {
        ProteomicsBaselineRequestPreferences requestPreferences = new ProteomicsBaselineRequestPreferences();

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.proteomicsExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertThat(Pattern.matches(".*Genes .* expressed.*" + experiment.getAccession(), queryLine), is(true));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertThat("Gene ID", is(columnHeaders[0]));
        assertThat("Gene Name", is(columnHeaders[1]));
        assertThat(columnHeaders.length, greaterThan(2));
    }

    private void weHaveSomeResultsProteomicsBaseline(BaselineExperiment experiment) throws Exception {
        ProteomicsBaselineRequestPreferences requestPreferences = new ProteomicsBaselineRequestPreferences();

        if (Math.random() < 0.5) {
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(0.0);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.proteomicsExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneNameMicroarray(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns, hasSize(greaterThan(0)));
        assertThat(dataColumns, hasSize(geneNames.size()));
    }

    private void noDataWithVeryLargeCutoffProteomicsBaseline(BaselineExperiment experiment) throws Exception {
        ProteomicsBaselineRequestPreferences requestPreferences = new ProteomicsBaselineRequestPreferences();

        requestPreferences.setCutoff(100D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.proteomicsExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertThat(headersAndBody.getRight(), hasSize(1));
    }
}
