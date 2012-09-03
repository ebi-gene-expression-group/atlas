package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.loader.ExperimentLoader;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named("rankExpressionLevels")
public class RankExpressionLevels implements Function<String, List<ExpressionLevel>> {

    private static final Logger logger = Logger.getLogger(RankExpressionLevels.class);

    private int rankingSize = 10;

    private ExperimentLoader experimentLoader;

    @Inject
    public RankExpressionLevels(ExperimentLoader loader) {
        this.experimentLoader = loader;
    }

    @Override
    public List<ExpressionLevel> apply(String experimentAccession) throws IllegalStateException {
        List<ExpressionLevel> topTenExpressionLevels = loadTopTenExpressionLevels(experimentAccession);
        if (topTenExpressionLevels == null) {
            throw new IllegalStateException("Data not found for experiment: " + experimentAccession);
        }
        return topTenExpressionLevels;
    }


    private List<ExpressionLevel> loadTopTenExpressionLevels(String experimentAccession) {
        ObjectInputStream inputStream = experimentLoader.getExpressionLevelsInputStream(experimentAccession);
        List<ExpressionLevel> expressionLevelsRanking = rankExpressionLevels(inputStream);
        try {
            inputStream.close();
            return expressionLevelsRanking;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }


    protected List<ExpressionLevel> rankExpressionLevels(ObjectInputStream expressionLevelReader) {
        RankStreamingObjects<ExpressionLevel> rankStreamingObjectsCommand = new RankStreamingObjects<ExpressionLevel>(rankingSize);
        return rankStreamingObjectsCommand.apply(expressionLevelReader);
    }

    public RankExpressionLevels setRankingSize(int rankingSize) {
        this.rankingSize = rankingSize;
        return this;
    }

    public RankExpressionLevels setDataFileURL(String dataFileURL) {
        this.experimentLoader.setDataFileURL(dataFileURL);
        return this;
    }
}
