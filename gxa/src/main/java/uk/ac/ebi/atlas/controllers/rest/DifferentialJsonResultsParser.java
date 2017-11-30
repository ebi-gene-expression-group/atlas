package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.trader.ContrastTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.IllegalFormatException;
import java.util.Optional;
import java.util.function.Function;

@Named
public class DifferentialJsonResultsParser extends JsonExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialJsonResultsParser.class);

    private ContrastTrader contrastTrader;

    @Inject
    public DifferentialJsonResultsParser(ContrastTrader contrastTrader)
    {
        this.contrastTrader = contrastTrader;
    }

    public ImmutableList<DiffAnalytics> parseDifferentialResults(JsonObject obj) {

        try {
            ImmutableList.Builder<DiffAnalytics> diffAnalyticsListBuilder = ImmutableList.builder();
            JsonArray results = obj.getAsJsonArray("results");

            for(JsonElement e : results) {

                DiffAnalytics diffAnalytics = buildDiffAnalyticsObject(e);

                if(Optional.ofNullable(diffAnalytics).isPresent()) {
                    diffAnalyticsListBuilder.add(diffAnalytics);
                }
            }

            return diffAnalyticsListBuilder.build();
        }
        catch (IllegalFormatException e) {
            throw new RuntimeException("Input Json is missing a results field OR results field is not an Array",e.getCause());
        }
    }

    private DiffAnalytics buildDiffAnalyticsObject(JsonElement e) {

        try {

            JsonObject resultObj = e.getAsJsonObject();

            String experiment_accession = resultObj.get("experiment_accession").getAsString();

            String bioentity_identifier = resultObj.get("bioentity_identifier").getAsString();

            String contrast_id = resultObj.get("contrast_id").getAsString();

            Double p_value = resultObj.get("p_value").getAsDouble();

            Double fold_change = resultObj.get("fold_change").getAsDouble();

            String keyword_symbol = Optional.of(resultObj.get("keyword_symbol")).map(JsonElement::getAsString).orElse(bioentity_identifier);

            JsonElement nullableTStatistic = resultObj.get("t_statistic");

            Optional<Double> tStatistic = nullableTStatistic == null ? Optional.empty() : Optional.of(nullableTStatistic.getAsDouble());

            Function<Double, DifferentialExpression> microarrayExpressionMapper = ts -> new MicroarrayExpression(p_value, fold_change, ts);
            DifferentialExpression expression = tStatistic.map(microarrayExpressionMapper).orElse(new DifferentialExpression(p_value, fold_change));

            Contrast contrast = contrastTrader.getContrast(experiment_accession, contrast_id);


            /*  Data coming from database has species name following Binomial nomenclature notation for naming species i.e. first character of species to be capital
                but data in solr doesn't follow this notation so to make it consistent first character of species is uppercase
            */

            String species = resultObj.get("species").getAsString();
            species = species.substring(0,1).toUpperCase() + species.substring(1);

            return new DiffAnalytics(bioentity_identifier, keyword_symbol, experiment_accession, expression, species, contrast);
        }
        catch(NullPointerException exp) {
            LOGGER.error("Error adding differential result: {}", exp.getMessage());
            return null;
        }
    }

}
