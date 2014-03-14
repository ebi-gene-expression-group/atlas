package uk.ac.ebi.atlas.experimentimport.experimentdesign.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class OLSClientIT {

    @Inject
    private OLSClient subject;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void isValidShouldSucceed() throws Exception {
        boolean valid = subject.isValid("degree celsius");
        assertThat(valid, is(true));
    }

    @Test
    public void isValidShouldReturnFalse() throws Exception {
        boolean valid = subject.isValid("degree b");
        assertThat(valid, is(false));
    }

}
