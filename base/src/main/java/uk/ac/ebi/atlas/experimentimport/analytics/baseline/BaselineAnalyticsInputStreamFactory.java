package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineAnalyticsInputStreamFactory {

    private final DataFileHub dataFileHub;

    @Inject
    public BaselineAnalyticsInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ObjectInputStream<BaselineAnalytics> create(String experimentAccession, ExperimentType experimentType) {
        Preconditions.checkArgument(experimentType.isBaseline());
        AtlasResource<CSVReader> r = dataFileHub.getExperimentFiles(experimentAccession).main;
        return experimentType.isProteomicsBaseline()
                ? new ProteomicsBaselineAnalyticsInputStream(r.get(), r.toString())
                : new RnaSeqBaselineAnalyticsInputStream(r.get(), r.toString());
    }
}
