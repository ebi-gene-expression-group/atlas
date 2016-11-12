package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.base.Throwables;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.MessageFormat;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.newInputStream;

@Named
public class QCReportUtil {

    private String qcFilePathTemplate;

    @Inject
    public QCReportUtil(@Value("#{configuration['microarray.experiment.arraydesign.qc.path.template']}") String qcFilePathTemplate) {
        this.qcFilePathTemplate = qcFilePathTemplate;
    }

    public boolean hasQCReport(String experimentAccession, String arrayDesign) {
        String path = buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);
        return exists(FileSystems.getDefault().getPath(path));
    }

    public String buildQCReportIndexHtmlPath(String experimentAccession, String arrayDesign)  {
        return MessageFormat.format(qcFilePathTemplate, experimentAccession, arrayDesign, "index.html");
    }

    public static void printContent(HttpServletRequest request, Writer out ){
        Path filePath = (Path)request.getAttribute("contentPath");
        try(InputStream f = newInputStream(filePath)) {
            IOUtils.copy(f, out);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }

    }

}
