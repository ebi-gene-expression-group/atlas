package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class BaselineCoexpressionProfileDaoIT {

    private static String EXPERIMENT_ACCESSION = "E-FOOBAR";
    private static int CE_PROFILES_COUNT = 1000;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    BaselineCoexpressionProfileInputStream baselineCoexpressionProfileInputStream;

    BaselineCoexpressionProfileDao subject;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        List<BaselineCoexpression> baaselineCoexpressions = IntStream
                .range(0, CE_PROFILES_COUNT)
                .boxed()
                .map(i ->
                        BaselineCoexpression.create(ThreadLocalRandom.current().nextDouble(),
                                "ENSG" + leftPad(Integer.toString(i), 11, '0')))
                .collect(Collectors.toList());

        when(baselineCoexpressionProfileInputStream.readNext()).thenReturn(new BaselineCoexpressionProfile("ENSG00000000001", baaselineCoexpressions));

        subject = new BaselineCoexpressionProfileDao(jdbcTemplate);
    }

    @Test
    public void loadCoexpressionsProfile() throws Exception {
        assertThat(subject.loadCoexpressionsProfile(EXPERIMENT_ACCESSION, baselineCoexpressionProfileInputStream), is(CE_PROFILES_COUNT));
        assertThat(subject.deleteCoexpressionsProfile(EXPERIMENT_ACCESSION), is(CE_PROFILES_COUNT));
    }

}
