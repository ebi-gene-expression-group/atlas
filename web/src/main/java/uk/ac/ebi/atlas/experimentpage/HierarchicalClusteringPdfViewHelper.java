package uk.ac.ebi.atlas.experimentpage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Named
@Scope("singleton")
public class HierarchicalClusteringPdfViewHelper {
    private String pdfFileTemplate;

    @Inject
    public HierarchicalClusteringPdfViewHelper(@Value("#{configuration['hierarchical.clustering.pdf.path.template']}") String pdfFileTemplate) {
        this.pdfFileTemplate = pdfFileTemplate;
    }

    public boolean hasSingleSpeciesPdf(String experimentAccession) {
        return hasPdf(experimentAccession, "");
    }

    public boolean hasPdf(String experimentAccession, String species) {
        String path = buildPath(experimentAccession, species);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    String buildPath(String experimentAccession, String species)  {
        String speciesPrefix = StringUtils.isNotEmpty(species) ? "_" + Species.convertSpacesToUnderscore(species) : "";
        return MessageFormat.format(pdfFileTemplate, experimentAccession, speciesPrefix);
    }

    public String generateSingleSpeciesUrl(String experimentAccession) {
        return generateUrl(experimentAccession, "");
    }

    public String generateUrl(String experimentAccession, String species) {
        String speciesPrefix = StringUtils.isNotEmpty(species) ? "_" + Species.convertSpacesToUnderscore(species) : "";
        return MessageFormat.format("/experiments/{0}/{0}{1}-heatmap.pdf", experimentAccession, speciesPrefix);
    }

}
