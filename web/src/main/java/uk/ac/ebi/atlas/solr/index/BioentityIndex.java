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

package uk.ac.ebi.atlas.solr.index;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Named
@Scope("prototype")
public class BioentityIndex {

    private static final Logger LOGGER = Logger.getLogger(BioentityIndex.class);
    private final BioentityPropertyStreamBuilder bioentityPropertyStreamBuilder;

    private SolrServer solrServer;

    @Inject
    public BioentityIndex(SolrServer solrServer, BioentityPropertyStreamBuilder bioentityPropertyStreamBuilder) {
        this.bioentityPropertyStreamBuilder = bioentityPropertyStreamBuilder;
        this.solrServer = solrServer;
    }

    public void add(Path bioentityPropertiesFilePath) {

        try(BioentityPropertiesStream bioentityBioentityPropertiesStream =
                    bioentityPropertyStreamBuilder.forPath(bioentityPropertiesFilePath).build()){

            List<BioentityProperty> documents;

            while ((documents = bioentityBioentityPropertiesStream.next()) != null) {
                solrServer.addBeans(documents);
            }
            solrServer.commit();

        } catch(IOException|SolrServerException e){
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
