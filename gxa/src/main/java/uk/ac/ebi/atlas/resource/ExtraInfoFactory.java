package uk.ac.ebi.atlas.resource;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.model.resource.ExtraInfoImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import javax.inject.Named;

@Named
public class ExtraInfoFactory {

    @Value("#{configuration['experiment.extra-info-image.path.template']}")

    String extraInfoPathTemplate;

    ExternalImage getExtraInfo(String experimentAccession){
        String uriTemplate ="/external-resources/{0}/"+ ResourceType.EXTRA_INFO.fileName();

        return new ExtraInfoImage(extraInfoPathTemplate, uriTemplate, experimentAccession);
    }
}
