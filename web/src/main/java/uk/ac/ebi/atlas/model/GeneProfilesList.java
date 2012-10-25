package uk.ac.ebi.atlas.model;

import org.apache.commons.math.util.MathUtils;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class GeneProfilesList extends ArrayList<GeneProfile> {
    
    private static final long serialVersionUID = -1678371004778942235L;

    private Integer totalResultCount = 0;

    public GeneProfilesList() {
        super();
    }

    public GeneProfilesList(Collection<GeneProfile> collection) {
        super(collection);
    }

    public GeneProfilesList getTop(int size) {
        return subList(0, size);
    }

    public Double getExpressionLevel(String geneId, String organismPart) {
        for (GeneProfile geneProfile : this) {
            if (geneId.equalsIgnoreCase(geneProfile.getGeneId())){
                return geneProfile.getExpressionLevel(organismPart);
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

    public double getRoundedMaxExpressionLevel() {
        double maxExpressionLevel = 0;
        for (GeneProfile geneProfile : this) {
            Double roundedMaxExpressionLevel = geneProfile.getRoundedMaxExpressionLevel();
            if (maxExpressionLevel < roundedMaxExpressionLevel) {
                maxExpressionLevel = roundedMaxExpressionLevel;
            }
        }
        return maxExpressionLevel;
    }

    public double getRoundedMinExpressionLevel() {
        double minExpressionLevel = Double.MAX_VALUE;
        for (GeneProfile geneProfile : this) {
            double geneProfileRoundedMinExpressionLevel = geneProfile.getRoundedMinExpressionLevel();
            if (geneProfileRoundedMinExpressionLevel < minExpressionLevel) {
                minExpressionLevel = geneProfileRoundedMinExpressionLevel;
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

    public SortedSet<String> getAllOrganismParts() {
        SortedSet<String> allOrganismParts = new TreeSet<>();
        for (GeneProfile geneProfile : this) {
            allOrganismParts.addAll(geneProfile.getOrganismParts());
        }
        return allOrganismParts;
    }
}
