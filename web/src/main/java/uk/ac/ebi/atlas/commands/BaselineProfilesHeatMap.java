package uk.ac.ebi.atlas.commands;

import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
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

    private static final Logger LOGGER = Logger.getLogger(BaselineProfilesHeatMap.class);

    private BaselineProfileInputStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public BaselineProfilesHeatMap(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                   RankBaselineProfilesFactory rankProfilesFactory,
                                   BaselineProfileInputStreamFactory inputStreamFactory,
                                   LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public BaselineProfilesList fetch(BaselineRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());

        Stopwatch stopwatch = Stopwatch.createStarted();

        ObjectInputStream<BaselineProfile> inputStream = inputStreamFactory.create(requestContext);
        BaselineProfilesList profiles = super.fetch(inputStream, requestContext);

        stopwatch.stop();
        LOGGER.debug(String.format("<fetch> for [%s] (geneSetMatch=%s) took %s secs", requestContext.getGeneQuery(), requestContext.isGeneSetMatch(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return profiles;
    }

    public BaselineProfilesList fetch(BaselineProfileStreamOptions options) throws GenesNotFoundException {
        ObjectInputStream<BaselineProfile> inputStream = inputStreamFactory.create(options);
        return super.fetch(inputStream, options);
    }

}
