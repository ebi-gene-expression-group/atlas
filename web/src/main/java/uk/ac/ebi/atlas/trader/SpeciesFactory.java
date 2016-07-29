package uk.ac.ebi.atlas.trader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SpeciesFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesFactory.class);

    private final SpeciesKingdomTrader speciesKingdomTrader;

    @Inject
    public SpeciesFactory(SpeciesKingdomTrader speciesKingdomTrader){
        this.speciesKingdomTrader=speciesKingdomTrader;
    }

    public Species create(ExperimentDTO experimentDTO, BaselineExperimentConfiguration factorsConfig){
        final String inputName = experimentDTO.getSpecies();
        return create(inputName, SpeciesUtils.convertToEnsemblSpecies(factorsConfig.getSpeciesMapping(), inputName));
    }

    public Species create(String inputName){
        return create( inputName, SpeciesUtils.convertToEnsemblSpecies(inputName));
    }

    private Species create(String inputName, String canonicalName){
        String ensemblDb = speciesKingdomTrader.getEnsemblDB(canonicalName);
        if(ensemblDb == null){
            LOGGER.warn(String.format("Could not look up ensemblDb for %s -> %s",inputName,canonicalName));
            ensemblDb = "";
        }
        String kingdom = speciesKingdomTrader.getKingdom(canonicalName);
        if(kingdom == null){
            LOGGER.warn(String.format("Could not look up kingdom for %s -> %s",inputName,canonicalName));
            kingdom = "";
        }
        return new Species(inputName,canonicalName,ensemblDb,kingdom);
    }

}
