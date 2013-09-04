package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class ConditionPropertiesBuilderTest {

    private ConditionPropertiesBuilder subject;

    @Mock
    private DifferentialExperiment experimentMock;

    @Before
    public void setup(){
        given(experimentMock.getAccession()).willReturn("EXP-1");

        Contrast contrastMock = mock(Contrast.class);
        given(contrastMock.getReferenceAssayGroup()).willReturn(new AssayGroup("Assay1", "Assay2"));
        given(contrastMock.getTestAssayGroup()).willReturn(new AssayGroup("Assay3"));
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


        given(experimentDesignMock.getFactors("Assay1")).willReturn(factors1);
        given(experimentDesignMock.getFactors("Assay2")).willReturn(factors1);
        given(experimentDesignMock.getFactors("Assay3")).willReturn(factors2);

        given(experimentDesignMock.getSamples("Assay1")).willReturn(samples1);
        given(experimentDesignMock.getSamples("Assay2")).willReturn(samples1);
        given(experimentDesignMock.getSamples("Assay3")).willReturn(samples2);

        given(experimentMock.getContrasts()).willReturn(Sets.newHashSet(contrastMock));
        given(experimentMock.getExperimentDesign()).willReturn(experimentDesignMock);

        subject = new ConditionPropertiesBuilder();
    }

    @Test
    public void testBuildProperties() throws Exception {

        Collection<ConditionProperty> result = subject.buildProperties(experimentMock);

        assertThat(result.size(), is(4));
        assertThat(result.contains(new ConditionProperty("EXP-1", "reference", "g1_g2", "fn1", "fv1")), is(true));
    }
}
