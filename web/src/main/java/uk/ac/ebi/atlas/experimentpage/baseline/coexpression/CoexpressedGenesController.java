package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.util.Set;


@Controller
@RequestMapping
public class CoexpressedGenesController {


    Gson gson = new Gson();
    CoexpressedGenesDao coexpressedGenesDao;

    @Inject
    public CoexpressedGenesController(JdbcTemplate jdbcTemplate){
        this.coexpressedGenesDao = new CoexpressedGenesDao(jdbcTemplate);
    }

    @RequestMapping(value ={"/json/experiments/coexpression"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchCoexpressedGenes(@RequestParam(value = "experiment", required = true) String experiment,
                                              @RequestParam(value = "identifier", required = true) String identifier) {
        return gson.toJson(coexpressedGenesDao.coexpressedGenesFor(experiment, identifier), Set.class);
    }
}
