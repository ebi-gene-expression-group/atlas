package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/*
Does not close TSV files! :(
 */
@Named
@Scope("prototype")
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
            headerIdsMatchConfigurationXml(rnaSeqIdsFromHeader(experimentFiles.dataFile(dataFile).get().readNext()), experimentAccession);
        }
    }

    String[] rnaSeqIdsFromHeader(String[] header) {
        return ArrayUtils.subarray(header, 2, header.length);
    }

    void checkProteomicsBaselineFiles(String experimentAccession) {
        DataFileHub.ProteomicsBaselineExperimentFiles experimentFiles = dataFileHub.getProteomicsBaselineExperimentFiles(experimentAccession);
        checkBaselineFiles(experimentFiles);
        checkResourceExistsAndIsReadable(experimentFiles.main);
        headerIdsMatchConfigurationXml(proteomicsIdsFromHeader(experimentFiles.main.get().readNext()), experimentAccession);
    }

    String[] proteomicsIdsFromHeader(String[] header) {
        return StringArrayUtil.substringBefore(StringArrayUtil.filterBySubstring(header, "WithInSampleAbundance"), ".");
    }

    void headerIdsMatchConfigurationXml(String[] assayGroupIds, String experimentAccession) {

        Preconditions.checkState(ImmutableSet.copyOf(assayGroupIds).equals(FluentIterable.from(configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups()).transform(new Function<AssayGroup, String>() {
            @Nullable
            @Override
            public String apply(@Nullable AssayGroup assayGroup) {
                return assayGroup.getId();
            }
        }).toSet()), "Ids in data file not matching in {0}-configuration.xml", experimentAccession);

    }


    void checkBaselineFiles(DataFileHub.BaselineExperimentFiles experimentFiles) {
        checkResourceExistsAndIsReadable(experimentFiles.factors);
    }

    void checkDifferentialFiles(String experimentAccession) {
        DataFileHub.RnaSeqDifferentialExperimentFiles experimentFiles =
                dataFileHub.getRnaSeqDifferentialExperimentFiles(experimentAccession);
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

    private void checkResourceExistsAndIsReadable(AtlasResource<?> resource) {
        checkState(resource.exists(), "Required file does not exist: " + resource.toString());
        checkState(resource.isReadable(), "Required file can not be read: " + resource.toString());
    }
}
