package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.common.base.Stopwatch;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;
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
    public void benchmarkSpecific() throws Exception {
        Set<String> sliceAssayGroupIds =
                IntStream.rangeClosed(1, 934).boxed().map(i -> "g" + Integer.toString(i)).collect(Collectors.toSet());

        Set<String> selectedAssayGroupIds =
                IntStream.rangeClosed(1, 934).boxed().map(i -> "g" + Integer.toString(i)).collect(Collectors.toSet());


        Stopwatch stopwatch = Stopwatch.createStarted();
        List<String> results = subject.specificExpressionInSliceAcrossSelectedAssayGroups("E-MTAB-2770", 0.0, sliceAssayGroupIds, selectedAssayGroupIds);
        LOGGER.info("Specific search across {}/{} assay groups finished in {} ms", selectedAssayGroupIds.size(), sliceAssayGroupIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));

        LOGGER.info("{}", results);
    }

    @Ignore
    public void benchmarkSpecificAll() throws Exception {
        Set<String> assayGroupIds =
                IntStream.rangeClosed(1, 800).boxed().map(i -> "g" + Integer.toString(i)).collect(Collectors.toSet());

        Stopwatch stopwatch = Stopwatch.createStarted();
        subject.specificExpressionAcrossAllAssayGroups("E-MTAB-2770", 0.0, 50);
        LOGGER.info("Specific search across {} assay groups finished in {} ms", assayGroupIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}