package uk.ac.ebi.atlas.model.resource;

import java.nio.file.Paths;
import java.text.MessageFormat;

public class MicroarrayContrastImage extends ContrastImage {

    public MicroarrayContrastImage(ResourceType type, String fileSystemTemplate,String externalLinkTemplate, String
            experimentAccession,
                                   String arrayDesign,  String contrast){
        super(type, Paths.get(MessageFormat.format(fileSystemTemplate,experimentAccession,arrayDesign,contrast)),
                MessageFormat.format(externalLinkTemplate, experimentAccession,arrayDesign, contrast));
    }
}
