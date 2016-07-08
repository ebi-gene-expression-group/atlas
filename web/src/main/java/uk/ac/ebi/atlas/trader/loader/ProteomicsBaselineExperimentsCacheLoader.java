
package uk.ac.ebi.atlas.trader.loader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ProteomicsBaselineExperimentsCacheLoader extends BaselineExperimentsCacheLoader {

    @Inject
    public ProteomicsBaselineExperimentsCacheLoader(ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                       ConfigurationTrader configurationTrader, SpeciesKingdomTrader speciesKingdomTrader) {
        super(expressionLevelFile, configurationTrader, speciesKingdomTrader);
    }

    @Override
    public BaselineExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                      boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getBaselineFactorsConfiguration(experimentAccession);

        AssayGroups assayGroups = configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups();

        boolean hasRData = configurationTrader.getExperimentConfiguration(experimentAccession).hasRData();

        String kingdom = speciesKingdomTrader.getKingdom(experimentDTO.getSpecies());

        String ensemblDB = speciesKingdomTrader.getEnsemblDB(experimentDTO.getSpecies());

        String[] orderedAssayGroupIds;
        boolean orderCurated;

        if(factorsConfig.getOrderFactor() != null && factorsConfig.getOrderFactor().equals("curated")) {
            orderCurated = true;
            orderedAssayGroupIds = assayGroups.getAssayGroupIds().toArray(new String[assayGroups.getAssayGroupIds().size()]);
        } else {
            orderCurated = false;
            orderedAssayGroupIds = expressionLevelFile.readOrderedAssayGroupIds(experimentAccession);
        }

        ExperimentalFactors experimentalFactors = createExperimentalFactors(experimentAccession, experimentDesign,
                factorsConfig, assayGroups, orderedAssayGroupIds, orderCurated);

        return new BaselineExperimentBuilder().forSpecies(experimentDTO.getSpecies())
                .ofKingdom(kingdom)
                .ofEnsemblDB(ensemblDB)
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withExtraInfo(hasExtraInfoFile)
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withSpeciesMapping(factorsConfig.getSpeciesMapping())
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withAssayGroups(assayGroups)
                .withExperimentDesign(experimentDesign)
                .withExperimentalFactors(experimentalFactors)
                .withDataProviderURL(factorsConfig.getDataProviderURL())
                .withDataProviderDescription(factorsConfig.getDataProviderDescription())
                .withRData(hasRData)
                .createProteomics();

    }
}
