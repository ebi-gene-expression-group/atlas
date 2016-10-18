package uk.ac.ebi.atlas.model.resource;

import java.nio.file.Paths;
import java.text.MessageFormat;

public class ExtraInfoImage extends ExternalImage {


    public ExtraInfoImage(String fileSystemTemplate,String externalLinkTemplate, String
            experimentAccession){
        super(ResourceType.EXTRA_INFO, Paths.get(MessageFormat.format(fileSystemTemplate, experimentAccession)),
                MessageFormat.format(externalLinkTemplate, experimentAccession));
    }
}
