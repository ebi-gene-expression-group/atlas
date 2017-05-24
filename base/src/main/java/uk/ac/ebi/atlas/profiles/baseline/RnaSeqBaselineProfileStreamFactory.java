package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.Validate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.KryoFile;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.BaselineProfileKryoInputStream;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.Map;

@Named
public class RnaSeqBaselineProfileStreamFactory extends BaselineProfileStreamFactory<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>> {

    @Inject
    RnaSeqBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<BaselineExpression> getExpressionsRowDeserializerBuilder(final BaselineExperiment experiment) {
        return new ExpressionsRowDeserializerBuilder<BaselineExpression>() {
            @Override
            public ExpressionsRowDeserializer<BaselineExpression> build(String... tsvFileHeaders) {
                return new ExpressionsRowTsvDeserializerBaseline(rowPositionsToDataColumns(experiment, tsvFileHeaders));
            }
        };
    }

    @Override
    protected Reader getDataFileReader(BaselineExperiment experiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> options) {
        try {
            return dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).dataFile(options.getExpressionUnit()).getReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment,
                                                     BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> baselineProfileStreamOptions) {
        AtlasResource<KryoFile.Handle> kryoFile = dataFileHub.getKryoFile(experiment.getAccession(), baselineProfileStreamOptions.getExpressionUnit());
        if (kryoFile.exists()) {
            return new BaselineProfileKryoInputStream(
                    BaselineExpressionsKryoReader.create(kryoFile), experiment,
                    filterExpressions(experiment, baselineProfileStreamOptions));
        } else {
            return super.create(experiment, baselineProfileStreamOptions);
        }
    }


    Map<Integer, AssayGroup> rowPositionsToDataColumns(BaselineExperiment experiment, String[] headers){
        ImmutableMap.Builder<Integer, AssayGroup> b = ImmutableMap.builder();

        for(int i = 0; i< headers.length ; i++){
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
