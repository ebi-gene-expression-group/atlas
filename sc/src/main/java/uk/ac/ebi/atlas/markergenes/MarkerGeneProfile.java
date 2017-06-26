package uk.ac.ebi.atlas.markergenes;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import uk.ac.ebi.atlas.markergenes.MarkerGeneDao.MarkerGeneDto;

@AutoValue
public abstract class MarkerGeneProfile {
    public abstract String experimentAccession();
    public abstract int perplexity();
    public abstract ImmutableList<ImmutablePair<Integer, Double>> clusters();

    private static MarkerGeneProfile create(String experimentAccession, int perplexity, List<Pair<Integer, Double>> clusters) {
        return new AutoValue_MarkerGeneProfile(
                experimentAccession, perplexity,
                ImmutableList.copyOf(
                        clusters.stream()
                                .map(cluster -> ImmutablePair.of(cluster.getLeft(), cluster.getRight()))
                                .collect(toList())));
    }

    public static MarkerGeneProfile create(Collection<MarkerGeneDto> markerGeneDtos) {
        validate(markerGeneDtos);

        MarkerGeneDto head = markerGeneDtos.iterator().next();

        return create (
                head.experimentAccession(),
                head.perplexity(),
                markerGeneDtos.stream()
                        .map(mgd -> Pair.of(mgd.clusterId(), mgd.p()))
                        .collect(toList()));
    }

    private static void validate(Collection<MarkerGeneDto> markerGeneDtos) {
        checkArgument(
                markerGeneDtos.stream().collect(groupingBy(MarkerGeneDto::experimentAccession)).keySet().size() == 1,
                "Error building marker gene profile: experiment accession must be unique");

        checkArgument(
                markerGeneDtos.stream().collect(groupingBy(MarkerGeneDto::perplexity)).keySet().size() == 1,
                "Error building marker gene profile: perplexity must be unique");

        markerGeneDtos.stream()
                .collect(groupingBy(
                        mgd -> Triple.of(mgd.experimentAccession(), mgd.perplexity(), mgd.clusterId())))
                .forEach(
                        (key, value) -> checkArgument(value.size() == 1,
                                String.format(
                                        "Error building marker gene profile: experiment accession %s, " +
                                                "perplexity %d and cluster ID %d must be unique",
                                        key.getLeft(), key.getMiddle(), key.getRight())));

        markerGeneDtos.forEach(
                mgd -> checkArgument(
                        mgd.perplexity() > mgd.clusterId(),
                        String.format(
                                "Error building marker gene profile: perplexity is %d but cluster ID is %d",
                                mgd.perplexity(), mgd.clusterId())));
    }
}
