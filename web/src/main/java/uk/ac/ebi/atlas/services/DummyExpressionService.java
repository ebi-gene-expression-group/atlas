package uk.ac.ebi.atlas.services;

import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

//ToDo: remove after replacing it with RankExpressionLevels command
@Named("dummyExpressionService")
public class DummyExpressionService implements ExpressionLevelService {


    @Override
    public List<ExpressionLevel> getExpressionLevels() {

        List<ExpressionLevel> expressionLevels = new ArrayList<>();

        ExperimentRun experimentRun = new ExperimentRun("RUN_ACCESSION_1")
                                    .addFactorValue("f1", "v1")
                                    .addFactorValue("f2", "v2");

        expressionLevels.add(new ExpressionLevel("ENST1", 100.0001, experimentRun));

        experimentRun = new ExperimentRun("RUN_ACCESSION_1")
                                    .addFactorValue("f1_1", "v1_1")
                                    .addFactorValue("f2_1", "v2_1");

        expressionLevels.add(new ExpressionLevel("ENST2", 100, experimentRun));

        return expressionLevels;
    }
}
