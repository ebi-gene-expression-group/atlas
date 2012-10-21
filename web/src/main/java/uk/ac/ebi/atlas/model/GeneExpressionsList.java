package uk.ac.ebi.atlas.model;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class GeneExpressionsList extends ArrayList<GeneExpression> {

	private static final long serialVersionUID = -1678371004778942235L;

	private Integer totalResultCount = 0;

    public GeneExpressionsList() {
        super();
    }

    public GeneExpressionsList(Collection<GeneExpression> collection) {
        super(collection);
    }

    public GeneExpressionsList getTop(int size) {
        return subList(0, size);
    }

    public Double getExpressionLevel(String geneId, String organismPart) {
        for (GeneExpression geneExpression : this) {
            if (checkNotNull(geneId).equalsIgnoreCase(geneExpression.getGeneId())
                    && checkNotNull(organismPart).equalsIgnoreCase(geneExpression.getOrganismPart())) {
                return geneExpression.getLevel();
            }
        }
        return null;
    }

    @Override
    public GeneExpressionsList subList(int fromIndex, int toIndex) {
        checkArgument(toIndex >= 0, "Upper index value must be larger than 0");
        if (toIndex > this.size()) {
            return this;
        }
        return new GeneExpressionsList(super.subList(fromIndex, toIndex));
    }

    public GeneExpressionsList subList(Set<String> geneIds) {
        GeneExpressionsList subList = new GeneExpressionsList();
        for (GeneExpression expression : this) {
            if (geneIds.contains(expression.getGeneId())) {
                subList.add(expression);
            }
        }
        return subList;
    }


    public Set<String> getDistinctGeneIds() {
        Set<String> geneIds = new LinkedHashSet<String>();
        for (GeneExpression geneExpression : this) {
            geneIds.add(geneExpression.getGeneId());
        }
        return geneIds;
    }

    public SortedSet<String> getDistinctOrganismParts(Set<String> geneIds) {
        SortedSet<String> organismParts = new TreeSet<>();

        for (GeneExpression geneExpression : this) {
            if (geneIds.contains(geneExpression.getGeneId())) {
                organismParts.add(geneExpression.getOrganismPart());
            }
        }
        return organismParts;
    }

    public Double getMaxExpressionLevel() {
        Double maxExpressionLevel = null;
        for (GeneExpression expression : this) {
            if (maxExpressionLevel == null || expression.getLevel() > maxExpressionLevel) {
                maxExpressionLevel = expression.getLevel();
            }
        }
        return maxExpressionLevel;
    }

    public Double getMinExpressionLevel() {
        Double minExpressionLevel = null;
        for (GeneExpression expression : this) {
            if (minExpressionLevel == null || expression.getLevel() < minExpressionLevel) {
                minExpressionLevel = expression.getLevel();
            }
        }
        return minExpressionLevel;
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }
}
