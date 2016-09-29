package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collection;

@Named
public class GseaPlotsBuilder {

    private String gseaPathTemplate;

    @Inject
    public GseaPlotsBuilder(@Value("#{configuration['experiment.gsea-plot.path.template']}") String gseaPathTemplate) {
        this.gseaPathTemplate = gseaPathTemplate;
    }

    @Deprecated //use ContrastImageFactory
    public JsonObject createJsonByContrastId(String experimentAccession, Collection<Contrast> contrasts) {
        JsonObject result = new JsonObject();
        for (Contrast contrast : contrasts) {
            JsonObject valuesForThisContrast = new JsonObject();
            valuesForThisContrast.addProperty("go",hasFile(experimentAccession, contrast.getId(), "go") );
            valuesForThisContrast.addProperty("interpro",hasFile(experimentAccession, contrast.getId(), "interpro") );
            valuesForThisContrast.addProperty("reactome",hasFile(experimentAccession, contrast.getId(), "reactome") );
            result.add(contrast.getId(), valuesForThisContrast);
        }
        return result;
    }

    private boolean hasFile(String experimentAccession, String contrastId, String geneSetType) {
        String imagePath = MessageFormat.format(gseaPathTemplate, experimentAccession, contrastId, geneSetType);

        return Files.exists(Paths.get(imagePath));
    }

}
