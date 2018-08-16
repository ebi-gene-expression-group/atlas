package uk.ac.ebi.atlas.model.resource;

import com.google.common.collect.ImmutableList;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collection;

public class ContrastImage extends ExternalImage {
    public static final Collection<ResourceType> RESOURCE_TYPES = ImmutableList.of(
            ResourceType.PLOT_GSEA_GO,
            ResourceType.PLOT_GSEA_INTERPRO,
            ResourceType.PLOT_GSEA_REACTOME,
            ResourceType.PLOT_MA);

    public ContrastImage(ResourceType type,
                         String fileSystemTemplate,
                         String externalLinkTemplate,
                         String experimentAccession,
                         String contrast) {
        super(type,
              Paths.get(MessageFormat.format(fileSystemTemplate, experimentAccession, contrast)),
              MessageFormat.format(externalLinkTemplate, experimentAccession, contrast));
    }

    public ContrastImage(ResourceType type,
                         String fileSystemTemplate,
                         String externalLinkTemplate,
                         String experimentAccession,
                         String arrayDesign,
                         String contrast) {
        super(type,
              Paths.get(MessageFormat.format(fileSystemTemplate, experimentAccession, arrayDesign, contrast)),
              MessageFormat.format(externalLinkTemplate, experimentAccession, arrayDesign, contrast));
    }
}
