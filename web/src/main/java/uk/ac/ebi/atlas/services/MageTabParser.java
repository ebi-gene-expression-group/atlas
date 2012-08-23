package uk.ac.ebi.atlas.services;

import uk.ac.ebi.arrayexpress2.magetab.datamodel.IDF;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.IDFParser;
import uk.ac.ebi.atlas.model.Experiment;

import java.io.InputStream;

public class MageTabParser {

    private IDFParser idfParser;

    public MageTabParser(IDFParser idfParser){
        this.idfParser = idfParser;
    }


    public Experiment parse(InputStream inputStream) throws ParseException {
        IDF idf = idfParser.parse(inputStream);

        return new Experiment(idf.accession);

    }
}
