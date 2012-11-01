package uk.ac.ebi.atlas.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.StreamGeneProfilesCommand;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

@Controller
@Scope("request")
public class GeneProfilesDownloadController {
    private static final Logger logger = Logger.getLogger(GeneProfilesDownloadController.class);

    private StreamGeneProfilesCommand streamGeneProfilesCommand;

    @Inject
    public GeneProfilesDownloadController(StreamGeneProfilesCommand streamGeneProfilesCommand) {
        this.streamGeneProfilesCommand = streamGeneProfilesCommand;
    }

    @RequestMapping("/experiments/{experimentAccession}.tsv")
    public void downloadGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , Model model, HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"" +experimentAccession + "-gene-profiles.tsv\"");

        OutputStream out = response.getOutputStream();

        response.setContentType("text/plain; charset=utf-8");

        OutputStreamWriter writer = new OutputStreamWriter(out);

        streamGeneProfilesCommand.setRequestPreferences(preferences);
        streamGeneProfilesCommand.setOutputStreamWriter(writer);

        long genesCount = streamGeneProfilesCommand.apply(experimentAccession);

        logger.info("<downloadGeneProfiles> streamed " + genesCount + "gene expression profiles");

    }

}
















