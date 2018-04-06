package uk.ac.ebi.atlas.download;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.download.ExperimentFileType;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import java.io.File;

@Controller
public class FileDownloadController extends HtmlExceptionHandlingController {

    private ExperimentFileLocationService experimentFileLocationService;
    private ScxaExperimentTrader experimentTrader;

    @Inject
    public FileDownloadController(ExperimentFileLocationService experimentFileLocationService, ScxaExperimentTrader experimentTrader) {
        this.experimentFileLocationService = experimentFileLocationService;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "experiment/{experimentAccession}/download", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> download(@PathVariable String experimentAccession,
                                                       @RequestParam(value= "fileType") String fileTypeId, @RequestParam(value="accessKey", defaultValue = "") String accessKey) {

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        File file = experimentFileLocationService.getFilePath(experiment.getAccession(), ExperimentFileType.fromId(fileTypeId)).toFile();
        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

}
