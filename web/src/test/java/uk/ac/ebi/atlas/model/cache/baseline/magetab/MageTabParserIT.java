package uk.ac.ebi.atlas.model.cache.baseline.magetab;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MageTabParserIT {

    @Inject
    private MageTabParserBuilder builder;

    private MageTabParser subject;


    @Before
    public void setUp() throws Exception {
        subject = builder.forExperimentAccession("E-GEOD-26284")
                .withRequiredFactorTypes(Sets.newHashSet("CELL_LINE", "CELLULAR_COMPONENT", "MATERIAL_TYPE"))
                .withProcessedRunAccessions(Collections.EMPTY_SET)
                .build();
    }

    @Test
    public void testExtractFactorNames() throws Exception {
        //given
        Map<String, String> factorNamesByType = subject.getFactorNamesByType();

        //then
        Assert.assertThat(factorNamesByType.keySet(), containsInAnyOrder("CELL_LINE", "CELLULAR_COMPONENT", "MATERIAL_TYPE"));
        //and
        Assert.assertThat(factorNamesByType.get("CELL_LINE"), is("cell line"));
        Assert.assertThat(factorNamesByType.get("CELLULAR_COMPONENT"), is("cellular component"));
        Assert.assertThat(factorNamesByType.get("MATERIAL_TYPE"), is("RNA type"));
    }
}
