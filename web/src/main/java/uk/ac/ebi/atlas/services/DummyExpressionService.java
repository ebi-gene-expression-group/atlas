package uk.ac.ebi.atlas.services;

import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.FactorValue;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named("dummyExpressionService")
public class DummyExpressionService implements ExpressionLevelService {


    @Override
    public List<ExpressionLevel> getExpressionLevels() {

        List<ExpressionLevel> expressionLevels = new ArrayList<>();

        Set<FactorValue> factorValues1 = new HashSet<>();
        factorValues1.add(new FactorValue("f1", "v1"));
        factorValues1.add(new FactorValue("f2", "v2"));

        expressionLevels.add(new ExpressionLevel("ENST1", factorValues1, 100));

        Set<FactorValue> factorValues2 = new HashSet<>();
        factorValues2.add(new FactorValue("f2_1", "v_2_1"));
        factorValues2.add(new FactorValue("f2", "v3"));

        expressionLevels.add(new ExpressionLevel("ENST2", factorValues2, 100));

        return expressionLevels;
    }
}
