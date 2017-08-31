package uk.ac.ebi.atlas.profiles.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.LinkToBaselineProfile;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ExternallyViewableProfilesList<DataColumnDescriptor extends DescribesDataColumns,
                                            Prof extends Profile<DataColumnDescriptor, ? extends Expression, Prof>,
                                            Unit extends ExpressionUnit> {

    private final GeneProfilesList<Prof> profiles;
    private final Function<Prof, URI> provideLinkToProfile;
    private final List<DataColumnDescriptor> prescribedOrderOfColumns;
    private final Function<Prof, Unit> expressionUnitForProfile;

    public ExternallyViewableProfilesList(GeneProfilesList<Prof> profiles,
                                          Function<Prof, URI> provideLinkToProfile,
                                          List<DataColumnDescriptor> prescribedOrderOfColumns,
                                          Function<Prof, Unit> expressionUnitForProfile){
        this.profiles = profiles;
        this.provideLinkToProfile = provideLinkToProfile;
        this.prescribedOrderOfColumns = prescribedOrderOfColumns;
        this.expressionUnitForProfile = expressionUnitForProfile;
    }

    public static
    ExternallyViewableProfilesList<FactorAcrossExperiments, BaselineExperimentProfile, ExpressionUnit.Absolute>
    createForExperimentProfiles(SemanticQuery geneQuery,
                                BaselineExperimentProfilesList experimentProfiles,
                                List<FactorAcrossExperiments> dataColumns) {
        return new ExternallyViewableProfilesList<>(
                experimentProfiles,
                new LinkToBaselineProfile(geneQuery),
                dataColumns,
                baselineExperimentProfile -> baselineExperimentProfile.getExperimentType().isRnaSeqBaseline() ?
                        ExpressionUnit.Absolute.Rna.TPM :
                        ExpressionUnit.Absolute.Protein.ANY);
    }

    public JsonObject asJson() {
        JsonObject result = new JsonObject();
        for(Map.Entry<String, String> e: profiles.properties().entrySet()){
            result.addProperty(e.getKey(), e.getValue());
        }

        JsonArray rows = new JsonArray();
        for(Prof profile : profiles) {
            rows.add(convert(profile));
        }
        result.add("rows", rows);

        return result;
    }

    private JsonObject convert(Prof profile) {

        JsonObject result = new JsonObject();
        for (Map.Entry<String, String> e: profile.properties().entrySet()) {
            result.addProperty(e.getKey(), e.getValue());
        }
        JsonArray expressions = new JsonArray();
        for (DataColumnDescriptor c: prescribedOrderOfColumns) {
            expressions.add(Optional.ofNullable(profile.getExpression(c))
                    .map(e -> e.toJson()).orElse(new JsonObject()));
        }
        result.add("expressions", expressions);
        result.addProperty("uri", provideLinkToProfile.apply(profile).toString());
        result.addProperty("expressionUnit", expressionUnitForProfile.apply(profile).toString());

        return result;

    }



}
