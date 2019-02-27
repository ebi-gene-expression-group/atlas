package uk.ac.ebi.atlas.download;

import org.apache.commons.io.IOUtils;
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
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class FileDownloadController extends HtmlExceptionHandlingController {
    private final ExperimentFileLocationService experimentFileLocationService;
    private final ScxaExperimentTrader experimentTrader;

    @Inject
    public FileDownloadController(ExperimentFileLocationService experimentFileLocationService,
                                  ScxaExperimentTrader experimentTrader) {
        this.experimentFileLocationService = experimentFileLocationService;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "experiment/{experimentAccession}/download",
                    method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource>
    download(@PathVariable String experimentAccession,
             @RequestParam(value = "fileType") String fileTypeId,
             @RequestParam(value = "accessKey", defaultValue = "") String accessKey) {

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        File file =
                experimentFileLocationService.getFilePath(
                        experiment.getAccession(), ExperimentFileType.fromId(fileTypeId)).toFile();
        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

    @RequestMapping(value = "experiment/{experimentAccession}/download/zip",
                    method = RequestMethod.GET,
                    produces = "application/zip")
    public void
    downloadArchive(HttpServletResponse response,
                    @PathVariable String experimentAccession,
                    @RequestParam(value = "fileType") String fileTypeId) throws IOException {

        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        List<Path> paths =
                experimentFileLocationService.getFilePathsForArchive(
                        experiment.getAccession(), ExperimentFileType.fromId(fileTypeId));

        String archiveName = experimentAccession + "-" + fileTypeId + "-files.zip";
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + archiveName);
        response.setContentType("application/zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for (Path path : paths) {
            File file = path.toFile();

            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
    }

    @RequestMapping(value = "experimentlist/download/zip",
            method = RequestMethod.GET,
            produces = "application/zip")
    public void
    downloadMultipleExperimentsArchive(HttpServletResponse response,
                    @RequestParam(value= "experimentAccessions", defaultValue = "") List<String> accessions,
                    @RequestParam(value = "accessKey", defaultValue = "") String accessKey) throws IOException {

        String archiveName = accessions.size() + "-" + "experiments" + "-files.zip";
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + archiveName);
        response.setContentType("application/zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for (String experimentAccession : accessions){
            Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

            List<Path> paths = new ArrayList<>();
            paths.addAll(experimentFileLocationService.getFilePathsForArchive(experiment.getAccession(), ExperimentFileType.QUANTIFICATION_FILTERED));
            paths.addAll(experimentFileLocationService.getFilePathsForArchive(experiment.getAccession(), ExperimentFileType.QUANTIFICATION_RAW));
            paths.addAll(experimentFileLocationService.getFilePathsForArchive(experiment.getAccession(), ExperimentFileType.NORMALISED));
            paths.add(experimentFileLocationService.getFilePath(experiment.getAccession(), ExperimentFileType.SDRF));

            for (Path path : paths) {
                File file = path.toFile();

                zipOutputStream.putNextEntry(new ZipEntry(experiment.getAccession() + "/" + file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);

                IOUtils.copy(fileInputStream, zipOutputStream);

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
        }
        zipOutputStream.close();
    }
}
