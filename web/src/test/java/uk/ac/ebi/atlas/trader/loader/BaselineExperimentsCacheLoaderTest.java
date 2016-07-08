
package uk.ac.ebi.atlas.trader.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentsCacheLoaderTest {

    public static final String SPECIES = "species";
    public static final String DEFAULT_QUERY_FACTOR_TYPE = "defaultQueryFactorType";
    public static final String FACTOR_TYPE = "factorType";

    @Mock
    private ExperimentRun experimentRunMock1;

    @Mock
    private ExperimentRun experimentRunMock2;

    @Mock
    private FactorGroup factorGroupMock1;

    @Mock
    private FactorGroup factorGroupMock2;

    @Mock
    private Factor factorMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private SpeciesKingdomTrader speciesKingdomTraderMock;

    @Mock
    private TsvReader experimentDataTsvReaderMock;

    @Mock
    private FileTsvReaderBuilder fileTsvReaderBuilderMock;

    @Mock
    private AssayGroups assayGroupsMock;

    private BaselineExperimentsCacheLoader subject;

    private final static String PATH_TEMPLATE_FAKE = "FAKE_PATH_TEMPLATE";

    @Before
    public void initSubject(){

        when(fileTsvReaderBuilderMock.forTsvFilePathTemplate(PATH_TEMPLATE_FAKE)).thenReturn(fileTsvReaderBuilderMock);
        when(fileTsvReaderBuilderMock.build()).thenReturn(experimentDataTsvReaderMock);

        subject = new BaselineExperimentsCacheLoader(new BaselineExperimentExpressionLevelFile(fileTsvReaderBuilderMock, PATH_TEMPLATE_FAKE), configurationTraderMock, speciesKingdomTraderMock);
    }


    @Test
    public void testGetRequiredFactorTypes() throws Exception {

        Set<Factor> defaultFilterFactors = new HashSet<>();

        Set<String> requiredFactorTypes = subject.getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        assertThat(requiredFactorTypes, contains(DEFAULT_QUERY_FACTOR_TYPE));

        when(factorMock.getType()).thenReturn(FACTOR_TYPE);
        defaultFilterFactors.add(factorMock);
        requiredFactorTypes = subject.getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        assertThat(requiredFactorTypes, containsInAnyOrder(DEFAULT_QUERY_FACTOR_TYPE, FACTOR_TYPE));
    }


}