
package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.ExperimentInfo;
import uk.ac.ebi.atlas.utils.ExperimentInfoListBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentsListControllerTest {

    private static final String EXPERIMENT_ACCESSION = "ACCESSION";
    private static final String EXPECTED_JSON = "{\"aaData\":[{\"experimentAccession\":\"" + EXPERIMENT_ACCESSION +
            "\",\"numberOfAssays\":0,\"numberOfContrasts\":0,\"experimentalFactors\":[],\"arrayDesigns\":[],\"arrayDesignNames\":[]}]}";

    @Mock
    private ExperimentInfoListBuilder experimentInfoListBuilderMock;

    private ExperimentInfo experimentInfo = new ExperimentInfo();

    private ExperimentsListController subject;

    @Before
    public void setUp() throws Exception {

        experimentInfo.setExperimentAccession(EXPERIMENT_ACCESSION);

        when(experimentInfoListBuilderMock.build()).thenReturn(Lists.newArrayList(experimentInfo));

        subject = new ExperimentsListController(experimentInfoListBuilderMock);
    }

    @Test
    public void testGetExperimentsList() throws Exception {
        assertThat(subject.getExperimentsList(), is(EXPECTED_JSON));
    }
}