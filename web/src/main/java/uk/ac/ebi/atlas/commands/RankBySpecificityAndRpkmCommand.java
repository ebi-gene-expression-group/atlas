package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptProfile;
import uk.ac.ebi.atlas.model.TranscriptSpecificityComparator;

import javax.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

@Named("rankBySpecificityAndRpkm")
@Scope("prototype")
public class RankBySpecificityAndRpkmCommand implements Function<ObjectInputStream<TranscriptProfile>, List<TranscriptExpression>> {

    private static final int DEFAULT_SIZE = 100;

    private int rankingSize = DEFAULT_SIZE;

    public RankBySpecificityAndRpkmCommand() {
    }

    @Override
    public List<TranscriptExpression> apply(ObjectInputStream<TranscriptProfile> objectStream) {
        Comparator<TranscriptExpression> reverseSpecificityComparator = Ordering.from(new TranscriptSpecificityComparator()).reverse();

        Queue<TranscriptExpression> topTenObjects = MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(rankingSize).create();

        TranscriptProfile transcriptProfile;

        while ((transcriptProfile = objectStream.readNext()) != null) {
            for (ExpressionLevel expressionLevel : transcriptProfile) {
                TranscriptExpression transcriptExpression =
                        new TranscriptExpression(transcriptProfile.getTranscriptId(), expressionLevel, transcriptProfile.getTranscriptSpecificity());
                topTenObjects.add(transcriptExpression);

            }
        }
        return Ordering.from(reverseSpecificityComparator).sortedCopy(topTenObjects);
    }

    public RankBySpecificityAndRpkmCommand setRankingSize(int rankingSize) {
        checkArgument(rankingSize > 0, "rankingSize must be greater then zero");
        this.rankingSize = rankingSize;
        return this;
    }

}
