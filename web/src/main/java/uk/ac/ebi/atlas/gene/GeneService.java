package uk.ac.ebi.atlas.gene;

import com.sleepycat.collections.StoredMap;

import javax.inject.Inject;
import javax.inject.Named;

@Named("geneService")
public class GeneService {

    StoredMap<String, String> geneNames;

    @Inject
    public GeneService(StoredMap<String, String> geneNames) {
        this.geneNames = geneNames;
    }

    public String getGeneName(String ensGeneId) {
        return geneNames.get(ensGeneId);
    }
}
