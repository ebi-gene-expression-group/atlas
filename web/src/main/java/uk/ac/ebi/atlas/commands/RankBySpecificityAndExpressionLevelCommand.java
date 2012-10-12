package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.*;

import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Named("rankBySpecificityAndExpressionLevel")
@Scope("prototype")
public class RankBySpecificityAndExpressionLevelCommand implements Function<ObjectInputStream<GeneProfile>, GeneExpressionsList> {

    private static final int DEFAULT_SIZE = 100;

    private int rankingSize = DEFAULT_SIZE;

    private Queue<GeneExpression> rankedObjects;

    private Set<String> organismParts;

    public RankBySpecificityAndExpressionLevelCommand() {
    }

    @Override
    public GeneExpressionsList apply(ObjectInputStream<GeneProfile> geneProfileInputStream) {

        Comparator<GeneExpression> reverseSpecificityComparator = Ordering.from(new GeneSpecificityComparator()).reverse();

        rankedObjects = MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(rankingSize).create();

        GeneProfile geneProfile;

        while ((geneProfile = geneProfileInputStream.readNext()) != null) {
            for (GeneExpression geneExpression: geneProfile.organismPartExpressions(organismParts)){
                rankedObjects.add(geneExpression);
            }
        }

        GeneExpressionsList list = new GeneExpressionsList(rankedObjects);

        Collections.sort(list, reverseSpecificityComparator);

        return list;
    }

    protected void setRankedObjects(Queue<GeneExpression> rankedObjects) {
        this.rankedObjects = rankedObjects;
    }

    private void addExpressionInQueue(GeneProfile geneProfile, Expression expression) {

    }

    private boolean isSearched(Set<String> searchedStrings, String value) {
        return CollectionUtils.isEmpty(searchedStrings) || searchedStrings.contains(value);
    }

    public RankBySpecificityAndExpressionLevelCommand setRankingSize(int rankingSize) {
        checkArgument(rankingSize > 0, "rankingSize must be greater then zero");
        this.rankingSize = rankingSize;
        return this;
    }

    public void setOrganismParts(Set<String> organismParts) {
        this.organismParts = organismParts;
    }
}