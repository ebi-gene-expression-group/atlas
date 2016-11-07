
package uk.ac.ebi.atlas.experiments;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.PublicExperimentTypesCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Named
@Scope("prototype")
public class ExperimentInfoListBuilder {

    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;

    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache;

    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private MicroarrayExperimentsCache microarrayExperimentsCache;

    private PublicExperimentTypesCache publicExperimentTypesCache;

    private ExperimentTrader experimentTrader;

    private ArrayDesignTrader arrayDesignTrader;


    @Inject
    public ExperimentInfoListBuilder(ExperimentTrader experimentTrader,
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

    public List<ExperimentInfo> build() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        experimentInfos.addAll(extractBaselineExperiments());
        experimentInfos.addAll(extractProteomicsBaselineExperiments());
        experimentInfos.addAll(extractRnaSeqDiffExperiments());
        experimentInfos.addAll(extractMicroarrayExperiments());

        for (ExperimentInfo experimentInfo : experimentInfos) {
            publicExperimentTypesCache.getExperimentType(experimentInfo.getExperimentAccession());
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractMicroarrayExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getMicroarrayExperimentAccessions()) {
            try {

                MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
                experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());
                experimentInfo.setArrayDesigns(experiment.getArrayDesignAccessions());
                experimentInfo.setArrayDesignNames(arrayDesignTrader.getArrayDesignNames(experiment.getArrayDesignAccessions()));

                experimentInfos.add(experimentInfo);

            } catch (ExecutionException e) {
                // continue;
            }
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractRnaSeqDiffExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()) {
            try {

                DifferentialExperiment experiment = rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
                experimentInfo.setNumberOfAssays(experiment.getAssayAccessions().size());
                experimentInfo.setNumberOfContrasts(experiment.getContrastIds().size());

                experimentInfos.add(experimentInfo);

            } catch (ExecutionException e) {
                // continue;
            }
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractBaselineExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getBaselineExperimentAccessions()) {
            try {

                BaselineExperiment experiment = rnaSeqBaselineExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getExperimentRunAccessions().size());

                experimentInfos.add(experimentInfo);

            } catch (ExecutionException e) {
                // continue;
            }
        }

        return experimentInfos;
    }

    protected List<ExperimentInfo> extractProteomicsBaselineExperiments() {

        List<ExperimentInfo> experimentInfos = Lists.newArrayList();

        for (String experimentAccession : experimentTrader.getProteomicsBaselineExperimentAccessions()) {
            try {

                  BaselineExperiment experiment = proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);

                ExperimentInfo experimentInfo = experiment.getExperimentInfo();
                experimentInfo.setNumberOfAssays(experiment.getExperimentRunAccessions().size());

                experimentInfos.add(experimentInfo);

            } catch (ExecutionException e) {
                // continue;
            }
        }

        return experimentInfos;
    }

}