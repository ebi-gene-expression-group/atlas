package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class ExperimentAdminController extends JsonExceptionHandlingController {

    private final ExperimentOps experimentOps;
    private final ExperimentAdminHelpPage helpPage = new ExperimentAdminHelpPage();

    public ExperimentAdminController(ExperimentOps experimentOps) {
        this.experimentOps = experimentOps;
    }

    @RequestMapping(
            value = "",
            produces = "application/json;charset=UTF-8")
    public void listAllExperiments(HttpServletResponse response) throws IOException {
        doOp("all", "list", response);
    }

    @RequestMapping(
            value = "/{accessionsOrCryForHelp}",
            method = RequestMethod.GET)
    public String listExperiments(@PathVariable("accessionsOrCryForHelp") String accessionsOrCryForHelp) {
        if ("help".equalsIgnoreCase(accessionsOrCryForHelp)) {
            return "forward:/admin/experiments/help";
        } else {
            return "forward:/admin/experiments/" + accessionsOrCryForHelp + "/list";
        }
    }

    @RequestMapping(
            value = "/help",
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String help() {
        return helpPage.getMessage();
    }

    @RequestMapping(value = "/{accessions}/{op}", produces = "application/json;charset=UTF-8")
    public void doOp(@PathVariable("accessions") String accessionParameter,
                     @PathVariable("op") String opParameter,
                     HttpServletResponse response) throws IOException {
        JsonWriter writer = new JsonWriter(response.getWriter());
        try {
            writer.setIndent("  ");
            writer.beginArray();

            final Collection<String> accessions =
                    accessionParameter.length() == 0 || accessionParameter.toLowerCase().equals("all") ?
                            ImmutableList.of() :
                            ImmutableList.copyOf(readAccessions(accessionParameter));

            Iterable<JsonElement> result =
                    maybeOps(opParameter)
                            .map(ops -> experimentOps.dispatchAndPerform(accessions, ops))
                            .orElse(ImmutableList.of(usageMessage(opParameter)));

            for (JsonElement element : result) {
                GSON.toJson(element, writer);
            }
        } catch (Exception e) {
            GSON.toJson(errorMessage(accessionParameter, e), writer);
        } finally {
            writer.endArray();
            writer.flush();
            writer.close();
        }
    }

    private Optional<List<Op>> maybeOps(String opParameter) {
        try {
            return Optional.of(Op.opsForParameter(opParameter));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private JsonElement errorMessage(String accessionParameter, Exception e) {
        JsonObject result = new JsonObject();
        result.addProperty("accession", accessionParameter);
        result.addProperty("error", e.getMessage() != null ? e.getMessage() : e.toString());
        return result;
    }

    private JsonElement usageMessage(String opParameter) {
        JsonObject messageObject = new JsonObject();
        messageObject.addProperty("error", "Could not understand: " + opParameter);
        messageObject.addProperty("help", "see gxa/admin/experiments/help for more info");
        return messageObject;
    }

    private Collection<String> readAccessions(String accessionParameter) {
        if (accessionParameter.contains("*")) {
            Pattern pattern = Pattern.compile(accessionParameter.replaceAll("\\*", ".*"));
            return experimentOps.findAllExperiments().stream()
                    .filter(experimentAccession ->
                            pattern.matcher(experimentAccession).matches()).collect(toList());
        } else {
            return Arrays.asList(accessionParameter.split(","));
        }
    }

}
