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
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialResultsReader;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.trader.ContrastTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class DifferentialJsonResultsParser extends JsonExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialResultsReader.class);

    private ContrastTrader contrastTrader;

    @Inject
    public DifferentialJsonResultsParser(ContrastTrader contrastTrader)
    {
        this.contrastTrader = contrastTrader;
    }

    public ImmutableList<DiffAnalytics> parseDifferentialResults(JsonObject obj)
    {
        try
        {
            ImmutableList.Builder<DiffAnalytics> diffAnalyticsListBuilder = ImmutableList.builder();
            JsonArray results = obj.getAsJsonArray("results");

            for(JsonElement e : results) {

                DiffAnalytics diffAnalytics = buildDiffAnalyticObject(e);

                if(diffAnalytics != null)
                {
                    diffAnalyticsListBuilder.add(diffAnalytics);
                }
            }

            return diffAnalyticsListBuilder.build();
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("Input Json is missing a results field OR results field is not an Array",e);
        }
    }

    private DiffAnalytics buildDiffAnalyticObject(JsonElement e) {

        try {

            JsonObject resultObj = e.getAsJsonObject();

            Contrast contrast = contrastTrader.getContrast(resultObj.get("experiment_accession").getAsString(), resultObj.get("contrast_id").getAsString());

            DifferentialExpression expression =
                    buildDifferentialExpression(
                            resultObj.get("p_value").getAsDouble(),
                            resultObj.get("fold_change").getAsDouble(),
                            Optional.ofNullable(resultObj.get("t_statistic")).map(JsonElement::getAsDouble).orElse(null));

            String species = resultObj.get("species").getAsString();
            species = species.substring(0,1).toUpperCase() + species.substring(1);


            return new DiffAnalytics(resultObj.get("bioentity_identifier").getAsString(), Optional.ofNullable(resultObj.get("keyword_symbol")).map(JsonElement::getAsString).orElse(resultObj.get("bioentity_identifier").getAsString()), resultObj.get("experiment_accession").getAsString(), expression, species, contrast);
        }
        catch(RuntimeException exp)
        {
            LOGGER.error("Error adding differential result: {}", exp.getMessage());
            return null;
        }
    }

    private DifferentialExpression buildDifferentialExpression(double pValue, double foldChange, Double tstatistic) {
        if (tstatistic == null) {
            return new DifferentialExpression(pValue, foldChange);
        }
        return new MicroarrayExpression(pValue, foldChange, tstatistic);
    }

}
