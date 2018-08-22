package uk.ac.ebi.atlas.experimentpage.analysismethods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

// I only know of E-PROT-1 that has this - and it's linked in the analysis methods tsv so can't change the url easily
// It seems to "win" against the all-grabbing /experiments/{experimentAccession}/** but it's fragile!
@Controller
@Scope("request")
public class AnalysisMethodsPageController {
    private final String pdfFileTemplate;

    @Inject
    public AnalysisMethodsPageController(DataFileHub dataFileHub) {
        this.pdfFileTemplate =
                dataFileHub.getExperimentMageTabDirLocation().resolve("{0}").resolve("{1}.pdf").toString();
    }

    @GetMapping(value = "/experiments/{experimentAccession}/analysis-methods/{resource}.pdf")
    public String getAnalysisMethodsPdf(@PathVariable String experimentAccession, @PathVariable String resource) {
        if (!buildPdfPath(experimentAccession, resource).toFile().exists()) {
            throw new ResourceNotFoundException("No PDF for " + resource);
        }

        String path = generatePDFPath(experimentAccession, resource);
        return "forward:" + path;
    }

    private Path buildPdfPath(String experimentAccession, String resource)  {
        return Paths.get(MessageFormat.format(pdfFileTemplate, experimentAccession, resource));
    }

    public String generatePDFPath(String experimentAccession, String resource) {
        return MessageFormat.format("/expdata/{0}/{1}.pdf", experimentAccession, resource);
    }

}
