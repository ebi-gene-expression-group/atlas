package uk.ac.ebi.atlas.streams.differential.rnaseq;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialExpressionPrecondition;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamFactory;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class RnaSeqProfileStreamFactory implements DifferentialProfileStreamFactory<RnaSeqProfile> {

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String experimentDataFileUrlTemplate;

    private RnaSeqExpressionsQueueBuilder expressionsQueueBuilder;

    private CsvReaderFactory csvReaderFactory;

    @Inject
    public RnaSeqProfileStreamFactory(RnaSeqExpressionsQueueBuilder expressionsQueueBuilder,
                                      CsvReaderFactory csvReaderFactory) {
        this.expressionsQueueBuilder = expressionsQueueBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }

    @Override
    public ObjectInputStream<RnaSeqProfile> create(String experimentAccession, double cutOff, Regulation regulation) {
        String tsvFileURL = MessageFormat.format(experimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        DifferentialExpressionPrecondition expressionFilter = new DifferentialExpressionPrecondition();
        expressionFilter.setCutoff(cutOff);
        expressionFilter.setRegulation(regulation);

        RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder = new RnaSeqProfileReusableBuilder(expressionFilter);

        return new RnaSeqProfileStream(csvReader, experimentAccession, expressionsQueueBuilder, rnaSeqProfileReusableBuilder);
    }

}
