package uk.ac.ebi.atlas.streams.differential;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.differential.DifferentialExpressionPrecondition;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsQueueBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfileReusableBuilder;
import uk.ac.ebi.atlas.streams.baseline.IsBaselineExpressionAboveCutoffAndForFilterFactors;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;

@Named
@Scope("prototype")
public class RnaSeqInputStreamFactory {

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String experimentDataFileUrlTemplate;

    private RnaSeqExpressionsQueueBuilder expressionsQueueBuilder;

    private CsvReaderFactory csvReaderFactory;

    @Inject
    public RnaSeqInputStreamFactory(RnaSeqExpressionsQueueBuilder expressionsQueueBuilder,
                                    CsvReaderFactory csvReaderFactory) {
        this.expressionsQueueBuilder = expressionsQueueBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }

    public ObjectInputStream<RnaSeqProfile> create(String experimentAccession, double cutOff, Regulation regulation) {
        String tsvFileURL = MessageFormat.format(experimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        DifferentialExpressionPrecondition expressionFilter = new DifferentialExpressionPrecondition();
        expressionFilter.setCutoff(cutOff);
        expressionFilter.setRegulation(regulation);

        RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder = new RnaSeqProfileReusableBuilder(expressionFilter);

        return new RnaSeqProfilesInputStream2(csvReader, experimentAccession, expressionsQueueBuilder, rnaSeqProfileReusableBuilder);
    }

}
