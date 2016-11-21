package uk.ac.ebi.atlas.experimentimport;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentChecker {

    private final DataFileHub dataFileHub;
    private final ConfigurationTrader configurationTrader;

    @Inject
    public ExperimentChecker(DataFileHub dataFileHub,
                             ConfigurationTrader configurationTrader) {
        this.dataFileHub = dataFileHub;
        this.configurationTrader = configurationTrader;
    }

    public void checkAllFiles(String experimentAccession, ExperimentType experimentType) {

        // every experiment should have configuration, condensed SDRF and analysis methods file
        checkConfigurationFile(experimentAccession);
        checkResourceExistsAndIsReadable(dataFileHub.getExperimentFiles(experimentAccession).analysisMethods);
        checkResourceExistsAndIsReadable(dataFileHub.getExperimentFiles(experimentAccession).condensedSdrf);

        switch (experimentType) {
            case RNASEQ_MRNA_BASELINE:
            case PROTEOMICS_BASELINE:
                checkBaselineFiles(experimentAccession);
                break;
            case RNASEQ_MRNA_DIFFERENTIAL:
                checkDifferentialFiles(experimentAccession);
                break;
            case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
                checkMicroarray1ColourFiles(experimentAccession,
                        configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession)
                                .getArrayDesignAccessions());
                break;
            case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
                checkMicroarray2ColourFiles(experimentAccession,
                        configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession)
                                .getArrayDesignAccessions());
                break;
            default:
                throw new IllegalStateException("The specified experiment type is not supported.");
        }
    }


    void checkBaselineFiles(String experimentAccession) {
        DataFileHub.BaselineExperimentFiles experimentFiles = dataFileHub.getBaselineExperimentFiles(experimentAccession);
        checkResourceExistsAndIsReadable(experimentFiles.main);
        checkResourceExistsAndIsReadable(experimentFiles.factors);
    }

    void checkDifferentialFiles(String experimentAccession) {
        DataFileHub.DifferentialExperimentFiles experimentFiles =
                dataFileHub.getDifferentialExperimentFiles(experimentAccession);
        checkResourceExistsAndIsReadable(experimentFiles.analytics);
        checkResourceExistsAndIsReadable(experimentFiles.rawCounts);
    }

    void checkMicroarray1ColourFiles(String experimentAccession, Set<String> arrayDesigns) {

        for (String arrayDesign : arrayDesigns) {
            DataFileHub.MicroarrayExperimentFiles experimentFiles =
                    dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesign);

            checkResourceExistsAndIsReadable(experimentFiles.analytics);
            checkResourceExistsAndIsReadable(experimentFiles.normalizedExpressions);
        }
    }

    void checkMicroarray2ColourFiles(String experimentAccession, Set<String> arrayDesigns) {

        for (String arrayDesign : arrayDesigns) {
            DataFileHub.MicroarrayExperimentFiles experimentFiles =
                    dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesign);

            checkResourceExistsAndIsReadable(experimentFiles.analytics);
            checkResourceExistsAndIsReadable(experimentFiles.logFoldChanges);
        }
    }

    void checkConfigurationFile(String accession) {
        checkResourceExistsAndIsReadable(dataFileHub.getExperimentFiles(accession).configuration);
    }

    private void checkResourceExistsAndIsReadable(AtlasResource<?> resource){
        checkState(resource.exists(), "Required file does not exist: " + resource.toString());
        checkState(resource.isReadable(), "Required file can not be read: " + resource.toString());
    }
}
