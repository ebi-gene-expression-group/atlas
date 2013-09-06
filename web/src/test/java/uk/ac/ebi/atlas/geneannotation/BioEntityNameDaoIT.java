package uk.ac.ebi.atlas.geneannotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BioEntityNameDaoIT {

    @Inject
    private BioEntityNameDao subject;

    @Test
    public void testCount() {
        subject.count();
    }
}
