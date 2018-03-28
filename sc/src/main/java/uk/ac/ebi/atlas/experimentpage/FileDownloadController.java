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
import uk.ac.ebi.atlas.download.FilePathService;

import javax.inject.Inject;
import java.io.File;

@Controller
public class FileDownloadController {

    private FilePathService filePathService;

    @Inject
    public FileDownloadController(FilePathService filePathService) {
        this.filePathService = filePathService;
    }

    @RequestMapping(value = "experiment/{experimentAccession}/download", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> download(@PathVariable String experimentAccession,
                                                  @RequestParam(value= "fileType", defaultValue = "") String fileType, @RequestParam(value="accessKey") String accessKey) {

        File file = filePathService.getFilePath(experimentAccession, fileType).toFile();
        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

}
