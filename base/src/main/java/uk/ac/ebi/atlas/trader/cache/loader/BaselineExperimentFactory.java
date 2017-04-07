package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class BaselineExperimentFactory implements ExperimentFactory<BaselineExperiment> {

    private final ExperimentType experimentType;
    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;
    private final DataFileHub dataFileHub;

    public BaselineExperimentFactory(ExperimentType experimentType, ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory, DataFileHub dataFileHub) {
        this.experimentType = experimentType;
        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
        this.dataFileHub = dataFileHub;
    }


    @Override
    public BaselineExperiment create(ExperimentDTO experimentDTO, String experimentDescription,
                                     ExperimentDesign experimentDesign) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        ExperimentConfiguration configuration = configurationTrader.getExperimentConfiguration(experimentAccession);
        BaselineExperimentConfiguration factorsConfig = configurationTrader.getBaselineFactorsConfiguration(experimentAccession);

        List<AssayGroup> assayGroups;

        if (factorsConfig.orderCurated()) {
            assayGroups = configuration.getAssayGroups();
        } else {
            final List<String> assayGroupIds = Arrays.asList(readOrderedAssayGroupIds(experimentAccession, experimentDTO.getExperimentType()));

            assayGroups = FluentIterable.from(configuration.getAssayGroups()).toSortedList(new Comparator<AssayGroup>() {
                @Override
                public int compare(AssayGroup o1, AssayGroup o2) {
                    return assayGroupIds.indexOf(o1.getId()) - assayGroupIds.indexOf(o2.getId());
                }
            });
        }

        return new BaselineExperimentBuilder()
                .ofType(experimentType)
                .forSpecies(speciesFactory.create(experimentDTO.getSpecies()))
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withDisclaimer(factorsConfig.disclaimer())
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withAssayGroups(assayGroups)
                .withExperimentDesign(experimentDesign)
                .withDisplayDefaults(ExperimentDisplayDefaults.create(
                        factorsConfig.getDefaultFilterFactors(),
                        factorsConfig.getDefaultQueryFactorType(),
                        factorsConfig.getMenuFilterFactorTypes()))
                .withDataProviderURL(factorsConfig.getDataProviderURL())
                .withDataProviderDescription(factorsConfig.getDataProviderDescription())
                .withRData(configuration.hasRData())
                .withAlternativeViews(extractAlternativeViews(factorsConfig))
                .create();

    }

    private Pair<List<String>, List<String>> extractAlternativeViews(BaselineExperimentConfiguration factorsConfig) {
        List<String> accessions = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        for (String alternativeViewAccession : factorsConfig.getAlternativeViews()) {
            accessions.add(alternativeViewAccession);
            descriptions.add("View by " + configurationTrader.getBaselineFactorsConfiguration
                    (alternativeViewAccession).getDefaultQueryFactorType().toLowerCase());
        }
        return Pair.of(accessions, descriptions);
    }

    private String[] readOrderedAssayGroupIds(String experimentAccession , ExperimentType experimentType){
        return readOrderedAssayGroupIds(dataFileHub.getBaselineExperimentFiles(experimentAccession).main.get().readLine(0), experimentType);
    }

    String[] readOrderedAssayGroupIds(String[] experimentRunHeaders, ExperimentType experimentType){
        if(experimentType.isProteomicsBaseline()){
            String[] filtered = StringArrayUtil.filterBySubstring(experimentRunHeaders, "WithInSampleAbundance");
            return StringArrayUtil.substringBefore(filtered, ".");
        } else {
            int ASSAY_GROUP_HEADER_START_INDEX = 2;
            return ArrayUtils.subarray(experimentRunHeaders,
                    ASSAY_GROUP_HEADER_START_INDEX, experimentRunHeaders.length);
        }
    }

}
