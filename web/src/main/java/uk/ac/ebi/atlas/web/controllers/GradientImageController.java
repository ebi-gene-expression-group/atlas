package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@Scope("request")
public class GradientImageController {

    @RequestMapping(value = "/util/colorgradient", method = RequestMethod.GET)
    public ResponseEntity<byte[]> streamGradient(@ModelAttribute("preferences") @Valid Preferences preferences) {

        //ColourGradient gradient = new ColourGradient();

        //gradient.setColourScale();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        //responseHeaders.setContentLength(fileSize);
        return null;
        //return new ResponseEntity<byte[]>(file.getDataArray(),
        //    responseHeaders, HttpStatus.OK);
    }

}
