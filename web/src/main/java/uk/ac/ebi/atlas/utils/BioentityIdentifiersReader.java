package uk.ac.ebi.atlas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.*;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 16/10/15.
 */
@Named
@Scope("singleton")
public class BioentityIdentifiersReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIdentifiersReader.class);

    private ExperimentTrader experimentTrader;

    private BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;
    private MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;

    @Inject
    public BioentityIdentifiersReader(ExperimentTrader experimentTrader,
                                      BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory,
                                      ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory,
                                      MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory,
                                      RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory) {
        this.experimentTrader = experimentTrader;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.proteomicsBaselineAnalyticsInputStreamFactory = proteomicsBaselineAnalyticsInputStreamFactory;
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
    }

    /*
    In this class we’re not using ImmutableSet because an immutable builder has a copy of all the elements in memory (including repeated ones)
    before flattening the set when we call builder.build() and therefore using up much more memory than needed in this case, where the number
    of repeated elements is very high.
     */

    public HashSet<String> getBioentityIdsFromAllExperiments() {
        return getBioentityIdsFromExperiments(
                ExperimentType.MICROARRAY_ANY,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                ExperimentType.RNASEQ_MRNA_BASELINE,
                ExperimentType.PROTEOMICS_BASELINE);
    }

    public HashSet<String> getBioentityIdsFromExperiments(ExperimentType... experimentTypes) {

        HashSet<String> bioentityIds = new HashSet<>();

        StopWatch stopWatch = new org.springframework.util.StopWatch(getClass().getSimpleName());
        stopWatch.start();
        LOGGER.info("Reading all bioentity identifiers from analytics TSV/kryo files...");

        for (ExperimentType experimentType : experimentTypes) {
            LOGGER.info("Reading all bioentity identifiers for experiments of type {}...", experimentType.getDescription());

            int count = addBioentityIdentifiers(bioentityIds, experimentType);

            LOGGER.info(
                "Finished reading all bioentity identifiers for experiments of type {}: {} bioentities added (total {})",
                experimentType.getDescription(), count, bioentityIds.size());
        }

        stopWatch.stop();
        LOGGER.info("Built a set of %,d bioentity identifiers in %s seconds", bioentityIds.size(), stopWatch.getTotalTimeSeconds());

        return bioentityIds;
    }

    private int addBioentityIdentifiers(HashSet<String> bioentityIdentifiers, ExperimentType experimentType) {
        if (experimentType.isBaseline()) {

            if (experimentType.isProteomicsBaseline()) {
                return addBioentityIdentifiersFromProteomicsBaselineExperiments(bioentityIdentifiers);
            } else {
                return addBioentityIdentifiersFromBaselineExperiments(bioentityIdentifiers);
            }

        } else {  //if (experimentType.isDifferential()) {

            if (experimentType.isMicroarray()) {
                return addBioentityIdentifiersFromMicroarrayExperiments(bioentityIdentifiers);
            } else {
                return addBioentityIdentifiersFromRnaSeqDifferentialExperiments(bioentityIdentifiers);
            }

        }
    }

    private int addBioentityIdentifiersFromBaselineExperiments(HashSet<String> bioentityIdentifiers) {
        int bioentityIdentifiersSizeWithoutNewElements = bioentityIdentifiers.size();

        for (String experimentAccession : experimentTrader.getBaselineExperimentAccessions()) {

            LOGGER.debug("Reading bioentity identifiers in {}", experimentAccession);

            BaselineAnalyticsInputStream inputStream = baselineAnalyticsInputStreamFactory.create(experimentAccession);
            BaselineAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    private int addBioentityIdentifiersFromProteomicsBaselineExperiments(HashSet<String> bioentityIdentifiers) {
        int bioentityIdentifiersSizeWithoutNewElements = bioentityIdentifiers.size();

        for (String experimentAccession : experimentTrader.getProteomicsBaselineExperimentAccessions()) {

            LOGGER.debug("Reading bioentity identifiers in {}", experimentAccession);

            ProteomicsBaselineAnalyticsInputStream inputStream = proteomicsBaselineAnalyticsInputStreamFactory.create(experimentAccession);
            BaselineAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    private int addBioentityIdentifiersFromMicroarrayExperiments(HashSet<String> bioentityIdentifiers) {
        int bioentityIdentifiersSizeWithoutNewElements = bioentityIdentifiers.size();

        for (String experimentAccession : experimentTrader.getMicroarrayExperimentAccessions()) {
            LOGGER.debug("Reading bioentity identifiers in {}", experimentAccession);
            MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(experimentAccession);

            for (String arrayDesign : experiment.getArrayDesignAccessions()) {
                MicroarrayDifferentialAnalyticsInputStream inputStream = microarrayDifferentialAnalyticsInputStreamFactory.create(experimentAccession, arrayDesign);

                DifferentialAnalytics analytics = inputStream.readNext();
                while (analytics != null) {
                    bioentityIdentifiers.add(analytics.getGeneId());
                    analytics = inputStream.readNext();
                }
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    private int addBioentityIdentifiersFromRnaSeqDifferentialExperiments(HashSet<String> bioentityIdentifiers) {
        int bioentityIdentifiersSizeWithoutNewElements = bioentityIdentifiers.size();

        for (String experimentAccession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()) {
            LOGGER.debug("Reading bioentity identifiers in {}", experimentAccession);
            RnaSeqDifferentialAnalyticsInputStream inputStream = rnaSeqDifferentialAnalyticsInputStreamFactory.create(experimentAccession);

            DifferentialAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    public HashSet<String> getBioentityIdsFromExperiment(String experimentAccession) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

        HashSet<String> bioentityIdentifiers = new HashSet<>();

        if (experiment.getType().isBaseline()) {
            ObjectInputStream<BaselineAnalytics> inputStream;
            if (experiment.getType().isProteomicsBaseline()) {
                inputStream = proteomicsBaselineAnalyticsInputStreamFactory.create(experimentAccession);
            } else {
                inputStream = baselineAnalyticsInputStreamFactory.create(experimentAccession);
            }
            BaselineAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        } else {  //if (experimentType.isDifferential()) {

            if (experiment.getType().isMicroarray()) {
                for (String arrayDesign : ((MicroarrayExperiment) experiment).getArrayDesignAccessions()) {
                    MicroarrayDifferentialAnalyticsInputStream inputStream = microarrayDifferentialAnalyticsInputStreamFactory.create(experimentAccession, arrayDesign);

                    DifferentialAnalytics analytics = inputStream.readNext();
                    while (analytics != null) {
                        bioentityIdentifiers.add(analytics.getGeneId());
                        analytics = inputStream.readNext();
                    }
                    try {
                        inputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }

            } else {
                RnaSeqDifferentialAnalyticsInputStream inputStream = rnaSeqDifferentialAnalyticsInputStreamFactory.create(experimentAccession);

                DifferentialAnalytics analytics = inputStream.readNext();
                while (analytics != null) {
                    bioentityIdentifiers.add(analytics.getGeneId());
                    analytics = inputStream.readNext();
                }
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

        }

        return bioentityIdentifiers;
    }
}
