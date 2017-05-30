package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.Validate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

@Named
public class RnaSeqBaselineProfileStreamFactory extends ProfileStreamKryoLayer<AssayGroup, BaselineExpression,
        BaselineExperiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>, BaselineProfile> {

    @Inject
    public RnaSeqBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(new Impl(dataFileHub));
    }

    static class Impl extends
            BaselineProfileStreamFactory<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>> {

        @Inject
        public Impl(DataFileHub dataFileHub) {
            super(dataFileHub);
        }

        @Override
        protected Collection<ObjectInputStream<String[]>> getDataFiles(BaselineExperiment experiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> options) {
            return ImmutableList.of(dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).dataFile(options.getExpressionUnit()).get());
        }

        @Override
        protected Map<Integer, AssayGroup> rowPositionsToDataColumns(BaselineExperiment experiment, String[] headers) {
            ImmutableMap.Builder<Integer, AssayGroup> b = ImmutableMap.builder();

            for (int i = 2; i < headers.length; i++) {
                String s = headers[i];
                AssayGroup assayGroup = experiment.getDataColumnDescriptor(s);
                Validate.notNull(assayGroup, MessageFormat.format("Unknown identifier in position {0}: {1}", i, s));
                b.put(new Integer(i), assayGroup);

            }

            Map<Integer, AssayGroup> result = b.build();

            Preconditions.checkState(result.size() == experiment.getDataColumnDescriptors().size(),
                    MessageFormat.format("Mismatch between data columns read in from the header:{0}, and data columns in experiment:{1}",
                            result.size(), experiment.getDataColumnDescriptors().size())
            );

            return result;
        }
    }
}