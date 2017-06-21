package uk.ac.ebi.atlas.markergenes;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class MarkerGenesSearchService {

    private final MarkerGeneDao markerGeneDao;

    @Inject
    public MarkerGenesSearchService(MarkerGeneDao markerGeneDao) {
        this.markerGeneDao = markerGeneDao;
    }

    public List<MarkerGene> searchMarkerGenesByGeneId(String geneId) {
        return markerGeneDao.fetchMarkerGenes(geneId);
    }

}
