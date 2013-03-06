package uk.ac.ebi.atlas.commons.configuration;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Inject;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ContrastsConfigurationTest {


    @Inject
    private ConfigurationTrader configurationTrader;

    private ContrastsConfiguration subject;

    @Before
    public void setUp() throws Exception {
        subject = configurationTrader.getContrastsConfiguration("E-GEOD-22351");
    }

    @Test
    public void testGetAssayAccessions() throws Exception {
        Set<String> assayAccession = subject.getAssayAccessions("g1");
        assertThat(assayAccession, containsInAnyOrder("SRR057596", "SRR057598", "SRR057597"));
    }

    @Test
    public void testGetContrast() throws Exception {
        Contrast contrast = subject.getContrast("g1_g2");
        assertThat(contrast.getDisplayName(), is("genotype:\'expressing human TDP-43\' vs \'non transgenic\'"));
        assertThat(Sets.newHashSet(contrast.getReferenceAssayGroup()), containsInAnyOrder("SRR057596", "SRR057598", "SRR057597"));
        assertThat(Sets.newHashSet(contrast.getTestAssayGroup()), containsInAnyOrder("SRR057599", "SRR057600", "SRR057601"));
    }

    @Test
    public void testGetContrasts() throws Exception {

    }
}
