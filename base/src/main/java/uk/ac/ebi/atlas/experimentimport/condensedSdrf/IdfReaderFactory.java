package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.ArrayExpressIdfReaderFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class IdfReaderFactory {
    private ArrayExpressIdfReaderFactory arrayExpressIdfReaderFactory;
    private DataFileHub dataFileHub;

    @Inject
    public IdfReaderFactory(ArrayExpressIdfReaderFactory arrayExpressIdfReaderFactory, DataFileHub dataFileHub) {
        this.arrayExpressIdfReaderFactory = arrayExpressIdfReaderFactory;
        this.dataFileHub = dataFileHub;
    }

    public TsvReader create(String experimentAccession) {
        try {
            return arrayExpressIdfReaderFactory.createArrayExpressIdfReader(experimentAccession);
        } catch (IOException e) {
            return dataFileHub.getExperimentFiles(experimentAccession).idf.get();
        }
    }

}