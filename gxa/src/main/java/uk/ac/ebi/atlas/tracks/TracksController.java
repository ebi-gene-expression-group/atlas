package uk.ac.ebi.atlas.tracks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/*
I am not a restful feature and my URL can't be changed easily :(
TODO move under experiments-content
 */

@Controller
public class TracksController {

    public static final String url = "/experiments/{experimentAccession}/tracks/{trackFileName:.*}";

    @RequestMapping(value = url, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String forwardToQcResource(@PathVariable String experimentAccession,@PathVariable String trackFileName) throws IOException {
        // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
        String path = String.format("/expdata/%s/%s", experimentAccession, trackFileName);

        return "forward:" + path;
    }

}
