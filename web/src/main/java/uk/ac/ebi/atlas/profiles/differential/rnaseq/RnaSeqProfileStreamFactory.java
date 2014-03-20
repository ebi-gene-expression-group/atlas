package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class RnaSeqProfileStreamFactory {

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

    public RnaSeqProfileStream create(DifferentialProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();
        double pValueCutOff = options.getPValueCutOff();
        double foldChangeCutOff = options.getFoldChangeCutOff();
        Regulation regulation = options.getRegulation();

        return create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation);
    }

    public RnaSeqProfileStream create(String experimentAccession, double pValueCutOff, double foldChangeCutOff, Regulation regulation) {
        String tsvFileURL = MessageFormat.format(experimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);

        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(pValueCutOff);
        expressionFilter.setFoldChangeCutOff(foldChangeCutOff);
        expressionFilter.setRegulation(regulation);

        RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder = new RnaSeqProfileReusableBuilder(expressionFilter);

        return new RnaSeqProfileStream(csvReader, experimentAccession, expressionsQueueBuilder, rnaSeqProfileReusableBuilder);
    }

}
