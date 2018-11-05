package uk.ac.ebi.atlas.metadata;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class CellMetadataService {
    private CellMetadataDao cellMetadataDao;

    public CellMetadataService(CellMetadataDao cellMetadataDao) {
        this.cellMetadataDao = cellMetadataDao;
    }

    public Optional<String> getInferredCellType(String experimentAccession, String cellId) {
        return cellMetadataDao.getMetadataValueForCellId(
                experimentAccession, SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE,
                cellId);
    }

    public Map<String, String> getFactors(String experimentAccession, String cellId) {
        List<SingleCellAnalyticsSchemaField> factorFieldNames =
                cellMetadataDao.getFactorFieldNames(experimentAccession, cellId);

        return cellMetadataDao.getQueryResultForMultiValueFields(
                experimentAccession, Optional.of(cellId), factorFieldNames)
                .entrySet().stream()
                .collect(toMap(
                        entry -> SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName(entry.getKey()),
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }

    public Map<String, String> getMetadata(String experimentAccession, String cellId) {
        Set<SingleCellAnalyticsSchemaField> metadataFieldNames = Stream.concat(
                cellMetadataDao.getMetadataFieldNames(experimentAccession).stream(),
                cellMetadataDao.getAdditionalAttributesFieldNames(experimentAccession).stream())
                .collect(Collectors.toSet());

        return cellMetadataDao
                .getQueryResultForMultiValueFields(experimentAccession, Optional.of(cellId), metadataFieldNames)
                .entrySet().stream()
                .collect(toMap(
                        entry -> SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName(entry.getKey()),
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }
}
