package uk.ac.ebi.atlas.web.controllers.rest.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.integration.ebisearch.ExperimentDescription;
import uk.ac.ebi.atlas.integration.ebisearch.ExperimentDescriptionDAO;
import uk.ac.ebi.atlas.integration.ebisearch.ExperimentDescriptionXMLFormatter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class EBEyeSearchController {

    private ExperimentDescriptionXMLFormatter experimentDescriptionXMLFormatter;

    private ExperimentDescriptionDAO experimentDescriptionDAO;

    @Inject
    public EBEyeSearchController(ExperimentDescriptionDAO experimentDescriptionDAO, ExperimentDescriptionXMLFormatter experimentDescriptionXMLFormatter) {
        this.experimentDescriptionDAO = experimentDescriptionDAO;
        this.experimentDescriptionXMLFormatter = experimentDescriptionXMLFormatter;
    }

    @RequestMapping(value = "/eb-eye/experiments", method = RequestMethod.GET, produces = "application/xml")
    public void listExperimentDescriptions(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        setDownloadHeaders(response, "experiments.xml");

        List<ExperimentDescription> experimentDescriptions = experimentDescriptionDAO.selectAllPublicExperimentDescriptions();

        writer.write(experimentDescriptionXMLFormatter.formatHeader(experimentDescriptions.size(), new Date()));

        for (ExperimentDescription experimentDescription : experimentDescriptions) {
            writer.write(experimentDescriptionXMLFormatter.formatExperimentDescription(experimentDescription));
        }

        writer.write(experimentDescriptionXMLFormatter.formatFooter());

    }

    private void setDownloadHeaders(HttpServletResponse response, String fileName) {
        //response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/xml");
    }


}
