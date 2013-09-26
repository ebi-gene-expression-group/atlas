package uk.ac.ebi.atlas.solr.query.conditions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineConditionsSearchServiceIT {

    @Inject
    private BaselineConditionsSearchService subject;

    @Test
    public void testFindContrasts() throws Exception {
        Collection<IndexedAssayGroup> contrasts = subject.findAssayGroups("liver");
        assertThat(contrasts, hasItem(new IndexedAssayGroup("E-GEOD-30352", "g6")));
    }
}
