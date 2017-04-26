package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class ExperimentAdminController extends JsonExceptionHandlingController {

    private final ExperimentOps experimentOps;
    private final ExperimentAdminHelpPage helpPage = new ExperimentAdminHelpPage();

    public ExperimentAdminController(ExperimentOps experimentOps) {
        this.experimentOps = experimentOps;
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listAllExperiments() {
        return doOp("all", "list");
    }

    @RequestMapping(
            value = "/{accessionsOrCryForHelp}",
            method = RequestMethod.GET)
    public String listExperiments(@PathVariable("accessionsOrCryForHelp") String accessionsOrCryForHelp) {
        if("help".equalsIgnoreCase(accessionsOrCryForHelp)) {
            return "forward:/admin/experiments/help";
        } else {
            return "forward:/admin/experiments/" + accessionsOrCryForHelp + "/list";
        }
    }

    @RequestMapping(
            value = "/help",
            method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String help() {
        return helpPage.getMessage();
    }

    @RequestMapping(
            value = "/{accessions}/{op}",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String doOp(@PathVariable("accessions") String accessionParameter, @PathVariable("op") String opParameter) {
        try {
            final Optional<Collection<String>> accessions = accessionParameter.length() == 0 || accessionParameter.toLowerCase().equals("all")
                    ? Optional.<Collection<String>>absent()
                    : Optional.of(readAccessions(accessionParameter));


            return gson.toJson(
                    maybeOps(opParameter)
                    .transform(new Function<List<Op>, JsonElement>() {
                        @Override
                        public JsonElement apply(List<Op> ops) {
                            return experimentOps.perform(accessions, ops);
                        }
                    }).or(usageMessage(opParameter))
            );
        } catch (Exception e) {
            return gson.toJson(errorMessage(accessionParameter, e));
        }
    }

    private Optional<List<Op>> maybeOps(String opParameter){
        try{
            return Optional.of(Op.opsForParameter(opParameter));
        } catch (IllegalArgumentException e){
            return Optional.absent();
        }
    }

    private JsonElement errorMessage(String accessionParameter, Exception e){
        JsonArray result = new JsonArray();
        JsonObject messageObject = new JsonObject();
        messageObject.addProperty("accession", accessionParameter);
        messageObject.addProperty("error", e.getMessage()!=null? e.getMessage() : e.toString());
        result.add(messageObject);
        return result;
    }

    private JsonElement usageMessage(String opParameter){
        JsonObject messageObject = new JsonObject();
        messageObject.addProperty("error","Could not understand: " + opParameter );
        messageObject.addProperty("help","see gxa/admin/experiments/help for more info");
        return messageObject;
    }

    private Collection<String> readAccessions(String accessionParameter) {
        if (accessionParameter.contains("*")) {
            List<String> result = new ArrayList<>();
            Pattern pattern = Pattern.compile(accessionParameter.replaceAll("\\*", ".*"));
            for (String experimentAccession : experimentOps.findAllExperiments()) {
                if (pattern.matcher(experimentAccession).matches()) {
                    result.add(experimentAccession);
                }
            }
            return result;
        } else {
            return Arrays.asList(accessionParameter.split(","));
        }
    }

}