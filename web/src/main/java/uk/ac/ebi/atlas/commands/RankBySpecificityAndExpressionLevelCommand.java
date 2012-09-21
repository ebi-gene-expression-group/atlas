package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.*;

import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

@Named("rankBySpecificityAndExpressionLevel")
@Scope("prototype")
public class RankBySpecificityAndExpressionLevelCommand implements Function<ObjectInputStream<TranscriptProfile>, TranscriptExpressionsList> {

    private static final int DEFAULT_SIZE = 100;

    private int rankingSize = DEFAULT_SIZE;

    public RankBySpecificityAndExpressionLevelCommand() {
    }

    @Override
    public TranscriptExpressionsList apply(ObjectInputStream<TranscriptProfile> objectStream) {

        Comparator<TranscriptExpression> reverseSpecificityComparator = Ordering.from(new TranscriptSpecificityComparator()).reverse();

        Queue<TranscriptExpression> topTenObjects = MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(rankingSize).create();

        TranscriptProfile transcriptProfile;

        while ((transcriptProfile = objectStream.readNext()) != null) {
            for (Expression expression : transcriptProfile) {
                TranscriptExpression transcriptExpression =
                        new TranscriptExpression(transcriptProfile.getTranscriptId(), expression, transcriptProfile.getTranscriptSpecificity());
                topTenObjects.add(transcriptExpression);

            }
        }

        TranscriptExpressionsList list = new TranscriptExpressionsList(topTenObjects);

        Collections.sort(list, reverseSpecificityComparator);

        return list;
    }

    public RankBySpecificityAndExpressionLevelCommand setRankingSize(int rankingSize) {
        checkArgument(rankingSize > 0, "rankingSize must be greater then zero");
        this.rankingSize = rankingSize;
        return this;
    }

}
