package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMap;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class BaselineProfilesHeatMap extends ProfilesHeatMap<BaselineProfile, BaselineRequestContext, BaselineProfilesList, BaselineProfileStreamOptions> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesHeatMap.class);

    private BaselineProfileInputStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public BaselineProfilesHeatMap(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                   RankBaselineProfilesFactory rankProfilesFactory,
                                   @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory,
                                   LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public BaselineProfilesList fetch(BaselineRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());

        LOGGER.debug(
            "<fetch> for {} genes (asGeneSets={}) for gene query [{}]",
            requestContext.getSelectedGeneIDs().size(), requestContext.asGeneSets(), requestContext.getGeneQuery());

        return fetch((BaselineProfileStreamOptions)requestContext);
    }

    public BaselineProfilesList fetch(BaselineProfileStreamOptions options) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        ObjectInputStream<BaselineProfile> inputStream = inputStreamFactory.create(options);
        BaselineProfilesList profiles = super.fetch(inputStream, options);

        stopwatch.stop();

        LOGGER.debug(
            "<fetch> for [{}] (asGeneSets={}) took {} secs",
            options.getSelectedGeneIDs().size(), options.asGeneSets(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return profiles;
    }

}
