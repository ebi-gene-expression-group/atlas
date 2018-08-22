package uk.ac.ebi.atlas.metadata;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.attributeNameToFieldName;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField;

@Component
public class CellMetadataService {
    private IdfParser idfParser;
    private CellMetadataDao cellMetadataDao;

    public CellMetadataService(IdfParser idfParser, CellMetadataDao cellMetadataDao) {
        this.idfParser = idfParser;
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
        List<SingleCellAnalyticsSchemaField> metadataFieldNames =
                cellMetadataDao.getMetadataFieldNames(experimentAccession);

        return cellMetadataDao
                .getQueryResultForMultiValueFields(experimentAccession, Optional.of(cellId), metadataFieldNames)
                .entrySet().stream()
                .collect(toMap(
                        entry -> SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName(entry.getKey()),
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }

    // This is not currently used but leaving in for now in case the logic is still useful
    protected Map<String, String> getIdfFileAttributes(String experimentAccession, String cellId) {
        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        if (idfParserOutput.getMetadataFieldsOfInterest().isEmpty()) {
            return emptyMap();
        }

        List<SingleCellAnalyticsSchemaField> attributeFields = idfParserOutput.getMetadataFieldsOfInterest()
                .stream()
                .map(attribute -> characteristicAsSchemaField(attributeNameToFieldName(attribute)))
                .collect(toList());

        return cellMetadataDao
                .getQueryResultForMultiValueFields(experimentAccession, Optional.of(cellId), attributeFields)
                .entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }
}
