package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;
import uk.ac.ebi.atlas.model.ExperimentDesign;

//ToDo: (N) to be tested

public abstract class ExperimentDesignWriter {

    public void write(String experimentAccession, CSVWriter csvWriter) throws ExperimentDesignWritingException {

        ExperimentDesign experimentDesign = getMageTabParser().parse(experimentAccession);
        csvWriter.writeNext(composeHeader(experimentDesign));

        csvWriter.writeAll(experimentDesign.asTableData());


    }

    protected abstract String[] composeHeader(ExperimentDesign experimentDesign);

    protected abstract ExperimentDesignMageTabParser getMageTabParser();
}
