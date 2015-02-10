package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

public class GoPoTermTSVReader implements Closeable {

    private final CSVReader csvReader;
    private String termType;

    private ImmutableMap<String, GoPoTerm> accessionToGoPoTerm;

    public GoPoTermTSVReader(CSVReader csvReader, String termType) {
        this.csvReader = csvReader;
        this.termType = termType;
    }

    void readAll() throws IOException {

        ImmutableMultimap.Builder<String, GoPoTerm> multimapBuilder = ImmutableMultimap.builder();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            String accession = nextLine[0];
            String term = nextLine[1].replace("_", " ");
            int depth = nextLine.length == 3 ? Integer.parseInt(nextLine[2]) : 1;

            if(accession.startsWith(termType)) {
                multimapBuilder.put(accession, GoPoTerm.create(accession, term, depth));
            }
        }
        ImmutableMultimap<String, GoPoTerm> accessionToGoPoTerms = multimapBuilder.build();


        ImmutableMap.Builder<String, GoPoTerm> mapBuilder = ImmutableMap.builder();
        for (String accession : accessionToGoPoTerms.keySet()) {

            int minDepth = minDepthOfTerms(accessionToGoPoTerms.get(accession));
            String termName = accessionToGoPoTerms.get(accession).iterator().next().name();

            mapBuilder.put(accession, GoPoTerm.create(accession, termName, minDepth));
        }
        accessionToGoPoTerm = mapBuilder.build();
    }

    private int minDepthOfTerms(Collection<GoPoTerm> terms) {
        int minDepth = Integer.MAX_VALUE;
        for (GoPoTerm term : terms) {
            minDepth = term.depth() < minDepth ? term.depth() : minDepth;
        }
        return minDepth;
    }

    ImmutableMap<String, GoPoTerm> getAccessionToTermMap() {
        return accessionToGoPoTerm;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }
}
