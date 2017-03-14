package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class DifferentialSecondaryDataFiles<E extends DifferentialExperiment> extends ExternallyAvailableContent.Supplier<E> {

    Function<HttpServletResponse, Void> streamFolder(final String folderName,
                                                             final List<Pair<String, Function<Writer, Void>>> documents){
        return new Function<HttpServletResponse, Void>() {
            @Override
            public Void apply(final HttpServletResponse response) {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + folderName + ".zip\"");
                response.setContentType("application/octet-stream");
                try {
                    ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
                    for (Pair<String, Function<Writer, Void>> p : documents) {
                        ZipEntry ze = new ZipEntry(p.getLeft());
                        zipOutputStream.putNextEntry(ze);
                        p.getRight().apply(new PrintWriter(zipOutputStream));
                        zipOutputStream.closeEntry();
                    }
                    zipOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
    }

    Function<HttpServletResponse, Void> streamFile(Pair<String,Function<Writer, Void>> p) {
        return streamFile(p.getLeft(), p.getRight());
    }

    Function<HttpServletResponse, Void> streamFile(final String fileName, final Function<Writer, Void> document){
        return new Function<HttpServletResponse, Void>() {
            @Override
            public Void apply(HttpServletResponse response) {
                response.setHeader("Content-Disposition", "attachment; filename=\""+fileName + "\"");
                response.setContentType("text/plain; charset=utf-8");

                try(Writer writer = response.getWriter()){
                    document.apply(writer);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
    }

    Function<Writer, Void> readFromResourceAndWrite(final AtlasResource<TsvReader> resource,
                                                    final Function<String[], String[]> whatToDoWithTheHeaders){
        return new Function<Writer, Void>() {
            @Override
            public Void apply(Writer writer) {
                try (CSVReader csvReader = new CSVReader(resource.getReader(), '\t') ;
                     CSVWriter csvWriter = new CSVWriter(writer, '\t')) {
                    String[] headers = whatToDoWithTheHeaders.apply(csvReader.readNext());

                    csvWriter.writeNext(headers);

                    String[] inputLine;
                    while ((inputLine = csvReader.readNext()) != null) {
                        csvWriter.writeNext(inputLine);
                    }
                    csvWriter.flush();
                } catch (IOException e) {
                    throw new IllegalStateException("Exception thrown while reading next csv line: " + e.getMessage());
                }
                return null;
            }
        };
    }

    @Named
    public static class RnaSeq extends DifferentialSecondaryDataFiles<DifferentialExperiment> {

        private final DataFileHub dataFileHub;
        @Inject
        public RnaSeq(DataFileHub dataFileHub){
            this.dataFileHub = dataFileHub;
        }
        @Override
        public Collection<ExternallyAvailableContent> get(DifferentialExperiment experiment) {
            ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();

            AtlasResource<TsvReader> analytics = dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).analytics;
            if(analytics.exists()){
                b.add(new ExternallyAvailableContent(
                                makeUri("analytics"),
                                ExternallyAvailableContent.Description.create("Data", "link",
                                        "Analytics file (tsv)"
                                ),
                                streamFile(experiment.getAccession()+"-analytics.tsv",
                                        readFromResourceAndWrite(analytics, AnalyticsDataHeaderBuilder.rnaSeq(experiment))
                                )
                        )
                );
            }

            AtlasResource<TsvReader> rawCounts = dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).rawCounts;
            if(rawCounts.exists()){
                b.add(new ExternallyAvailableContent(
                                makeUri("raw counts"),
                                ExternallyAvailableContent.Description.create("Data", "link",
                                        "Raw counts (tsv)"
                                ),
                                streamFile(experiment.getAccession()+"-raw-counts.tsv",
                                        readFromResourceAndWrite(analytics, Functions.<String[]>identity())
                                )
                        )
                );
            }

            return b.build();
        }
    }

    @Named
    public static class Microarray extends DifferentialSecondaryDataFiles<MicroarrayExperiment> {

        private final DataFileHub dataFileHub;
        @Inject
        public Microarray(DataFileHub dataFileHub){
            this.dataFileHub = dataFileHub;
        }
        @Override
        public Collection<ExternallyAvailableContent> get(final MicroarrayExperiment experiment) {
            ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();

            List<Pair<String, Function<Writer, Void>>> analytics = new ArrayList<>();
            for(String arrayDesign: experiment.getArrayDesignAccessions()){
                AtlasResource<TsvReader> r = dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesign).analytics;
                if(r.exists()){
                    analytics.add(Pair.of(
                            MessageFormat.format("{0}-{1}-analytics.tsv", experiment.getAccession(), arrayDesign),
                            readFromResourceAndWrite(r, AnalyticsDataHeaderBuilder.microarray(experiment))
                    ));
                }
            }
            if(analytics.size()>0){
                b.add(new ExternallyAvailableContent(
                        makeUri("analytics"),
                        ExternallyAvailableContent.Description.create("Data", "link",
                                "Analytics file (tsv)"
                                ),
                        analytics.size() == 1 ? streamFile(analytics.get(0)) : streamFolder(experiment.getAccession()+"-analytics", analytics)
                ));
            }



            List<Pair<String, Function<Writer, Void>>> logFoldChanges = new ArrayList<>();
            for(String arrayDesign: experiment.getArrayDesignAccessions()){
                AtlasResource<TsvReader> r = dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesign).logFoldChanges;
                if(r.exists()){
                    logFoldChanges.add(Pair.of(
                            MessageFormat.format("{0}-{1}-log-fold-changes.tsv", experiment.getAccession(), arrayDesign),
                            readFromResourceAndWrite(r, Functions.<String[]>identity())
                    ));
                }
            }
            if(logFoldChanges.size()>0){
                b.add(new ExternallyAvailableContent(
                        makeUri("log-fold-changes"),
                        ExternallyAvailableContent.Description.create("Data", "link",
                                "Log fold changes (tsv)"
                        ),
                        logFoldChanges.size() == 1 ? streamFile( logFoldChanges.get(0)) : streamFolder(experiment.getAccession()+"-log-fold-changes", logFoldChanges)
                ));
            }




            List<Pair<String, Function<Writer, Void>>> normalizedExpressions = new ArrayList<>();
            for(String arrayDesign: experiment.getArrayDesignAccessions()){
                AtlasResource<TsvReader> r = dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesign).normalizedExpressions;
                if(r.exists()){
                    normalizedExpressions.add(Pair.of(
                            MessageFormat.format("{0}-{1}-normalized-expressions.tsv", experiment.getAccession(), arrayDesign),
                            readFromResourceAndWrite(r, Functions.<String[]>identity())
                    ));
                }
            }
            if(normalizedExpressions.size()>0){
                b.add(new ExternallyAvailableContent(
                        makeUri("normalized-expressions"),
                        ExternallyAvailableContent.Description.create("Data", "link",
                                "Normalized expressions (tsv)"
                        ),
                        normalizedExpressions.size() == 1 ? streamFile( normalizedExpressions.get(0)) : streamFolder(experiment.getAccession()+"-normalized-expressions", normalizedExpressions)
                ));
            }

            return b.build();
        }
    }
}
