package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.trader.ContrastTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.function.Function;

@Named
public class DifferentialJsonResultsParser extends JsonExceptionHandlingController {

    private ContrastTrader contrastTrader;

    @Inject
    public DifferentialJsonResultsParser(ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
    }

    public ImmutableList<DiffAnalytics> parseDifferentialResults(JsonObject obj) {

        ImmutableList.Builder<DiffAnalytics> diffAnalyticsListBuilder = ImmutableList.builder();
        JsonArray results = obj.getAsJsonArray("results");

        for (JsonElement e : results) {

            DiffAnalytics diffAnalytics = buildDiffAnalyticsObject(e);

            if (Optional.ofNullable(diffAnalytics).isPresent()) {
                diffAnalyticsListBuilder.add(diffAnalytics);
            }
        }

        return diffAnalyticsListBuilder.build();

    }

    private DiffAnalytics buildDiffAnalyticsObject(JsonElement element) {

        JsonObject resultObj = element.getAsJsonObject();

        String experimentAccession = resultObj.get("experiment_accession").getAsString();

        String bioentityIdentifier = resultObj.get("bioentity_identifier").getAsString();

        String contrastId = resultObj.get("contrast_id").getAsString();

        Double pValue = resultObj.get("p_value").getAsDouble();

        Double foldChange = resultObj.get("fold_change").getAsDouble();

        String keywordSymbol = Optional.of(resultObj.get("keyword_symbol")).map(JsonElement::getAsString).orElse(bioentityIdentifier);

        Optional<Double> tStatistic =
                Optional.ofNullable(resultObj.get("t_statistic"))
                        .map(JsonElement::getAsDouble);

        Function<Double, DifferentialExpression> microarrayExpressionMapper = ts -> new MicroarrayExpression(pValue, foldChange, ts);
        DifferentialExpression expression = tStatistic.map(microarrayExpressionMapper).orElse(new DifferentialExpression(pValue, foldChange));

        Contrast contrast = contrastTrader.getContrast(experimentAccession, contrastId);


            /*  Data coming from database has species name following Binomial nomenclature notation for naming species i.e. first character of species to be capital
                but data in solr doesn't follow this notation so to make it consistent first character of species is uppercase
            */

        String species = resultObj.get("species").getAsString();
        species = species.substring(0, 1).toUpperCase() + species.substring(1);

        return new DiffAnalytics(bioentityIdentifier, keywordSymbol, experimentAccession, expression, species, contrast);
    }


}
