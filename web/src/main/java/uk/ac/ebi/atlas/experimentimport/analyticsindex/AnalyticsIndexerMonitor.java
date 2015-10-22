package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.TreeMultimap;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.*;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 20/07/15.
 */

@Named
@Scope("singleton")
public class AnalyticsIndexerMonitor implements Observer {

    private long magetabFilesTotalSize;
    private long processedMagetabFilesSize;
    private int processedExperimentsCount;
    private Map<String, Long> experimentAccessionsToFileSize = ImmutableMap.of();
    private StringBuilder stringBuilder = new StringBuilder("No builds have been run in this session.");

    private String progressTemplate;

    private void initializeMonitor(TreeMultimap<Long, String> descendingExperimentSizeToExperimentAccessions) {
        processedExperimentsCount = 0;
        processedMagetabFilesSize = 0L;
        magetabFilesTotalSize = 0L;
        stringBuilder = new StringBuilder();

        ImmutableMap.Builder<String, Long> builder = new ImmutableMap.Builder<>();
        for (long documents : descendingExperimentSizeToExperimentAccessions.keySet()) {
            for (String experimentAccession : descendingExperimentSizeToExperimentAccessions.get(documents)) {
                builder.put(experimentAccession, documents);
                magetabFilesTotalSize += documents;
            }
        }
        experimentAccessionsToFileSize = builder.build();

        progressTemplate = "%tF %tT " +
                           "%s - %,d / " + String.format("%,d experiments",  experimentAccessionsToFileSize.size()) +
                           " - %,d / " + String.format("%,d bytes", magetabFilesTotalSize) +
                           " - %.2f%%" +
                           "%n";

        stringBuilder.append(String.format("--- Analytics index build started %s --- %n", new Date()));
        stringBuilder.append("Experiments to index: ")
                .append(Arrays.deepToString(experimentAccessionsToFileSize.keySet().toArray()))
                .append(String.format("%n"));

        Date date = new Date();
        stringBuilder.append(String.format(
                progressTemplate,
                date, date, "", processedExperimentsCount, processedMagetabFilesSize, 0D));
    }

    @Override
    public void update(Observable o, @Nullable Object arg) {
        if (arg == null) {
            stringBuilder.append(String.format("--- Analytics index build finished %s --- %n", new Date()));
        }
        else if (arg instanceof TreeMultimap) {
            @SuppressWarnings("unchecked")
            TreeMultimap<Long, String> descendingExperimentSizeToExperimentAccessions = (TreeMultimap<Long, String>) arg;
            initializeMonitor(descendingExperimentSizeToExperimentAccessions);
        }
        else if (arg instanceof String) {
            if (experimentAccessionsToFileSize.keySet().contains(arg)) {
                processedMagetabFilesSize += experimentAccessionsToFileSize.get(arg);
                processedExperimentsCount++;

                Date date = new Date();
                stringBuilder.append(String.format(
                        progressTemplate,
                        date, date,
                        arg,
                        processedExperimentsCount, processedMagetabFilesSize, (double) processedMagetabFilesSize / (double) magetabFilesTotalSize * 100));
            } else {
                Date date = new Date();
                stringBuilder.append(String.format("%tF %tT %s%n", date, date, arg));
            }
        }
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
