package uk.ac.ebi.atlas.experimentimport.idf;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.commons.readers.ArrayExpressIdfStreamerFactory;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;

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

    private IdfParser subject;

    @Inject
    private ArrayExpressIdfStreamerFactory arrayExpressIdfStreamerFactory;
    @Inject
    private DataFileHubFactory dataFileHubFactory;

    @BeforeAll
    public void setUp() {
        IdfStreamerFactory idfStreamerFactory = new IdfStreamerFactory(arrayExpressIdfStreamerFactory, dataFileHubFactory.getScxaDataFileHub());
        this.subject = new IdfParser(idfStreamerFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = { "E-ENAD-13", "E-GEOD-106540"})
    public void testParserForSingleCell(String experimentAccession) {
        IdfParserOutput result = subject.newParse(experimentAccession);

        assertThat(result.getExpectedClusters(), is(greaterThanOrEqualTo(0)));
        assertThat(result.getTitle(), is(not(isEmptyString())));
        assertThat(result.getPublications(), is(notNullValue()));
    }
}
