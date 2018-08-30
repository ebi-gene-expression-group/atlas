package uk.ac.ebi.atlas.bioentity.interpro;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.OntologyTerm;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

public class InterProTsvParser {
    private static final int NAME_COLUMN_INDEX = 0;
    private static final int ACCESSION_COLUMN_INDEX = 1;
    private static final int TYPE_COLUMN_INDEX = 2;

    protected InterProTsvParser() {
        throw new UnsupportedOperationException();
    }

    public static ImmutableMap<String, OntologyTerm> parse(TsvStreamer tsvStreamer) {
        return tsvStreamer.get()
                .filter(line -> line.length > 2)
                .filter(line -> line[ACCESSION_COLUMN_INDEX].startsWith("IPR"))
                .collect(toImmutableMap(
                        line -> line[ACCESSION_COLUMN_INDEX],
                        line ->
                                OntologyTerm.create(
                                        line[ACCESSION_COLUMN_INDEX],
                                        line[NAME_COLUMN_INDEX] +
                                                " (" + line[TYPE_COLUMN_INDEX].toLowerCase() + ")")));
    }
}
