package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.Validate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

@Named
public class ProteomicsBaselineProfileStreamFactory extends ProfileStreamKryoLayer<AssayGroup, BaselineExpression,
        BaselineExperiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein>, BaselineProfile> {

    @Inject
    public ProteomicsBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(new Impl(dataFileHub));
    }

    static class Impl
            extends BaselineProfileStreamFactory<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein>> {
        private static final String AGREED_POSTFIX_FOR_DATA_COLUMNS = ".WithInSampleAbundance";

        @Inject
        public Impl(DataFileHub dataFileHub) {
            super(dataFileHub);
        }

        @Override
        protected Collection<ObjectInputStream<String[]>> getDataFiles(BaselineExperiment experiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein> options) {
            return ImmutableList.of(
                    dataFileHub.getProteomicsBaselineExperimentFiles(experiment.getAccession()).main.get()
            );
        }

        @Override
        protected Map<Integer, AssayGroup> rowPositionsToDataColumns(BaselineExperiment experiment, String[] headers) {
            ImmutableMap.Builder<Integer, AssayGroup> b = ImmutableMap.builder();

            for (int i = 2; i < headers.length; i++) {
                String s = headers[i];
                if (s.endsWith(AGREED_POSTFIX_FOR_DATA_COLUMNS)) {
                    AssayGroup assayGroup = experiment.getDataColumnDescriptor(s.replace(AGREED_POSTFIX_FOR_DATA_COLUMNS, ""));
                    Validate.notNull(assayGroup, MessageFormat.format("Unknown identifier in position {0}: {1}", i, s));
                    b.put(i, assayGroup);
                }
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