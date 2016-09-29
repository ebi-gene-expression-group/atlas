package uk.ac.ebi.atlas.model.resource;

import java.nio.file.Paths;
import java.text.MessageFormat;

public class RnaSeqContrastImage extends ContrastImage {

    public RnaSeqContrastImage(ResourceType type, String fileSystemTemplate,String externalLinkTemplate, String
            experimentAccession, String
            contrast){
        super(type,Paths.get(MessageFormat.format(fileSystemTemplate, experimentAccession,contrast)),
                MessageFormat.format(externalLinkTemplate, experimentAccession, contrast));
    }
}
