package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

/**
 * Created by barrera on 12/05/2017.
 *
 */
public class SingleCellBaseline {

    private String geneId;

    private String cellId;

    private Double expressionLevel;

    public SingleCellBaseline(String geneId, String cellId, Double expressionLevel) {
        this.geneId = geneId;
        this.cellId = cellId;
        this.expressionLevel = expressionLevel;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getCellId() {
        return cellId;
    }

    public Double getExpressionLevel() {
        return expressionLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleCellBaseline)) return false;

        SingleCellBaseline that = (SingleCellBaseline) o;

        if (geneId != null ? !geneId.equals(that.geneId) : that.geneId != null) return false;
        if (cellId != null ? !cellId.equals(that.cellId) : that.cellId != null) return false;
        return expressionLevel != null ? expressionLevel.equals(that.expressionLevel) : that.expressionLevel == null;
    }

    @Override
    public int hashCode() {
        int result = geneId != null ? geneId.hashCode() : 0;
        result = 31 * result + (cellId != null ? cellId.hashCode() : 0);
        result = 31 * result + (expressionLevel != null ? expressionLevel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SingleCellBaseline{" +
                "geneId='" + geneId + '\'' +
                ", cellId='" + cellId + '\'' +
                ", expressionLevel=" + expressionLevel +
                '}';
    }
}
