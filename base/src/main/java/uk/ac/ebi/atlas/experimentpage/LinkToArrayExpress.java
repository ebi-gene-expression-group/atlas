package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;

import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.function.Function;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Collections.singleton;

public abstract class LinkToArrayExpress<E extends Experiment> extends ExternallyAvailableContent.Supplier<E> {
    private static final Function<Experiment, String> formatLabelToExperiment =
            e -> MessageFormat.format("ArrayExpress: experiment {0}", e.getAccession());
    private static final Function<String, String> formatLabelToArray =
            arrayAccession -> MessageFormat.format("ArrayExpress: array design {0}", arrayAccession);

    private static final Function<Experiment, String> formatLinkToExperiment =
            e -> MessageFormat.format("https://www.ebi.ac.uk/arrayexpress/experiments/{0}", e.getAccession());
    private static final Function<String, String> formatLinkToArray =
            arrayAccession -> MessageFormat.format("https://www.ebi.ac.uk/arrayexpress/arrays/{0}", arrayAccession);

    private static final Function<String, ExternallyAvailableContent.Description> createIcon =
            label -> ExternallyAvailableContent.Description.create("icon-ae", label);

    private static final Function<Experiment, ExternallyAvailableContent.Description> createIconForExperiment =
            formatLabelToExperiment.andThen(createIcon);
    private static final Function<String, ExternallyAvailableContent.Description> createIconForArray =
            formatLabelToArray.andThen(createIcon);

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION;
    }

    @Override
    public Collection<ExternallyAvailableContent> get(E experiment) {
        return singleton(
                new ExternallyAvailableContent(
                        formatLinkToExperiment.apply(experiment),
                        createIconForExperiment.apply(experiment)));
    }

    @Named
    public static class ProteomicsBaseline extends LinkToArrayExpress<BaselineExperiment> {
        // In the future we will source proteomics experiments from PRIDE, and one requirement is that they are not
        // backported into ArrayExpress. E-PROT-6 is an example experiment from PRIDE not in AE.
        private static final Collection<String> PROTEOMICS_EXPERIMENTS_S_IN_AE =
                ImmutableList.of("E-PROT-1", "E-PROT-3", "E-PROT-5");

        @Override
        public Collection<ExternallyAvailableContent> get(BaselineExperiment experiment) {
            return PROTEOMICS_EXPERIMENTS_S_IN_AE.contains(experiment.getAccession()) ?
                    super.get(experiment) :
                    ImmutableList.of();
        }
    }

    @Named
    public static class RnaSeqBaseline extends LinkToArrayExpress<BaselineExperiment> {}

    @Named
    public static class Differential extends LinkToArrayExpress<DifferentialExperiment> {}

    @Named
    public static class Microarray extends LinkToArrayExpress<MicroarrayExperiment> {
        @Override
        public Collection<ExternallyAvailableContent> get(MicroarrayExperiment experiment) {
            return experiment.getArrayDesignAccessions().stream()
                    .map(arrayDesignAccession -> new ExternallyAvailableContent(
                            formatLinkToArray.apply(arrayDesignAccession),
                            createIconForArray.apply(arrayDesignAccession)))
                    .collect(toImmutableList());
        }
    }
}
