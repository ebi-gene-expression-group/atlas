package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public void listAllExperiments(HttpServletResponse response) throws IOException {
        doOp("all", "list" ,response);
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
    public void doOp(@PathVariable("accessions") String accessionParameter, @PathVariable("op") String opParameter, HttpServletResponse response) throws IOException {
        JsonWriter writer = new JsonWriter(response.getWriter());
        writer.setIndent("  ");
        writer.beginArray();
        try {

            final Collection<String> accessions = accessionParameter.length() == 0 || accessionParameter.toLowerCase().equals("all")
                    ? ImmutableList.of()
                    : ImmutableList.copyOf(readAccessions(accessionParameter));

            Iterable<JsonElement> result = maybeOps(opParameter)
                    .transform(ops -> experimentOps.dispatchAndPerform(accessions, ops)).or(ImmutableList.of(usageMessage(opParameter)));

            for (JsonElement element : result) {
                gson.toJson(element, writer);
                writer.flush();
            }

        } catch (Exception e) {
            gson.toJson(errorMessage(accessionParameter, e),writer);
        } finally {
            writer.endArray();
            writer.flush();
            writer.close();
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
            result.addAll(experimentOps.findAllExperiments().stream().filter(experimentAccession -> pattern.matcher(experimentAccession).matches()).collect(Collectors.toList()));
            return result;
        } else {
            return Arrays.asList(accessionParameter.split(","));
        }
    }

}