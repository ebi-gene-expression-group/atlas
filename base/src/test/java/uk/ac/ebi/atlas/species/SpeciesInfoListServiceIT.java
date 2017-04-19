package uk.ac.ebi.atlas.species;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import javax.inject.Inject;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class SpeciesInfoListServiceIT {

    @Inject
    private SpeciesPropertiesTrader speciesPropertiesTrader;

    @Mock
    private ExperimentInfoListService experimentInfoListService;

    @Mock
    private ExperimentInfo experimentInfo1;
    @Mock
    private ExperimentInfo experimentInfo2;
    @Mock
    private ExperimentInfo experimentInfo3;
    @Mock
    private ExperimentInfo experimentInfo4;
    @Mock
    private ExperimentInfo experimentInfo5;
    @Mock
    private ExperimentInfo experimentInfo6;
    @Mock
    private ExperimentInfo experimentInfo7;
    @Mock
    private ExperimentInfo experimentInfo8;
    @Mock
    private ExperimentInfo experimentInfo9;
    @Mock
    private ExperimentInfo experimentInfo10;
    @Mock
    private ExperimentInfo experimentInfo11;
    @Mock
    private ExperimentInfo experimentInfo12;

    private SpeciesInfoListService subject;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(experimentInfo1.getSpecies()).thenReturn("Homo sapiens");
        when(experimentInfo1.getKingdom()).thenReturn("animals");
        when(experimentInfo1.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentInfo2.getSpecies()).thenReturn("Anolis carolensis");
        when(experimentInfo2.getKingdom()).thenReturn("animals");
        when(experimentInfo2.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentInfo11.getSpecies()).thenReturn("Mus musculus");
        when(experimentInfo11.getKingdom()).thenReturn("animals");
        when(experimentInfo11.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentInfo3.getSpecies()).thenReturn("Mus musculus");
        when(experimentInfo3.getKingdom()).thenReturn("animals");
        when(experimentInfo3.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        when(experimentInfo4.getSpecies()).thenReturn("Rattus norvegicus");
        when(experimentInfo4.getKingdom()).thenReturn("animals");
        when(experimentInfo4.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentInfo5.getSpecies()).thenReturn("Sorghum bicolor");
        when(experimentInfo5.getKingdom()).thenReturn("plants");
        when(experimentInfo5.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentInfo12.getSpecies()).thenReturn("Rattus norvegicus");
        when(experimentInfo12.getKingdom()).thenReturn("animals");
        when(experimentInfo12.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        when(experimentInfo6.getSpecies()).thenReturn("Glycine max");
        when(experimentInfo6.getKingdom()).thenReturn("plants");
        when(experimentInfo6.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        when(experimentInfo7.getSpecies()).thenReturn("Solanum tuberosum");
        when(experimentInfo7.getKingdom()).thenReturn("plants");
        when(experimentInfo7.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        when(experimentInfo8.getSpecies()).thenReturn("Saccharomyces cerevisiae");
        when(experimentInfo8.getKingdom()).thenReturn("fungi");
        when(experimentInfo8.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        when(experimentInfo9.getSpecies()).thenReturn("Homo sapiens");
        when(experimentInfo9.getKingdom()).thenReturn("animals");
        when(experimentInfo9.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentInfo10.getSpecies()).thenReturn("Homo sapiens");
        when(experimentInfo10.getKingdom()).thenReturn("animals");
        when(experimentInfo10.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        List<ExperimentInfo> experimentInfos = Lists.newArrayList(experimentInfo1, experimentInfo2, experimentInfo3,
                experimentInfo4, experimentInfo5, experimentInfo6, experimentInfo7, experimentInfo8,
                experimentInfo9, experimentInfo10, experimentInfo11, experimentInfo12);


        when(experimentInfoListService.listPublicExperiments()).thenReturn(experimentInfos);

        subject = new SpeciesInfoListService(speciesPropertiesTrader, experimentInfoListService);

    }

    @Test
    public void getTopSixSpeciesByExperimentCount() throws Exception {

        //return
        List<SpeciesInfoListService.SpeciesInfo> speciesInfoList = subject.getTopSixSpeciesByExperimentCount();

        assertThat(speciesInfoList.size(), is(6));

        assertThat(speciesInfoList.get(0).getSpecies(), is("homo sapiens"));
        assertThat(speciesInfoList.get(0).getKingdom(), is("animals"));
        assertThat(speciesInfoList.get(0).getTotalExperiments(), is(3));
        assertThat(speciesInfoList.get(0).getBaselineExperiments(), is(2));
        assertThat(speciesInfoList.get(0).getDifferentialExperiments(), is(1));

        assertThat(speciesInfoList.get(1).getSpecies(), is("rattus norvegicus"));
        assertThat(speciesInfoList.get(1).getKingdom(), is("animals"));
        assertThat(speciesInfoList.get(1).getTotalExperiments(), is(2));
        assertThat(speciesInfoList.get(1).getBaselineExperiments(), is(1));
        assertThat(speciesInfoList.get(1).getDifferentialExperiments(), is(1));

        assertThat(speciesInfoList.get(2).getSpecies(), is("mus musculus"));
        assertThat(speciesInfoList.get(2).getKingdom(), is("animals"));
        assertThat(speciesInfoList.get(2).getTotalExperiments(), is(2));
        assertThat(speciesInfoList.get(2).getBaselineExperiments(), is(1));
        assertThat(speciesInfoList.get(2).getDifferentialExperiments(), is(1));

        assertThat(speciesInfoList.get(3).getSpecies(), is("sorghum bicolor"));
        assertThat(speciesInfoList.get(3).getKingdom(), is("plants"));
        assertThat(speciesInfoList.get(3).getTotalExperiments(), is(1));
        assertThat(speciesInfoList.get(3).getBaselineExperiments(), is(1));
        assertThat(speciesInfoList.get(3).getDifferentialExperiments(), is(0));

        assertThat(speciesInfoList.get(4).getSpecies(), is("solanum tuberosum"));
        assertThat(speciesInfoList.get(4).getKingdom(), is("plants"));
        assertThat(speciesInfoList.get(4).getTotalExperiments(), is(1));
        assertThat(speciesInfoList.get(4).getBaselineExperiments(), is(0));
        assertThat(speciesInfoList.get(4).getDifferentialExperiments(), is(1));

        assertThat(speciesInfoList.get(5).getSpecies(), is("saccharomyces cerevisiae"));
        assertThat(speciesInfoList.get(5).getKingdom(), is("fungi"));
        assertThat(speciesInfoList.get(5).getTotalExperiments(), is(1));
        assertThat(speciesInfoList.get(5).getBaselineExperiments(), is(0));
        assertThat(speciesInfoList.get(5).getDifferentialExperiments(), is(1));

    }

    @Test
    public void getFilterByKingdom() throws Exception {

        //return
        List<SpeciesInfoListService.SpeciesInfo> fungiList = subject.getFilterByKingdom("fungi");
        assertThat(fungiList.size(), is(1));

        List<SpeciesInfoListService.SpeciesInfo> animalsList = subject.getFilterByKingdom("animals");
        assertThat(animalsList.size(), is(3));

        List<SpeciesInfoListService.SpeciesInfo> plantsList = subject.getFilterByKingdom("plants");
        assertThat(plantsList.size(), is(3));
    }

}