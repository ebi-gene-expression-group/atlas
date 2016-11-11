package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.DifferentialExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.DifferentialExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Named
public class ExperimentDataPointStreamFactory {
    private final EFOLookupService efoParentsLookupService;
    private final MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private final RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final DifferentialConditionsBuilder diffConditionsBuilder;
    private final BaselineConditionsBuilder baselineConditionsBuilder;

    @Inject
    public ExperimentDataPointStreamFactory(EFOLookupService efoParentsLookupService,
                                            MicroarrayDifferentialAnalyticsInputStreamFactory
                                            microarrayDifferentialAnalyticsInputStreamFactory,
                                            RnaSeqDifferentialAnalyticsInputStreamFactory
                                                        rnaSeqDifferentialAnalyticsInputStreamFactory,
                                            BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory) {

        this.efoParentsLookupService = efoParentsLookupService;
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.diffConditionsBuilder = new DifferentialConditionsBuilder(efoParentsLookupService);
        this.baselineConditionsBuilder = new BaselineConditionsBuilder(efoParentsLookupService);

    }

    public Iterable<? extends ExperimentDataPoint> stream(Experiment experiment){
        if(experiment instanceof MicroarrayExperiment){
            return stream((MicroarrayExperiment) experiment);
        } else if (experiment instanceof DifferentialExperiment){
            return stream((DifferentialExperiment) experiment);
        } else if (experiment instanceof BaselineExperiment) {
            return stream((BaselineExperiment) experiment);
        } else {
            return ImmutableList.of();
        }
    }

    private Iterable<BaselineExperimentDataPoint> stream(BaselineExperiment experiment){

        return new BaselineExperimentDataPointStream(
                experiment,
                new IterableObjectInputStream<>(
                        baselineAnalyticsInputStreamFactory.create(
                                experiment.getAccession(), experiment.getType())
                ),
                buildAssayGroupIdToConditionsSearchTerms(experiment,
                        efoParentsLookupService.expandOntologyTerms(
                                experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession())
                )
        );
    }

    private Iterable<DifferentialExperimentDataPoint> stream(DifferentialExperiment experiment){

        return new DifferentialExperimentDataPointStream(experiment,
                new IterableObjectInputStream<>(
                        rnaSeqDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession())
                ),
                buildConditionSearchTermsByAssayGroupId(experiment,
                        efoParentsLookupService
                                .expandOntologyTerms(experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession())
                ),
                buildNumReplicatesByContrastId(experiment)
        );
    }

    private Iterable<DifferentialExperimentDataPoint> stream(final MicroarrayExperiment experiment){

        class MicroarrayExperimentDataPointIterator extends UnmodifiableIterator<DifferentialExperimentDataPoint> {

            private final Iterator<String> arrayDesigns;
            private Iterator<DifferentialExperimentDataPoint> current = null;
            MicroarrayExperimentDataPointIterator(Iterator<String> arrayDesigns){
                this.arrayDesigns = arrayDesigns;
            }
            @Override
            public boolean hasNext() {
                return arrayDesigns.hasNext() || (current != null && current.hasNext());
            }

            @Override
            public DifferentialExperimentDataPoint next() {
                if(current == null || !current.hasNext()){
                    current = stream(experiment, arrayDesigns.next()).iterator();
                }
                return current.next();
            }
        }

        return new Iterable<DifferentialExperimentDataPoint>() {
            @Override
            public Iterator<DifferentialExperimentDataPoint> iterator() {
                return new MicroarrayExperimentDataPointIterator(experiment.getArrayDesignAccessions().iterator());
            }
        };
    }

    private Iterable<DifferentialExperimentDataPoint> stream(MicroarrayExperiment experiment, String designElement){

        return new DifferentialExperimentDataPointStream(experiment,
                new IterableObjectInputStream<>(
                        microarrayDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession(),designElement)
                ),
                buildConditionSearchTermsByAssayGroupId(experiment,
                        efoParentsLookupService
                                .expandOntologyTerms(experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession())
                ),
                buildNumReplicatesByContrastId(experiment)
        );
    }

    private Map<String, Integer> buildNumReplicatesByContrastId(DifferentialExperiment experiment) {
        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();

        for (Contrast contrast : experiment.getContrasts()) {
            int numReplicates = Math.min(contrast.getReferenceAssayGroup().getReplicates(), contrast.getTestAssayGroup().getReplicates());
            builder.put(contrast.getId(), numReplicates);
        }

        return builder.build();
    }

    private ImmutableSetMultimap<String, String> buildAssayGroupIdToConditionsSearchTerms(BaselineExperiment experiment, SetMultimap<String, String> AssayIdToOntologyAccessions) {
        Collection<Condition> conditions = baselineConditionsBuilder.buildProperties(experiment, AssayIdToOntologyAccessions);
        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (Condition condition : conditions) {
            builder.putAll(condition.getAssayGroupId(), condition.getValues());
        }

        return builder.build();
    }

    private ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(DifferentialExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = diffConditionsBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();

    }
}
