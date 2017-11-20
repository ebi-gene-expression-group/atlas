package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.solr.analytics.fullanalytics.FullAnalyticsSearchService.SpecificGeneProfile.COMPARATOR_BY_SPECIFICITY_AND_NAIVE_DIFF_EXPRESSION;

@Named
public class FullAnalyticsSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsSearchService.class);

    // Number of rows in the heatmap
    private static final int DEFAULT_RESULT_SIZE = 50;

    private final FullAnalyticsDao fullAnalyticsDao;

    @Inject
    public FullAnalyticsSearchService(FullAnalyticsDao fullAnalyticsDao) {
        this.fullAnalyticsDao = fullAnalyticsDao;
    }

    public List<String> specificExpressionInSliceAcrossSelectedAssayGroups(String experimentAccession,
                                                                           Double expressionThreshold,
                                                                           Set<String> sliceAssayGroupIds,
                                                                           Set<String> selectedAssayGroupIds) {
        return specificExpressionInSliceAcrossSelectedAssayGroups(
                experimentAccession,
                expressionThreshold,
                sliceAssayGroupIds,
                selectedAssayGroupIds,
                DEFAULT_RESULT_SIZE);
    }

    public List<String> specificExpressionAcrossAllAssayGroups(String experimentAccession,
                                                               Double expressionThreshold,
                                                               int resultSize) {
        Collection<SpecificGeneProfile> geneProfilesInSelectedAssayGroups =
                mapJsonBucketsWithAverageExpressionAndCountsToGeneProfiles(
                        fullAnalyticsDao.genesInAllAssayGroupsWithAverageExpressionSortedByCounts(
                                experimentAccession,expressionThreshold));

        return
                geneProfilesInSelectedAssayGroups.stream()
                        .sorted(COMPARATOR_BY_SPECIFICITY_AND_NAIVE_DIFF_EXPRESSION)
                        .limit(resultSize)
                        .map(SpecificGeneProfile::geneId)
                        .collect(Collectors.toList());
    }

    public List<String> specificExpressionInSliceAcrossSelectedAssayGroups(String experimentAccession,
                                                                          Double expressionThreshold,
                                                                          Set<String> sliceAssayGroupIds,
                                                                          Set<String> selectedAssayGroupIds,
                                                                          int resultSize) {
        checkArgument(
                sliceAssayGroupIds.containsAll(selectedAssayGroupIds),
                "Some of the selected assay groups are not part of the viewed slice assay groups");

        Collection<SpecificGeneProfile> geneProfilesInSelectedAssayGroups =
                mapJsonBucketsWithAverageExpressionAndCountsToGeneProfiles(
                        fullAnalyticsDao.genesInAssayGroupsWithAverageExpressionSortedByCounts(
                                experimentAccession, sliceAssayGroupIds, selectedAssayGroupIds, expressionThreshold));

        Collection<SpecificGeneProfile> geneProfilesOutsideSelectedAssayGroups =
                (sliceAssayGroupIds.size() > selectedAssayGroupIds.size()) ?
                        mapJsonBucketsWithMaxExpressionAndCountsToGeneProfiles(
                                fullAnalyticsDao.genesNotInAssayGroupsWithMaxExpressionSortedByCounts (
                                        experimentAccession,
                                        sliceAssayGroupIds,
                                        selectedAssayGroupIds,
                                        geneProfilesInSelectedAssayGroups.stream().map(SpecificGeneProfile::geneId).collect(Collectors.toSet()),
                                        expressionThreshold)) :
                        ImmutableSet.of();

        return
                mergeGeneProfiles(geneProfilesInSelectedAssayGroups, geneProfilesOutsideSelectedAssayGroups).stream()
                .sorted(COMPARATOR_BY_SPECIFICITY_AND_NAIVE_DIFF_EXPRESSION)
                .limit(resultSize)
                .map(SpecificGeneProfile::geneId)
                .collect(Collectors.toList());
    }

    private Set<SpecificGeneProfile> mapJsonBucketsWithAverageExpressionAndCountsToGeneProfiles(String json) {
        Collection<Map<String, Object>> buckets = JsonPath.read(json, "$.facets.genes.buckets.*");

        return buckets.stream()
                .filter(bucket -> bucket.containsKey("avg_expression"))
                .map(bucket ->
                        SpecificGeneProfile.create(
                                (String) bucket.get("val"),
                                ((Number) bucket.get("count")).intValue(),
                                ((Number) bucket.get("avg_expression")).doubleValue(),
                                0, 0.0))
                .collect(Collectors.toSet());
    }

    private Set<SpecificGeneProfile> mapJsonBucketsWithMaxExpressionAndCountsToGeneProfiles(String json) {
        Collection<Map<String, Object>> buckets = JsonPath.read(json, "$.facets.genes.buckets.*");

        return buckets.stream()
                .filter(bucket -> bucket.containsKey("max_expression"))
                .map(bucket ->
                        SpecificGeneProfile.create(
                                (String) bucket.get("val"),
                                0, 0.0,
                                ((Number) bucket.get("count")).intValue(),
                                ((Number) bucket.get("max_expression")).doubleValue()))
                .collect(Collectors.toSet());
    }

    private Set<SpecificGeneProfile> mergeGeneProfiles(
            Collection<SpecificGeneProfile> geneProfilesInSelectedAssayGroupsWithSelectionData,
            Collection<SpecificGeneProfile> geneProfilesInSelectedAssayGroupsWithSliceData) {

        Map<String, SpecificGeneProfile> geneProfilesInSelectedAssayGroupsWithSliceDataByGeneId =
                geneProfilesInSelectedAssayGroupsWithSliceData.stream()
                        .collect(Collectors.toMap(SpecificGeneProfile::geneId, profile -> profile));

        return geneProfilesInSelectedAssayGroupsWithSelectionData.stream()
                .map(profile ->
                        SpecificGeneProfile.merge(
                                profile,
                                geneProfilesInSelectedAssayGroupsWithSliceDataByGeneId.get(profile.geneId())))
                .collect(Collectors.toSet());
    }

    @AutoValue
    static abstract class SpecificGeneProfile {
        static final Comparator<SpecificGeneProfile> COMPARATOR_BY_SPECIFICITY_AND_NAIVE_DIFF_EXPRESSION = (o1, o2) ->
            ComparisonChain.start()
                    .compare(
                            o1.countsInSelection() + o1.countsOutsideSelection(),
                            o2.countsInSelection() + o2.countsOutsideSelection())
                    .compare(o1.countsOutsideSelection(), o2.countsOutsideSelection())
                    // Notice that we place o2 before o1 to achieve descending order
                    .compare(o2.avgExpressionInSelection() / (o2.maxExpressionOutsideSelection().equals(0.0) ? 1.0 : o2.maxExpressionOutsideSelection()),
                             o1.avgExpressionInSelection() / (o1.maxExpressionOutsideSelection().equals(0.0) ? 1.0 : o1.maxExpressionOutsideSelection()))
                    .result();

        static SpecificGeneProfile create(String geneId,
                                          Integer countsInSelection, Double avgExpressionInSelection,
                                          Integer countsOutsideSelection, Double maxExpressionOutsideSelection) {
            return new AutoValue_FullAnalyticsSearchService_SpecificGeneProfile(
                    geneId,
                    countsInSelection, avgExpressionInSelection,
                    countsOutsideSelection, maxExpressionOutsideSelection);
        }

        static SpecificGeneProfile merge(SpecificGeneProfile geneProfile1, SpecificGeneProfile geneProfile2) {
            if (geneProfile2 == null) {
                return geneProfile1;
            }

            checkArgument(geneProfile1.geneId().equals(geneProfile2.geneId()), "Gene profile IDs do not match");
            return new AutoValue_FullAnalyticsSearchService_SpecificGeneProfile(
                    geneProfile1.geneId(),
                    geneProfile1.countsInSelection(), geneProfile1.avgExpressionInSelection(),
                    geneProfile2.countsOutsideSelection(), geneProfile2.maxExpressionOutsideSelection());
        }

        abstract String geneId();
        abstract Integer countsInSelection();
        abstract Double avgExpressionInSelection();
        abstract Integer countsOutsideSelection();
        abstract Double maxExpressionOutsideSelection();
    }
}
