package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class BaselineExperimentSearchService {



    public void specificSearch(String experimentAccession,
                               double expressionLevelThreshold,
                               SemanticQuery bioentityIdentifierSearch,
                               SemanticQuery assayGroupIdSearch) {
        // bioentityIdentifierSearch -> subset of bioentity identifiers in the experiment or the whole set


        // assayGroupIdSearch -> subset of assay group IDs in the experiment or the whole set


        // Search

    }


}
