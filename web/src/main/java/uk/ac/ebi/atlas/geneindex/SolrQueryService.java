/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

@Named
public class SolrQueryService {

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    private HttpSolrServer solrServer;

    @PostConstruct
    private void initServer(){
        solrServer = new HttpSolrServer( serverURL );
        solrServer.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
        solrServer.setConnectionTimeout(2000); // 5 seconds to establish TCP
    }

    public List<String> getGeneIds(String queryString) throws SolrServerException {
        SolrQuery solrQuery = buildSolrQuery(queryString, "identifier");

        QueryResponse solrResponse = solrServer.query(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        if (solrResponse.getFacetFields().get(0).getValues() != null) {
            for (FacetField.Count facetField : solrResponse.getFacetFields().get(0).getValues()) {
                geneNames.add(facetField.getName());
            }
        }
        return geneNames;
    }

    SolrQuery buildSolrQuery(String queryString, String facedField) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(facedField);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(-1);
        solrQuery.setFacetMinCount(1);
        return solrQuery;
    }


}
