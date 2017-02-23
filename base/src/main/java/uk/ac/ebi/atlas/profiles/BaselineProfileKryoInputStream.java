package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;

import java.io.IOException;

public class BaselineProfileKryoInputStream implements ObjectInputStream<BaselineProfile> {

    private BaselineExpressionsKryoReader baselineExpressionsKryoReader;

    private final BaselineExperiment baselineExperiment;

    private final Predicate<BaselineExpression> filterExpressions;


    public BaselineProfileKryoInputStream(BaselineExpressionsKryoReader baselineExpressionsKryoReader,
                                          BaselineExperiment baselineExperiment,
                                          Predicate<BaselineExpression> filterExpressions) {
        this.baselineExpressionsKryoReader = baselineExpressionsKryoReader;
        this.baselineExperiment = baselineExperiment;
        this.filterExpressions = filterExpressions;
    }

    @Override
    public BaselineProfile readNext() {
        BaselineProfile geneProfile;

        do {
            if (!baselineExpressionsKryoReader.readLine()) {
                return null;
            }
            geneProfile =  buildObjectFromValues(baselineExpressionsKryoReader.getGeneId(), baselineExpressionsKryoReader.getGeneName(), baselineExpressionsKryoReader.getExpressions());

        } while (geneProfile == null);

        return geneProfile;
    }

    protected BaselineProfile buildObjectFromValues(String geneId, String geneName, BaselineExpression[] expressions) {
        BaselineProfile baselineProfile = new BaselineProfile(geneId, geneName);

        for(BaselineExpression baselineExpression : expressions){
            if(filterExpressions.apply(baselineExpression)){
                baselineProfile.add(baselineExperiment.getDataColumnDescriptor(baselineExpression.getDataColumnDescriptorId()), baselineExpression);
            }
        }

        return baselineProfile;
    }

    @Override
    public void close() throws IOException {
        baselineExpressionsKryoReader.close();
    }

}
