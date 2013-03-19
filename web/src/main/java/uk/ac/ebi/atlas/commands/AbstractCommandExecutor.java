package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

public abstract class AbstractCommandExecutor<T, K> implements CommandExecutor<T> {

    protected static final Logger logger = Logger.getLogger(AbstractCommandExecutor.class);

    private SolrClient solrClient;

    protected InputStreamFactory inputStreamFactory;

    @Inject
    public void setInputStreamFactory(InputStreamFactory inputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public T execute(String experimentAccession) throws GenesNotFoundException {
        Set<String> selectedGeneIds = getSelectedGeneIds();

        try (ObjectInputStream<K> inputStream = new GeneProfileInputStreamFilter(createInputStream(experimentAccession), selectedGeneIds, getRequestContext().getSelectedQueryFactors())) {

            return execute(inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    private Set<String> getSelectedGeneIds() throws GenesNotFoundException {
        Set<String> selectedGeneIds = null;

        if (StringUtils.isNotBlank(getRequestContext().getGeneQuery())) {

            selectedGeneIds = solrClient.findGeneIds(getRequestContext().getGeneQuery(), getRequestContext().getFilteredBySpecies());

        }
        return selectedGeneIds;
    }

    protected abstract ObjectInputStream<K> createInputStream(String experimentAccession);

    protected abstract RequestContext getRequestContext();

    protected abstract T execute(ObjectInputStream<K> inputStream);
}
