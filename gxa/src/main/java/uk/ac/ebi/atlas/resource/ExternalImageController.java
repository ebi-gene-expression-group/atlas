
package uk.ac.ebi.atlas.resource;


import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExternalImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalImageController.class);

    private ContrastImageFactory contrastImageFactory;

    private ExtraInfoFactory extraInfoFactory;

    @Inject
    public ExternalImageController(ExtraInfoFactory extraInfoFactory,
                                   ContrastImageFactory contrastImageFactory) {
        this.extraInfoFactory = extraInfoFactory;
        this.contrastImageFactory = contrastImageFactory;
    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/extra-info.png")
    public void streamExtraInfoImage(HttpServletResponse response, @PathVariable String experimentAccession) {

        streamExternalImage(response, extraInfoFactory.getExtraInfo(experimentAccession).get());

    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{contrastName}/{fileName}")
    public void streamRnaSeqImage(HttpServletResponse response, @PathVariable String experimentAccession, @PathVariable String
                                              contrastName, @PathVariable String fileName) {

        streamExternalImage(response, contrastImageFactory.getContrastImage(
                ResourceType.forFileName(fileName),
                experimentAccession,
                contrastName).get());
    }


    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{arrayDesignAccession}/{contrastName}/{fileName}")
    public void streamMicroarrayImage(HttpServletResponse response, @PathVariable String experimentAccession,
                                            @PathVariable String arrayDesignAccession, @PathVariable String
                                                        contrastName, @PathVariable String fileName) {

        streamExternalImage(response, contrastImageFactory.getContrastImage(
                ResourceType.forFileName(fileName),
                experimentAccession,
                Optional.of(arrayDesignAccession),
                contrastName).get());
    }

    void streamExternalImage(HttpServletResponse response, Function<HttpServletResponse, ?> callback) {
        callback.apply(response);
    }
}
