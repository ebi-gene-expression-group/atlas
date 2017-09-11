package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.inject.Inject;
import java.util.Set;

@Controller
@RequestMapping
public class CoexpressedGenesController extends JsonExceptionHandlingController {

    private final Gson gson = new Gson();
    private final CoexpressedGenesDao coexpressedGenesDao;

    @Inject
    public CoexpressedGenesController(JdbcTemplate jdbcTemplate){
        this.coexpressedGenesDao = new CoexpressedGenesDao(jdbcTemplate);
    }

    @RequestMapping(value ={"/json/experiments/coexpression"},
produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchCoexpressedGenes(@RequestParam(value = "experiment") String experiment,
                                        @RequestParam(value = "identifier") String identifier) {
        return gson.toJson(coexpressedGenesDao.coexpressedGenesFor(experiment, identifier), Set.class);
    }
}
