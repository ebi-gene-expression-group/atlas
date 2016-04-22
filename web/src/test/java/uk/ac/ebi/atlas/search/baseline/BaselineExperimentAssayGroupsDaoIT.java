package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})

public class BaselineExperimentAssayGroupsDaoIT {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Inject
    OracleObjectFactory oracleObjectFactory;

    JdbcTemplate jdbcTemplateSpy;

    OracleObjectFactory oracleObjectFactorySpy;

    private BaselineExperimentAssayGroupsDao subject;

    @Before
    public void setUp(){
        jdbcTemplateSpy = Mockito.spy(jdbcTemplate);
        oracleObjectFactorySpy = Mockito.spy(oracleObjectFactory);
        subject = new BaselineExperimentAssayGroupsDao(jdbcTemplateSpy, oracleObjectFactorySpy);
    }

    @Test
    public void doesNotFindNonExistentExperimentAssayGroupButGoesAndQueries() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup("E-MTAB-599", "DOES_NOT_EXIST");
        SetMultimap<String, String> multimap = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>absent());
        assertThat(multimap.entries(), is(empty()));

        verify(jdbcTemplateSpy).query(anyString(), any(Object [] .class),  any(ResultSetExtractor.class));
        verify(oracleObjectFactorySpy).createOracleArrayForIndexedAssayGroup(anyCollection());

    }

    @Test
    public void doesNotFindNonExistentGenes() throws Exception {
        SetMultimap<String, String> results = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.<Collection<String>>of(ImmutableList.of("DOES_NOT_EXIST")));
        assertThat(results.entries(), is(empty()));

        verify(jdbcTemplateSpy).query(anyString(), any(Object [] .class),  any(ResultSetExtractor.class));
        verify(oracleObjectFactorySpy).createOracleArrayForIdentifiers(anyCollection());

    }

}
