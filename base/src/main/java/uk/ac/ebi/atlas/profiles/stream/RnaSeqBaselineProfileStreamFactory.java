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
public class RnaSeqBaselineProfileStreamFactory
        extends ProfileStreamFactory<AssayGroup,
                                       BaselineExpression,
                                       BaselineExperiment,
                                       BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>,
                                       BaselineProfile> {

    private final CreatesProfilesFromTsvFiles<AssayGroup,
                                              BaselineExpression,
                                              BaselineExperiment,
                                              BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>,
                                              BaselineProfile> profileStreamFactory;

    @Inject
    public RnaSeqBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        profileStreamFactory = new Impl(dataFileHub);
    }

    @Override
    public ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment,
                                                     BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> options,
                                                     Collection<String> keepGeneIds) {
        return profileStreamFactory.create(experiment, options, keepGeneIds);
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
                b.put(i, assayGroup);
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