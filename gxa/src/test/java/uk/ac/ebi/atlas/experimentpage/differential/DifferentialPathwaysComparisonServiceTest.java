package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialPathwaysComparisonServiceTest {

    @Mock
    private DifferentialRequestContextFactory mockdifferentialRequestContextFactory;
    @Mock
    private DifferentialRequestPreferences mockdifferentialRequestPreferences;
    @Mock
    private ConfigurationTrader mockConfigurationTrader;
    @Mock
    private MicroarrayExperimentConfiguration mockMicroarrayExperimentConfiguration;
    @Mock
    private DifferentialProfilesHeatMap mockDifferentialProfilesHeatMap;
    @Mock
    private DifferentialExperiment mockDifferentialExperiment;

    private MockDataFileHub mockDataFileHub;
    private DifferentialPathwaysComparisonService subject;

    private String accession = "E-GEOD-54351";

    private AssayGroup referenceAssay1 = new AssayGroup("g1", "assay1");
    private AssayGroup testAssay1 = new AssayGroup("g2", "assay2");
    private AssayGroup referenceAssay2 = new AssayGroup("g3", "assay3");
    private AssayGroup testAssay2 = new AssayGroup("g4", "assay41", "assay42");

    private Contrast c1 = new Contrast("g3_g1", null, referenceAssay1, testAssay1, "first contrast");
    private Contrast c2 = new Contrast("g3_g2", null, referenceAssay2, testAssay2, "second contrast");

    private String[] c1s1 = new String[]{"Term", "Accession", "Genes (tot)", "Stat (non-dir.) p", "p adj (non-dir.)"};
    private String[] c1s2 = new String[]{"R-MMU-109581", "Apoptosis", "45", "0.535169358108118", "1"};
    private String[] c1s3 = new String[]{"R-MMU-109606", "Apoptosis", "16", "0.535169358108118", "1"};
    private String[] c1s4 = new String[]{"R-MMU-109688", "Apoptosis", "23", "0.535169358108118", "0.951294722034645"};
    private String[] c1s5 = new String[]{"R-MMU-110056", "Apoptosis", "10", "0.535169358108118", "0.7611075912506"};
    private String[] c1s6 = new String[]{"R-MMU-110362", "Apoptosis", "45", "0.535169358108118", "1"};
    private String[] c1s7 = new String[]{"R-MMU-111996", "Apoptosis", "45", "0.535169358108118", "1"};
    private String[] c1s8 = new String[]{"R-MMU-112040", "Apoptosis", "45", "0.535169358108118", "0.7611075912506"};
    private String[] c1s9 = new String[]{"R-MMU-1362300", "Apoptosis", "45", "0.535169358108118", "1"};
    private String[] c1s10 = new String[]{"R-MMU-141430", "Apoptosis", "45", "0.535169358108118", "0.87881981279076"};
    private String[] c1s11 = new String[]{"R-MMU-170822", "Apoptosis", "45", "0.535169358108118", "1"};

    private String[] c2s1 = new String[]{"Term", "Accession", "Genes (tot)", "Stat (non-dir.) p", "p adj (non-dir.)"};
    private String[] c2s2 = new String[]{"R-MMU-416482", "G alpha (12/13) signalling events", "33", "0.538397547287844", "0.0191488220970447"};
    private String[] c2s3 = new String[]{"R-MMU-2132295", "Apoptosis", "16", "0.535169358108118", "0.0280433635326222"};
    private String[] c2s4 = new String[]{"R-MMU-5620924", "Apoptosis", "23", "0.535169358108118", "0.0280433635326222"};
    private String[] c2s5 = new String[]{"R-MMU-397014", "Muscle contraction", "10", "0.535169358108118", "0.0280433635326222"};
    private String[] c2s6 = new String[]{"R-MMU-193648", "NRAGE signals death through JNK", "45", "0.535169358108118", "0.0280433635326222"};
    private String[] c2s7 = new String[]{"R-MMU-373760", "Apoptosis", "45", "0.535169358108118", "0.0280433635326222"};
    private String[] c2s8 = new String[]{"R-MMU-199418", "Negative regulation of the PI3K/AKT network", "45", "0.535169358108118", "0.0436946551249873"};
    private String[] c2s9 = new String[]{"R-MMU-2029480", "Fcgamma receptor (FCGR) dependent phagocytosis", "45", "0.535169358108118", "0.0436946551249873"};
    private String[] c2s10 = new String[]{"R-MMU-5654741", "Signaling by FGFR3", "45", "0.535169358108118", "0.0436946551249873"};
    private String[] c2s11 = new String[]{"R-MMU-1483206", "Apoptosis", "45", "0.535169358108118", "0.0436946551249873"};


    @Before
    public void setUp() throws Exception {
        mockDataFileHub = new MockDataFileHub();
        mockDataFileHub.addReactomePathwaysFile(accession, c1.getId(),
                ImmutableList.of(c1s1, c1s2, c1s3, c1s4, c1s5, c1s6, c1s7, c1s8, c1s9, c1s10, c1s11));
        mockDataFileHub.addReactomePathwaysFile(accession, c2.getId(),
                ImmutableList.of(c2s1, c2s2, c2s3, c2s4, c2s5, c2s6, c2s7, c2s8, c2s9, c2s10, c2s11));

        this.subject = new DifferentialPathwaysComparisonService<>(mockDataFileHub, mockConfigurationTrader,
                mockdifferentialRequestContextFactory, mockDifferentialProfilesHeatMap);

        when(mockDifferentialExperiment.getAccession()).thenReturn(accession);
        when(mockConfigurationTrader.getMicroarrayExperimentConfiguration(accession)).thenReturn(mockMicroarrayExperimentConfiguration);
        when(mockConfigurationTrader.getMicroarrayExperimentConfiguration(accession).getContrasts()).thenReturn(Lists.newArrayList(c1,c2));

    }

    @Test
    public void testGetPathwaysListPerComparison() throws Exception {

        Map comparisonPathwaysMap = subject.getPathwaysListPerComparison(mockDifferentialExperiment);

        assertNotNull(comparisonPathwaysMap);
        assertThat(comparisonPathwaysMap.size(), is(2));
        assertThat(comparisonPathwaysMap.get(c1), is(new ArrayList<>()));

        List<String> expectedList = Arrays.asList("R-MMU-416482", "R-MMU-397014", "R-MMU-2132295", "R-MMU-193648", "R-MMU-373760",
                "R-MMU-5620924", "R-MMU-5654741", "R-MMU-199418", "R-MMU-2029480", "R-MMU-1483206");
        assertThat(comparisonPathwaysMap.get(c2), is(expectedList));

    }

//    @Test
//    public void testConstructPathwaysByComparison() throws Exception {
//
//        assertNotNull(subject.constructPathwaysByComparison(mockDifferentialExperiment, mockdifferentialRequestPreferences));
//
//    }
}