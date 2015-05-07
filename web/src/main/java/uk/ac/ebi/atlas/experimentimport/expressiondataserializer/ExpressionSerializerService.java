package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 02/04/15.
 */
@Named
@Scope("singleton")
public class ExpressionSerializerService {

    private ExperimentTrader experimentTrader;
    private ExpressionSerializerFactory expressionSerializerFactory;



    @Inject
    public ExpressionSerializerService(ExperimentTrader experimentTrader, ExpressionSerializerFactory expressionSerializerFactory) {
        this.experimentTrader = experimentTrader;
        this.expressionSerializerFactory = expressionSerializerFactory;
    }


    public void serializeExpressionData(String experimentAccession) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        expressionSerializerFactory.getSerializer(experiment.getType()).serializeExpressionData(experimentAccession);
    }

}
