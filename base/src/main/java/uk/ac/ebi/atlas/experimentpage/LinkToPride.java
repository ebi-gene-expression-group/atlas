package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;

import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

public abstract class LinkToPride<E extends Experiment> extends ExternallyAvailableContent.Supplier<E> {

    @Override
    public Collection<ExternallyAvailableContent> get(E experiment) {
        return Collections.singleton(
                new ExternallyAvailableContent(
                        "https://www.ebi.ac.uk/pride/archive/projects/" + experiment.getSecondaryAccession(),
                        ExternallyAvailableContent.Description.create(
                                "icon-pride",
                                MessageFormat.format("PRIDE Archive: project {0}", experiment.getSecondaryAccession()))));
    }

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION;
    }

    @Named
    public static class ProteomicsBaseline extends LinkToPride<BaselineExperiment> {

    }
}
