package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ArrayDesignDAOIT {
    private static final int ENSG00000109929_ARRAY_DESIGNS = 74;

    @Inject
    private ArrayDesignDAO subject;

    @Test
    public void testGetDesignElements() {
        List<String> designElements = subject.getDesignElements("ENSG00000109929");
        assertThat(designElements, hasItem("211423_s_at"));
    }

    @Test
    public void testGetAllArrayDesignMapNames() {
        Map<String, String> arrayDesigns = subject.getArrayDesignMapNames();
        assertThat(arrayDesigns.size(), is(ENSG00000109929_ARRAY_DESIGNS));
    }
}
