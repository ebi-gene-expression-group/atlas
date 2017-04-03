package uk.ac.ebi.atlas.species;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIdentifiersReader.class);
    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final ExperimentInfoListService experimentInfoListService;

    private final LazyReference<List<SpeciesInfo>> topSixSpeciesByExperimentCount = new LazyReference<List<SpeciesInfo>>() {
        @Override
        protected List<SpeciesInfo> create() throws Exception {
            return getTopSixSpeciesByExperimentCount();
        }
    };

    public List<String> getTopSixSpecies() {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();
        LOGGER.info("Sorting species by experiment count (lazy ref mode)...");

        List<String> topSix =
                FluentIterable.from(topSixSpeciesByExperimentCount.get()).transform(
                        new Function<SpeciesInfo, String>() {
                            @Override
                            public String apply(SpeciesInfo speciesInfo) {
                                return speciesInfo.getSpecies();
                            }
                        }
                ).toList();

        stopWatch.stop();
        LOGGER.info("Sorting species by experiment count (lazy ref mode): {} seconds", stopWatch.getTotalTimeSeconds());

        return topSix;
    }

    public SpeciesInfoListService(SpeciesPropertiesTrader speciesPropertiesTrader, ExperimentInfoListService experimentInfoListService) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        this.experimentInfoListService = experimentInfoListService;
    }

    private List<SpeciesInfo> getSpeciesByExperimentCount() {
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

        return speciesInfoList;
    }

    private List<SpeciesInfo> getFirstSixSpecies(List<SpeciesInfo> speciesList) {
        return speciesList.subList(0, Math.min(6, speciesList.size()));
    }

    private List<SpeciesInfo> filterListByKingdom(String kingdom) {
        List<SpeciesInfo> speciesInfoList = getSpeciesByExperimentCount();
        List<SpeciesInfo> kingdomSubList = new ArrayList<>();

        for (SpeciesInfo info : speciesInfoList) {
            if (info.getKingdom().equals(kingdom)) {
                kingdomSubList.add(info);
            }
        }

        return kingdomSubList;
    }

    public List<SpeciesInfo> getTopSixSpeciesByExperimentCount() {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();
        LOGGER.info("Sorting species by experiment count (normal mode)...");

        List<SpeciesInfo> speciesInfoList = getFirstSixSpecies(getSpeciesByExperimentCount());

        stopWatch.stop();
        LOGGER.info("Sorting species by experiment count (normal mode): {} seconds", stopWatch.getTotalTimeSeconds());

        return speciesInfoList;
    }

    public List<SpeciesInfo> getFilterByKingdom (String kingdom) {
        List<SpeciesInfo> filteredKingdomList = filterListByKingdom(kingdom);
        return filteredKingdomList.size() > 6 ? getFirstSixSpecies(filteredKingdomList) : filteredKingdomList;
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

        public String getKingdom() {
            return kingdom;
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
