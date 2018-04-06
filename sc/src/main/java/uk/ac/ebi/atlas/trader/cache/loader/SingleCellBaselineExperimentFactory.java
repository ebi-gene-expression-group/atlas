package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.SingleCellBaselineExperimentBuilder;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleCellBaselineExperimentFactory implements ExperimentFactory<BaselineExperiment> {

    private final ExperimentType experimentType;
    private final SpeciesFactory speciesFactory;

    public SingleCellBaselineExperimentFactory(ExperimentType experimentType, SpeciesFactory speciesFactory) {
        this.experimentType = experimentType;
        this.speciesFactory = speciesFactory;
    }

    @Override
    public BaselineExperiment create(ExperimentDTO experimentDTO, String experimentDescription,
                                     ExperimentDesign experimentDesign) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        return new SingleCellBaselineExperimentBuilder()
                .ofType(experimentType)
                .forSpecies(speciesFactory.create(experimentDTO.getSpecies()))
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withAssayGroups(new ArrayList<>())
                .withExperimentDesign(experimentDesign)
                .withDataProviderDescription(new ArrayList<>())
                .create();
    }
}
