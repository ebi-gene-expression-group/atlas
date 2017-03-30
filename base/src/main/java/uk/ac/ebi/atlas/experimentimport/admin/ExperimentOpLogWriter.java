package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderDummy;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentOpLogWriter {

    private final DataFileHub dataFileHub;

    public static final int MAX_LENGTH = 50;

    public ExperimentOpLogWriter(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    ImmutableList<OpLogEntry> getCurrentOpLog(String accession) {
        ImmutableList.Builder<OpLogEntry> result = ImmutableList.builder();

        AtlasResource<TsvReader> r = dataFileHub.getExperimentFiles(accession).adminOpLog;

        TsvReader tsvReader = r.exists() ? r.get() : new TsvReaderDummy();

        for (String[] line : tsvReader.readAll()) {
            result.add(OpLogEntry.fromArray(line));
        }
        return result.build();
    }

    void persistOpLog(String accession, List<OpLogEntry> opLog) {

        TsvWriter tsvWriter =  dataFileHub.getExperimentFiles(accession).adminOpLogWrite.get();
        List<String[]> lines = new ArrayList<>();
        for (int i = opLog.size() - Math.min(opLog.size(), MAX_LENGTH); i < opLog.size(); i++) {
            OpLogEntry loggedOp = opLog.get(i);
            lines.add(loggedOp.toArray());
        }
        tsvWriter.writeAll(lines);

        try {
            tsvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
