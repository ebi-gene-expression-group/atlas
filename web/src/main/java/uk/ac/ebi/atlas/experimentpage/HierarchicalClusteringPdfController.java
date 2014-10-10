package uk.ac.ebi.atlas.experimentpage;


import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Scope("request")
public class HierarchicalClusteringPdfController {

    private static final Pattern AFTER_UNDERSCORE = Pattern.compile("_(.*)");

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccessionAndSpecies}-heatmap.pdf", params = "type")
    public String downloadPdf(@PathVariable String experimentAccession, @PathVariable String experimentAccessionAndSpecies) throws IOException {
        String path = generateExpdataUrl(experimentAccession, experimentAccessionAndSpecies);
        return "forward:" + path;
    }

    String generateExpdataUrl(String experimentAccession, String experimentAccessionAndSpecies) throws UnsupportedEncodingException {
        String species = extractSpecies(experimentAccessionAndSpecies);
        String speciesPrefix = StringUtils.isNotEmpty(species) ? "_" + species : "";

        return MessageFormat.format("/expdata/{0}/{0}{1}-heatmap.pdf", experimentAccession, speciesPrefix);
    }

    String extractSpecies(String experimentAccessionAndSpecies) {
        Matcher matcher = AFTER_UNDERSCORE.matcher(experimentAccessionAndSpecies);
        return matcher.find() ? matcher.group(1) : "";
    }


}
