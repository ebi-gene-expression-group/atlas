package uk.ac.ebi.atlas.experimentpage;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Named
public class HierarchicalClusteringPdfViewHelper {
    private String pdfFileTemplate;
    private String pdfUrlTemplate;

    @Inject
    public HierarchicalClusteringPdfViewHelper(@Value("#{configuration['hierarchical.clustering.pdf.path.template']}") String pdfFileTemplate,
                                               @Value("#{configuration['hierarchical.clustering.pdf.url.template']}") String pdfUrlTemplate) {
        this.pdfFileTemplate = pdfFileTemplate;
        this.pdfUrlTemplate = pdfUrlTemplate;
    }

    public boolean hasClusteringPdf(String experimentAccession) {
        String path = MessageFormat.format(pdfFileTemplate, experimentAccession);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public String generateClusteringPdfUrl(String experimentAccession) {
        return MessageFormat.format(pdfUrlTemplate, experimentAccession);
    }

}
