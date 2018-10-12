package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.model.experiment.baseline.SingleCellBaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.SingleCellBaselineExperimentBuilder;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class SingleCellBaselineExperimentFactory implements ExperimentFactory<SingleCellBaselineExperiment> {

    private final ExperimentType experimentType;
    private final SpeciesFactory speciesFactory;

    public SingleCellBaselineExperimentFactory(ExperimentType experimentType, SpeciesFactory speciesFactory) {
        this.experimentType = experimentType;
        this.speciesFactory = speciesFactory;
    }

    @Override
    public SingleCellBaselineExperiment create(ExperimentDTO experimentDTO,
                                               ExperimentDesign experimentDesign,
                                               IdfParserOutput idfParserOutput) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        return new SingleCellBaselineExperimentBuilder()
                .ofType(experimentType)
                .forSpecies(speciesFactory.create(experimentDTO.getSpecies()))
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(idfParserOutput.getTitle())
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withDois(experimentDTO.getDois())
                .withCells(experimentDesign.getAllRunOrAssay().stream().map(Cell::new).collect(Collectors.toList()))
                .withExperimentDesign(experimentDesign)
                .withDataProviderDescription(new ArrayList<>())
                .create();
    }
}
