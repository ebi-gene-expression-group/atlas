package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ReactomeBiomartClientIT {

    @Inject
    private ReactomeBiomartClient subject;

    @Test
    public void testPathwayReactomeName() throws Exception {
        String react_111102 = subject.fetchPathwayName("REACT_111102");
        assertThat(react_111102, is("Signal Transduction"));

        String react_1258 = subject.fetchPathwayName("REACT_1258");
        assertThat(react_1258, is("Activation of the mRNA upon binding of the cap-binding complex and eIFs, and subsequent binding to 43S"));

        String bad_ID = subject.fetchPathwayName("bad_ID");
        assertThat(bad_ID, isEmptyOrNullString());
    }
}
