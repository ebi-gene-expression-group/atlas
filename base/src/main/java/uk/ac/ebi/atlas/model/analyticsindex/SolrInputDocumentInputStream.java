package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.asAnalyticsSchemaField;

public class SolrInputDocumentInputStream implements ObjectInputStream<SolrInputDocument> {
    private final ObjectInputStream<? extends ExperimentDataPoint> experimentDataPointInputStream;
    private final Iterator<? extends ExperimentDataPoint> experimentDataPointIterator;
    private final Map<String,Map<BioentityPropertyName, Set<String>>> propertiesPerBioentityIdentifier;

    public SolrInputDocumentInputStream(
            ObjectInputStream<? extends ExperimentDataPoint> experimentDataPointInputStream,
            Map<String, Map<BioentityPropertyName, Set<String>>> propertiesPerBioentityIdentifier) {
        this.experimentDataPointInputStream = experimentDataPointInputStream;
        this.experimentDataPointIterator = new IterableObjectInputStream<>(experimentDataPointInputStream).iterator();
        this.propertiesPerBioentityIdentifier = propertiesPerBioentityIdentifier;
    }


    @Override
    public void close() throws IOException {
        experimentDataPointInputStream.close();
    }

    @Override
    public SolrInputDocument readNext() {
        if (experimentDataPointIterator.hasNext()) {
            ExperimentDataPoint experimentDataPoint = experimentDataPointIterator.next();
            Map<BioentityPropertyName, Set<String>> m =
                    propertiesPerBioentityIdentifier.get(experimentDataPoint.bioentityIdentifier);

            return create(experimentDataPoint, m != null ? m : ImmutableMap.of());
        }
        else {
            return null;
        }
    }

    private static SolrInputDocument create (
            ExperimentDataPoint experimentDataPoint, Map<BioentityPropertyName, Set<String>> bioentityProperties) {

        SolrInputDocument solrInputDocument = new SolrInputDocument();

        for(Map.Entry<String, Object> e : experimentDataPoint.getProperties().entrySet()) {
            solrInputDocument.addField(e.getKey(), e.getValue());
        }

        List<String> nonKeywordProperties = new LinkedList<>();
        nonKeywordProperties.add(experimentDataPoint.bioentityIdentifier);
        for (BioentityPropertyName bioentityPropertyName : experimentDataPoint.getRelevantBioentityPropertyNames()) {
            for (String value : bioentityProperties.getOrDefault(bioentityPropertyName, Collections.emptySet())) {
                if (bioentityPropertyName.isKeyword) {
                    solrInputDocument.addField(asAnalyticsSchemaField(bioentityPropertyName).name(), value);
                }
                nonKeywordProperties.add(value);
            }
        }

        solrInputDocument.addField(IDENTIFIER_SEARCH.name(), Joiner.on(" ").join(nonKeywordProperties));

        return solrInputDocument;
    }
}
