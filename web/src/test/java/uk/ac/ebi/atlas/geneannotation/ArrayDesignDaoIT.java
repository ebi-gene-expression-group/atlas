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
public class ArrayDesignDaoIT {

    @Inject
    private ArrayDesignDao subject;

    private Map<String, String> annotations = Maps.newHashMap();

    private static final String ARRAY_DESIGN = "A-AFFY-666";

    @Before
    public void setup() {
        annotations.put("de1", "ens1");
        annotations.put("de2", "ens2");

        subject.deleteMappings(ARRAY_DESIGN);
    }

    @Test
    public void testSaveGetMappings() throws Exception {
        subject.saveMappings(annotations, ARRAY_DESIGN, "ensembl");
        assertThat(subject.getIdentifier("de1", ARRAY_DESIGN), is("ens1"));
        assertThat(subject.getIdentifier("de1", "NOT EXISRING AD"), is(nullValue()));
        assertThat(subject.getIdentifier("not there", ARRAY_DESIGN), is(nullValue()));
    }

    @Test
    public void testIsArrayDesignPresent() throws Exception {
        assertThat(subject.isArrayDesignPresent(ARRAY_DESIGN), is(false));
        subject.saveMappings(annotations, ARRAY_DESIGN, "ensembl");
        assertThat(subject.isArrayDesignPresent(ARRAY_DESIGN), is(true));

    }
}
