package uk.ac.ebi.atlas.search.EFO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml", "classpath:oracleUcpContext.xml"})
public class EFOIdToTermMapperIT {

    @Inject
    EFOIdToTermMapper subject;

    @Test
    public void cancer() {
        assertThat(subject.getIdsForTermSubstring("cancer").size(), is(greaterThanOrEqualTo(44)));
    }

    @Test
    public void cancerIncludesResultsContainingCancerAsASubstring() {
        String cancerBiomarkerMeasurement = "EFO_0005127";          // see http://www.ebi.ac.uk/efo/EFO_0005127

        assertThat(subject.getIdsForTermSubstring("cancer"), hasItem(cancerBiomarkerMeasurement));
    }

}