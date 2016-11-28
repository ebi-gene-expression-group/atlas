
package uk.ac.ebi.atlas.experiments;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.PublicExperimentTypesCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ExperimentInfoListService {

    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;

    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    private PublicExperimentTypesCache publicExperimentTypesCache;

    private ExpressionAtlasExperimentTrader experimentTrader;

    private ArrayDesignTrader arrayDesignTrader;


    @Inject
    public ExperimentInfoListService(ExpressionAtlasExperimentTrader experimentTrader,
                                     RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache,
                                     ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache,
                                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                                     MicroarrayExperimentsCache microarrayExperimentsCache,
                                     PublicExperimentTypesCache publicExperimentTypesCache,
                                     ArrayDesignTrader arrayDesignTrader) {
        this.experimentTrader = experimentTrader;
        this.rnaSeqBaselineExperimentsCache = rnaSeqBaselineExperimentsCache;
        this.proteomicsBaselineExperimentsCache = proteomicsBaselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
        this.publicExperimentTypesCache = publicExperimentTypesCache;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    public List<ExperimentInfo> listPublicExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        experimentInfos.addAll(extractBaselineExperiments());
        experimentInfos.addAll(extractProteomicsBaselineExperiments());
        experimentInfos.addAll(extractRnaSeqDiffExperiments());
        experimentInfos.addAll(extractMicroarrayExperiments());

        //TODO This looks insane - possibly desired because we're using the experiments page to warm the caches? Wojtek
        for (ExperimentInfo experimentInfo : experimentInfos) {
            publicExperimentTypesCache.getExperimentType(experimentInfo.getExperimentAccession());
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractMicroarrayExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getMicroarrayExperimentAccessions()) {

                MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
                experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());
                experimentInfo.setArrayDesigns(experiment.getArrayDesignAccessions());
                experimentInfo.setArrayDesignNames(arrayDesignTrader.getArrayDesignNames(experiment.getArrayDesignAccessions()));

                experimentInfos.add(experimentInfo);

        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractRnaSeqDiffExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()) {

                DifferentialExperiment experiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
                experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
                experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());

                experimentInfos.add(experimentInfo);

        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractBaselineExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getBaselineExperimentAccessions()) {

                BaselineExperiment experiment = rnaSeqBaselineExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getExperimentRunAccessions().size());

                experimentInfos.add(experimentInfo);

        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractProteomicsBaselineExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getProteomicsBaselineExperimentAccessions()) {

                  BaselineExperiment experiment = proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getExperimentRunAccessions().size());

                experimentInfos.add(experimentInfo);

        }

        return experimentInfos;
    }

}