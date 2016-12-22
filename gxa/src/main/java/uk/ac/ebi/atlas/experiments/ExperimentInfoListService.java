
package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static uk.ac.ebi.atlas.model.experiment.ExperimentType.*;

@Named
public class ExperimentInfoListService {
    private ExpressionAtlasExperimentTrader experimentTrader;



    @Inject
    public ExperimentInfoListService(ExpressionAtlasExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public List<ExperimentInfo> listPublicExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        for(ExperimentType experimentType : ImmutableList.of(
                RNASEQ_MRNA_BASELINE, PROTEOMICS_BASELINE,
                RNASEQ_MRNA_DIFFERENTIAL,
                MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL, MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL
        )){
            for(Experiment experiment: experimentTrader.getPublicExperiments(experimentType)){
                experimentInfos.add(experiment.buildExperimentInfo());
            }
        }
        return experimentInfos;
    }

}