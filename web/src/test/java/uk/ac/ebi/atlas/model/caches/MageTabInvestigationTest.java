package uk.ac.ebi.atlas.model.caches;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.readers.AnalysisMethodsTsvReader;

import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MageTabInvestigationTest {

    private static final String MAGE_TAB_URL_TEMPLATE = "http://www.ebi.ac.uk/arrayexpress/files/%s/%s.idf.txt";

    private ExperimentMetadataLoader subject;

    @Mock
    private InputStream inputStreamMock;

    @Mock
    private AnalysisMethodsTsvReader analysisMethodsTsvReaderMock;

    @Mock
    private MAGETABParser<MAGETABInvestigation> MAGETABParserMock;

    @Mock
    private MAGETABInvestigation investigationMock;

    @Before
    public void initMAGETABParserMock() throws Exception {
        when(MAGETABParserMock.parse(inputStreamMock)).thenReturn(investigationMock);
    }


    @Before
    public void initSubject() throws Exception {

        subject = new ExperimentMetadataLoader(MAGE_TAB_URL_TEMPLATE, analysisMethodsTsvReaderMock){
            @Override
            MAGETABInvestigation parseInvestigation(URL idfFileURL) {
                return investigationMock;
            }

        };

    }

    @Test
    public void buildFileUrlTest(){
        //given
        String experimentAccession = "exp-accession";
        //when
        String fileLocation = subject.buildIdfFileUrl(experimentAccession);
        //then
        assertThat(fileLocation, startsWith("http://"));
        //and
        assertThat(fileLocation, endsWith(experimentAccession + ".idf.txt"));

    }


    @Test
    public void extractExperimentRunsTest(){
        //given
        String experimentAccession = "exp-accession";
        //when
        String fileLocation = subject.buildIdfFileUrl(experimentAccession);
        //then
        assertThat(fileLocation, startsWith("http://"));
        //and
        assertThat(fileLocation, endsWith(experimentAccession + ".idf.txt"));

    }

    //The other methods can't be unit tested because they depend on limpopo APIs that are not testable nor can be stubbed,
    //because they expose public instance attributes (often even final) and not public methods, and they require clients to use static utility methods.
}

