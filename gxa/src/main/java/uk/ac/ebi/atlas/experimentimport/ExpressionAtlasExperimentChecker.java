package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

/*
Does not close TSV files! :(
 */
@Named
public class ExpressionAtlasExperimentChecker implements ExperimentChecker {

    private final DataFileHub dataFileHub;
    private final ConfigurationTrader configurationTrader;

    @Inject
    public ExpressionAtlasExperimentChecker(DataFileHub dataFileHub,
                                            ConfigurationTrader configurationTrader) {
        this.dataFileHub = dataFileHub;
        this.configurationTrader = configurationTrader;
    }

    @Override
    public void checkAllFiles(String experimentAccession, ExperimentType experimentType) {

        // every experiment should have configuration, condensed SDRF and analysis methods file
        checkConfigurationFile(experimentAccession);
        checkResourceExistsAndIsReadable(dataFileHub.getExperimentFiles(experimentAccession).analysisMethods);
        checkResourceExistsAndIsReadable(dataFileHub.getExperimentFiles(experimentAccession).condensedSdrf);

        switch (experimentType) {
            case RNASEQ_MRNA_BASELINE:
                checkRnaSeqBaselineFiles(experimentAccession);
                break;
            case PROTEOMICS_BASELINE:
                checkProteomicsBaselineFiles(experimentAccession);
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

    void checkRnaSeqBaselineFiles(String experimentAccession) {
        DataFileHub.RnaSeqBaselineExperimentFiles experimentFiles = dataFileHub.getRnaSeqBaselineExperimentFiles(experimentAccession);
        checkBaselineFiles(experimentFiles);
        ImmutableList<ExpressionUnit.Absolute.Rna> dataFiles = experimentFiles.dataFiles();
        Preconditions.checkState(dataFiles.size()> 0, MessageFormat.format("No data files (FPKM/TPM) present for {0}!", experimentAccession));
        for(ExpressionUnit.Absolute.Rna dataFile: dataFiles){
            checkResourceExistsAndIsReadable(experimentFiles.dataFile(dataFile));
            assayGroupIdsInHeaderMatchConfigurationXml(rnaSeqIdsFromHeader(experimentFiles.dataFile(dataFile).get().readNext()), experimentAccession);
        }

        AtlasResource<ObjectInputStream<String[]>> transcripts = experimentFiles.transcriptsTpms;
        if(transcripts.exists()){
            biologicalReplicateIdsInHeaderMatchConfigurationXml(transcriptIdsFromHeader(transcripts.get().readNext()), experimentAccession);
        }

    }

    private String[] rnaSeqIdsFromHeader(String[] header) {
        return ArrayUtils.subarray(header, 2, header.length);
    }

    private String[] transcriptIdsFromHeader(String[] header) {
        return ArrayUtils.subarray(header, 3, header.length);
    }

    void checkProteomicsBaselineFiles(String experimentAccession) {
        DataFileHub.ProteomicsBaselineExperimentFiles experimentFiles = dataFileHub.getProteomicsBaselineExperimentFiles(experimentAccession);
        checkBaselineFiles(experimentFiles);
        checkResourceExistsAndIsReadable(experimentFiles.main);
        assayGroupIdsInHeaderMatchConfigurationXml(proteomicsIdsFromHeader(experimentFiles.main.get().readNext()), experimentAccession);
    }

    String[] proteomicsIdsFromHeader(String[] header) {
        return StringArrayUtil.substringBefore(StringArrayUtil.filterBySubstring(header, "WithInSampleAbundance"), ".");
    }

    private void biologicalReplicateIdsInHeaderMatchConfigurationXml(String[] biologicalReplicateIds, String experimentAccession) {
        Set<String> idsInConfiguration=configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups().stream()
                .flatMap(a -> a.biologicalReplicatesForThisDataColumn().stream())
                .map(DescribesDataColumns::getId)
                .collect(Collectors.toSet());
        Preconditions.checkState(
                ImmutableSet.copyOf(biologicalReplicateIds).equals(idsInConfiguration),
                MessageFormat.format("Biological replicate ids in data file (#:{1}) not matching ids in {0}-configuration.xml (#:{2})", experimentAccession, biologicalReplicateIds.length, idsInConfiguration.size()));
    }

    private void assayGroupIdsInHeaderMatchConfigurationXml(String[] assayGroupIds, String experimentAccession) {
        Set<String> idsInConfiguration=configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups().stream()
                .map(DescribesDataColumns::getId)
                .collect(Collectors.toSet());
        Preconditions.checkState(
                ImmutableSet.copyOf(assayGroupIds).equals(idsInConfiguration),
                MessageFormat.format("Assay group ids in data file (#:{1}) not matching ids in {0}-configuration.xml (#:{2})", experimentAccession, assayGroupIds.length, idsInConfiguration.size()));
    }


    private void checkBaselineFiles(DataFileHub.BaselineExperimentFiles experimentFiles) {
        checkResourceExistsAndIsReadable(experimentFiles.factors);
    }

    private void checkDifferentialFiles(String experimentAccession) {
        DataFileHub.RnaSeqDifferentialExperimentFiles experimentFiles =
                dataFileHub.getRnaSeqDifferentialExperimentFiles(experimentAccession);
        checkResourceExistsAndIsReadable(experimentFiles.analytics);
        checkResourceExistsAndIsReadable(experimentFiles.rawCounts);
    }

    private void checkMicroarray1ColourFiles(String experimentAccession, Set<String> arrayDesigns) {
        for (String arrayDesign : arrayDesigns) {
            DataFileHub.MicroarrayExperimentFiles experimentFiles =
                    dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesign);

            checkResourceExistsAndIsReadable(experimentFiles.analytics);
            checkResourceExistsAndIsReadable(experimentFiles.normalizedExpressions);
        }
    }

    private void checkMicroarray2ColourFiles(String experimentAccession, Set<String> arrayDesigns) {
        for (String arrayDesign : arrayDesigns) {
            DataFileHub.MicroarrayExperimentFiles experimentFiles =
                    dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesign);

            checkResourceExistsAndIsReadable(experimentFiles.analytics);
            checkResourceExistsAndIsReadable(experimentFiles.logFoldChanges);
        }
    }

    private void checkConfigurationFile(String accession) {
        checkResourceExistsAndIsReadable(dataFileHub.getExperimentFiles(accession).configuration);
    }

    private void checkResourceExistsAndIsReadable(AtlasResource<?> resource) {
        checkState(resource.exists(), "Required file does not exist: " + resource.toString());
        checkState(resource.isReadable(), "Required file can not be read: " + resource.toString());
    }
}
