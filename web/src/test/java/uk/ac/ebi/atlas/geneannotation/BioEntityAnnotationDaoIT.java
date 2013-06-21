package uk.ac.ebi.atlas.geneannotation;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BioEntityAnnotationDaoIT {

    @Inject
    private BioEntityAnnotationDao subject;

    private Map<String, String> annotations = Maps.newHashMap();

    private static final String ORGANISM = "little mouse";

    @Before
    public void setup() {
        annotations.put("ens1", "gene1");
        annotations.put("ens2", "gene2");
    }

    @Test
    public void testSaveGetAnnotations() throws Exception {
        subject.saveAnnotations(annotations, ORGANISM);
        assertThat(subject.getName("ens1"), is("gene1"));
        assertThat(subject.getName("not there"), is(nullValue()));

    }
}
