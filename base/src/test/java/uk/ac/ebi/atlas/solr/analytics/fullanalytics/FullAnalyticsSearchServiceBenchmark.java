//package uk.ac.ebi.atlas.solr.analytics.fullanalytics;
//
//import com.google.common.base.Stopwatch;
//import com.google.common.collect.ImmutableList;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.inject.Inject;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.get.Collectors;
//import java.util.get.IntStream;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
//public class FullAnalyticsSearchServiceBenchmark {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsSearchServiceBenchmark.class);
//
//    @Inject
//    FullAnalyticsSearchService subject;
//
//    @Test
//    public void specificSelection() throws Exception {
//
//        Set<String> sliceAssayGroupIds =
//                IntStream.rangeClosed(1, 934).boxed()
//                      .map(i -> "g" + Integer.toString(i)).collect(Collectors.toSet());
//
//        for (double expressionLevelCutoff : ImmutableList.of(0.0, 0.5, 1.0, 10.0, 100.0, 1000.0)) {
//            for (int assayGroupIds :
//                  ImmutableList.of(1, 10, 100, 200, 300, 400, 500, 600, 700, 800, 900, 933, 934)) {
//                for (String experimentAccession : ImmutableList.of("E-MTAB-2770", "E-MTAB-5423")) {
//                    Set<String> selectedAssayGroupIds =
//                            IntStream.rangeClosed(1, assayGroupIds).boxed()
//                                    .map(i -> "g" + Integer.toString(i))
//                                    .collect(Collectors.toSet());
//                    Stopwatch stopwatch = Stopwatch.createStarted();
//                    subject.specificExpressionInSliceAcrossSelectedAssayGroups(
//                            experimentAccession, expressionLevelCutoff, selectedAssayGroupIds, selectedAssayGroupIds);
//                    LOGGER.info(
//                            "{} specific search across {}/{} (cutoff={}) assay groups finished in {} ms",
//                            experimentAccession,
//                            selectedAssayGroupIds.size(),
//                            sliceAssayGroupIds.size(),
//                            expressionLevelCutoff,
//                            stopwatch.elapsed(TimeUnit.MILLISECONDS));
//                }
//            }
//        }
//    }
//
//    @Test
//    public void specificAll() throws Exception {
//        Stopwatch stopwatch = Stopwatch.createStarted();
//        subject.specificExpressionAcrossAllAssayGroups("E-MTAB-2770", 0.0, 50);
//        LOGGER.info(
//              "E-MTAB-2770 specific search across all (934) assay groups finished in {} ms",
//              stopwatch.elapsed(TimeUnit.MILLISECONDS));
//        subject.specificExpressionAcrossAllAssayGroups("E-MTAB-5423", 0.0, 50);
//        LOGGER.info(
//              "E-MTAB-5423 specific search across all (934) assay groups finished in {} ms",
//              stopwatch.elapsed(TimeUnit.MILLISECONDS));
//    }
//}
