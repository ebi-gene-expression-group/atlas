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


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

@Controller
public class ExperimentImageController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentImageController.class);

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    @ResponseBody
    @RequestMapping("/experiments/{experimentAccession}/extra-info.png")
    public void getImage(HttpServletResponse response, @PathVariable String experimentAccession){

        try{

            String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);

            File extraInfoFile = new File(extraInfoFileLocation);
            BufferedImage image = ImageIO.read(extraInfoFile);

            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);
            out.close();

        } catch (IOException e){
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Error loading extra info for experiment " + experimentAccession);
        }
    }
}
