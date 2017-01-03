package uk.ac.ebi.atlas.species.admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import javax.inject.Inject;
import java.io.IOException;

@Controller
@Scope("request")
@RequestMapping("/admin/species")
public class SpeciesAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesAdminController.class);

    private static final String HELP_MESSAGE =
            "Species admin - list of operations\n" +
            "----------------------------------\n" +
            "\n" +
            "### Operations by name\n" +
            "\n" +
            "#### HELP\n" +
            "This message\n" +
            "\n" +
            "#### LIST\n" +
            "List all available references species with their properties\n" +
            "\n" +
            "#### REFRESH\n" +
            "Reload species properties from $ATLAS_DATA/species/species-properties.json";
            // If the source of species properties changes, change the above. I don’t think it’s worth to expose the
            // paths in AtlasResource and add one more collaborator here for just this.

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final SpeciesPropertiesTrader speciesPropertiesTrader;

    @Inject
    public SpeciesAdminController(SpeciesPropertiesTrader speciesPropertiesTrader) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
    }

    @RequestMapping(value = "/{op}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String doOp(@PathVariable("op") String opParameter) {

        switch (opParameter.toUpperCase()) {
            case "HELP": return HELP_MESSAGE;
            case "LIST": return gson.toJson(speciesPropertiesTrader.getAll());
            case "REFRESH": return gson.toJson(refreshSpecies());
            default: return usageMessage(opParameter);
        }
    }

    private JsonElement refreshSpecies() {
        JsonObject refreshStatus = new JsonObject();

        try {
            refreshStatus.addProperty("success", speciesPropertiesTrader.refresh());
        } catch (Exception e) {
            refreshStatus.addProperty("error", "Epic fail!");
        }

        return refreshStatus;
    }

    private String usageMessage(String opParameter) {
        String errorMessage = "Could not understand: " + opParameter;
        return errorMessage + "\n\n" + HELP_MESSAGE;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException(Exception e) throws IOException {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }
}
