package uk.ac.ebi.atlas.experimentimport.coexpression;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class BaselineCoexpressionProfile {
    private String geneID;
    private Iterable<BaselineCoexpression> coexpressions;

    public BaselineCoexpressionProfile(String geneID, Iterable<BaselineCoexpression> coexpressions) {
        this.geneID = geneID;
        this.coexpressions = coexpressions;
    }

    public String geneID() {
        return geneID;
    }

    public String coexpressedGenesAsString() {
        return Joiner.on(",").join(coexpressedGenesToList());
    }

    public ImmutableList<String> coexpressedGenesToList() {
        ImmutableList.Builder<String> geneIDsBuilder = new ImmutableList.Builder<>();
        for (BaselineCoexpression baselineCoexpression : coexpressions) {
            geneIDsBuilder.add(baselineCoexpression.ceGeneID());
        }
        return geneIDsBuilder.build();
    }

}
