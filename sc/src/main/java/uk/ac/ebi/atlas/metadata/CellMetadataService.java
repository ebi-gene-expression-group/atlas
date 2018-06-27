package uk.ac.ebi.atlas.metadata;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.attributeNameToFieldName;

@Component
public class CellMetadataService {
    private IdfParser idfParser;
    private CellMetadataDao cellMetadataDao;

    public CellMetadataService(IdfParser idfParser, CellMetadataDao cellMetadataDao) {
        this.idfParser = idfParser;
        this.cellMetadataDao = cellMetadataDao;
    }

    public Optional<String> getInferredCellType(String experimentAccession, String cellId) {
        return cellMetadataDao.getMetadataFieldValueForCellId(
                SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE,
                experimentAccession,
                cellId);
    }

    public Map<String, String> getFactors(String experimentAccession, String cellId) {
        SingleCellAnalyticsSchemaField[] factorFieldNames = cellMetadataDao.getFactorFieldNames(experimentAccession, cellId);

        return cellMetadataDao.getQueryResultForMultiValueFields(experimentAccession, Optional.of(cellId), factorFieldNames)
                .entrySet().stream()
                .collect(toMap(
                        entry -> SingleCellAnalyticsCollectionProxy.factorFieldNameToDisplayName(entry.getKey()),
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }

    // This is not currently used but leaving in for now in case the logic is still useful
    protected Map<String, String> getIdfFileAttributes(String experimentAccession, String cellId) {
        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        if (idfParserOutput.getMetadataFieldsOfInterest().isEmpty()) {
            return emptyMap();
        }

        SingleCellAnalyticsSchemaField[] attributeFields = idfParserOutput.getMetadataFieldsOfInterest()
                .stream()
                .map(attribute -> SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField(attributeNameToFieldName(attribute)))
                .toArray(SingleCellAnalyticsSchemaField[]::new);

        return cellMetadataDao.getQueryResultForMultiValueFields(experimentAccession, Optional.of(cellId), attributeFields)
                .entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }
}
