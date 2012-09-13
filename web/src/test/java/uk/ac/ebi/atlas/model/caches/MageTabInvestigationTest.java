package uk.ac.ebi.atlas.model.caches;

/*
@RunWith(MockitoJUnitRunner.class)
public class MageTabInvestigationTest {

    private MageTabInvestigationLoader subject;

    private URL urlFake;

    @Mock
    private InputStream inputStreamMock;

    @Mock
    private MAGETABParser MAGETABParserMock;

    @Mock
    private MAGETABInvestigation investigationMock;

    @Before
    public void initMAGETABParserMock() throws Exception {
        urlFake = new URL("file://fakeURL");
        when(MAGETABParserMock.parse(inputStreamMock)).thenReturn(investigationMock);
    }


    @Before
    public void initSubject() throws Exception {

        subject = new MageTabInvestigationLoader(){
            @Override
            MAGETABInvestigation parseInvestigation(URL idfFileURL) {
                return investigationMock;
            }

        };

    }

    @Test
    public void parseInvestigationShouldUseMAGETABParser() throws Exception {
        //given
        subject.parseInvestigation(urlFake);
        //then
        verify(MAGETABParserMock).parse(urlFake);
    }


    @Test(expected = IllegalStateException.class)
    public void parseInvestigationShouldThrowIllegalStateExceptionOnMAGETABParserErrors() throws Exception {
        //given
        given(MAGETABParserMock.parse(urlFake)).willThrow(new ParseException());
        //when
        subject.parseInvestigation(urlFake);
        //then expect IllegalStateException
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
        assertThat(fileLocation, endsWith("fileName"));

    }

    //The other methods can't be unit tested becouse they depend on limpopo APIs that are not testable nor can be stubbed,
    //because they expose public instance attributes (often even final) and not public methods, and they require clients to use static utility methods.
}
    */
