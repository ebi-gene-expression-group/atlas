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

package uk.ac.ebi.atlas.solr.admin.index;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Named
@Scope("prototype")
public class BioentityIndex {

    private static final Logger LOGGER = Logger.getLogger(BioentityIndex.class);

    private BioentityIndexMonitor bioentityIndexMonitor;
    private final BioentityPropertiesStreamBuilder bioentityPropertiesStreamBuilder;

    private SolrServer solrServer;

    @Inject
    public BioentityIndex(BioentityIndexMonitor bioentityIndexMonitor, SolrServer solrServer, BioentityPropertiesStreamBuilder bioentityPropertiesStreamBuilder) {
        this.bioentityIndexMonitor = bioentityIndexMonitor;
        this.bioentityPropertiesStreamBuilder = bioentityPropertiesStreamBuilder;
        this.solrServer = solrServer;
    }

    public void indexAll(final DirectoryStream<Path> directoryStream) {
        try {
            indexDirectory(directoryStream);

            solrServer.commit();

            optimize();

            bioentityIndexMonitor.stop();

        } catch (IOException | SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }


    }

    void indexDirectory(DirectoryStream<Path> bioentityPropertiesDirectoryStream) throws IOException, SolrServerException {
        try (DirectoryStream<Path> directoryStream = bioentityPropertiesDirectoryStream) {

            for (Path path : directoryStream) {

                if (Files.isDirectory(path)) {
                    indexDirectory(Files.newDirectoryStream(path));
                } else if (Files.isRegularFile(path)) {
                    indexFile(path);
                }
            }
        }
    }

    void indexFile(Path filePath) throws IOException, SolrServerException {

        if (filePath.toString().endsWith(".tsv")) {

            try (BioentityPropertiesStream bioentityBioentityPropertiesStream =
                         bioentityPropertiesStreamBuilder.forPath(filePath).build()) {

                LOGGER.info("<indexFile> streaming started for file: " + filePath);

                bioentityIndexMonitor.processing(filePath);

                Collection<BioentityProperty> documents;

                while ((documents = bioentityBioentityPropertiesStream.next()) != null) {

                    solrServer.addBeans(documents);

                }

                bioentityIndexMonitor.completed(filePath);

            }


        }

    }


    public void deleteAll() {
        try {
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void optimize() {
        try {

            solrServer.optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
