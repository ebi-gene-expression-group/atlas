package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMapSource;
import uk.ac.ebi.atlas.profiles.baseline.*;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class BaselineProfilesHeatMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesHeatMap.class);

    private ProfilesHeatMapSource<BaselineProfile, BaselineProfilesList, BaselineProfileStreamOptions, Factor>
            profilesHeatmapSource;
    @Inject
    public BaselineProfilesHeatMap(RankBaselineProfilesFactory rankProfilesFactory,
                                   @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory) {
        profilesHeatmapSource = new ProfilesHeatMapSource<>( rankProfilesFactory,
                inputStreamFactory,new BaselineProfileStreamFilters());
    }

    public BaselineProfilesList fetch(BaselineProfileStreamOptions options,
                                      Optional<GeneQueryResponse> geneQueryResponse) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        BaselineProfilesList profiles = profilesHeatmapSource.fetch(options,geneQueryResponse);

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
