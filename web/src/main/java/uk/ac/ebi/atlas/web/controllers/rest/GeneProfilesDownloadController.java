package uk.ac.ebi.atlas.web.controllers.rest;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.WriteGeneProfilesCommand;
import uk.ac.ebi.atlas.model.FilterParameters;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Controller
@Scope("request")
public class GeneProfilesDownloadController {
    private static final Logger LOGGER = Logger.getLogger(GeneProfilesDownloadController.class);

    private WriteGeneProfilesCommand writeGeneProfilesCommand;

    @Inject
    public GeneProfilesDownloadController(WriteGeneProfilesCommand writeGeneProfilesCommand) {
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
    }

    @RequestMapping("/experiments/{experimentAccession}.tsv")
    public void downloadGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "-gene-expression-profiles.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        FilterParameters parameters = new FilterParameters();
        parameters.setGeneQuery(preferences.getGeneQuery())
                .setQueryFactorType(preferences.getQueryFactorType())
                .setQueryFactorValues(preferences.getQueryFactorValues())
                .setFilterFactorValues(preferences.getFilterFactorValues())
                .setCutoff(preferences.getCutoff());
        writeGeneProfilesCommand.setParameters(parameters);

        CSVWriter csvWriter = new CSVWriter(response.getWriter(), '\t', NO_QUOTE_CHARACTER);

        writeGeneProfilesCommand.setCsvWriter(csvWriter);

        long genesCount = writeGeneProfilesCommand.apply(experimentAccession);

        LOGGER.info("<downloadGeneProfiles> streamed " + genesCount + "gene expression profiles");

        csvWriter.flush();
        csvWriter.close();

    }

}
















