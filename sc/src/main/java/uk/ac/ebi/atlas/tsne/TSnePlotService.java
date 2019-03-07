package uk.ac.ebi.atlas.tsne;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.util.MathUtils;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;
import uk.ac.ebi.atlas.metadata.CellMetadataDao;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Component
public class TSnePlotService {
    private final TSnePlotDao tSnePlotDao;
    private final CellMetadataDao cellMetadataDao;

    public TSnePlotService(TSnePlotDao tSnePlotDao, CellMetadataDao cellMetadataDao) {
        this.tSnePlotDao = tSnePlotDao;
        this.cellMetadataDao = cellMetadataDao;
    }

    public Set<TSnePoint> fetchTSnePlotWithExpression(String experimentAccession, int perplexity, String geneId) {
        return tSnePlotDao.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId).stream()
                .map(
                        pointDto ->
                                TSnePoint.create(
                                        MathUtils.round(pointDto.x(), 2),
                                        MathUtils.round(pointDto.y(), 2),
                                        pointDto.expressionLevel(),
                                        pointDto.name()))
                .collect(toSet());
    }

    public Map<Integer, Set<TSnePoint>> fetchTSnePlotWithClusters(String experimentAccession, int perplexity, int k) {
        List<TSnePoint.Dto> points = tSnePlotDao.fetchTSnePlotWithClusters(experimentAccession, perplexity, k);

        return points.stream()
                .collect(groupingBy(TSnePoint.Dto::clusterId))
                .entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(pointDto -> TSnePoint.create(pointDto.x(), pointDto.y(), pointDto.name()))
                                .collect(toSet())));
    }


    public Map<String, Set<TSnePoint>> fetchTSnePlotWithMetadata(String experimentAccession,
                                                                 int perplexity,
                                                                 String metadataCategory) {
        List<TSnePoint.Dto> pointDtos = tSnePlotDao.fetchTSnePlotForPerplexity(experimentAccession, perplexity);

        // An alternative implementation would be to get the metadata for each cell in the tSnePlotServiceDao method
        // and create TSnePoint.Dto objects with metadata values. This would require separate requests to Solr for
        // each cell ID.
        List<String> cellIds = pointDtos
                .stream()
                .map(TSnePoint.Dto::name)
                .collect(Collectors.toList());

        Map<String, String> metadataValuesForCells =
                cellMetadataDao.getMetadataValueForCellIds(
                        experimentAccession,
                        SingleCellAnalyticsCollectionProxy.metadataAsSchemaField(metadataCategory),
                        cellIds);

        return pointDtos.stream()
                .map(
                        pointDto ->
                                TSnePoint.create(
                                        pointDto.x(),
                                        pointDto.y(),
                                        pointDto.name(),
                                        StringUtils.capitalize(metadataValuesForCells.get(pointDto.name()))))
                .collect(groupingBy(TSnePoint::metadata, mapping(Function.identity(), Collectors.toSet())));
    }
}
