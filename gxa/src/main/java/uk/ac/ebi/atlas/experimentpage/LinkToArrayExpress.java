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
import java.util.Collections;

public abstract class LinkToArrayExpress<E extends Experiment> extends ExternallyAvailableContent.Supplier<E> {

    @Override
    public ExternallyAvailableContent.ContentType contentType() {
        return ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION;
    }

    @Override
    public Collection<ExternallyAvailableContent> get(E experiment) {
        return Collections.singleton(new ExternallyAvailableContent("https://www.ebi.ac.uk/arrayexpress/experiments/"+experiment.getAccession(),
                ExternallyAvailableContent.Description.create(
                        "icon-ae", MessageFormat.format("ArrayExpress: experiment {0}", experiment.getAccession())
                )));
    }

    @Named
    public static class ProteomicsBaseline extends LinkToArrayExpress<BaselineExperiment> {

        /*
        In the future we will source proteomics experiments from PRIDE, and one requirement is that they are not backported into ArrayExpress.
        E-PROT-6 is an example experiment from PRIDE not in AE.
        */
        static Collection<String> PROTEOMICS_EXPS_STILL_IN_AE = ImmutableList.of(
                "E-PROT-1",
                "E-PROT-3",
                "E-PROT-5"
        );

        @Override
        public Collection<ExternallyAvailableContent> get(BaselineExperiment experiment) {
            return PROTEOMICS_EXPS_STILL_IN_AE.contains(experiment.getAccession()) ? super.get(experiment) : ImmutableList.of();
        }
    }

    @Named
    public static class RnaSeqBaseline extends LinkToArrayExpress<BaselineExperiment> {

    }

    @Named
    public static class Differential extends LinkToArrayExpress<DifferentialExperiment> {

    }


    @Named
    public static class Microarray extends LinkToArrayExpress<MicroarrayExperiment> {

        @Override
        public Collection<ExternallyAvailableContent> get(MicroarrayExperiment experiment) {
            ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
            b.addAll(super.get(experiment));
            for(String arrayDesign : experiment.getArrayDesignAccessions()){
                b.add(new ExternallyAvailableContent("https://www.ebi.ac.uk/arrayexpress/arrays/"+arrayDesign,
                        ExternallyAvailableContent.Description.create(
                                "icon-ae", MessageFormat.format("ArrayExpress: array design {0}", arrayDesign)
               )));
            }
            return b.build();
        }
    }



}
