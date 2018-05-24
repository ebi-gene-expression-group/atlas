package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.io.LineNumberReader;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
class FeaturedSpeciesDaoIT {
    @Inject
    private JdbcTemplate jdbcTemplate;

    // @Inject
    // @Qualifier("dataSource")
    // private DataSource dataSource;

    private FeaturedSpeciesDao subject;

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "scxa_experiment");
        subject = new FeaturedSpeciesDao(jdbcTemplate);
    }

    @Test
    void whenNoExperimentsAreLoadedResultsAreEmpty() {
        assertThat(subject.fetchSpeciesSortedByExperimentCount()).isEmpty();
    }

    @Test
    void sortsSpeciesNamesByNumberOfExperiments() throws Exception {
        EncodedResource resource = new EncodedResource(new ClassPathResource("uk/ac/ebi/atlas/search/scxa_experiment_fixture.sql"));
        LineNumberReader lnr = new LineNumberReader(resource.getReader());
        jdbcTemplate.execute(ScriptUtils.readScript(lnr, "--", ";"));

        // I would’ve loved to use ScriptUtils.executeSqlScript with dataSource, see above, but when it reaches
        // org.postgresql.core.v3.QueryExecutorImpl::processResults, it hangs in the line c = pgStream.receiveChar();
        // Ain’t nobody got time for that!
        // ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
        assertThat(subject.fetchSpeciesSortedByExperimentCount())
                .containsExactly("Mus musculus", "Homo sapiens");
    }
}