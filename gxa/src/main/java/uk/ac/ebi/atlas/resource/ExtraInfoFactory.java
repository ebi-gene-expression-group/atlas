package uk.ac.ebi.atlas.resource;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.model.resource.ExtraInfoImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import java.io.File;
import java.nio.file.Paths;

@Component
public class ExtraInfoFactory {
    private final String extraInfoPathTemplate;

    public ExtraInfoFactory(DataFileHub dataFileHub) {
        extraInfoPathTemplate =
                dataFileHub.getExperimentMageTabDirLocation().resolve("{0}").resolve("{0}-extra-info.png").toString();
    }

    ExternalImage getExtraInfo(String experimentAccession) {
        String uriTemplate =
                Paths.get(
                        File.separator,
                        "external-resources",
                        "{0}",
                        ResourceType.EXTRA_INFO.fileName()).toString();

        return new ExtraInfoImage(extraInfoPathTemplate, uriTemplate, experimentAccession);
    }
}
