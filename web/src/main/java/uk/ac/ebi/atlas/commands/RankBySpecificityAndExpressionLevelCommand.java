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
public class RankBySpecificityAndExpressionLevelCommand implements Function<ObjectInputStream<GeneProfile>, GeneExpressionsList> {

    private static final int DEFAULT_SIZE = 100;

    private int rankingSize = DEFAULT_SIZE;

    public RankBySpecificityAndExpressionLevelCommand() {
    }

    @Override
    public GeneExpressionsList apply(ObjectInputStream<GeneProfile> objectStream) {

        Comparator<GeneExpression> reverseSpecificityComparator = Ordering.from(new GeneSpecificityComparator()).reverse();

        Queue<GeneExpression> topTenObjects = MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(rankingSize).create();

        GeneProfile geneProfile;

        while ((geneProfile = objectStream.readNext()) != null) {
            for (Expression expression : geneProfile) {
                GeneExpression geneExpression =
                        new GeneExpression(geneProfile.getGeneId(), expression, geneProfile.getGeneSpecificity());
                topTenObjects.add(geneExpression);

            }
        }

        GeneExpressionsList list = new GeneExpressionsList(topTenObjects);

        Collections.sort(list, reverseSpecificityComparator);

        return list;
    }

    public RankBySpecificityAndExpressionLevelCommand setRankingSize(int rankingSize) {
        checkArgument(rankingSize > 0, "rankingSize must be greater then zero");
        this.rankingSize = rankingSize;
        return this;
    }

}
