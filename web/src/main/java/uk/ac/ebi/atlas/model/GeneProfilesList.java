package uk.ac.ebi.atlas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneProfilesList extends ArrayList<GeneProfile> {

    private static final long serialVersionUID = -1678371004778942235L;

    private Integer totalResultCount = 0;

    public GeneProfilesList(Collection<GeneProfile> collection) {
        super(collection);
    }

    public GeneProfilesList() {
    }

    public GeneProfilesList getTop(int size) {
        return subList(0, size);
    }

    //ToDo: refactor this method with heatmap-matrix-gene-oriented.jsp
//    public Double getExpressionLevel(String geneId, String factorValueString) {
//        for (GeneProfile geneProfile : this) {
//            if (geneId.equalsIgnoreCase(geneProfile.getGeneId())) {
//                return geneProfile.getExpressionLevelByFactorVauleString(factorValueString);
//            }
//        }
//        return null;
//    }

    public Double getExpressionLevel(String geneId, FactorValue factorValue) {
        for (GeneProfile geneProfile : this) {
            if (geneId.equalsIgnoreCase(geneProfile.getGeneId())) {
                return geneProfile.getExpressionLevel(factorValue);
            }
        }
        return null;
    }

    @Override
    public GeneProfilesList subList(int fromIndex, int toIndex) {
        checkArgument(toIndex >= 0, "Upper index value must be larger than 0");
        if (toIndex > this.size()) {
            return this;
        }
        return new GeneProfilesList(super.subList(fromIndex, toIndex));
    }

    public double getMaxExpressionLevel() {
        double maxExpressionLevel = 0;
        for (GeneProfile geneProfile : this) {
            if (maxExpressionLevel < geneProfile.getMaxExpressionLevel()) {
                maxExpressionLevel = geneProfile.getMaxExpressionLevel();
            }
        }
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        double minExpressionLevel = Double.MAX_VALUE;
        for (GeneProfile geneProfile : this) {
            if (geneProfile.getMinExpressionLevel() < minExpressionLevel) {
                minExpressionLevel = geneProfile.getMinExpressionLevel();
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

    public SortedSet<FactorValue> getAllExperimentalFactors() {
        SortedSet<FactorValue> allExperimentalFactors = new TreeSet<>();
        for (GeneProfile geneProfile : this) {
            allExperimentalFactors.addAll(geneProfile.getFactorValues());
        }
        return allExperimentalFactors;
    }
}
