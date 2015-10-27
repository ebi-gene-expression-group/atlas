/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.rest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private static final Logger LOGGER = LogManager.getLogger(ExternalImageController.class);

    private String gseaPathTemplate;

    private String extraInfoPathTemplate;

    private String rnaSeqPathTemplate;

    private String microarrayPathTemplate;

    private ImageIOUtils imageIOUtils;

    @Inject
    public ExternalImageController(ImageIOUtils imageIOUtils,
                                   @Value("#{configuration['experiment.extra-info-image.path.template']}") String extraInfoPathTemplate,
                                   @Value("#{configuration['experiment.rnaseq.ma-plot.path.template']}") String rnaSeqPathTemplate,
                                   @Value("#{configuration['experiment.microarray.ma-plot.path.template']}") String microarrayPathTemplate,
                                   @Value("#{configuration['experiment.gsea-plot.path.template']}") String gseaPathTemplate) {
        this.imageIOUtils = imageIOUtils;
        this.extraInfoPathTemplate = extraInfoPathTemplate;
        this.rnaSeqPathTemplate = rnaSeqPathTemplate;
        this.microarrayPathTemplate = microarrayPathTemplate;
        this.gseaPathTemplate = gseaPathTemplate;
    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/extra-info.png")
    public void streamExtraInfoImage(HttpServletResponse response, @PathVariable String experimentAccession) throws IOException{

        String imagePath = MessageFormat.format(extraInfoPathTemplate, experimentAccession);

        InputStream imageInputStream = Files.newInputStream(Paths.get(imagePath));

        streamExternalImage(response, imageInputStream);

    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{arrayDesignAccession}/{contrastName}/ma-plot.png")
    public void streamMicroarrayMaPlotImage(HttpServletResponse response, @PathVariable String experimentAccession,
                                            @PathVariable String arrayDesignAccession, @PathVariable String contrastName) throws IOException{

        String imagePath = MessageFormat.format(microarrayPathTemplate, experimentAccession,
                arrayDesignAccession,
                contrastName);

        InputStream imageInputStream = Files.newInputStream(Paths.get(imagePath));

        streamExternalImage(response, imageInputStream);

    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{contrastName}/ma-plot.png")
    public void streamRnaSeqMaPlotImage(HttpServletResponse response, @PathVariable String experimentAccession
            , @PathVariable String contrastName) throws IOException{

        String rnaSeqMaPlotImagePath = MessageFormat.format(rnaSeqPathTemplate, experimentAccession, contrastName);

        InputStream imageInputStream = Files.newInputStream(Paths.get(rnaSeqMaPlotImagePath));

        streamExternalImage(response, imageInputStream);

    }

    @ResponseBody
    @RequestMapping(value = "/external-resources/{experimentAccession}/{contrastName}/gsea_{geneSetType}.png")
    public void streamGSEPlot(HttpServletResponse response, @PathVariable String experimentAccession
            ,@PathVariable String contrastName, @PathVariable String geneSetType) throws IOException{

        String imagePath = MessageFormat.format(gseaPathTemplate, experimentAccession, contrastName, geneSetType);

        Path path = Paths.get(imagePath);

        if (!Files.exists(path)) {
            throw new ResourceNotFoundException(path + " does not exist");
        }

        InputStream imageInputStream = Files.newInputStream(path);

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
