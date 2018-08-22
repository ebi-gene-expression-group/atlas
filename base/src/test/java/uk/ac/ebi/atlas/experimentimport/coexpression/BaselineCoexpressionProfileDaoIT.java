package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BaselineCoexpressionProfileDaoIT {

    private static final String EXPERIMENT_ACCESSION = "E-FOOBAR";
    private static final int CE_PROFILES_COUNT = 100;
    private static final int GENE_ID_CODE_LENGTH = 11;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    private DummyBaselineCoexpressionProfileInputStream baselineCoexpressionProfileInputStream;

    private BaselineCoexpressionProfileDao subject;

    @Before
    public void setUp() {
        jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_CE_PROFILES WHERE EXPERIMENT = ?", EXPERIMENT_ACCESSION);
        subject = new BaselineCoexpressionProfileDao(jdbcTemplate);
        baselineCoexpressionProfileInputStream = new DummyBaselineCoexpressionProfileInputStream();
    }

    @Test
    public void loadCoexpressionsProfile() {
        assertThat(
                subject.loadCoexpressionsProfile(EXPERIMENT_ACCESSION, baselineCoexpressionProfileInputStream),
                is(CE_PROFILES_COUNT));
        assertThat(
                subject.deleteCoexpressionsProfile(EXPERIMENT_ACCESSION),
                is(CE_PROFILES_COUNT));
    }

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_CE_PROFILES WHERE EXPERIMENT = ?", EXPERIMENT_ACCESSION);
    }

    private final class DummyBaselineCoexpressionProfileInputStream
                        implements ObjectInputStream<BaselineCoexpressionProfile> {

        private int position = 0;
        private List<BaselineCoexpressionProfile> baselineCoexpressionProfiles;

        private DummyBaselineCoexpressionProfileInputStream() {
            List<String> geneIds =
                    IntStream.rangeClosed(1, CE_PROFILES_COUNT) .boxed()
                            .map(i -> "ENSG" + leftPad(Integer.toString(i), GENE_ID_CODE_LENGTH, '0'))
                            .collect(Collectors.toList());

            baselineCoexpressionProfiles =
                    geneIds.stream().map(geneId ->
                            new BaselineCoexpressionProfile(
                                    geneId,
                                    geneIds.stream()
                                            .map(_geneId ->
                                                    BaselineCoexpression.create(
                                                            ThreadLocalRandom.current().nextDouble(), _geneId))
                                            .collect(Collectors.toList())))
                            .collect(Collectors.toList());
        }

        @Override
        public BaselineCoexpressionProfile readNext() {
            return position < baselineCoexpressionProfiles.size() ?
                    baselineCoexpressionProfiles.get(position++) :
                    null;
        }

        @Override
        public void close() {

        }
    }
}
