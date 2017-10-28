package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.efo.EFOTreeNodesTrader;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroupsFake;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.ContrastTest;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperimentTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConditionsLookupServiceTest {

    private ConditionsLookupService subject;

    private ExperimentDesign experimentDesignMock;

    private String experimentAccession = "EXP-1";

    private Contrast contrastMock;

    @Mock
    private EFOLookupService efoLookupService;

    @Before
    public void setup(){

        contrastMock = mock(Contrast.class);
        given(contrastMock.getReferenceAssayGroup()).willReturn(new AssayGroup("g1", "Assay1", "Assay2"));
        given(contrastMock.getTestAssayGroup()).willReturn(new AssayGroup("g2", "Assay3"));
        given(contrastMock.getId()).willReturn("g1_g2");

        experimentDesignMock = mock(ExperimentDesign.class);
        HashMap<String, String> factors1 = Maps.newHashMap();
        factors1.put("fn1", "fv1");
        HashMap<String, String> factors2 = Maps.newHashMap();
        factors2.put("fn2", "fv2");

        HashMap<String, String> samples1 = Maps.newHashMap();
        samples1.put("sn1", "sv1");
        HashMap<String, String> samples2 = Maps.newHashMap();
        samples2.put("sn2", "sv2");


        given(experimentDesignMock.getFactorValues("Assay1")).willReturn(factors1);
        given(experimentDesignMock.getFactorValues("Assay2")).willReturn(factors1);
        given(experimentDesignMock.getFactorValues("Assay3")).willReturn(factors2);

        given(experimentDesignMock.getSampleCharacteristicsValues("Assay1")).willReturn(samples1);
        given(experimentDesignMock.getSampleCharacteristicsValues("Assay2")).willReturn(samples1);
        given(experimentDesignMock.getSampleCharacteristicsValues("Assay3")).willReturn(samples2);

        given(efoLookupService.getLabels(any())).willReturn(ImmutableSet.of());

        subject = new ConditionsLookupService(efoLookupService);
    }

    @Test
    public void buildDifferentialConditionProperties() throws Exception {
        when(efoLookupService.expandOntologyTerms(any()))
                .thenReturn(ImmutableSetMultimap.of());

        Collection<DifferentialCondition> result = subject.buildPropertiesForDifferentialExperiment
                (experimentAccession,experimentDesignMock, ImmutableSet.of(contrastMock));

        assertThat(result.size(), is(2));
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g1", "g1_g2", Sets.newHashSet("fv1", "sv1"))), is(true));
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g2", "g1_g2", Sets.newHashSet("fv2", "sv2"))), is(true));
    }


    @Test
    public void buildDifferentialConditionPropertiesIncludingOntologyTerms() throws Exception {

        when(efoLookupService.expandOntologyTerms(any())).thenReturn(
                new ImmutableSetMultimap.Builder<String, String>()
                        .putAll("Assay1", "obo", "efo")
                        .build()
        );
        Collection<DifferentialCondition> result = subject.buildPropertiesForDifferentialExperiment(experimentAccession,experimentDesignMock, ImmutableSet.of(contrastMock));

        assertThat(result.size(), is(3));
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g1", "g1_g2", Sets.newHashSet("fv1", "sv1", "obo", "efo"))), is(true));  //Assay1
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g1", "g1_g2", Sets.newHashSet("fv1", "sv1"))), is(true));   //Assay2
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g2", "g1_g2", Sets.newHashSet("fv2", "sv2"))), is(true));

    }

    private void testWithOneAssayGroup(BiConsumer<AssayGroup,ExperimentDesign> setup, Collection<String> expectedConditions){
        EFOTreeNodesTrader m = mock(EFOTreeNodesTrader.class);
        when(m.getTreeNodes()).thenReturn(ImmutableMap.of());

        ExperimentDesign experimentDesign = new ExperimentDesign();
        AssayGroup assayGroup = AssayGroupsFake.get().iterator().next();
        List<AssayGroup> assayGroups = ImmutableList.of(assayGroup);

        setup.accept(assayGroup, experimentDesign);


        assertThat(
                new ConditionsLookupService(new EFOLookupService(m)).conditionsPerDataColumnDescriptor(BaselineExperimentTest.mockExperiment(
                        experimentDesign,
                        assayGroups,
                        ExperimentDisplayDefaults.simpleDefaults(),
                        "accession"
                )),
                is(ImmutableSetMultimap.builder().putAll(assayGroup.getId(), expectedConditions).build())
        );
    }

    private void testWithOneContrast(BiConsumer<Contrast,ExperimentDesign> setup, Collection<String> expectedConditions){
        EFOTreeNodesTrader m = mock(EFOTreeNodesTrader.class);
        when(m.getTreeNodes()).thenReturn(ImmutableMap.of());

        ExperimentDesign experimentDesign = new ExperimentDesign();
        Contrast contrast = ContrastTest.get(1).iterator().next();
        List<Contrast> contrasts = ImmutableList.of(contrast);

        setup.accept(contrast, experimentDesign);

        assertThat(
                new ConditionsLookupService(new EFOLookupService(m)).conditionsPerDataColumnDescriptor(DifferentialExperimentTest.mockExperiment(
                        "accession",
                        contrasts,
                        experimentDesign
                )),
                is(ImmutableSetMultimap.builder().putAll(contrast.getId(), expectedConditions).build())
        );
    }

    @Test
    public void nullCases() {
        testWithOneAssayGroup((assayGroup,experimentDesign)-> {}, ImmutableSet.of());
        testWithOneContrast((contrast,experimentDesign)->{}, ImmutableSet.of());
    }

    @Test
    public void factorValueAppearsAsCondition() {
        testWithOneAssayGroup((assayGroup,experimentDesign) ->
                experimentDesign.putFactor(
                        assayGroup.getFirstAssayAccession(), "factor header", "factor value"
                ), ImmutableSet.of("factor value"));
    }

    @Test
    public void sampleCharacteristicAppearsAsCondition() {
        testWithOneAssayGroup((assayGroup,experimentDesign) ->
                experimentDesign.putSampleCharacteristic(
                        assayGroup.getFirstAssayAccession(), "sample characteristic header", "sample characteristic value"
                ), ImmutableSet.of("sample characteristic value"));
    }

    @Test
    public void assayGroupFactorValueAppearsAsCondition(){
        testWithOneContrast((contrast,experimentDesign) ->
                experimentDesign.putFactor(
                        contrast.getReferenceAssayGroup().getFirstAssayAccession(), "factor header", "factor value"
                ), ImmutableSet.of("factor value"));
        testWithOneContrast((contrast,experimentDesign) ->
                experimentDesign.putFactor(
                        contrast.getTestAssayGroup().getFirstAssayAccession(), "factor header", "factor value"
                ), ImmutableSet.of("factor value"));
    }

    @Test
    public void assayGroupSampleCharacteristicAppearsAsCondition(){
        testWithOneContrast((contrast,experimentDesign) ->
                experimentDesign.putSampleCharacteristic(
                        contrast.getReferenceAssayGroup().getFirstAssayAccession(), "sample characteristic header", "sample characteristic value"
                ), ImmutableSet.of("sample characteristic value"));
        testWithOneContrast((contrast,experimentDesign) ->
                experimentDesign.putSampleCharacteristic(
                        contrast.getTestAssayGroup().getFirstAssayAccession(),  "sample characteristic header", "sample characteristic value"
                ), ImmutableSet.of("sample characteristic value"));
    }
}
