package uk.ac.ebi.atlas.utils;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashSet;

/**
 * TODO: this code could be much shorter. Make the DifferentialAnalytics and BaselineAnalytics inherit from
 * Analytics,with has a method getGeneId(), make the analytics input streams inherit from a common parent, and write
 * one method that gets all gene ids for an experiment accession.
 */
@Named
public class ExpressionAtlasBioentityIdentifiersReader extends BioentityIdentifiersReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionAtlasBioentityIdentifiersReader.class);

    private ExpressionAtlasExperimentTrader experimentTrader;

    private BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;

    @Inject
    public ExpressionAtlasBioentityIdentifiersReader(ExpressionAtlasExperimentTrader experimentTrader,
                                                     BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory,
                                                     MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory,
                                                     RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory) {
        this.experimentTrader = experimentTrader;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
    }

    @Override
    protected int addBioentityIdentifiers(HashSet<String> bioentityIdentifiers, ExperimentType experimentType) throws IOException {
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

    private int addBioentityIdentifiersFromBaselineExperiments(HashSet<String> bioentityIdentifiers) throws IOException {
        int bioentityIdentifiersSizeWithoutNewElements = bioentityIdentifiers.size();

        for (String experimentAccession : experimentTrader.getBaselineExperimentAccessions()) {

            LOGGER.debug("Reading bioentity identifiers in {}", experimentAccession);

            ObjectInputStream<BaselineAnalytics> inputStream = baselineAnalyticsInputStreamFactory.create(experimentAccession, ExperimentType.RNASEQ_MRNA_BASELINE);
            BaselineAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                LOGGER.error(exception.getMessage());
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    private int addBioentityIdentifiersFromProteomicsBaselineExperiments(HashSet<String> bioentityIdentifiers) throws IOException {
        int bioentityIdentifiersSizeWithoutNewElements = bioentityIdentifiers.size();

        for (String experimentAccession : experimentTrader.getProteomicsBaselineExperimentAccessions()) {

            LOGGER.debug("Reading bioentity identifiers in {}", experimentAccession);

            ObjectInputStream<BaselineAnalytics> inputStream = baselineAnalyticsInputStreamFactory.create
                    (experimentAccession, ExperimentType.PROTEOMICS_BASELINE);
            BaselineAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                LOGGER.error(exception.getMessage());
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    private int addBioentityIdentifiersFromMicroarrayExperiments(HashSet<String> bioentityIdentifiers) throws IOException {
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
                    LOGGER.error(exception.getMessage());
                }
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    private int addBioentityIdentifiersFromRnaSeqDifferentialExperiments(HashSet<String> bioentityIdentifiers) throws IOException {
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
                LOGGER.error(exception.getMessage());
            }
        }

        return bioentityIdentifiers.size() - bioentityIdentifiersSizeWithoutNewElements;
    }

    @Override
    public HashSet<String> getBioentityIdsFromExperiment(String experimentAccession) throws IOException {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

        HashSet<String> bioentityIdentifiers = new HashSet<>();

        if (experiment.getType().isBaseline()) {
            ObjectInputStream<BaselineAnalytics> inputStream = baselineAnalyticsInputStreamFactory.create
                    (experimentAccession, experiment.getType());
            BaselineAnalytics analytics = inputStream.readNext();
            while (analytics != null) {
                bioentityIdentifiers.add(analytics.getGeneId());
                analytics = inputStream.readNext();
            }
            try {
                inputStream.close();
            } catch (IOException exception) {
                LOGGER.error(exception.getMessage());
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
                        LOGGER.error(exception.getMessage());
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
                    LOGGER.error(exception.getMessage());
                }
            }

        }

        return bioentityIdentifiers;
    }
}
