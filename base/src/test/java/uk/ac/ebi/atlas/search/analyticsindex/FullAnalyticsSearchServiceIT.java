package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Stopwatch;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FullAnalyticsSearchServiceIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsSearchServiceIT.class);

    @Inject
    FullAnalyticsSearchService subject;

    @Ignore
    public void benchmark() throws Exception {
        Set<String> assayGroupIds =
                IntStream.rangeClosed(1, 800).boxed().map(i -> "g" + Integer.toString(i)).collect(Collectors.toSet());

        Stopwatch stopwatch = Stopwatch.createStarted();
        subject.searchSpecificExpression("E-MTAB-2770", 1.0, assayGroupIds);
        LOGGER.info("Specific search across {} assay groups finished in {} ms", assayGroupIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

}