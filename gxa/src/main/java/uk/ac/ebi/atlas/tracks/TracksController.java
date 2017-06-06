package uk.ac.ebi.atlas.tracks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

import java.io.IOException;

@Controller
public class TracksController extends HtmlExceptionHandlingController {

    @RequestMapping(value = "/experiments-content/{experimentAccession}/tracks/{trackFileName:.*}",
                    method = {RequestMethod.GET})
    public String forwardToTrackFile(@PathVariable String experimentAccession,@PathVariable String trackFileName)
    throws IOException {
        String path = String.format("/expdata/%s/%s", experimentAccession, trackFileName);
        return "forward:" + path;
    }

}
