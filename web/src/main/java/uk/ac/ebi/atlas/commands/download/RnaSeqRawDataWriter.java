package uk.ac.ebi.atlas.commands.download;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqRawDataWriter extends ExpressionsWriter {

    @Value("#{configuration['diff.experiment.raw-counts.path.template']}")
    private String differentialExperimentRawCountsFileUrlTemplate;

    private RnaSeqRawDataHeaderBuilder headerBuilder;

    @Inject
    public RnaSeqRawDataWriter(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider, RnaSeqRawDataHeaderBuilder headerBuilder) {
        super(csvReaderBuilder, geneNamesProvider);
        this.headerBuilder = headerBuilder;
        setFileUrlTemplate(differentialExperimentRawCountsFileUrlTemplate);
    }


    @Override
    protected String[] buildHeader(String[] header) {
        return  headerBuilder.buildHeader(header);
    }

}
