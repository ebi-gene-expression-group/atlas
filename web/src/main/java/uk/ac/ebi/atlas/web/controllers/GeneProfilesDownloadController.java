package uk.ac.ebi.atlas.web.controllers;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.WriteGeneProfilesCommand;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class GeneProfilesDownloadController {
    private static final Logger logger = Logger.getLogger(GeneProfilesDownloadController.class);

    private WriteGeneProfilesCommand writeGeneProfilesCommand;

    @Inject
    public GeneProfilesDownloadController(WriteGeneProfilesCommand writeGeneProfilesCommand) {
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
    }

    @RequestMapping("/experiments/{experimentAccession}.tsv")
    public void downloadGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"" +experimentAccession + "-gene-profiles.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        writeGeneProfilesCommand.setRequestPreferences(preferences);

        CSVWriter csvWriter = new CSVWriter(response.getWriter(), '\t'/*, CSVWriter.NO_QUOTE_CHARACTER*/);

        writeGeneProfilesCommand.setCsvWriter(csvWriter);

        long genesCount = writeGeneProfilesCommand.apply(experimentAccession);

        logger.debug("<downloadGeneProfiles> streamed " + genesCount + "gene expression profiles");

        csvWriter.flush();
        csvWriter.close();

    }

}
















