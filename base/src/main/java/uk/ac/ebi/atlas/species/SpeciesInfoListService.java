package uk.ac.ebi.atlas.species;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by barrera on 10/02/2017.
 *
 * This class is used to retrieved the top 6 species that have more experiments
 * in the Browse by species section from the home page
 *
 */
public class SpeciesInfoListService {

    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final ExperimentInfoListService experimentInfoListService;


    public SpeciesInfoListService(SpeciesPropertiesTrader speciesPropertiesTrader, ExperimentInfoListService experimentInfoListService) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        this.experimentInfoListService = experimentInfoListService;
    }

    public List<SpeciesInfo> getNumberExperimentsBySpecies() {

        List<SpeciesInfo> speciesInfoList = new ArrayList<>();

        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            String species = speciesProperties.referenceName();

            JsonObject experimentsJson = experimentInfoListService.getExperimentsJson();
            JsonArray data = experimentsJson.getAsJsonArray("aaData");

            String kingdom="";
            int totalExperiments = 0;
            int totalBaseline = 0;
            int totalDifferential = 0;
            for (int i=0; i<data.size(); i++) {
                if (data.get(i).getAsJsonObject().get("species").getAsString().toLowerCase().equals(species)) {
                    totalExperiments ++;

                    kingdom = data.get(i).getAsJsonObject().get("kingdom").getAsString();
                    String experimentType = data.get(i).getAsJsonObject().get("experimentType").getAsString();
                    if (experimentType.equals(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL.name()) || experimentType.equals(ExperimentType.MICROARRAY_ANY.name())) {
                        totalDifferential ++;
                    }

                    if (experimentType.equals(ExperimentType.RNASEQ_MRNA_BASELINE.name()) || experimentType.equals(ExperimentType.PROTEOMICS_BASELINE.name())) {
                        totalBaseline ++;
                    }
                }
            }

            SpeciesInfo speciesInfo = new SpeciesInfo(species, kingdom, totalExperiments, totalBaseline, totalDifferential);

            speciesInfoList.add(speciesInfo);
        }

        Collections.sort(speciesInfoList, new Comparator<SpeciesInfo>() {

            @Override
            public int compare(SpeciesInfo o1, SpeciesInfo o2) {
                if (o1.getTotalExperiments() >= o2.getTotalExperiments() ) {
                    return -1;
                }else{
                    return 1;
                }
            }

        });

        return speciesInfoList.subList(0, Math.min(6, speciesInfoList.size()));
    }

    private class SpeciesInfo {
        String species;
        String kingdom;
        Integer totalExperiments;
        Integer baselineExperiments;
        Integer differentialExperiments;

        SpeciesInfo(String species, String kingdom, Integer totalExperiments, Integer baselineExperiments, Integer differentialExperiments) {
            this.species = species;
            this.kingdom = kingdom;
            this.totalExperiments = totalExperiments;
            this.baselineExperiments = baselineExperiments;
            this.differentialExperiments = differentialExperiments;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getKingdom() {
            return kingdom;
        }

        public void setKingdom(String kingdom) {
            this.kingdom = kingdom;
        }

        Integer getTotalExperiments() {
            return totalExperiments;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SpeciesInfo)) return false;

            SpeciesInfo that = (SpeciesInfo) o;

            return totalExperiments != null ? totalExperiments.equals(that.totalExperiments) : that.totalExperiments == null;
        }

        @Override
        public int hashCode() {
            return totalExperiments != null ? totalExperiments.hashCode() : 0;
        }
    }

}
