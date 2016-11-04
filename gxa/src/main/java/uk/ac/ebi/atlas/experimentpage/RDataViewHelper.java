package uk.ac.ebi.atlas.experimentpage;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RDataViewHelper extends ExperimentFileViewHelper {
    @Inject
    public RDataViewHelper(@Value("#{configuration['r.data.path.template']}") String fileTemplate,
                           @Value("#{configuration['r.data.clustering.pdf.url.template']}") String urlTemplate) {
        this.fileTemplate = fileTemplate;
        this.urlTemplate = urlTemplate;
    }
}
