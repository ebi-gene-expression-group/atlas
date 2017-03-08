package uk.ac.ebi.atlas.experimentpage.analysismethods;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Controller
@Scope("request")
public class AnalysisMethodsPageController {

    private String pdfFileTemplate;

    @Inject
    public AnalysisMethodsPageController(@Value("#{configuration['analysis-methods.pdf.path.template']}") String pdfFileTemplate) {

        this.pdfFileTemplate = pdfFileTemplate;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods/{resource}.pdf", method = RequestMethod.GET)
    public String getAnalysisMethodsPdf(@PathVariable String experimentAccession,@PathVariable String resource) throws IOException {
        if(!hasPDF(experimentAccession, resource)) {
            throw new ResourceNotFoundException("No pdf for " + resource);
        }

        String path = generatePDFPath(experimentAccession, resource);
        return "forward:" + path;
    }

    /***** analysis-methods pdf path ****/
    private boolean hasPDF(String experimentAccession, String resource) throws IOException {
        String path = buildPDFPath(experimentAccession, resource);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    private String buildPDFPath(String experimentAccession, String resource)  {
        return MessageFormat.format(pdfFileTemplate, experimentAccession, resource);
    }

    public String generatePDFPath(String experimentAccession, String resource) {
        return MessageFormat.format("/expdata/{0}/{1}.pdf", experimentAccession, resource);
    }

}
















