package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.differential.CanStreamSupplier;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class DifferentialSecondaryDataFiles<E extends DifferentialExperiment> extends CanStreamSupplier<E> {

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.DATA;
    }

    @Named
    public static class RnaSeq extends DifferentialSecondaryDataFiles<DifferentialExperiment> {
        private final DataFileHub dataFileHub;

        @Inject
        public RnaSeq(DataFileHub dataFileHub) {
            this.dataFileHub = dataFileHub;
        }

        @Override
        public Collection<ExternallyAvailableContent> get(DifferentialExperiment experiment) {
            ImmutableList.Builder<ExternallyAvailableContent> builder = ImmutableList.builder();

            AtlasResource<ObjectInputStream<String[]>> analytics =
                    dataFileHub.getRnaSeqDifferentialExperimentFiles(experiment.getAccession()).analytics;
            if (analytics.exists()) {
                builder.add(new ExternallyAvailableContent(
                                makeUri("analytics"),
                                ExternallyAvailableContent.Description.create(
                                        "icon-tsv", "All analytics for the experiment"),
                                streamFile(
                                        experiment.getAccession() + "-analytics.tsv",
                                        readFromStreamAndWriteTsv(
                                                analytics, new AnalyticsDataHeaderBuilder(experiment)))));
            }

            AtlasResource<TsvStreamer> rawCounts =
                    dataFileHub.getRnaSeqDifferentialExperimentFiles(experiment.getAccession()).rawCounts;
            if (rawCounts.exists()) {
                builder.add(new ExternallyAvailableContent(
                                makeUri("raw-counts"),
                                ExternallyAvailableContent.Description.create(
                                        "icon-tsv", "All the raw counts for the experiment"),
                                streamFile(
                                        experiment.getAccession() + "-raw-counts.tsv",
                                        readFromResourceAndWriteTsv(rawCounts, Function.identity()))));
            }

            return builder.build();
        }
    }

    @Named
    public static class Microarray extends DifferentialSecondaryDataFiles<MicroarrayExperiment> {
        private final DataFileHub dataFileHub;

        @Inject
        public Microarray(DataFileHub dataFileHub) {
            this.dataFileHub = dataFileHub;
        }

        @Override
        public Collection<ExternallyAvailableContent> get(final MicroarrayExperiment experiment) {
            ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();

            List<Pair<String, Function<Writer, Void>>> analytics = new ArrayList<>();
            for (String arrayDesign: experiment.getArrayDesignAccessions()) {
                AtlasResource<ObjectInputStream<String[]>> resource =
                        dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesign).analytics;
                if (resource.exists()) {
                    analytics.add(Pair.of(
                            MessageFormat.format("{0}-{1}-analytics.tsv", experiment.getAccession(), arrayDesign),
                            readFromStreamAndWriteTsv(resource, new AnalyticsDataHeaderBuilder(experiment))));
                }
            }

            if (analytics.size() > 0) {
                b.add(new ExternallyAvailableContent(
                        makeUri("analytics"),
                        ExternallyAvailableContent.Description.create(
                                "icon-tsv", "All the analytics for this experiment"),
                        analytics.size() == 1 ?
                                streamFile(analytics.get(0)) :
                                streamFolder(experiment.getAccession() + "-analytics", analytics)
                ));
            }

            List<Pair<String, Function<Writer, Void>>> logFoldChanges = new ArrayList<>();
            for (String arrayDesign: experiment.getArrayDesignAccessions()) {
                AtlasResource<TsvStreamer> resource =
                        dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesign)
                                .logFoldChanges;
                if (resource.exists()) {
                    logFoldChanges.add(Pair.of(
                            MessageFormat.format(
                                    "{0}-{1}-log-fold-changes.tsv", experiment.getAccession(), arrayDesign),
                            readFromResourceAndWriteTsv(resource, Function.identity())));
                }
            }

            if (logFoldChanges.size() > 0) {
                b.add(new ExternallyAvailableContent(
                        makeUri("log-fold-changes"),
                        ExternallyAvailableContent.Description.create(
                                "icon-tsv", "All the log fold changes for this experiment"),
                        logFoldChanges.size() == 1 ?
                                streamFile(logFoldChanges.get(0)) :
                                streamFolder(experiment.getAccession() + "-log-fold-changes", logFoldChanges)));
            }

            List<Pair<String, Function<Writer, Void>>> normalizedExpressions = new ArrayList<>();
            for (String arrayDesign: experiment.getArrayDesignAccessions()) {
                AtlasResource<TsvStreamer> resource =
                        dataFileHub.getMicroarrayExperimentFiles(
                                experiment.getAccession(),
                                arrayDesign)
                                .normalizedExpressions;

                if (resource.exists()) {
                    normalizedExpressions.add(Pair.of(
                            MessageFormat.format(
                                    "{0}-{1}-normalized-expressions.tsv", experiment.getAccession(), arrayDesign),
                            readFromResourceAndWriteTsv(resource, Function.identity())));
                }
            }

            if (normalizedExpressions.size() > 0) {
                b.add(new ExternallyAvailableContent(
                        makeUri("normalized-expressions"),
                        ExternallyAvailableContent.Description.create(
                                "icon-tsv",
                                "All the normalized expressions for this experiment"),
                        normalizedExpressions.size() == 1 ?
                                streamFile(normalizedExpressions.get(0)) :
                                streamFolder(
                                        experiment.getAccession() + "-normalized-expressions",
                                        normalizedExpressions)));
            }

            return b.build();
        }
    }
}
