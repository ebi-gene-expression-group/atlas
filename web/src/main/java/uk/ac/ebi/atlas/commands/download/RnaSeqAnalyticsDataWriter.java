package uk.ac.ebi.atlas.commands.download;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;

//@Named
public class RnaSeqAnalyticsDataWriter extends ExpressionsWriter {

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialExperimentAnalyticsFileUrlTemplate;

    private AnalyticsDataHeaderBuilder headerBuilder;

    @Inject
    public RnaSeqAnalyticsDataWriter(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider, AnalyticsDataHeaderBuilder headerBuilder) {
        super(csvReaderBuilder, geneNamesProvider);
        this.headerBuilder = headerBuilder;
        setFileUrlTemplate(differentialExperimentAnalyticsFileUrlTemplate);
    }


    @Override
    protected String[] buildHeader(String[] header) {
       return headerBuilder.buildHeader(header);
    }


    public void setExperiment(DifferentialExperiment experiment) {
        headerBuilder.setExperiment(experiment);
    }

}
