package uk.ac.ebi.atlas.experimentpage;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ClusteringPdfViewHelper extends ExperimentFileViewHelper {
    @Inject
    public ClusteringPdfViewHelper(@Value("#{configuration['hierarchical.clustering.pdf.path.template']}") String fileTemplate,
                                   @Value("#{configuration['hierarchical.clustering.pdf.url.template']}") String urlTemplate) {
        this.fileTemplate = fileTemplate;
        this.urlTemplate = urlTemplate;
    }
}
