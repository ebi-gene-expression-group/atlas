package uk.ac.ebi.atlas.experimentpage.baseline.tsne;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Component
public class TSnePlotService {
    private final TSnePlotServiceDao tSnePlotServiceDao;

    public TSnePlotService(TSnePlotServiceDao tSnePlotServiceDao) {
        this.tSnePlotServiceDao = tSnePlotServiceDao;
    }

    public Set<TSnePoint> fetchTSnePlotWithExpression(String experimentAccession, int perplexity, String geneId) {
        return tSnePlotServiceDao.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId).stream()
                .map(pointDto -> TSnePoint.create(pointDto.x(), pointDto.y(), pointDto.expressionLevel(), pointDto.name()))
                .collect(toSet());
    }

    public Map<Integer, Set<TSnePoint>> fetchTSnePlotWithClusters(String experimentAccession, int perplexity, int k) {
        List<TSnePoint.Dto> points = tSnePlotServiceDao.fetchTSnePlotWithClusters(experimentAccession, perplexity, k);

        return points.stream()
                .collect(groupingBy(TSnePoint.Dto::clusterK))
                .entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(pointDto-> TSnePoint.create(pointDto.x(), pointDto.y(), pointDto.name()))
                                .collect(toSet())));
    }

    // fetchTSnePlotWithCluster(String experimentAccession, int perplexity, String factorValue) {
    // // Query DB for points and Solr for factors
    // }


// To understand what this is about see comment in TSnePlotServiceDao...

//    public Map<Integer, Set<TSnePoint>> fetchTSnePlotWithClusters(String experimentAccession,
//                                                                  int perplexity,
//                                                                  int k,
//                                                                  String geneId) {
//        List<TSnePoint.Dto> points =
//                tSnePlotServiceDao.fetchTSnePlotWithExpressionAndClusters(experimentAccession, perplexity, geneId, k);
//
//        return points.stream()
//                .collect(groupingBy(TSnePoint.Dto::clusterK))
//                .entrySet().stream()
//                .collect(toMap(
//                        Map.Entry::getKey,
//                        entry -> entry.getValue().stream()
//                                .map(pointDto-> TSnePoint.create(pointDto.x(), pointDto.y(), pointDto.expressionLevel(), pointDto.name()))
//                                .collect(toSet())));
//    }
}
