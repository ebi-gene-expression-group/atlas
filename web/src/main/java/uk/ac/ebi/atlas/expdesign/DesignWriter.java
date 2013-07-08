package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;

public abstract class DesignWriter {

    public void write(String experimentAccession, CSVWriter csvWriter) throws ExperimentDesignWritingException {

    }

    protected  String[] composeAssayRow(String assay, ExperimentDesign experimentDesign) {
        return null;
    }

    protected abstract String[] composeHeader(ExperimentDesign experimentDesign);
}
