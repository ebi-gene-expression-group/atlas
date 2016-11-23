package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.utils.ColourGradient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.NumberFormat;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfilesViewModelBuilder {

    private final ColourGradient colourGradient;
    private final PValueFormatter pValueFormatter;
    private final NumberFormat format2Dp = NumberFormat.getNumberInstance();

    @Inject
    public DifferentialProfilesViewModelBuilder(ColourGradient colourGradient, PValueFormatter pValueFormatter) {
        this.colourGradient = colourGradient;
        this.pValueFormatter = pValueFormatter;

        format2Dp.setGroupingUsed(false);
        format2Dp.setMaximumFractionDigits(2);
    }

    public JsonObject build(DifferentialProfilesList<? extends DifferentialProfile<? extends
                DifferentialExpression>> diffProfiles, Set<Contrast> orderedContrasts) {
        JsonObject result = new JsonObject();
        addDoublePropertyIfNotNaN(result, "maxDownLevel", diffProfiles.getMaxDownRegulatedExpressionLevel());
        addDoublePropertyIfNotNaN(result, "maxUpLevel", diffProfiles.getMaxUpRegulatedExpressionLevel());
        addDoublePropertyIfNotNaN(result, "minDownLevel", diffProfiles.getMinDownRegulatedExpressionLevel());
        addDoublePropertyIfNotNaN(result, "minUpLevel", diffProfiles.getMinUpRegulatedExpressionLevel());

        result.add("rows", buildGenes(diffProfiles, orderedContrasts));
        result.addProperty("searchResultTotal", diffProfiles.getTotalResultCount());
        return result;
    }

    private void addDoublePropertyIfNotNaN(JsonObject o,String name, double d){
        if(! Double.isNaN(d)&&!Double.isInfinite(d)){
            o.addProperty(name,FoldChangeRounder.round(d));
        }
    }

    private <P extends DifferentialProfile<?
                extends DifferentialExpression>> JsonArray buildGenes(DifferentialProfilesList<P> profiles,
                                                                  Set<Contrast> orderedContrasts) {
        JsonArray serializedProfiles = new JsonArray();
        for(P profile: profiles){
            serializedProfiles.add(build(profile, orderedContrasts, profiles.getMinUpRegulatedExpressionLevel(),
                    profiles.getMaxUpRegulatedExpressionLevel(), profiles.getMinDownRegulatedExpressionLevel(),
                    profiles.getMaxDownRegulatedExpressionLevel()));
        }
        return serializedProfiles;
    }

    private JsonObject build(DifferentialProfile<? extends DifferentialExpression> profile,
                             Set<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        JsonObject serializedProfileRow = new JsonObject();
        serializedProfileRow.addProperty("id", profile.getId());
        serializedProfileRow.addProperty("name", profile.getName());
        serializedProfileRow.addProperty("designElement", profile.getDesignElementName());
        serializedProfileRow.add("expressions", buildExpressions(profile, orderedContrasts, minUpLevel,
                maxUpLevel, minDownLevel, maxDownLevel) );
        return serializedProfileRow;
    }

    private JsonArray buildExpressions(DifferentialProfile<? extends DifferentialExpression>
                                                                        profile, Set<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        JsonArray serializedExpressions = new JsonArray();
        for(Contrast contrast: orderedContrasts){
            JsonObject expr = new JsonObject();
            expr.addProperty("contrastName", contrast.getDisplayName());

            DifferentialExpression expression = profile.getExpression(contrast);
            if(expression!=null){

                expr.addProperty("color",
                        expression.isOverExpressed()
                                ? colourGradient.getGradientColour(expression.getFoldChange(), minUpLevel, maxUpLevel, "pink", "red")
                                : colourGradient.getGradientColour(expression.getFoldChange(), minDownLevel, maxDownLevel, "lightGray", "blue"));
                expr.addProperty("foldChange",FoldChangeRounder.round(expression.getFoldChange()));
                expr.addProperty("pValue",pValueFormatter.formatPValueAsScientificNotation(expression.getPValue()) );
                if(expression instanceof MicroarrayExpression){
                    expr.addProperty("tStat", format2Dp.format(((MicroarrayExpression) expression).getTstatistic()));
                }
            }
            serializedExpressions.add(expr);
        }
        return serializedExpressions;
    }
}
