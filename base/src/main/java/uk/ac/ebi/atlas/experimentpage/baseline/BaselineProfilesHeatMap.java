package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.SelectProfiles;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.PrescribedOrderProfileSelection;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMapSource;

import javax.inject.Named;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class BaselineProfilesHeatMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesHeatMap.class);

    private final RankBaselineProfilesFactory rankProfilesFactory;
    private ProfilesHeatMapSource<BaselineExperiment, OldBaselineProfile, BaselineProfilesList, BaselineProfileStreamOptions, Factor>
            profilesHeatmapSource;

    public BaselineProfilesHeatMap(RankBaselineProfilesFactory rankProfilesFactory,
                                   @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory) {
        this.rankProfilesFactory = rankProfilesFactory;
        profilesHeatmapSource = new ProfilesHeatMapSource<>(inputStreamFactory, new BaselineProfileStreamFilters());
    }

    public BaselineProfilesList fetch(BaselineExperiment experiment, BaselineProfileStreamOptions options,
                                      GeneQueryResponse geneQueryResponse, boolean asGeneSets) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        BaselineProfilesList profiles =
                profilesHeatmapSource.fetch(experiment,options, rankProfilesFactory.create(options), geneQueryResponse,
                        asGeneSets);

        stopwatch.stop();

        LOGGER.debug("<fetch> for [{}] (asGeneSets={}) took {} secs",
                geneQueryResponse.getAllGeneIds().size(), asGeneSets,
                stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return profiles;
    }

    public BaselineProfilesList fetchInPrescribedOrder(List<String> geneNamesInOrder,BaselineExperiment experiment, BaselineProfileStreamOptions options,
                                                        GeneQueryResponse geneQueryResponse, boolean asGeneSets) {

        SelectProfiles<OldBaselineProfile, BaselineProfilesList> s =
                new PrescribedOrderProfileSelection<>(geneNamesInOrder, new BaselineProfilesListBuilder());

        return profilesHeatmapSource.fetch(experiment,options, s,geneQueryResponse, asGeneSets);
    }

}
