package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.io.IOException;

public abstract class ProfilesHeatMap<P extends Profile, C extends RequestContext, L extends GeneProfilesList<P>, O
        extends ProfileStreamOptions<T>, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilesHeatMap.class);

    private ProfileStreamPipelineBuilder<P, O, T> pipelineBuilder;
    private RankProfilesFactory<P, L, O> rankProfilesFactory;

    protected ProfilesHeatMap(ProfileStreamPipelineBuilder<P, O, T> pipelineBuilder, RankProfilesFactory<P, L, O>
            rankProfilesFactory) {
        this.pipelineBuilder = pipelineBuilder;
        this.rankProfilesFactory = rankProfilesFactory;
    }

    protected L fetch(ObjectInputStream<P> inputStream, O options, Optional<GeneQueryResponse> geneQueryResponse)  {
        int maxSize = options.getHeatmapMatrixSize();

        RankProfiles<P, L> rankProfiles = rankProfilesFactory.create(options);

        try (ObjectInputStream<P> source = inputStream) {

            Iterable<P> profiles = new IterableObjectInputStream<>(source);

            Iterable<P> profilesPipeline = pipelineBuilder.build(profiles, options,geneQueryResponse);

            return rankProfiles.rank(profilesPipeline, maxSize);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
