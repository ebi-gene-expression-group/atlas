package uk.ac.ebi.atlas.geneannotation;

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
        String value = geneNames.get(ensGeneId);
        return value == null ? ensGeneId : value;
    }
}
