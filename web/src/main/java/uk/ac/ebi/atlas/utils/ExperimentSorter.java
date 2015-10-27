package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;
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

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineTsvFileTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialTsvFileTemplate;

    private ExperimentTrader experimentTrader;

    @Inject
    public ExperimentSorter(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
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
}
