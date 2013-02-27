package uk.ac.ebi.atlas.model.caches.magetab;

import com.google.common.collect.Sets;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MageTabLoaderIT {

    @Inject
    private MageTabLoaderBuilder builder;

    private MageTabLoader subject;


    @Before
    public void setUp() throws Exception {
        subject = builder.forExperimentAccession("E-GEOD-26284")
                .withRequiredFactorTypes(Sets.newHashSet("cell line", "cellular component", "material type"))
                .withProcessedRunAccessions(Collections.EMPTY_SET)
                .build();
    }

    @Test
    public void testExtractFactorNames() throws Exception {
        //given
        Map<String, String> factorNames = subject.getFactorNamesByType();

        //then
        assertThat(factorNames.keySet(), containsInAnyOrder("cell line", "cellular component", "material type"));
        //and
        assertThat(factorNames.get("cell line"),is("cell line"));
        assertThat(factorNames.get("cellular component"),is("cellular component"));
        assertThat(factorNames.get("material type"),is("RNA type"));
    }
}
