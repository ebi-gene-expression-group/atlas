package uk.ac.ebi.atlas.experimentimport.idf;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.commons.readers.ArrayExpressIdfStreamerFactory;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IdfParserIT {

    @Inject
    private ArrayExpressIdfStreamerFactory arrayExpressIdfStreamerFactory;
    @Inject
    private DataFileHubFactory dataFileHubFactory;
    @Inject
    private JdbcUtils jdbcUtils;

    @ParameterizedTest
    @MethodSource("singleCellExperimentsProvider")
    public void testParserForSingleCell(String experimentAccession) {
        IdfStreamerFactory idfStreamerFactory = new IdfStreamerFactory(arrayExpressIdfStreamerFactory, dataFileHubFactory.getScxaDataFileHub());
        IdfParser idfParser = new IdfParser(idfStreamerFactory);

        parseForDBExperimentAccession(idfParser, experimentAccession);
    }

    @ParameterizedTest
    @MethodSource("expressionAtlasExperimentsProvider")
    public void testParserForExpressionAtlas(String experimentAccession) {
        IdfStreamerFactory idfStreamerFactory = new IdfStreamerFactory(arrayExpressIdfStreamerFactory, dataFileHubFactory.getGxaDataFileHub());
        IdfParser idfParser = new IdfParser(idfStreamerFactory);

        parseForDBExperimentAccession(idfParser, experimentAccession);
    }

    private void parseForDBExperimentAccession(IdfParser idfParser, String experimentAccession) {
        IdfParserOutput result = idfParser.parse(experimentAccession);

        assertThat(result.getExpectedClusters(), is(greaterThanOrEqualTo(0)));
        assertThat(result.getTitle(), is(not(isEmptyString())));
        assertThat(result.getPublications(), is(notNullValue()));
    }

    private Iterable<String> singleCellExperimentsProvider() {
        return jdbcUtils.getPublicSingleCellExperimentAccessions();
    }

    private Iterable<String> expressionAtlasExperimentsProvider() {
        return jdbcUtils.getAllExpressionAtlasExperimentAccessions();
    }
}
