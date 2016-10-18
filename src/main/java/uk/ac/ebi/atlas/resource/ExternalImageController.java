
package uk.ac.ebi.atlas.resource;


import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.model.resource.ResourceType;
import uk.ac.ebi.atlas.utils.ImageIOUtils;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Controller
public class ExternalImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalImageController.class);

    private ContrastImageFactory contrastImageFactory;

    private ExtraInfoFactory extraInfoFactory;

    private ImageIOUtils imageIOUtils;

    @Inject
    public ExternalImageController(ImageIOUtils imageIOUtils,
                                   ExtraInfoFactory extraInfoFactory,
                                   ContrastImageFactory contrastImageFactory) {
        this.imageIOUtils = imageIOUtils;
        this.extraInfoFactory = extraInfoFactory;
        this.contrastImageFactory = contrastImageFactory;
    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/extra-info.png")
    public void streamExtraInfoImage(HttpServletResponse response, @PathVariable String experimentAccession) throws IOException{


        InputStream imageInputStream = extraInfoFactory.getExtraInfo(experimentAccession).get();

        streamExternalImage(response, imageInputStream);

    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{contrastName}/{fileName}")
    public void streamRnaSeqImage(HttpServletResponse response, @PathVariable String experimentAccession, @PathVariable String
                                              contrastName, @PathVariable String fileName) throws IOException{

        InputStream imageInputStream = contrastImageFactory.getContrastImage(
                ResourceType.forFileName(fileName),
                experimentAccession,
                contrastName).get();

        streamExternalImage(response, imageInputStream);
    }


    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{arrayDesignAccession}/{contrastName}/{fileName}")
    public void streamMicroarrayImage(HttpServletResponse response, @PathVariable String experimentAccession,
                                            @PathVariable String arrayDesignAccession, @PathVariable String
                                                        contrastName, @PathVariable String fileName) throws IOException{

        InputStream imageInputStream = contrastImageFactory.getContrastImage(
                ResourceType.forFileName(fileName),
                experimentAccession,
                Optional.of(arrayDesignAccession),
                contrastName).get();

        streamExternalImage(response, imageInputStream);
    }

    void streamExternalImage(HttpServletResponse response, InputStream extraInfoImageInputStream) {
        try {

            BufferedImage image = imageIOUtils.read(extraInfoImageInputStream);

            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            imageIOUtils.write(image, "png", out);
            out.close();

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Error loading external image");
        }
    }
}
