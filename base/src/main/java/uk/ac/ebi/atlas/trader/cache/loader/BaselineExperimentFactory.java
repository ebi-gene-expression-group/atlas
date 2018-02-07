package uk.ac.ebi.atlas.trader.cache.loader;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.util.ArrayList;
import java.util.List;

public abstract class BaselineExperimentFactory implements ExperimentFactory<BaselineExperiment> {

    private final ExperimentType experimentType;
    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;

    public BaselineExperimentFactory(ExperimentType experimentType, ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory) {
        this.experimentType = experimentType;
        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
    }


    @Override
    public BaselineExperiment create(ExperimentDTO experimentDTO, String experimentDescription,
                                     ExperimentDesign experimentDesign) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        ExperimentConfiguration configuration = configurationTrader.getExperimentConfiguration(experimentAccession);
        BaselineExperimentConfiguration factorsConfig =
                configurationTrader.getBaselineFactorsConfiguration(experimentAccession);

        return new BaselineExperimentBuilder()
                .ofType(experimentType)
                .forSpecies(speciesFactory.create(experimentDTO.getSpecies()))
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withDisclaimer(factorsConfig.disclaimer())
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withAssayGroups(configuration.getAssayGroups())
                .withExperimentDesign(experimentDesign)
                .withDisplayDefaults(ExperimentDisplayDefaults.create(
                        factorsConfig.getDefaultFilterFactors(),
                        factorsConfig.getDefaultQueryFactorType(),
                        factorsConfig.getMenuFilterFactorTypes(), factorsConfig.orderCurated()))
                .withDataProviderURL(factorsConfig.getDataProviderURL())
                .withDataProviderDescription(factorsConfig.getDataProviderDescription())
                .withAlternativeViews(extractAlternativeViews(factorsConfig))
                .create();

    }

    private Pair<List<String>, List<String>> extractAlternativeViews(BaselineExperimentConfiguration factorsConfig) {
        List<String> accessions = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        for (String alternativeViewAccession : factorsConfig.getAlternativeViews()) {
            accessions.add(alternativeViewAccession);
            descriptions.add(
                    "View by " +
                    configurationTrader.getBaselineFactorsConfiguration(alternativeViewAccession)
                            .getDefaultQueryFactorType()
                            .toLowerCase()
                            .replace("_", " "));
        }

        return Pair.of(accessions, descriptions);
    }
}
