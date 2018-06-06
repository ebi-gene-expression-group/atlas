package uk.ac.ebi.atlas.experimentimport.idf;

import uk.ac.ebi.atlas.commons.readers.ArrayExpressIdfStreamerFactory;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class IdfStreamerFactory {
    private ArrayExpressIdfStreamerFactory arrayExpressIdfReaderFactory;
    private DataFileHub dataFileHub;

    @Inject
    public IdfStreamerFactory(ArrayExpressIdfStreamerFactory arrayExpressIdfReaderFactory, DataFileHub dataFileHub) {
        this.arrayExpressIdfReaderFactory = arrayExpressIdfReaderFactory;
        this.dataFileHub = dataFileHub;
    }

    public TsvStreamer create(String experimentAccession) {
        try {
            return arrayExpressIdfReaderFactory.createArrayExpressIdfReader(experimentAccession);
        } catch (IOException e) {
            return dataFileHub.getExperimentFiles(experimentAccession).idf.get();
        }
    }

}