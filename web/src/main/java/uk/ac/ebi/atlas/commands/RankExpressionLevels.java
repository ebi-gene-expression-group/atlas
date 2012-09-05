package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.streams.ExpressionLevelInputStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named("rankExpressionLevels")
@Scope("prototype")
public class RankExpressionLevels implements Function<String, List<ExpressionLevel>> {

    private static final Logger logger = Logger.getLogger(RankExpressionLevels.class);

    private int rankingSize = 10;

    private ExpressionLevelInputStreamBuilder inputStreamBuilder;
    RankStreamingObjects<ExpressionLevel> rankStreamingObjectsCommand;

    @Inject
    public RankExpressionLevels(ExpressionLevelInputStreamBuilder inputStreamBuilder, RankStreamingObjects<ExpressionLevel> rankStreamingObjects) {
        this.inputStreamBuilder = inputStreamBuilder;
        this.rankStreamingObjectsCommand = rankStreamingObjects.setRankSize(rankingSize);
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

        ObjectInputStream inputStream = inputStreamBuilder.createFor(experimentAccession);

        List<ExpressionLevel> expressionLevelsRanking = rankStreamingObjectsCommand.apply(inputStream);

        try {

            inputStream.close();
            return expressionLevelsRanking;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }


    public RankExpressionLevels setRankingSize(int rankingSize) {
        this.rankingSize = rankingSize;
        return this;
    }

    public RankExpressionLevels setDataFileURL(String dataFileURL) {
        this.inputStreamBuilder.setDataFileURL(dataFileURL);
        return this;
    }
}
