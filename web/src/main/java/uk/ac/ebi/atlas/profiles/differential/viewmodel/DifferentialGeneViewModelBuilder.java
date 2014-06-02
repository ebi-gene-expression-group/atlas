package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;

@Named
@Scope("prototype")
public class DifferentialGeneViewModelBuilder {

    private final ColourGradient colourGradient;

    @Inject
    public DifferentialGeneViewModelBuilder(ColourGradient colourGradient) {
        this.colourGradient = colourGradient;
    }

    public DifferentialGeneViewModel[] build(DifferentialProfilesList<? extends DifferentialProfile<? extends DifferentialExpression>> profiles, SortedSet<Contrast> orderedContrasts) {
        return build(profiles, orderedContrasts, profiles.getMinUpRegulatedExpressionLevel(), profiles.getMaxUpRegulatedExpressionLevel(), profiles.getMinDownRegulatedExpressionLevel(), profiles.getMaxDownRegulatedExpressionLevel());
    }

    public DifferentialGeneViewModel[] build(List<? extends DifferentialProfile<? extends DifferentialExpression>> profiles, SortedSet<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        DifferentialGeneViewModel[] viewModels = new DifferentialGeneViewModel[profiles.size()];

        int i = 0;
        for (DifferentialProfile<? extends DifferentialExpression> profile : profiles) {
            DifferentialGeneViewModel profileViewModel = build(profile, orderedContrasts, minUpLevel, maxUpLevel, minDownLevel, maxDownLevel);
            viewModels[i++] = profileViewModel;
        }

        return viewModels;
    }

    public DifferentialGeneViewModel build(DifferentialProfile<? extends DifferentialExpression> profile, SortedSet<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        String geneId = profile.getId();
        String geneName = profile.getName();
        DifferentialExpressionViewModel[] expressions = buildExpressions(profile, orderedContrasts, minUpLevel, maxUpLevel, minDownLevel, maxDownLevel);
        return new DifferentialGeneViewModel(geneId, geneName, expressions);
    }

    private DifferentialExpressionViewModel[] buildExpressions(DifferentialProfile<? extends DifferentialExpression> profile, SortedSet<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        DifferentialExpressionViewModel[] expressionViewModels = new DifferentialExpressionViewModel[orderedContrasts.size()];

        int i = 0;
        for (Contrast contrast : orderedContrasts) {
            String contrastName = contrast.getDisplayName();
            DifferentialExpression expression = profile.getExpression(contrast);

            String foldChange = (expression == null) ? "" : Double.toString(expression.getFoldChange());
            String color = (expression == null) ? "" : expression.isOverExpressed() ? colourGradient.getGradientColour(expression.getFoldChange(), minUpLevel, maxUpLevel, "pink", "red") : colourGradient.getGradientColour(expression.getFoldChange(), minDownLevel, maxDownLevel, "lightGray", "blue");
            String pValue = (expression == null) ? "" : Double.toString(expression.getPValue());
            String tStat = !(expression instanceof MicroarrayExpression) ? "" : Double.toString(((MicroarrayExpression) expression).getTstatistic());

            DifferentialExpressionViewModel expressionViewModel = new DifferentialExpressionViewModel(contrastName, color, foldChange, pValue, tStat);
            expressionViewModels[i++] = expressionViewModel;
        }

        return expressionViewModels;
    }

}
