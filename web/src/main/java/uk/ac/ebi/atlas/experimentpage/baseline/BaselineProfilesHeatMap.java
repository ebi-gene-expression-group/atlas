package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMap;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class BaselineProfilesHeatMap extends ProfilesHeatMap<BaselineProfile, BaselineProfilesList, BaselineProfileStreamOptions, Factor> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesHeatMap.class);

    private BaselineProfileInputStreamFactory inputStreamFactory;

    @Inject
    public BaselineProfilesHeatMap(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                   RankBaselineProfilesFactory rankProfilesFactory,
                                   @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
    }

    public BaselineProfilesList fetch(BaselineProfileStreamOptions options,
                                      Optional<GeneQueryResponse> geneQueryResponse) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        ObjectInputStream<BaselineProfile> inputStream = inputStreamFactory.create(options);
        BaselineProfilesList profiles = super.fetch(inputStream, options,geneQueryResponse);

        stopwatch.stop();

        LOGGER.debug(
                "<fetch> for [{}] (asGeneSets={}) took {} secs",
                geneQueryResponse.isPresent()? geneQueryResponse.get().getAllGeneIds().size() :0, geneQueryResponse.isPresent(),
                stopwatch
                .elapsed(TimeUnit
                .MILLISECONDS) /
                        1000D);

        return profiles;
    }

}
