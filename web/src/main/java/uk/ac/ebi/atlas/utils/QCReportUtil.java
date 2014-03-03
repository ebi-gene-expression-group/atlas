package uk.ac.ebi.atlas.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResourceLoader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkNotNull;

@Named("qcReportUtil")
@Scope("singleton")
public class QCReportUtil {

    private String qcFilePathTemplate;

    @Inject
    public QCReportUtil(@Value("#{configuration['microarray.experiment.arraydesign.qc.path.template']}") String qcFilePathTemplate) {
        this.qcFilePathTemplate = qcFilePathTemplate;
    }

    public boolean hasQCReport(String experimentAccession, String arrayDesign) throws IOException {
        String path = buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public String buildQCReportIndexHtmlPath(String experimentAccession, String arrayDesign)  {
        return MessageFormat.format(qcFilePathTemplate, experimentAccession, arrayDesign, "index.html");
    }


}
