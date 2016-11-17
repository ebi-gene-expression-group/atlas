package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;

import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ConditionPropertiesBuilderTest {

    private DifferentialConditionsBuilder subject;

    @Mock
    private DifferentialExperiment experimentMock;

    @Mock
    private EFOLookupService efoLookupService;

    @Before
    public void setup(){
        given(experimentMock.getAccession()).willReturn("EXP-1");

        Contrast contrastMock = mock(Contrast.class);
        given(contrastMock.getReferenceAssayGroup()).willReturn(new AssayGroup("g1", "Assay1", "Assay2"));
        given(contrastMock.getTestAssayGroup()).willReturn(new AssayGroup("g2", "Assay3"));
        given(contrastMock.getId()).willReturn("g1_g2");

        ExperimentDesign experimentDesignMock = mock(ExperimentDesign.class);
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

        given(experimentMock.getContrasts()).willReturn(Sets.newHashSet(contrastMock));
        given(experimentMock.getExperimentDesign()).willReturn(experimentDesignMock);

        given(efoLookupService.getLabels(anySetOf(String.class))).willReturn(ImmutableSet.<String>of());

        subject = new DifferentialConditionsBuilder(efoLookupService);
    }

    @Test
    public void buildDifferentialConditionProperties() throws Exception {
        when(efoLookupService.expandOntologyTerms((ImmutableSetMultimap<String, String>) Matchers.any())).thenReturn(
                ImmutableSetMultimap.<String, String>of()
        );

        Collection<DifferentialCondition> result = subject.buildProperties(experimentMock);

        assertThat(result.size(), is(2));
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g1", "g1_g2", Sets.newHashSet("fv1", "sv1"))), is(true));
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g2", "g1_g2", Sets.newHashSet("fv2", "sv2"))), is(true));
    }


    @Test
    public void buildDifferentialConditionPropertiesIncludingOntologyTerms() throws Exception {

        when(efoLookupService.expandOntologyTerms((ImmutableSetMultimap<String, String>) Matchers.any())).thenReturn(
                new ImmutableSetMultimap.Builder<String, String>()
                        .putAll("Assay1", "obo", "efo")
                        .build()
        );
        Collection<DifferentialCondition> result = subject.buildProperties(experimentMock);

        assertThat(result.size(), is(3));
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g1", "g1_g2", Sets.newHashSet("fv1", "sv1", "obo", "efo"))), is(true));  //Assay1
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g1", "g1_g2", Sets.newHashSet("fv1", "sv1"))), is(true));   //Assay2
        assertThat(result.contains(new DifferentialCondition("EXP-1", "g2", "g1_g2", Sets.newHashSet("fv2", "sv2"))), is(true));

    }
}
