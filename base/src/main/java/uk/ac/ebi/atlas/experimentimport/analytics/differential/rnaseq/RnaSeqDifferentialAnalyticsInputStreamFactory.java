package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqDifferentialAnalyticsInputStreamFactory {

    private final DataFileHub dataFileHub;

    @Inject
    public RnaSeqDifferentialAnalyticsInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public RnaSeqDifferentialAnalyticsInputStream create(String experimentAccession) {
        AtlasResource<CSVReader> r = dataFileHub.getDifferentialExperimentFiles(experimentAccession).analytics;
        return new RnaSeqDifferentialAnalyticsInputStream(r.get(), r.toString());
    }
}
