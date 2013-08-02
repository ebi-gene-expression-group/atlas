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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("prototype")
public class BioentityIndex {

    private static final Logger LOGGER = Logger.getLogger(BioentityIndex.class);
    private final BioentityPropertiesStreamBuilder bioentityPropertiesStreamBuilder;

    private SolrServer solrServer;

    @Inject
    public BioentityIndex(SolrServer solrServer, BioentityPropertiesStreamBuilder bioentityPropertiesStreamBuilder) {
        this.bioentityPropertiesStreamBuilder = bioentityPropertiesStreamBuilder;
        this.solrServer = solrServer;
    }

    public void indexAll(DirectoryStream<Path> bioentityPropertiesDirectoryStream) {

        try (DirectoryStream<Path> directoryStream = bioentityPropertiesDirectoryStream) {
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)){
                    indexAll(Files.newDirectoryStream(path));
                    return;
                }
                indexFile(path);
            }
        } catch(IOException e){
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

    void indexFile(Path filePath){
        checkArgument(Files.isRegularFile(filePath), "This is not a regular file: " + filePath);

        try(BioentityPropertiesStream bioentityBioentityPropertiesStream =
                    bioentityPropertiesStreamBuilder.forPath(filePath).build()){

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


    public void deleteAll() {
        try {
            solrServer.deleteByQuery("*:*");
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public void commit() {
        try {
            solrServer.commit();
            solrServer.optimize();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
