package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.PrescribedOrderProfileSelection;
import uk.ac.ebi.atlas.profiles.stream.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaselineProfilesHeatMap<StreamOptions extends BaselineProfileStreamOptions<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesHeatMap.class);

    private final BaselineProfileStreamFactory<StreamOptions> baselineProfileStreamFactory;
    public BaselineProfilesHeatMap(BaselineProfileStreamFactory<StreamOptions> inputStreamFactory) {
        this.baselineProfileStreamFactory = inputStreamFactory;
    }

    public BaselineProfilesList fetch(BaselineExperiment experiment, StreamOptions options,
                                      GeneQueryResponse geneQueryResponse, boolean asGeneSets) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        BaselineProfilesList profiles = baselineProfileStreamFactory.select(experiment, options,
                new BaselineProfileStreamTransforms(options, geneQueryResponse, asGeneSets),
                new MinMaxProfileRanking<>(BaselineProfileComparator.create(options), new BaselineProfilesListBuilder()));

        stopwatch.stop();

        LOGGER.debug("<fetch> for [{}] (asGeneSets={}) took {} secs",
                geneQueryResponse.getAllGeneIds().size(), asGeneSets,
                stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return profiles;
    }

    public BaselineProfilesList fetchInPrescribedOrder(List<String> geneNamesInOrder,BaselineExperiment experiment, StreamOptions options,
                                                        GeneQueryResponse geneQueryResponse, boolean asGeneSets) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        BaselineProfilesList profiles = baselineProfileStreamFactory.select(experiment, options,
                new BaselineProfileStreamTransforms(options, geneQueryResponse, asGeneSets),
                new PrescribedOrderProfileSelection<>(geneNamesInOrder, new BaselineProfilesListBuilder()));

        stopwatch.stop();

        LOGGER.debug("<fetch> for [{}] (asGeneSets={}) took {} secs",
                geneQueryResponse.getAllGeneIds().size(), asGeneSets,
                stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        profiles.setTotalResultCount(profiles.size());
        return profiles;
    }

}
