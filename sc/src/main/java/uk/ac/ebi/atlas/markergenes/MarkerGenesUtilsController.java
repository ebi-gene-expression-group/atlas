package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.inject.Inject;

@Controller
public class MarkerGenesUtilsController extends JsonExceptionHandlingController {

    private final MarkerGeneDao markerGeneDao;

    @Inject
    public MarkerGenesUtilsController(MarkerGeneDao markerGeneDao) {
        this.markerGeneDao = markerGeneDao;
    }

    @RequestMapping(
            value = "/admin/populateMarkerGenesWithRandomData",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String blah(@RequestParam(defaultValue = "1000") int records) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        markerGeneDao.deleteAll();
        markerGeneDao.loadMarkerGenes(new RandomMarkerGeneInputStream(records));
        stopWatch.stop();

        return new Gson().toJson(ImmutableMap.of("rowsAffected", records, "time", stopWatch.getTotalTimeSeconds()));
    }
}
