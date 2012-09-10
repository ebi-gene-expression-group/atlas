package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.streams.TranscriptProfilesInputStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named("loadExpressionLevels")
@Scope("prototype")
public class LoadExpressionLevelsCommand implements Function<String, List<TranscriptExpression>> {

    private static final Logger logger = Logger.getLogger(LoadExpressionLevelsCommand.class);

    private TranscriptProfilesInputStreamBuilder inputStreamBuilder;
    RankTopObjectsCommand<TranscriptExpression> rankTopObjectsCommand;

    @Inject
    public LoadExpressionLevelsCommand(TranscriptProfilesInputStreamBuilder inputStreamBuilder, RankTopObjectsCommand<TranscriptExpression> rankTopObjectsCommand) {
        this.inputStreamBuilder = inputStreamBuilder;
        this.rankTopObjectsCommand = rankTopObjectsCommand;
    }

    @Override
    public List<TranscriptExpression> apply(String experimentAccession) throws IllegalStateException {
        List<TranscriptExpression> topTenTranscriptExpressions = loadTopTenExpressions(experimentAccession);
        if (topTenTranscriptExpressions == null) {
            throw new IllegalStateException("Data not found for experiment: " + experimentAccession);
        }
        return topTenTranscriptExpressions;
    }


    private List<TranscriptExpression> loadTopTenExpressions(String experimentAccession) {

        try (ObjectInputStream<TranscriptExpression> inputStream = inputStreamBuilder.createFor(experimentAccession)) {

            List<TranscriptExpression> transcriptExpressionsRanking = rankTopObjectsCommand.apply(inputStream);

            return transcriptExpressionsRanking;

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
