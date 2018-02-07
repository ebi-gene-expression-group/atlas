package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DifferentialPathwaysComparisonService <Expr extends DifferentialExpression, E extends DifferentialExperiment,
        K extends DifferentialRequestPreferences,
        P extends DifferentialProfile<Expr, P>,  R extends DifferentialRequestContext<E, K> >
        extends ExperimentPageService {

    private final DataFileHub dataFileHub;
    private final ConfigurationTrader configurationTrader;
    private final DifferentialRequestContextFactory<E, K, R> differentialRequestContextFactory;
    private final DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap;


    public DifferentialPathwaysComparisonService(DataFileHub dataFileHub,
                                                 ConfigurationTrader configurationTrader,
                                                 DifferentialRequestContextFactory<E, K, R> differentialRequestContextFactory,
                                                 DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap) {

        this.differentialRequestContextFactory = differentialRequestContextFactory;
        this.profilesHeatMap = profilesHeatMap;
        this.dataFileHub = dataFileHub;
        this.configurationTrader = configurationTrader;
    }

    public Map<Contrast, List<String>> getPathwaysListPerComparison(DifferentialExperiment experiment) {
        Map<Contrast, List<String>> comparisonsPathwaysMap = new HashMap<>();

        String experimentAccession = experiment.getAccession();
        List<Contrast> comparisons = configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession).getContrasts();

        //fetch pathwaysId from file for each comparison
        for (Contrast comparison : comparisons) {
            List<String> pathwaysValues = fetchPathwaysFromFile(experimentAccession, experiment.getType(), comparison);

            comparisonsPathwaysMap.put(comparison, pathwaysValues);
        }

        return comparisonsPathwaysMap;
    }

    private List<String> fetchPathwaysFromFile (String experimentAccession, ExperimentType experimentType, Contrast comparison) {
        try (TsvReader tsvReader =
                     dataFileHub.getDifferentialExperimentFiles(experimentAccession)
                             .reactomePathwaysFiles(experimentAccession, comparison.getId())
                             .get()) {
            return tsvReader.stream()
                    .skip(1)
                    .filter(line -> line.length > 4)
                    .filter(line -> Double.parseDouble(line[4]) < 0.05)
                    .collect(Collectors.toMap(line -> line[0], line -> Double.valueOf(line[4])))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(10)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    public Map<Contrast, SetMultimap<String, Pair<String, DifferentialExpression>>> constructPathwaysByComparison (E experiment,
                                                                                                                   K preferences) {
        //Read pathwaysId per comparison from file ({comparison}-reactome.gsea.tsv)
        Map<Contrast, List<String>> contrastPathwaysListMap = getPathwaysListPerComparison(experiment);
        SetMultimap<String, Pair<String, DifferentialExpression>> pathwayGeneExpressionMap = HashMultimap.create();

        Map<Contrast, SetMultimap<String, Pair<String, DifferentialExpression>>> result = new HashMap<>();

        //For each comparison and each pathway get differential profiles (geneIds and differential expressions)
        for (Map.Entry<Contrast, List<String>> comparison : contrastPathwaysListMap.entrySet()) {
            for (String pathwayId : comparison.getValue()) {
                preferences.setGeneQuery(SemanticQuery.create(pathwayId));
                R requestContext = differentialRequestContextFactory.create(experiment, preferences);

                DifferentialProfilesList<P> differentialProfiles = profilesHeatMap.fetch(requestContext);

                if (!differentialProfiles.isEmpty()) {
                    for (P differentialProfile: differentialProfiles) {
                        String geneId = differentialProfile.getId();
                        DifferentialExpression differentialExpression = differentialProfile.getExpression(comparison.getKey());

                        if (differentialExpression != null) {
                            if (pathwayGeneExpressionMap.get(pathwayId).isEmpty()) {
                                pathwayGeneExpressionMap.put(pathwayId, Pair.of(geneId, differentialExpression));
                            }
                            else {
                                for (Pair<String, DifferentialExpression> geneExpressionInfo : pathwayGeneExpressionMap.get(pathwayId)) {
                                    if (geneExpressionInfo.getLeft().equals(geneId)) {
                                        DifferentialExpression highestExpression = geneExpressionInfo.getRight().getAbsoluteFoldChange() <
                                                differentialExpression.getAbsoluteFoldChange() ? differentialExpression : geneExpressionInfo.getRight();
                                        pathwayGeneExpressionMap.get(pathwayId).add(Pair.of(geneId, highestExpression));

                                    } else {
                                        pathwayGeneExpressionMap.get(pathwayId).add(Pair.of(geneId, differentialExpression));
                                    }
                                }
                            }
                        }

                    }
                }
            }

            if (!pathwayGeneExpressionMap.isEmpty()) {
                result.put(comparison.getKey(), pathwayGeneExpressionMap);
            }
        }

        return result;
    }

}
