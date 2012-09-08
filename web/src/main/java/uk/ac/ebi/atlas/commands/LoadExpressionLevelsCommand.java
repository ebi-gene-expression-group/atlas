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

@Named("loadExpressionLevels")
@Scope("prototype")
public class LoadExpressionLevelsCommand implements Function<String, List<ExpressionLevel>> {

    private static final Logger logger = Logger.getLogger(LoadExpressionLevelsCommand.class);

    private ExpressionLevelInputStreamBuilder inputStreamBuilder;
    RankTopObjectsCommand<ExpressionLevel> rankTopObjectsCommand;

    @Inject
    public LoadExpressionLevelsCommand(ExpressionLevelInputStreamBuilder inputStreamBuilder, RankTopObjectsCommand<ExpressionLevel> rankTopObjectsCommand) {
        this.inputStreamBuilder = inputStreamBuilder;
        this.rankTopObjectsCommand = rankTopObjectsCommand;
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

        try (ObjectInputStream<ExpressionLevel> inputStream = inputStreamBuilder.createFor(experimentAccession)){

            List<ExpressionLevel> expressionLevelsRanking = rankTopObjectsCommand.apply(inputStream);

            return expressionLevelsRanking;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public LoadExpressionLevelsCommand setRankingSize(int rankingSize){
        rankTopObjectsCommand.setRankingSize(rankingSize);
        return this;
    }

    public LoadExpressionLevelsCommand setDataFileURL(String dataFileURL) {
        this.inputStreamBuilder.setDataFileURL(dataFileURL);
        return this;
    }

    public LoadExpressionLevelsCommand setRpkmCutOff(double rpkmCutOffValue) {
        this.inputStreamBuilder.setRpkmCutOff(rpkmCutOffValue);
        return this;
    }
}
