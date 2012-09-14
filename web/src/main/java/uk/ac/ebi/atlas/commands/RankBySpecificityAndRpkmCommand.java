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

@Named("rankBySpecificityAndRpkm")
@Scope("prototype")
public class RankBySpecificityAndRpkmCommand implements Function<ObjectInputStream<TranscriptProfile>, List<TranscriptExpression>> {

    private static final int DEFAULT_SIZE = 100;

    private int rankingSize = DEFAULT_SIZE;

    public RankBySpecificityAndRpkmCommand() {
    }

    @Override
    public List<TranscriptExpression>  apply(ObjectInputStream<TranscriptProfile> objectStream) {
        Queue<TranscriptExpression> topTenObjects = MinMaxPriorityQueue.orderedBy(new ReverseOrderComparator()).maximumSize(rankingSize).create();

        TranscriptProfile transcriptProfile;

        while ((transcriptProfile = objectStream.readNext()) != null) {
            for (ExpressionLevel expressionLevel : transcriptProfile) {
                TranscriptExpression transcriptExpression =
                        new TranscriptExpression(transcriptProfile.getTranscriptId(), expressionLevel, transcriptProfile.getTranscriptSpecificity());
                topTenObjects.add(transcriptExpression);

            }
        }

        return Ordering.from(new TranscriptSpecificityComparator()).reverse().sortedCopy(topTenObjects);
    }

    public RankBySpecificityAndRpkmCommand setRankingSize(int rankingSize) {
        this.rankingSize = rankingSize;
        return this;
    }

    class ReverseOrderComparator implements Comparator<TranscriptExpression> {

        private TranscriptSpecificityComparator transcriptSpecificityComparator = new TranscriptSpecificityComparator();

        @Override
        public int compare(TranscriptExpression object, TranscriptExpression other) {
            return transcriptSpecificityComparator.compare(other, object);
        }
    }
}
