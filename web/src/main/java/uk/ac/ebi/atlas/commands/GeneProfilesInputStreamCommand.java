package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.commons.lang.StringUtils;
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
import java.util.HashSet;
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

    protected ObjectInputStream<GeneProfile> buildGeneProfilesInputStream(String experimentAccession, Set<String> geneIDs) {

        ObjectInputStream<GeneProfile> geneProfileInputStream = geneProfileInputStreamBuilder.forExperiment(experimentAccession)
                .withCutoff(requestPreferences.getCutoff()).create();


        return new GeneProfileInputStreamFilter(geneProfileInputStream, geneIDs, requestPreferences.getOrganismParts());

    }

    protected ObjectInputStream<GeneProfile> buildGeneProfilesInputStream(String experimentAccession) {
        return buildGeneProfilesInputStream(experimentAccession, null);

    }

    public void setRequestPreferences(RequestPreferences requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    public T apply(String experimentAccession) {

        Set<String> geneIDs = new HashSet<>();

        String geneProperties = requestPreferences.getGeneIDsString();

        if (!StringUtils.isEmpty(geneProperties)) {

            String organism = experimentsCache.getExperiment(experimentAccession).getSpecie();
            geneIDs.addAll(indexClient.findGeneIds(geneProperties, organism));

            if (geneIDs.isEmpty()) {
                return returnEmpty();
            }
        }

        try (ObjectInputStream<GeneProfile> inputStream = buildGeneProfilesInputStream(experimentAccession, geneIDs)) {

            return apply(requestPreferences, inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    protected abstract T apply(RequestPreferences requestPreferences
            , ObjectInputStream<GeneProfile> inputStream) throws IOException;

    protected abstract T returnEmpty();
}
