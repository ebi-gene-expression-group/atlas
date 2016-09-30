package uk.ac.ebi.atlas.model.resource;

import com.google.common.collect.ImmutableList;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collection;

public class MicroarrayContrastImage extends ContrastImage {

    public static Collection<ResourceType> RESOURCE_TYPES = ImmutableList.of(
            ResourceType.PLOT_MA);

    public MicroarrayContrastImage(ResourceType type, String fileSystemTemplate,String externalLinkTemplate, String
            experimentAccession,
                                   String arrayDesign,  String contrast){
        super(type, Paths.get(MessageFormat.format(fileSystemTemplate,experimentAccession,arrayDesign,contrast)),
                MessageFormat.format(externalLinkTemplate, experimentAccession,arrayDesign, contrast));
    }
}
