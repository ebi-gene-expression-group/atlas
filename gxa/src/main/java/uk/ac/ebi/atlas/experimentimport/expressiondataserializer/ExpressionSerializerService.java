package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.KryoFile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.*;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.List;

@Named
public class ExpressionSerializerService {

    private final MicroarrayProfileStreamFactory microarrayProfileStreamFactory;
    private final RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;
    private final RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory;
    private final ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory;
    private final DataFileHub dataFileHub;


    @Inject
    public ExpressionSerializerService(DataFileHub dataFileHub){
        this(new MicroarrayProfileStreamFactory(dataFileHub),
                new RnaSeqProfileStreamFactory(dataFileHub),
                new RnaSeqBaselineProfileStreamFactory(dataFileHub),
                new ProteomicsBaselineProfileStreamFactory(dataFileHub),
                dataFileHub);
    }

    ExpressionSerializerService(
            MicroarrayProfileStreamFactory microarrayProfileStreamFactory,
            RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory,
            RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory,
            ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory,
            DataFileHub dataFileHub) {
        this.microarrayProfileStreamFactory = microarrayProfileStreamFactory;
        this.rnaSeqProfileStreamFactory = rnaSeqProfileStreamFactory;
        this.rnaSeqBaselineProfileStreamFactory = rnaSeqBaselineProfileStreamFactory;
        this.proteomicsBaselineProfileStreamFactory = proteomicsBaselineProfileStreamFactory;
        this.dataFileHub = dataFileHub;
    }

    public String kryoSerializeExpressionData(Experiment<?> experiment) {
        if (experiment.getType().isRnaSeqBaseline()) {
            return serializeAll(rnaSeqBaselineProfileStreamFactory, (BaselineExperiment) experiment, rnaSeqBaselineStreamOptions((BaselineExperiment) experiment));
        } else if (experiment.getType().isProteomicsBaseline()) {
            return serializeAll(proteomicsBaselineProfileStreamFactory, (BaselineExperiment) experiment, proteomicsBaselineStreamOptions((BaselineExperiment) experiment)
            );
        } else if (experiment.getType().isRnaSeqDifferential()) {
            return serializeAll(rnaSeqProfileStreamFactory, (DifferentialExperiment) experiment, rnaSeqStreamOptions((DifferentialExperiment) experiment));
        } else if (experiment.getType().isMicroarray()) {
            return serializeAll(microarrayProfileStreamFactory, (MicroarrayExperiment) experiment, microarrayProfileStreamOptions((MicroarrayExperiment) experiment));
        }
        return "skipped";
    }

    private ImmutableList<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>> rnaSeqBaselineStreamOptions(BaselineExperiment experiment) {
        ImmutableList.Builder<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>> b = ImmutableList.builder();

        for(ExpressionUnit.Absolute.Rna unit: dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).dataFiles()){
            RnaSeqBaselineRequestPreferences p = new RnaSeqBaselineRequestPreferences();
            p.setUnit(unit);
            b.add(new BaselineRequestContext<>(p, experiment));
        }

        return b.build();
    }

    private ImmutableList<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein>> proteomicsBaselineStreamOptions(BaselineExperiment experiment) {
        return ImmutableList.<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein>>of(
                new BaselineRequestContext<ExpressionUnit.Absolute.Protein>(new ProteomicsBaselineRequestPreferences(), experiment)
        );
    }

    private ImmutableList<RnaSeqRequestContext> rnaSeqStreamOptions(DifferentialExperiment experiment) {
        return ImmutableList.of(
                new RnaSeqRequestContext(new DifferentialRequestPreferences(), experiment)
        );
    }

    private ImmutableList<MicroarrayRequestContext> microarrayProfileStreamOptions(MicroarrayExperiment microarrayExperiment) {
        return ImmutableList.of(
                new MicroarrayRequestContext(new MicroarrayRequestPreferences(), microarrayExperiment)
        );
    }


    <StreamOptions extends ProfileStreamOptions<?>, E extends Experiment<?>> String serializeAll(ProfileStreamKryoLayer<?, ?, E, StreamOptions, ?> profileStreamKryoLayer, E experiment, List<StreamOptions> uses) {
        for (StreamOptions options : uses) {
            profileStreamKryoLayer.persist(experiment, options);
        }

        return MessageFormat.format("serialized {0} files", uses.size());
    }

    <StreamOptions extends ProfileStreamOptions<?>, E extends Experiment<?>> String deleteAll(E experiment, List<StreamOptions> uses) {
        int deletedFiles = 0;
        for (StreamOptions options : uses) {
            AtlasResource<KryoFile.Handle> file = dataFileHub.getKryoFile(experiment.getAccession(), options);
            if (file.exists()) {
                file.get().delete();
                deletedFiles += 1;
            }
        }

        return MessageFormat.format("removed {0} files", deletedFiles);
    }


    public String removeKryoFile(Experiment experiment) {
        if (experiment.getType().isRnaSeqBaseline()) {
            return deleteAll(experiment, rnaSeqBaselineStreamOptions((BaselineExperiment) experiment));
        } else if (experiment.getType().isProteomicsBaseline()) {
            return deleteAll(experiment, proteomicsBaselineStreamOptions((BaselineExperiment) experiment));
        } else if (experiment.getType().isRnaSeqDifferential()) {
            return deleteAll(experiment, rnaSeqStreamOptions((DifferentialExperiment) experiment));
        } else if (experiment.getType().isMicroarray()) {
            return deleteAll(experiment, microarrayProfileStreamOptions((MicroarrayExperiment) experiment));
        }
        return "skipped";
    }
}
