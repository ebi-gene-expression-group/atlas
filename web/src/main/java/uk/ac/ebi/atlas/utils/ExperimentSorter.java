package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.*;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collections;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 21/07/15.
 */

@Named
@Scope("singleton")
public class ExperimentSorter {

    private static final Logger LOGGER = Logger.getLogger(ExperimentSorter.class);

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineTsvFileTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialTsvFileTemplate;

    private ExperimentTrader experimentTrader;

    private BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;
    private MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;

    @Inject
    public ExperimentSorter(ExperimentTrader experimentTrader,
                            BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory,
                            ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory,
                            MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory,
                            RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory) {
        this.experimentTrader = experimentTrader;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.proteomicsBaselineAnalyticsInputStreamFactory = proteomicsBaselineAnalyticsInputStreamFactory;
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
    }

    public TreeMultimap<Long, String> reverseSortAllExperimentsPerSize() {
        return reverseSortExperimentsPerSize(
                ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_BASELINE,
                ExperimentType.PROTEOMICS_BASELINE);
    }

    public TreeMultimap<Long, String> reverseSortBaselineExperimentsPerSize() {
        return reverseSortExperimentsPerSize(
                ExperimentType.RNASEQ_MRNA_BASELINE,
                ExperimentType.PROTEOMICS_BASELINE);
    }

    public TreeMultimap<Long, String> reverseSortDifferentialExperimentsPerSize() {
        return reverseSortExperimentsPerSize(
                ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
    }

    public TreeMultimap<Long, String> reverseSortExperimentsPerSize(ExperimentType... experimentTypes) {
        TreeMultimap<Long, String> fileSizeToExperimentsMap = TreeMultimap.create(Collections.reverseOrder(), Ordering.natural());

        for (ExperimentType experimentType : experimentTypes) {

            if (experimentType.isDifferential()) {
                for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
                    Path diffExperimentDir = FileSystems.getDefault().getPath(MessageFormat.format(differentialTsvFileTemplate, experimentAccession)).getParent();
                    Path diffExperimentGlobPath = FileSystems.getDefault().getPath(MessageFormat.format(differentialTsvFileTemplate, experimentAccession + "*")).getFileName();
                    try (DirectoryStream<Path> stream = Files.newDirectoryStream(diffExperimentDir, diffExperimentGlobPath.toString())) {
                        long diffExperimentSize = 0;
                        for (Path path : stream) {
                            diffExperimentSize += new File(String.valueOf(path)).length();
                        }
                        fileSizeToExperimentsMap.put(diffExperimentSize, experimentAccession);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
                    String tsvFilePath = MessageFormat.format(baselineTsvFileTemplate, experimentAccession);
                    fileSizeToExperimentsMap.put(new File(tsvFilePath).length(), experimentAccession);
                }
            }
        }

        return fileSizeToExperimentsMap;
    }



    public ImmutableSet<String> getBioentityIdsFromAllExperiments() {
        return getBioentityIdsFromExperiments(
                ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_BASELINE,
                ExperimentType.PROTEOMICS_BASELINE);
    }


    public ImmutableSet<String> getBioentityIdsFromExperiments(ExperimentType... experimentTypes) {

        StopWatch stopWatch = new org.springframework.util.StopWatch(getClass().getSimpleName());
        stopWatch.start();
        LOGGER.info("Retrieving all bioentity identifiers...");

        ImmutableSet.Builder<String> bioentityIdsBuilder = new ImmutableSet.Builder<>();

        try {
            for (ExperimentType experimentType : experimentTypes) {

                if (experimentType.isBaseline()) {

                    LOGGER.info("Retrieving all bioentity identifiers from baseline experiments:");
                    for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
                        LOGGER.info(String.format("Retrieving all bioentity identifiers for %s", experimentAccession));
                        BaselineAnalyticsInputStream inputStream = baselineAnalyticsInputStreamFactory.create(experimentAccession);

                        BaselineAnalytics analytics = inputStream.readNext();
                        while (analytics != null) {
                            bioentityIdsBuilder.add(analytics.getGeneId());
                            analytics = inputStream.readNext();
                        }

                        inputStream.close();
                    }

                } else if (experimentType.isProteomicsBaseline()) {

                    LOGGER.info("Retrieving all bioentity identifiers from proteomics experiments...");
                    for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
                        LOGGER.info(String.format("Retrieving all bioentity identifiers for %s", experimentAccession));
                        ProteomicsBaselineAnalyticsInputStream inputStream = proteomicsBaselineAnalyticsInputStreamFactory.create(experimentAccession);

                        BaselineAnalytics analytics = inputStream.readNext();
                        while (analytics != null) {
                            bioentityIdsBuilder.add(analytics.getGeneId());
                            analytics = inputStream.readNext();
                        }

                        inputStream.close();
                    }

                } else if (experimentType.isMicroarray()) {

                    LOGGER.info("Retrieving all bioentity identifiers from microarray experiments...");
                    for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
                        LOGGER.info(String.format("Retrieving all bioentity identifiers for %s", experimentAccession));
                        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(experimentAccession);

                        for (String arrayDesign : experiment.getArrayDesignAccessions()) {
                            MicroarrayDifferentialAnalyticsInputStream inputStream = microarrayDifferentialAnalyticsInputStreamFactory.create(experimentAccession, arrayDesign);

                            DifferentialAnalytics analytics = inputStream.readNext();
                            while (analytics != null) {
                                bioentityIdsBuilder.add(analytics.getGeneId());
                                analytics = inputStream.readNext();
                            }

                            inputStream.close();
                        }
                    }

                } else if (experimentType.isRnaSeqDifferential()) {

                    LOGGER.info("Retrieving all bioentity identifiers from RNA-seq differential experiments...");
                    for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
                        LOGGER.info(String.format("Retrieving all bioentity identifiers for %s", experimentAccession));
                        RnaSeqDifferentialAnalyticsInputStream inputStream = rnaSeqDifferentialAnalyticsInputStreamFactory.create(experimentAccession);

                        DifferentialAnalytics analytics = inputStream.readNext();
                        while (analytics != null) {
                            bioentityIdsBuilder.add(analytics.getGeneId());
                            analytics = inputStream.readNext();
                        }

                        inputStream.close();
                    }

                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        stopWatch.stop();
        LOGGER.info(String.format("Retrieved bioentity identifiers in %s seconds. Building set...", stopWatch.getTotalTimeSeconds()));

        stopWatch.start();
        ImmutableSet<String> bioentityIdentifiers = bioentityIdsBuilder.build();
        stopWatch.stop();
        LOGGER.info(String.format("Built a set of %,d bioentity identifiers in %s seconds.", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds()));

        return bioentityIdentifiers;
    }
}
