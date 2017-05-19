package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.TsvInputStream;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;

public abstract class BaselineProfileStreamFactory<StreamOptions extends BaselineProfileStreamOptions<?>> extends ProfileStreamFactory<AssayGroup, BaselineExpression,
        BaselineExperiment, StreamOptions,BaselineProfile>{

    BaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    protected ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment, StreamOptions options, Reader dataFile, ExpressionsRowDeserializerBuilder<BaselineExpression> readRowBuilder){
        return new TsvInputStream<>(dataFile, readRowBuilder, filterExpressions(experiment, options), experiment, 2,
                new Function<String[], BaselineProfile>() {
                    @Nullable
                    @Override
                    public BaselineProfile apply(@Nullable String[] identifiers) {
                        return new BaselineProfile(identifiers[0], identifiers[1]);
                    }
                });
    }

    @Override
    protected Predicate<BaselineExpression> filterExpressions(BaselineExperiment experiment, StreamOptions options) {
        IsBaselineExpressionAboveCutoffAndForFilterFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(options.getCutoff());
        //TODO pay attention to other options
        // we used to only pick up expressions that will later be retrieved
        // e.g.
        // baselineExpressionFilter.setFilterFactors(filterFactors);
        return baselineExpressionFilter;
    }
}
