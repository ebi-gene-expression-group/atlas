package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.net.MalformedURLException;

public abstract class ExperimentDesignWriter {

    public void write(String experimentAccession, CSVWriter csvWriter) throws ExperimentDesignWritingException {
        try {
            ExperimentDesign experimentDesign = getMageTabParser().parse(experimentAccession);
            csvWriter.writeNext(composeHeader(experimentDesign));


            csvWriter.writeAll(experimentDesign.asTableData());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected abstract String[] composeHeader(ExperimentDesign experimentDesign);

    protected abstract MageTabParser getMageTabParser();
}
