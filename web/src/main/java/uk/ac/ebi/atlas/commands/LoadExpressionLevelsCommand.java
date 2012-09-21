package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptExpressionsList;
import uk.ac.ebi.atlas.streams.TranscriptProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Named("loadExpressionLevels")
@Scope("prototype")
public class LoadExpressionLevelsCommand implements Function<String, List<TranscriptExpression>> {

    private static final Logger logger = Logger.getLogger(LoadExpressionLevelsCommand.class);

    @Value("#{configuration['magetab.test.datafile.url']}")
    private String dataFileURL;

    private LoadingCache<String, List<ExperimentRun>> experiments;
    private RankBySpecificityAndRpkmCommand rankBySpecificityObjectsCommand;
    private Double rpkmCutOff;

    @Inject
    public LoadExpressionLevelsCommand(LoadingCache<String, List<ExperimentRun>> experiments, RankBySpecificityAndRpkmCommand rankBySpecificityObjectsCommand) {

        this.experiments = experiments;

        this.rankBySpecificityObjectsCommand = rankBySpecificityObjectsCommand;
    }

    @Override
    public TranscriptExpressionsList apply(String experimentAccession) throws IllegalStateException {

        TranscriptExpressionsList topTenTranscriptExpressions = loadTopTenExpressions(experimentAccession);
        return topTenTranscriptExpressions;

    }

    //This is a bit smelly, this cache could be directly used by TranscriptProfilesInputStrea.Builder
    //rather then being used here just to bounce the cached data back to TranscriptProfilesInputStrea.Builder
    List<ExperimentRun> getExperimentRuns(String experimentAccession) {

        try {

            return experiments.get(experimentAccession);

        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading MAGE TAB file: " + e.getMessage(), e.getCause());
        }

    }


    TranscriptExpressionsList loadTopTenExpressions(String experimentAccession) {

        List<ExperimentRun> experimentRuns = getExperimentRuns(experimentAccession);

        try (TranscriptProfilesInputStream objectInputStream = TranscriptProfilesInputStream.forFile(dataFileURL)
                .withExperimentRuns(experimentRuns)
                .withRpkmCutOff(rpkmCutOff).create()) {

            return rankBySpecificityObjectsCommand.apply(objectInputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public LoadExpressionLevelsCommand setRankingSize(int rankingSize) {
        rankBySpecificityObjectsCommand.setRankingSize(rankingSize);
        return this;
    }

    public LoadExpressionLevelsCommand setRpkmCutOff(double rpkmCutOff) {
        this.rpkmCutOff = rpkmCutOff;
        return this;
    }
}
