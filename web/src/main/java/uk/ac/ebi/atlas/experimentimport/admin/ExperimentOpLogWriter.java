package uk.ac.ebi.atlas.experimentimport.admin;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class ExperimentOpLogWriter {


    @Value("#{configuration['experiment.op_log.template']}")
    String opLogTemplate;

    private final FileTsvReaderBuilder fileTsvReaderBuilder;
    private final FileTsvWriterBuilder fileTsvWriterBuilder;

    public static final int MAX_LENGTH = 50;

    @Inject
    public ExperimentOpLogWriter(FileTsvReaderBuilder fileTsvReaderBuilder, FileTsvWriterBuilder fileTsvWriterBuilder) {
        this.fileTsvReaderBuilder = fileTsvReaderBuilder;
        this.fileTsvWriterBuilder = fileTsvWriterBuilder;
    }

    List<Pair<String, Pair<Long, Long>>> getCurrentOpLog(String accession) {
        List<Pair<String, Pair<Long, Long>>> result = new ArrayList<>();

        TsvReader tsvReader = fileTsvReaderBuilder
                .forTsvFilePathTemplate(opLogTemplate)
                .withExperimentAccession(accession)
                .returnDummyIfFileMissing()
                .build();
        for (String[] line : tsvReader.readAll()) {
            Validate.isTrue(line.length == 3);
            result.add(Pair.of(line[0], Pair.of(Long.parseLong(line[1]), Long.parseLong(line[2]))));
        }
        return result;
    }

    void persistOpLog(String accession, List<Pair<String, Pair<Long, Long>>> opLog) {
        TsvWriter tsvWriter = fileTsvWriterBuilder
                .forTsvFilePathTemplate(opLogTemplate)
                .withExperimentAccession(accession)
                .withAppend(false)
                .build();
        List<String[]> lines = new ArrayList<>();
        for (int i = opLog.size() - Math.min(opLog.size(), MAX_LENGTH); i < opLog.size(); i++) {
            Pair<String, Pair<Long, Long>> loggedOp = opLog.get(i);
            lines.add(new String[]{
                    loggedOp.getLeft(),
                    loggedOp.getRight().getLeft().toString(),
                    loggedOp.getRight().getRight().toString()});
        }
        tsvWriter.writeAll(lines);
    }
}
