package uk.ac.ebi.atlas.ebeye;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@Scope("request")
public class EbEyeSearchController {
    private final ExperimentDescriptionXmlFormatter experimentDescriptionXMLFormatter;
    private final ExperimentDescriptionDao experimentDescriptionDAO;

    @Inject
    public EbEyeSearchController(ExperimentDescriptionDao experimentDescriptionDAO,
                                 ExperimentDescriptionXmlFormatter experimentDescriptionXMLFormatter) {
        this.experimentDescriptionDAO = experimentDescriptionDAO;
        this.experimentDescriptionXMLFormatter = experimentDescriptionXMLFormatter;
    }

    @RequestMapping(value = "/api/experiments.xml")
    public void listExperimentDescriptions(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        response.setContentType("application/xml");

        List<ExperimentDescription> experimentDescriptions =
                experimentDescriptionDAO.selectAllPublicExperimentDescriptions();

        writer.write(experimentDescriptionXMLFormatter.formatHeader(experimentDescriptions.size(), new Date()));

        for (ExperimentDescription experimentDescription : experimentDescriptions) {
            writer.write(experimentDescriptionXMLFormatter.formatExperimentDescription(experimentDescription));
        }

        writer.write(experimentDescriptionXMLFormatter.formatFooter());
    }
}
