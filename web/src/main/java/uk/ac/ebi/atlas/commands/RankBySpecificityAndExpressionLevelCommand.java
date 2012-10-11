package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
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

    private Queue<GeneExpression> topTenObjects;

    private Set<String> geneIDs;

    private Set<String> organismParts;

    public RankBySpecificityAndExpressionLevelCommand() {
    }

    @Override
    public GeneExpressionsList apply(ObjectInputStream<GeneProfile> objectStream) {

        Comparator<GeneExpression> reverseSpecificityComparator = Ordering.from(new GeneSpecificityComparator()).reverse();

        topTenObjects = MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(rankingSize).create();

        GeneProfile geneProfile;

        while ((geneProfile = objectStream.readNext()) != null) {
            addGeneProfileToQueue(geneProfile);
        }

        GeneExpressionsList list = new GeneExpressionsList(topTenObjects);

        Collections.sort(list, reverseSpecificityComparator);

        return list;
    }

    protected void setTopTenObjects(Queue<GeneExpression> topTenObjects) {
        this.topTenObjects = topTenObjects;
    }

    protected void addGeneProfileToQueue(GeneProfile geneProfile) {
        if (isInQuery(geneIDs, geneProfile.getGeneId())) {
            for (Expression expression : geneProfile) {
                if (isInQuery(organismParts, expression.getOrganismPart())) {
                    addExpressionInQueue(geneProfile, expression);
                }

            }
        }
    }

    private void addExpressionInQueue(GeneProfile geneProfile, Expression expression) {
        GeneExpression geneExpression =
                new GeneExpression(geneProfile.getGeneId(), expression, geneProfile.getGeneSpecificity());
        topTenObjects.add(geneExpression);
    }

    private boolean isInQuery(Set<String> query, String value) {
        return CollectionUtils.isEmpty(query) || geneIDs.contains(value);
    }

    public RankBySpecificityAndExpressionLevelCommand setRankingSize(int rankingSize) {
        checkArgument(rankingSize > 0, "rankingSize must be greater then zero");
        this.rankingSize = rankingSize;
        return this;
    }

    public void setGeneIDs(Set<String> geneIDs) {
        this.geneIDs = geneIDs;
    }

    public void setOrganismParts(Set<String> organismParts) {
        this.organismParts = organismParts;
    }
}
