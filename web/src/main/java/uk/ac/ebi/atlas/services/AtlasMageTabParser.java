package uk.ac.ebi.atlas.services;

import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.SDRF;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.Experiment;

import java.io.InputStream;

public class AtlasMageTabParser {

    private MAGETABParser parser;

    private MAGETABInvestigation investigation;

    private SDRF sdrf;


    public AtlasMageTabParser(MAGETABParser parser){
        this.parser = parser;
    }


    public AtlasMageTabParser parse(InputStream inputStream) throws ParseException {
        investigation = parser.parse(inputStream);
        return this;
    }

    public Experiment getExperiment(){

        return new Experiment(investigation.getAccession());

    }




}
