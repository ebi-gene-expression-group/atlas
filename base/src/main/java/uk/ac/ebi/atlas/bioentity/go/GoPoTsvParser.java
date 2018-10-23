package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.IOException;
import java.util.Collection;

public class GoPoTsvParser {

    private static final String[] VALID_PREFIXES = {"GO:", "PO:"};

    private static final int TERM_ACCESSION_COLUMN_INDEX = 0;
    private static final int TERM_NAME_COLUMN_INDEX = 1;
    private static final int TERM_DEPTH_COLUMN_INDEX = 2;
    public static final int DEFAULT_DEPTH = 1;

    private final CSVReader csvReader;

    public GoPoTsvParser(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    ImmutableMap<String, OntologyTerm> parse() throws IOException {

        String startsWithGoPoRegex = "^(" + Joiner.on("|").join(VALID_PREFIXES) + ").+";

        ImmutableMultimap.Builder<String, OntologyTerm> multimapBuilder = ImmutableMultimap.builder();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            if (line.length > TERM_ACCESSION_COLUMN_INDEX) {
                String accession = line[TERM_ACCESSION_COLUMN_INDEX];
                if (accession.matches(startsWithGoPoRegex)) {
                    String name = line.length > TERM_NAME_COLUMN_INDEX ? line[TERM_NAME_COLUMN_INDEX] : "";
                    int depth = parseDepth(line);
                    multimapBuilder.put(accession, OntologyTerm.create(accession, name, "", depth));
                }
            }
        }
        ImmutableMultimap<String, OntologyTerm> accessionToGoPoTerms = multimapBuilder.build();


        ImmutableMap.Builder<String, OntologyTerm> mapBuilder = ImmutableMap.builder();
        for (String accession : accessionToGoPoTerms.keySet()) {

            int minDepth = minDepthOfTerms(accessionToGoPoTerms.get(accession));
            String name = accessionToGoPoTerms.get(accession).iterator().next().name();

            mapBuilder.put(accession, OntologyTerm.create(accession, name, "", minDepth));
        }

        return mapBuilder.build();
    }

    private int minDepthOfTerms(Collection<OntologyTerm> terms) {
        int minDepth = Integer.MAX_VALUE;
        for (OntologyTerm term : terms) {
            minDepth = term.depth() < minDepth ? term.depth() : minDepth;
        }
        return minDepth;
    }

    private int parseDepth(String[] line) {
        try {
            return line.length > TERM_DEPTH_COLUMN_INDEX ?
                    Integer.parseInt(line[TERM_DEPTH_COLUMN_INDEX]) :
                    DEFAULT_DEPTH;
        } catch (NumberFormatException e) {
            return DEFAULT_DEPTH;
        }
    }

}
