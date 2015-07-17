/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.util.Collection;

import static org.mockito.Mockito.mock;

public class DummyUpdateSolrClient extends HttpSolrClient {

    public DummyUpdateSolrClient(String baseUrl){
        super(baseUrl);
    }

    @Override
    public UpdateResponse deleteByQuery(String query){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse commit(){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse addBeans(Collection<?> documentBeans){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse optimize(){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }


}
