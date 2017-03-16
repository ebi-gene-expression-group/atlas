package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Function;
import uk.ac.ebi.atlas.experimentpage.differential.download.CanStreamSupplier;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public abstract class ExperimentDesignFile<E extends Experiment> extends CanStreamSupplier<E> {

    private final DataFileHub dataFileHub;
    public ExperimentDesignFile(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    public Collection<ExternallyAvailableContent> get(final E experiment){
        return Collections.singleton(
                new ExternallyAvailableContent(
                        makeUri("experiment-design"),
                        ExternallyAvailableContent.Description.create("Supplementary Information", "icon-tsv", "Experiment Design as tab-separated file"),
                        streamFile(experiment.getAccession() + "-experiment-design.tsv", new Function<Writer, Void>() {
                            @Nullable
                            @Override
                            public Void apply(@Nullable Writer writer) {
                                writeLines(experiment.getAccession(), getAnalysedRowsAccessions(experiment), writer);
                                return null;
                            }
                        })
                )
        );
    }

    protected void writeLines(String experimentAccession, Set<String> analysedRowsAccessions, Writer writer){
        List<String[]> newCsvLines = getLines(analysedRowsAccessions, dataFileHub.getExperimentFiles(experimentAccession).experimentDesign.get().readAll());

        try {
            CSVWriter csvWriter = new CSVWriter(writer, '\t', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER);
            csvWriter.writeAll(newCsvLines);

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    List<String[]> getLines(Set<String> analysedRowsAccessions, List<String[]> csvLines){

        List<String[]> newCsvLines = new ArrayList<>(csvLines.size());


        // modify header by adding new column
        String[] header = csvLines.get(0);
        String[] newHeader = new String[header.length + 1];
        System.arraycopy(header, 0, newHeader, 0, header.length);
        newHeader[header.length] = "Analysed";
        newCsvLines.add(newHeader);

        // copy content and add used field
        for (String[] array : csvLines.subList(1, csvLines.size())) {
            String[] newArray = new String[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            boolean isRunAnalysed = analysedRowsAccessions.contains(newArray[0]);
            newArray[array.length] = isRunAnalysed ? "Yes" : "No";
            newCsvLines.add(newArray);
        }

        return newCsvLines;
    }


    protected abstract Set<String> getAnalysedRowsAccessions(E experiment);


    @Named
    public static class Baseline extends ExperimentDesignFile<BaselineExperiment> {

        @Inject
        public Baseline(DataFileHub dataFileHub){
            super(dataFileHub);
        }

        @Override
        protected Set<String> getAnalysedRowsAccessions(BaselineExperiment experiment) {
            Set<String> result = new HashSet<>();
            for(AssayGroup assayGroup: experiment.getDataColumnDescriptors()){
                for(String assay: assayGroup){
                    result.add(assay);
                }
            }
            return result;
        }
    }

    @Named
    public static class RnaSeq extends Differential<DifferentialExperiment>{

        @Inject
        public RnaSeq(DataFileHub dataFileHub) {
            super(dataFileHub);
        }
    }

    @Named
    public static class Microarray extends Differential<MicroarrayExperiment>{

        @Inject
        public Microarray(DataFileHub dataFileHub) {
            super(dataFileHub);
        }
    }

    public static abstract class Differential<E extends DifferentialExperiment> extends ExperimentDesignFile<E> {

        public Differential(DataFileHub dataFileHub){
            super(dataFileHub);
        }

        @Override
        protected Set<String> getAnalysedRowsAccessions(E experiment) {
            Set<String> result = new HashSet<>();
            for(Contrast contrast: experiment.getDataColumnDescriptors()){
                for(String assay: contrast.getReferenceAssayGroup()){
                    result.add(assay);
                }
                for(String assay: contrast.getTestAssayGroup()){
                    result.add(assay);
                }
            }
            return result;
        }
    }
}
