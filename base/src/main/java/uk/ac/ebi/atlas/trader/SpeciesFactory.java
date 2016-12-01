package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.species.SpeciesTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SpeciesFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesFactory.class);

    private final SpeciesTrader speciesTrader;

    public final static Species NULL = new AnySpecies();

    @Inject
    public SpeciesFactory(SpeciesTrader speciesTrader){
        this.speciesTrader = speciesTrader;
    }

    public Species create(ExperimentDTO experimentDTO, BaselineExperimentConfiguration factorsConfig){
        final String inputName = experimentDTO.getSpecies();
        return create(inputName, SpeciesUtils.convertToEnsemblSpecies(factorsConfig.getSpeciesMapping(), inputName));
    }

    public Species create(ExperimentDTO experimentDTO){
        return create(experimentDTO.getSpecies());
    }

    public Species create(String inputName){
        return StringUtils.isNotEmpty(inputName) ?
                create( inputName, SpeciesUtils.convertToEnsemblSpecies(inputName)) :
                NULL;
    }

    private Species create(String inputName, String canonicalName){
        String ensemblDb = speciesTrader.getByName(inputName).name();
        if(ensemblDb == null){
            LOGGER.warn(String.format("Could not look up ensemblDb for %s -> %s",inputName,canonicalName));
            ensemblDb = "";
        }
        String kingdom = speciesTrader.getByName(inputName).kingdom();
        if(kingdom == null){
            LOGGER.warn(String.format("Could not look up kingdom for %s -> %s",inputName,canonicalName));
            kingdom = "";
        }
        return new Species(inputName,canonicalName,ensemblDb,kingdom);
    }

    static class AnySpecies extends Species {
        AnySpecies(){
            super("","","","");
        }

        @Override
        public boolean isBlank(){
            return true;
        }
        @Override
        public String toString(){
            return "BLANK SPECIES";
        }
    }

}
