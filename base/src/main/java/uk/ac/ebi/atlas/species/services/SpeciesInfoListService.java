package uk.ac.ebi.atlas.species.services;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

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
    private final LazyReference<List<SpeciesInfo>> speciesInfoList = new LazyReference<List<SpeciesInfo>>() {
        @Override
        protected List<SpeciesInfo> create() throws Exception {
            return populateSpeciesByExperimentCount();
        }
    };

    private final LazyReference<List<SpeciesInfo>> topSixSpeciesByExperimentCountReference =
            new LazyReference<List<SpeciesInfo>>() {
        @Override
        protected List<SpeciesInfo> create() throws Exception {
            return getFirstSix(speciesInfoList.get());
        }
    };

    public List<String> getTopSixSpecies() {
        return FluentIterable.from(topSixSpeciesByExperimentCountReference.get()).transform(
                        new Function<SpeciesInfo, String>() {
                            @Override
                            public String apply(SpeciesInfo speciesInfo) {
                                return speciesInfo.getSpecies();
                            }
                        }
                ).toList();
    }

    public SpeciesInfoListService(SpeciesPropertiesTrader speciesPropertiesTrader, ExperimentInfoListService experimentInfoListService) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        this.experimentInfoListService = experimentInfoListService;
    }

    private List<SpeciesInfo> populateSpeciesByExperimentCount() {
        List<SpeciesInfo> speciesInfoList = new ArrayList<>();

        List<ExperimentInfo> experimentInfos = experimentInfoListService.listPublicExperiments();
        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            String species = speciesProperties.referenceName();

            String kingdom = "";
            int totalExperiments = 0;
            int totalBaseline = 0;
            int totalDifferential = 0;

            for (ExperimentInfo experimentInfo : experimentInfos) {

                if (experimentInfo.getSpecies().toLowerCase().equals(species)) {
                    totalExperiments++;

                    kingdom = experimentInfo.getKingdom();
                    ExperimentType experimentType = experimentInfo.getExperimentType();
                    if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL || experimentType == ExperimentType.MICROARRAY_ANY) {
                        totalDifferential ++;
                    }
                    if (experimentType == ExperimentType.RNASEQ_MRNA_BASELINE || experimentType == ExperimentType.PROTEOMICS_BASELINE) {
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

    private <A> List<A> getFirstSix(List<A> l) {
        return l.subList(0, Math.min(6, l.size()));
    }

    private List<SpeciesInfo> filterListByKingdom(String kingdom) {
        List<SpeciesInfo> kingdomSubList = new ArrayList<>();

        for (SpeciesInfo info : speciesInfoList.get()) {
            if (info.getKingdom().equals(kingdom)) {
                kingdomSubList.add(info);
            }
        }

        return kingdomSubList;
    }

    public List<SpeciesInfo> getTopSixSpeciesByExperimentCount() {
        return topSixSpeciesByExperimentCountReference.get();
    }

    public List<SpeciesInfo> getFilterByKingdom (String kingdom) {
        return getFirstSix(filterListByKingdom(kingdom));
    }

    protected class SpeciesInfo {
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

        Integer getBaselineExperiments() {
            return baselineExperiments;
        }

        Integer getDifferentialExperiments() {
            return differentialExperiments;
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
