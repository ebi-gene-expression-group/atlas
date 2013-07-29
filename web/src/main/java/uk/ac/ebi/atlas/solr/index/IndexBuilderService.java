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

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

public abstract class IndexBuilderService {
    final private Logger log = LoggerFactory.getLogger(this.getClass());
    private SolrServer solrServer;

    final public SolrServer getSolrServer() {
        return solrServer;
    }

    @Inject
    final public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public interface ProgressUpdater {
        void update(String progress);
    }

    /**
     * Build the index for this particular IndexBuilderService implementation. Once the index has been built, this
     * method will automatically commit any changes and release any resources held by the SOLR server.
     *
     * @param command         command
     * @param progressUpdater listener for passing progress updates
     * @throws IndexBuilderException if the is a problem whilst generating the index
     */
    final public void build(final IndexBuilderCommand command, final ProgressUpdater progressUpdater) throws IndexBuilderException {
        command.visit(new IndexBuilderCommandVisitor() {
            public void process(IndexAllCommand cmd) throws IndexBuilderException {
                processCommand(cmd, progressUpdater);
                finalizeCommand();
            }
        });
    }

    final void deleteAll() throws IndexBuilderException {
        try {
            getSolrServer().deleteByQuery("*:*");
        } catch (IOException e) {
            throw new IndexBuilderException(e);
        } catch (SolrServerException e) {
            throw new IndexBuilderException(e);
        }

    }

    final void commit() throws IndexBuilderException {
        try {
            if (getSolrServer() != null)
                getSolrServer().commit();
        } catch (IOException e) {
            throw new IndexBuilderException("Cannot commit changes to the SOLR server", e);
        } catch (SolrServerException e) {
            throw new IndexBuilderException("Cannot commit changes to the SOLR server - server threw exception", e);
        }

    }

    final void optimize() throws IndexBuilderException {
        try {
            if (getSolrServer() != null)
                getSolrServer().optimize();
        } catch (IOException e) {
            throw new IndexBuilderException("Cannot commit changes to the SOLR server", e);
        } catch (SolrServerException e) {
            throw new IndexBuilderException("Cannot commit changes to the SOLR server - server threw exception", e);
        }

    }

    void processCommand(IndexAllCommand indexAll, ProgressUpdater progressUpdater) throws IndexBuilderException {
        deleteAll();
    }

    void finalizeCommand() throws IndexBuilderException {
        commit();
        optimize();
    }

    /**
     * Returns index name, which this service builds
     *
     * @return text string
     */
    public abstract String getName();
}
