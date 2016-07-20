package uk.ac.ebi.atlas.trader.loader;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BaselineExperimentsCacheLoader extends ExperimentsCacheLoader<BaselineExperiment> {


    private final ExperimentType experimentType;
    private final BaselineExperimentExpressionLevelFile expressionLevelFile;
    private final ConfigurationTrader configurationTrader;
    private final SpeciesKingdomTrader speciesKingdomTrader;
    private final ExperimentalFactorsFactory experimentalFactorsFactory;

    protected BaselineExperimentsCacheLoader(ExperimentType experimentType, BaselineExperimentExpressionLevelFile
            expressionLevelFile, ConfigurationTrader configurationTrader,
                                             SpeciesKingdomTrader speciesKingdomTrader) {
        this(new ExperimentalFactorsFactory(), experimentType, expressionLevelFile, configurationTrader,speciesKingdomTrader);
    }

    BaselineExperimentsCacheLoader(ExperimentalFactorsFactory experimentalFactorsFactory, ExperimentType
            experimentType, BaselineExperimentExpressionLevelFile expressionLevelFile,
                                   ConfigurationTrader configurationTrader,
                                   SpeciesKingdomTrader speciesKingdomTrader) {
        this.experimentalFactorsFactory = experimentalFactorsFactory;
        this.experimentType = experimentType;
        this.configurationTrader = configurationTrader;
        this.expressionLevelFile = expressionLevelFile;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }


    @Override
    public BaselineExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                   boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        ExperimentConfiguration configuration = configurationTrader.getExperimentConfiguration(experimentAccession);
        BaselineExperimentConfiguration factorsConfig = configurationTrader.getBaselineFactorsConfiguration(experimentAccession);

        AssayGroups assayGroups = configuration.getAssayGroups();

        String[] orderedAssayGroupIds;
        boolean orderCurated;

        if (factorsConfig.orderCurated()) {
            orderCurated = true;
            orderedAssayGroupIds = assayGroups.getAssayGroupIds().toArray(new String[assayGroups.getAssayGroupIds().size()]);
        } else {
            orderCurated = false;
            orderedAssayGroupIds = expressionLevelFile.readOrderedAssayGroupIds(experimentAccession);
        }

        return new BaselineExperimentBuilder().forSpecies(experimentDTO.getSpecies())
                .ofType(experimentType)
                .ofKingdom(speciesKingdomTrader.getKingdom(experimentDTO.getSpecies()))
                .ofEnsemblDB(speciesKingdomTrader.getEnsemblDB(experimentDTO.getSpecies()))
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withExtraInfo(hasExtraInfoFile)
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withSpeciesMapping(factorsConfig.getSpeciesMapping())
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

}
