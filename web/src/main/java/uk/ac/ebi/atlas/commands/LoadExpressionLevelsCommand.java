package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptProfile;
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
    private RankAndConvertTopObjectsCommand rankAndConvertTopObjectsCommand;

    @Inject
    public LoadExpressionLevelsCommand(TranscriptProfilesInputStreamBuilder inputStreamBuilder,
                                       RankAndConvertTopObjectsCommand rankAndConvertTopObjectsCommand) {
        this.inputStreamBuilder = inputStreamBuilder;

        this.rankAndConvertTopObjectsCommand = rankAndConvertTopObjectsCommand;
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

        try (ObjectInputStream<TranscriptProfile> inputStream = inputStreamBuilder.createFor(experimentAccession)) {

            List<TranscriptExpression> transcriptExpressionsRanking = rankAndConvertTopObjectsCommand.apply(inputStream);

            return transcriptExpressionsRanking;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public LoadExpressionLevelsCommand setRankingSize(int rankingSize) {
        rankAndConvertTopObjectsCommand.setRankingSize(rankingSize);
        return this;
    }

    public LoadExpressionLevelsCommand setRpkmCutOff(double rpkmCutOffValue) {
        this.inputStreamBuilder.setRpkmCutOff(rpkmCutOffValue);
        return this;
    }
}
