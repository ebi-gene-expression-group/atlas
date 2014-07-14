package uk.ac.ebi.atlas.experiment.differential;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class GseaPlotsBuilder {

    private String gseaPathTemplate;

    @Inject
    public GseaPlotsBuilder(@Value("#{configuration['experiment.gsea-plot.path.template']}") String gseaPathTemplate) {
        this.gseaPathTemplate = gseaPathTemplate;
    }

    public GseaPlots create(String experimentAccession, String contrastId) {
        return new GseaPlots(gseaPathTemplate, experimentAccession, contrastId);
    }

    public ImmutableMap<String, GseaPlots> createMapByContrastId(String experimentAccession, Set<Contrast> contrasts) {
        ImmutableMap.Builder<String, GseaPlots> builder = ImmutableMap.builder();

        for (Contrast contrast : contrasts) {
            GseaPlots gseaPlots = new GseaPlots(gseaPathTemplate, experimentAccession, contrast.getId());
            builder.put(contrast.getId(), gseaPlots);
        }

        return builder.build();
    }

}
