package uk.ac.ebi.atlas.model.view;

import uk.ac.ebi.atlas.model.differential.DifferentialExpression;

public class DifferentialGeneExpression {

    private String identifier;

    private String experimentAccession;

    private DifferentialExpression expression;

    private String organism;

    public DifferentialGeneExpression(String geneQuery, String experimentAccession, DifferentialExpression expression, String organism) {
        this.identifier = geneQuery;
        this.experimentAccession = experimentAccession;
        this.expression = expression;
        this.organism = organism;
    }

    public String getContrastDisplayName() {
        return expression.getContrast().getDisplayName();
    }

    public String getContrastId() {
        return expression.getContrast().getId();
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getUrl() {
        return experimentAccession + "?geneQuery=" + identifier + "&queryFactorValues=" + expression.getContrast().getId();
    }

    public DifferentialExpression getExpression() {
        return expression;
    }

    public double getValue() {
        return expression.getLevel();
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getOrganism() {
        return organism;
    }
}
