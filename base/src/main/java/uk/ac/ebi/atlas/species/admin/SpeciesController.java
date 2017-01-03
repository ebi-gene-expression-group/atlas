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
public class SpeciesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesController.class);

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final SpeciesPropertiesTrader speciesPropertiesTrader;

    @Inject
    public SpeciesController(SpeciesPropertiesTrader speciesPropertiesTrader) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
    }

    @RequestMapping(value = "/json/species/{speciesName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSpeciesInfo(@PathVariable("speciesName") String speciesName) {
        return gson.toJson(speciesPropertiesTrader.get(speciesName));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException(Exception e) throws IOException {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }
}
