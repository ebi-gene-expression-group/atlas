package uk.ac.ebi.atlas.experimentpage.qc;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Named
@Scope("singleton")
public class QCReportUtil {

    private String qcFilePathTemplate;

    @Inject
    public QCReportUtil(@Value("#{configuration['microarray.experiment.arraydesign.qc.path.template']}") String qcFilePathTemplate) {
        this.qcFilePathTemplate = qcFilePathTemplate;
    }

    public boolean hasQCReport(String experimentAccession, String arrayDesign) {
        String path = buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public String buildQCReportIndexHtmlPath(String experimentAccession, String arrayDesign)  {
        return MessageFormat.format(qcFilePathTemplate, experimentAccession, arrayDesign, "index.html");
    }


}
