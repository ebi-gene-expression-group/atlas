package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionLevelsInputStream;
import uk.ac.ebi.atlas.services.MageTabInvestigation;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("rankExpressionLevels")
public class RankExpressionLevels implements Function<String, List<ExpressionLevel>> {

    private static final Logger logger = Logger.getLogger(RankExpressionLevels.class);

    private static final Map<String, List<ExpressionLevel>> experiments = new HashMap<>();

    private int rankingSize = 10;

    @Override
    public List<ExpressionLevel> apply(String experimentAccession) throws IllegalStateException{
        List<ExpressionLevel> topTenExpressionLevels = experiments.get(experimentAccession);
        if (topTenExpressionLevels == null) {
            topTenExpressionLevels = loadTopTenExpressionLevels(experimentAccession);
            if (topTenExpressionLevels == null){
                throw new IllegalStateException("Data not found for experiment: " + experimentAccession);
            }
        }
        return topTenExpressionLevels;
    }

    private List<ExpressionLevel> loadTopTenExpressionLevels(String experimentAccession) {

        try {
            //ToDo: build URL properly...
            URL mageTabURL = buildURL("..." + experimentAccession + "...");

            List<ExperimentRun> experimentRuns =  Lists.newArrayList(MageTabInvestigation.parse(mageTabURL)
                    .extractExperimentRuns());
            //ToDo: build URL properly...
            URL dataFileURL = buildURL("..." + experimentAccession + "...");

            Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

            ExpressionLevelsInputStream expressionLevelStream = new ExpressionLevelsInputStream(dataFileReader, experimentRuns);

            List<ExpressionLevel> expressionLevelsRanking = rankExpressionLevels(expressionLevelStream);

            return expressionLevelsRanking;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            //ToDo: find a good exception message !
            throw new IllegalArgumentException("...");
        }
    }

    protected List<ExpressionLevel> rankExpressionLevels(ExpressionLevelsInputStream expressionLevelReader) {
        RankStreamingObjects<ExpressionLevel> rankStreamingObjectsCommand = new RankStreamingObjects<ExpressionLevel>(rankingSize);
        return rankStreamingObjectsCommand.apply(expressionLevelReader);
    }

    public RankExpressionLevels setRankingSize(int rankingSize){
        this.rankingSize = rankingSize;
        return this;
    }

    private URL buildURL(String experimentAccession) throws IOException{
        return new URL(experimentAccession);
    }

}
