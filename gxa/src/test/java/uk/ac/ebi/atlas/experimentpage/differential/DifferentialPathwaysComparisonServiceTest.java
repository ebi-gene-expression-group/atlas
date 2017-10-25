package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialPathwaysComparisonServiceTest {

    @Mock
    private DifferentialRequestContextFactory mockDifferentialRequestContextFactory;
    @Mock
    private DifferentialRequestContext mockRequestContext;
    @Mock
    private DifferentialRequestPreferences mockDifferentialRequestPreferences;
    @Mock
    private ConfigurationTrader mockConfigurationTrader;
    @Mock
    private MicroarrayExperimentConfiguration mockMicroarrayExperimentConfiguration;
    @Mock
    private DifferentialProfilesHeatMap mockDifferentialProfilesHeatMap;
    @Mock
    private DifferentialExperiment mockDifferentialExperiment;

    private DifferentialPathwaysComparisonService subject;

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
        MockDataFileHub mockDataFileHub = MockDataFileHub.create();

        String accession = "E-GEOD-54351";

        mockDataFileHub.addReactomePathwaysFile(accession, c1.getId(),
                Lists.newArrayList(c1s1, c1s2, c1s3, c1s4, c1s5, c1s6, c1s7, c1s8, c1s9, c1s10, c1s11));
        mockDataFileHub.addReactomePathwaysFile(accession, c2.getId(),
                Lists.newArrayList(c2s1, c2s2, c2s3, c2s4, c2s5, c2s6, c2s7, c2s8, c2s9, c2s10, c2s11));

        this.subject = new DifferentialPathwaysComparisonService<>(mockDataFileHub, mockConfigurationTrader,
                mockDifferentialRequestContextFactory, mockDifferentialProfilesHeatMap);

        when(mockDifferentialExperiment.getAccession()).thenReturn(accession);
        when(mockConfigurationTrader.getMicroarrayExperimentConfiguration(accession)).thenReturn(mockMicroarrayExperimentConfiguration);
        when(mockConfigurationTrader.getMicroarrayExperimentConfiguration(accession).getContrasts()).thenReturn(Lists.newArrayList(c1,c2));

        setUpDataForTest();
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

    @Test
    public void testConstructPathwaysByComparison() throws Exception {

        Map multimap = subject.constructPathwaysByComparison(mockDifferentialExperiment, mockDifferentialRequestPreferences);
        assertNotNull(multimap);
        assertThat(multimap.size(), is(1));

        //Contrast 2
        HashMultimap values = (HashMultimap) multimap.get(c2);
        Map map = values.asMap();

        Set<String> expectedPathways = Sets.newHashSet("R-MMU-416482", "R-MMU-397014", "R-MMU-199418", "R-MMU-193648", "R-MMU-1483206");
        assertThat(map.keySet(), is(expectedPathways));

        Collection<Pair<String, DifferentialExpression>> pairCollection1 = (Collection<Pair<String, DifferentialExpression>>) map.get("R-MMU-416482");
        assertThat(pairCollection1.size(), is(1));
        assertThat(pairCollection1.iterator().next().getLeft(), is("ENSMUSG00000002489"));
        assertThat(pairCollection1.iterator().next().getRight().getAbsoluteFoldChange(), is(12.9595151130145));

        Collection<Pair<String, DifferentialExpression>> pairCollection2 = (Collection<Pair<String, DifferentialExpression>>) map.get("R-MMU-397014");
        assertThat(pairCollection2.size(), is(1));
        assertThat(pairCollection2.iterator().next().getLeft(), is("ENSMUSG00000004988"));
        assertThat(pairCollection2.iterator().next().getRight().getAbsoluteFoldChange(), is(1.8));

        Collection<Pair<String, DifferentialExpression>> pairCollection3 = (Collection<Pair<String, DifferentialExpression>>) map.get("R-MMU-199418");
        assertThat(pairCollection3.size(), is(1));
        assertThat(pairCollection3.iterator().next().getLeft(), is("ENSMUSG00000005534"));
        assertThat(pairCollection3.iterator().next().getRight().getAbsoluteFoldChange(), is(1.6));

        Collection<Pair<String, DifferentialExpression>> pairCollection4 = (Collection<Pair<String, DifferentialExpression>>) map.get("R-MMU-193648");
        assertThat(pairCollection4.size(), is(1));
        assertThat(pairCollection4.iterator().next().getLeft(), is("ENSMUSG00000002489"));
        assertThat(pairCollection4.iterator().next().getRight().getAbsoluteFoldChange(), is(1.9));

        Pair<String, DifferentialExpression> pair1 = new ImmutablePair<>("ENSMUSG00000001211",
                new MicroarrayExpression(3.79115384690853E-4, -1.2, -9.63904617660614));
        Pair<String, DifferentialExpression> pair2 = new ImmutablePair<>("ENSMUSG00000002475",
                new MicroarrayExpression(1.63591283639218E-4, -1.3, -11.5703820776891));
        Collection<Pair<String, DifferentialExpression>> expectedCollection = new HashSet<>();
        expectedCollection.add(pair1);
        expectedCollection.add(pair2);

        Collection<Pair<String, DifferentialExpression>> pairCollection5 = (Collection<Pair<String, DifferentialExpression>>) map.get("R-MMU-1483206");
        assertThat(pairCollection5.size(), is(2));
        assertThat(pairCollection5, is(expectedCollection));
    }

    public void setUpDataForTest() {
        //R-MMU-416482
        MicroarrayProfile p1 = new MicroarrayProfile("ENSMUSG00000002489", "Tiam1", "10440738" );
        p1.add(c1, new MicroarrayExpression(0.00106066313805208, -1.3, -9.62450495107545));
        p1.add(c2, new MicroarrayExpression(9.6990163222472E-5, 12.9595151130145, 12.9595151130145));

        DifferentialProfilesList<MicroarrayProfile> profile1 = new DifferentialProfilesList<>();
        profile1.add(p1);
        profile1.setTotalResultCount(1);

        //R-MMU-2132295 profiles = 0
        DifferentialProfilesList<MicroarrayProfile> profile2 = new DifferentialProfilesList<>();
        profile2.setTotalResultCount(0);

        //R-MMU-5620924 profiles = 0
        DifferentialProfilesList<MicroarrayProfile> profile3 = new DifferentialProfilesList<>();
        profile3.setTotalResultCount(0);

        //R-MMU-397014
        MicroarrayProfile p2 = new MicroarrayProfile("ENSMUSG00000004988", "Fxyd4", "10547206" );
        p2.add(c1, new MicroarrayExpression(0.0472977321050985, 1.5, 3.2390476812278));
        p2.add(c2, new MicroarrayExpression(9.21199754501842E-4, 1.8, 7.89282725675418));

        DifferentialProfilesList<MicroarrayProfile> profile4 = new DifferentialProfilesList<>();
        profile4.add(p2);
        profile4.setTotalResultCount(1);


        //R-MMU-193648
        MicroarrayProfile p3 = new MicroarrayProfile("ENSMUSG00000002489", "Tiam1", "10440738" );
        p3.add(c1, new MicroarrayExpression(0.00106066313805208, -1.3, -9.62450495107545));
        p3.add(c2, new MicroarrayExpression(9.6990163222472E-5, 1.9, 12.9595151130145));

        DifferentialProfilesList<MicroarrayProfile> profile5 = new DifferentialProfilesList<>();
        profile5.add(p3);
        profile5.setTotalResultCount(1);

        //R-MMU-373760 profiles = 0
        DifferentialProfilesList<MicroarrayProfile> profile6 = new DifferentialProfilesList<>();
        profile6.setTotalResultCount(0);

        //R-MMU-199418
        MicroarrayProfile p4 = new MicroarrayProfile("ENSMUSG00000005534", "Insr", "10576692" );
        p4.add(c2, new MicroarrayExpression(3.49771991467334E-4, -1.6, -9.81644391683006));
        MicroarrayProfile p5 = new MicroarrayProfile("ENSMUSG00000005534", "Insr", "10576696" );
        p5.add(c2, new MicroarrayExpression(3.44661129727514E-4, -1.4, -9.8473138195065));
        MicroarrayProfile p6 = new MicroarrayProfile("ENSMUSG00000003541", "Ier3", "10444890" );
        p6.add(c1, new MicroarrayExpression(0.00221450787547184, 1.1, 8.05959759149795));

        DifferentialProfilesList<MicroarrayProfile> profile7 = new DifferentialProfilesList<>();
        profile7.add(p4);
        profile7.add(p5);
        profile7.add(p6);
        profile7.setTotalResultCount(3);

        //R-MMU-2029480
        DifferentialProfilesList<MicroarrayProfile> profile8 = new DifferentialProfilesList<>();
        profile8.setTotalResultCount(0);

        //R-MMU-5654741
        DifferentialProfilesList<MicroarrayProfile> profile9 = new DifferentialProfilesList<>();
        profile9.setTotalResultCount(0);

        //R-MMU-1483206
        MicroarrayProfile p7 = new MicroarrayProfile("ENSMUSG00000001211", "Agpat3", "10370471" );
        p7.add(c2, new MicroarrayExpression(3.79115384690853E-4, -1.2, -9.63904617660614));
        MicroarrayProfile p8 = new MicroarrayProfile("ENSMUSG00000002475", "Abhd3", "10457475" );
        p8.add(c1, new MicroarrayExpression(9.80641472323071E-4, -1.5, -9.82105668776165));
        MicroarrayProfile p9 = new MicroarrayProfile("ENSMUSG00000002475", "Abhd3", "10457475" );
        p9.add(c2, new MicroarrayExpression(1.63591283639218E-4, -1.3, -11.5703820776891));

        DifferentialProfilesList<MicroarrayProfile> profile10 = new DifferentialProfilesList<>();
        profile10.add(p7);
        profile10.add(p8);
        profile10.add(p9);
        profile10.setTotalResultCount(3);


        when(mockDifferentialRequestContextFactory.create(mockDifferentialExperiment, mockDifferentialRequestPreferences)).thenReturn(mockRequestContext);
        when(mockDifferentialProfilesHeatMap.fetch(mockRequestContext)).thenReturn(profile1,profile4,profile2,profile5,profile6,profile3,
                profile9,profile7,profile8,profile10);

    }
}