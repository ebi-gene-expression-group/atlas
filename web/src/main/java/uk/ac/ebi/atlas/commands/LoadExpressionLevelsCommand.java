package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpressionLevel;
import uk.ac.ebi.atlas.streams.ExpressionLevelInputStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named("loadExpressionLevels")
@Scope("prototype")
public class LoadExpressionLevelsCommand implements Function<String, List<TranscriptExpressionLevel>> {

    private static final Logger logger = Logger.getLogger(LoadExpressionLevelsCommand.class);

    private ExpressionLevelInputStreamBuilder inputStreamBuilder;
    RankTopObjectsCommand<TranscriptExpressionLevel> rankTopObjectsCommand;

    @Inject
    public LoadExpressionLevelsCommand(ExpressionLevelInputStreamBuilder inputStreamBuilder, RankTopObjectsCommand<TranscriptExpressionLevel> rankTopObjectsCommand) {
        this.inputStreamBuilder = inputStreamBuilder;
        this.rankTopObjectsCommand = rankTopObjectsCommand;
    }

    @Override
    public List<TranscriptExpressionLevel> apply(String experimentAccession) throws IllegalStateException {
        List<TranscriptExpressionLevel> topTenTranscriptExpressionLevels = loadTopTenExpressionLevels(experimentAccession);
        if (topTenTranscriptExpressionLevels == null) {
            throw new IllegalStateException("Data not found for experiment: " + experimentAccession);
        }
        return topTenTranscriptExpressionLevels;
    }


    private List<TranscriptExpressionLevel> loadTopTenExpressionLevels(String experimentAccession) {

        try (ObjectInputStream<TranscriptExpressionLevel> inputStream = inputStreamBuilder.createFor(experimentAccession)) {

            List<TranscriptExpressionLevel> transcriptExpressionLevelsRanking = rankTopObjectsCommand.apply(inputStream);

            return transcriptExpressionLevelsRanking;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public LoadExpressionLevelsCommand setRankingSize(int rankingSize) {
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
