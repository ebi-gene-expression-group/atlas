package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import oracle.sql.ARRAY;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml", "classpath:oracleUcpContext.xml"})
public class OracleObjectFactoryIT {

    @Inject
    OracleObjectFactory subject;

    @Test
    public void createOracleArrayForIndexedAssayGroup() throws SQLException {
        IndexedAssayGroup iag1 = new IndexedAssayGroup("EXP1", "G1");
        IndexedAssayGroup iag2 = new IndexedAssayGroup("EXP2", "G2");

        ARRAY array = subject.createOracleArrayForIndexedAssayGroup(ImmutableList.of(iag1, iag2));

        assertThat(array.getOracleArray().length, is(2));
    }
}
