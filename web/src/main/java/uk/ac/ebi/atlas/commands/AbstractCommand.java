package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

public abstract class AbstractCommand<T, K extends GeneProfile> implements Command<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractCommand.class);

    private SolrClient solrClient;

    private RequestContext requestContext;

    protected AbstractCommand(RequestContext requestContext){
        this.requestContext = requestContext;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public T execute(String experimentAccession) throws GenesNotFoundException {
        Set<String> selectedGeneIds = getSelectedGeneIds();

        try (ObjectInputStream<K> inputStream = new GeneProfileInputStreamFilter(createInputStream(experimentAccession), selectedGeneIds, requestContext.getSelectedQueryFactors())) {

            return execute(inputStream, requestContext);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    Set<String> getSelectedGeneIds() throws GenesNotFoundException {
        Set<String> selectedGeneIds = null;

        if (StringUtils.isNotBlank(requestContext.getGeneQuery())) {

            selectedGeneIds = solrClient.findGeneIds(requestContext.getGeneQuery(), requestContext.isExactMatch(), requestContext.getFilteredBySpecies());

        }
        return selectedGeneIds;
    }

    protected abstract ObjectInputStream<K> createInputStream(String experimentAccession);

    protected abstract T execute(ObjectInputStream<K> inputStream, RequestContext requestContext);
}
