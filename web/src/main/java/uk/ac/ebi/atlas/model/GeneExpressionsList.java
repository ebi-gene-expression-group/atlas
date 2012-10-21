package uk.ac.ebi.atlas.model;

import org.apache.commons.math.util.MathUtils;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class GeneExpressionsList extends ArrayList<GeneExpression> {
    
    private static final long serialVersionUID = -1678371004778942235L;

    public static final int FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE = 0;
    public static final int FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE = 1;
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

    public Double getRoundedExpressionLevel(String geneId, String organismPart){
        return roundedValue(getExpressionLevel(geneId, organismPart));
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

    public Double getRoundedMaxExpressionLevel(){
        return roundedValue(getMaxExpressionLevel());
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

    public Double getRoundedMinExpressionLevel(){
        return roundedValue(getMinExpressionLevel());
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    private Double roundedValue(Double value){
        if (value == null){
            return null;
        }
        return MathUtils.round(value, value >= 1 ? FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE
                                                : FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE);
    }

}
