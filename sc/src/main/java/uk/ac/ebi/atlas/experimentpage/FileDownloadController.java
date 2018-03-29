package uk.ac.ebi.atlas.experimentpage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.download.ExperimentFileType;

import javax.inject.Inject;
import java.io.File;

@Controller
public class FileDownloadController {

    private ExperimentFileLocationService experimentFileLocationService;

    @Inject
    public FileDownloadController(ExperimentFileLocationService experimentFileLocationService) {
        this.experimentFileLocationService = experimentFileLocationService;
    }

    @RequestMapping(value = "experiment/{experimentAccession}/download", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> download(@PathVariable String experimentAccession,
                                                       @RequestParam(value= "fileType", defaultValue = "") String fileTypeId, @RequestParam(value="accessKey") String accessKey) {

        File file = experimentFileLocationService.getFilePath(experimentAccession, ExperimentFileType.fromId(fileTypeId)).toFile();
        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

}
