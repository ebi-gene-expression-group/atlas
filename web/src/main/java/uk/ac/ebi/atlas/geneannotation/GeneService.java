package uk.ac.ebi.atlas.geneannotation;


import javax.annotation.Resource;
import javax.inject.Named;
import java.util.concurrent.ConcurrentMap;

@Named("geneService")
public class GeneService {

    ConcurrentMap<String, String> geneNames;

    public GeneService() {
    }

    public String getGeneName(String ensGeneId) {
        String value = geneNames.get(ensGeneId);
        return value == null ? ensGeneId : value;
    }

    @Resource(name = "geneNames")
    public void setGeneNames(ConcurrentMap<String, String> geneNames) {
        this.geneNames = geneNames;
    }
}
