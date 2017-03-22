package uk.ac.ebi.atlas.experimentpage.qc;

import org.apache.commons.io.IOUtils;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Path;

import static java.nio.file.Files.newInputStream;

public class QCReportUtil {

    private QCReportUtil(){

    }

    public static void printContent(HttpServletRequest request, Writer out ){
        Path filePath = (Path)request.getAttribute("contentPath");
        try(InputStream f = newInputStream(filePath)) {
            IOUtils.copy(f, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
