package uk.ac.ebi.atlas.experimentimport.admin;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderDummy;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentOpLogWriter {

    private final DataFileHub dataFileHub;

    public static final int MAX_LENGTH = 50;

    public ExperimentOpLogWriter(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    List<Pair<String, Pair<Long, Long>>> getCurrentOpLog(String accession) {
        List<Pair<String, Pair<Long, Long>>> result = new ArrayList<>();

        AtlasResource<TsvReader> r = dataFileHub.getExperimentFiles(accession).adminOpLog;

        TsvReader tsvReader = r.exists() ? r.get() : new TsvReaderDummy();

        for (String[] line : tsvReader.readAll()) {
            Validate.isTrue(line.length == 3);
            result.add(Pair.of(line[0], Pair.of(Long.parseLong(line[1]), Long.parseLong(line[2]))));
        }
        return result;
    }

    void persistOpLog(String accession, List<Pair<String, Pair<Long, Long>>> opLog) {

        TsvWriter tsvWriter =  dataFileHub.getExperimentFiles(accession).adminOpLogWrite.get();
        List<String[]> lines = new ArrayList<>();
        for (int i = opLog.size() - Math.min(opLog.size(), MAX_LENGTH); i < opLog.size(); i++) {
            Pair<String, Pair<Long, Long>> loggedOp = opLog.get(i);
            lines.add(new String[]{
                    loggedOp.getLeft(),
                    loggedOp.getRight().getLeft().toString(),
                    loggedOp.getRight().getRight().toString()});
        }
        tsvWriter.writeAll(lines);

        try {
            tsvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
