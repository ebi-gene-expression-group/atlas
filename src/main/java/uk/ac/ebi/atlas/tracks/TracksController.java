package uk.ac.ebi.atlas.tracks;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@Scope("singleton")
public class TracksController {

    @RequestMapping(value = "/experiments/{experimentAccession}/tracks/{trackFileName:.*}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String forwardToQcResource(@PathVariable String experimentAccession,@PathVariable String trackFileName) throws IOException {
        // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
        String path = String.format("/expdata/%s/%s", experimentAccession, trackFileName);

        return "forward:" + path;
    }
}
