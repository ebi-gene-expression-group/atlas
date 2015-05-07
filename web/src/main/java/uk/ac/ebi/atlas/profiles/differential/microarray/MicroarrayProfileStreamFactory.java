package uk.ac.ebi.atlas.profiles.differential.microarray;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.SequenceObjectInputStream;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
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

    private ExpressionsRowDeserializerMicroarrayBuilder expressionsRowDeserializerMicroarrayBuilder;

    private CsvReaderFactory csvReaderFactory;

    @Inject
    public MicroarrayProfileStreamFactory(ExpressionsRowDeserializerMicroarrayBuilder expressionsRowDeserializerMicroarrayBuilder,
                                          CsvReaderFactory csvReaderFactory) {
        this.expressionsRowDeserializerMicroarrayBuilder = expressionsRowDeserializerMicroarrayBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }


    public ObjectInputStream<MicroarrayProfile> createForAllArrayDesigns(MicroarrayProfileStreamOptions options) {
        String experimentAccession = options.getExperimentAccession();
        double pValueCutOff = options.getPValueCutOff();
        double foldChangeCutOff = options.getFoldChangeCutOff();
        Regulation regulation = options.getRegulation();
        Iterable<String> arrayDesignAccessions = options.getArrayDesignAccessions();

        return create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation, arrayDesignAccessions);
    }

    public MicroarrayProfilesTsvInputStream create(MicroarrayProfileStreamOptions options, String arrayDesign) {
        String experimentAccession = options.getExperimentAccession();
        double pValueCutOff = options.getPValueCutOff();
        double foldChangeCutOff = options.getFoldChangeCutOff();
        Regulation regulation = options.getRegulation();

        return create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation, arrayDesign);
    }

    public ObjectInputStream<MicroarrayProfile> create(String experimentAccession, double pValueCutOff, double foldChangeCutOff, Regulation regulation, Iterable<String> arrayDesignAccessions) {
        Vector<ObjectInputStream<MicroarrayProfile>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : arrayDesignAccessions) {
            ObjectInputStream<MicroarrayProfile> stream = create(experimentAccession, pValueCutOff, foldChangeCutOff, regulation, arrayDesignAccession);
            inputStreams.add(stream);
        }

        return new SequenceObjectInputStream<>(inputStreams.elements());
    }

    public MicroarrayProfilesTsvInputStream create(String experimentAccession, double pValueCutOff, double foldChangeCutOff, Regulation regulation, String arrayDesignAccession) {
        MicroarrayProfileReusableBuilder profileBuilder = createProfileBuilder(pValueCutOff, foldChangeCutOff, regulation);
        CSVReader csvReader = createCsvReader(experimentAccession, arrayDesignAccession);

        return new MicroarrayProfilesTsvInputStream(csvReader, experimentAccession, expressionsRowDeserializerMicroarrayBuilder, profileBuilder);
    }


    private CSVReader createCsvReader(String experimentAccession, String arrayDesignAccession) {
        String tsvFileURL = MessageFormat.format(experimentDataFileUrlTemplate, experimentAccession, arrayDesignAccession);
        return csvReaderFactory.createTsvReader(tsvFileURL);
    }

    private MicroarrayProfileReusableBuilder createProfileBuilder(double pValueCutOff, double foldChangeCutOff, Regulation regulation) {
        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(pValueCutOff);
        expressionFilter.setFoldChangeCutOff(foldChangeCutOff);
        expressionFilter.setRegulation(regulation);

        return new MicroarrayProfileReusableBuilder(expressionFilter);
    }

}
