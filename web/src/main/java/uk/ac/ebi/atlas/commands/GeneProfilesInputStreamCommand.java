package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.IndexClient;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

public abstract class GeneProfilesInputStreamCommand<T> implements Function<String, T> {
    private static final Logger logger = Logger.getLogger(RankGeneProfilesCommand.class);

    private RequestPreferences requestPreferences;

    private GeneProfilesInputStream.Builder geneProfileInputStreamBuilder;

    private IndexClient indexClient;

    private ExperimentsCache experimentsCache;

    @Inject
    protected void setGeneProfileInputStreamBuilder(GeneProfilesInputStream.Builder geneProfileInputStreamBuilder) {
        this.geneProfileInputStreamBuilder = geneProfileInputStreamBuilder;
    }

    @Inject
    public void setIndexClient(IndexClient indexClient) {
        this.indexClient = indexClient;
    }

    @Inject
    public void setExperimentsCache(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    protected ObjectInputStream<GeneProfile> buildGeneProfilesInputStream(String experimentAccession) {

        ObjectInputStream<GeneProfile> geneProfileInputStream = geneProfileInputStreamBuilder.forExperiment(experimentAccession)
                .withCutoff(requestPreferences.getCutoff()).create();

        Set<String> geneIDs = indexClient.findGeneIds(requestPreferences.getGeneIDs(),
                experimentsCache.getExperiment(experimentAccession).getSpecie());

        return new GeneProfileInputStreamFilter(geneProfileInputStream, geneIDs, requestPreferences.getOrganismParts());

    }

    public void setRequestPreferences(RequestPreferences requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    public T apply(String experimentAccession){
        try (ObjectInputStream<GeneProfile> inputStream = buildGeneProfilesInputStream(experimentAccession)){

            return apply(requestPreferences, inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    protected abstract T apply(RequestPreferences requestPreferences
                                , ObjectInputStream<GeneProfile> inputStream) throws IOException;


}
