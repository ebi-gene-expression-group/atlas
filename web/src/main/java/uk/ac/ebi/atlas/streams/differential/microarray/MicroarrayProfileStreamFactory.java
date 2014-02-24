package uk.ac.ebi.atlas.streams.differential.microarray;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.SequenceObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialExpressionPrecondition;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Vector;

@Named
@Scope("prototype")
public class MicroarrayProfileStreamFactory {

    @Value("#{configuration['microarray.experiment.data.path.template']}")
    private String experimentDataFileUrlTemplate;

    private MicroarrayExpressionsQueueBuilder expressionsQueueBuilder;

    private CsvReaderFactory csvReaderFactory;

    @Inject
    public MicroarrayProfileStreamFactory(MicroarrayExpressionsQueueBuilder expressionsQueueBuilder,
                                          CsvReaderFactory csvReaderFactory) {
        this.expressionsQueueBuilder = expressionsQueueBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }


    public ObjectInputStream<MicroarrayProfile> createForAllArrayDesigns(MicroarrayProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();
        double cutOff = options.getCutoff();
        Regulation regulation = options.getRegulation();
        Iterable<String> arrayDesignAccessions = options.getArrayDesignAccessions();

        return create(experimentAccession, cutOff, regulation, arrayDesignAccessions);
    }

    public MicroarrayProfileStream create(MicroarrayProfileStreamOptions options, String arrayDesign) {
        String experimentAccession = options.getExperimentAccession();
        double cutOff = options.getCutoff();
        Regulation regulation = options.getRegulation();

        return create(experimentAccession, cutOff, regulation, arrayDesign);
    }

    public ObjectInputStream<MicroarrayProfile> create(String experimentAccession, double cutOff, Regulation regulation, Iterable<String> arrayDesignAccessions) {
        Vector<ObjectInputStream<MicroarrayProfile>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : arrayDesignAccessions) {
            ObjectInputStream<MicroarrayProfile> stream = create(experimentAccession, cutOff, regulation, arrayDesignAccession);
            inputStreams.add(stream);
        }

        return new SequenceObjectInputStream<>(inputStreams.elements());
    }

    public MicroarrayProfileStream create(String experimentAccession, double cutOff, Regulation regulation, String arrayDesignAccession) {
        MicroarrayProfileReusableBuilder profileBuilder = createProfileBuilder(cutOff, regulation);
        CSVReader csvReader = createCsvReader(experimentAccession, arrayDesignAccession);

        return new MicroarrayProfileStream(csvReader, experimentAccession, expressionsQueueBuilder, profileBuilder);
    }


    private CSVReader createCsvReader(String experimentAccession, String arrayDesignAccession) {
        String tsvFileURL = MessageFormat.format(experimentDataFileUrlTemplate, experimentAccession, arrayDesignAccession);
        return csvReaderFactory.createTsvReader(tsvFileURL);
    }

    private MicroarrayProfileReusableBuilder createProfileBuilder(double cutOff, Regulation regulation) {
        DifferentialExpressionPrecondition expressionFilter = new DifferentialExpressionPrecondition();
        expressionFilter.setCutoff(cutOff);
        expressionFilter.setRegulation(regulation);

        return new MicroarrayProfileReusableBuilder(expressionFilter);
    }

}
