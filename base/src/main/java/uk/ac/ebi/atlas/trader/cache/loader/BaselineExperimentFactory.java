package uk.ac.ebi.atlas.trader.cache.loader;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactorsFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaselineExperimentFactory implements ExperimentFactory<BaselineExperiment> {

    private final ExperimentType experimentType;
    private final ConfigurationTrader configurationTrader;
    private final ExperimentalFactorsFactory experimentalFactorsFactory;
    private final SpeciesFactory speciesFactory;
    private final DataFileHub dataFileHub;

    protected BaselineExperimentFactory(ExperimentType experimentType, ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory, DataFileHub dataFileHub) {
        this(new ExperimentalFactorsFactory(), experimentType, configurationTrader,
                speciesFactory,dataFileHub);
    }

    BaselineExperimentFactory(ExperimentalFactorsFactory experimentalFactorsFactory, ExperimentType experimentType,
                              ConfigurationTrader configurationTrader,
                              SpeciesFactory speciesFactory, DataFileHub dataFileHub) {
        this.experimentalFactorsFactory = experimentalFactorsFactory;
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

        List<AssayGroup> assayGroups = configuration.getAssayGroups();

        String[] orderedAssayGroupIds;
        boolean orderCurated;

        if (factorsConfig.orderCurated()) {
            orderCurated = true;
            orderedAssayGroupIds = new String[assayGroups.size()];
            for(int i = 0 ; i < assayGroups.size() ; i++){
                orderedAssayGroupIds[i] = assayGroups.get(i).getId();
            }

        } else {
            orderCurated = false;
            orderedAssayGroupIds = readOrderedAssayGroupIds(experimentAccession, experimentDTO.getExperimentType());
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
                .withExperimentalFactors(experimentalFactorsFactory.createExperimentalFactors(experimentAccession,
                        experimentDesign,
                        factorsConfig, assayGroups, orderedAssayGroupIds, orderCurated))
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
