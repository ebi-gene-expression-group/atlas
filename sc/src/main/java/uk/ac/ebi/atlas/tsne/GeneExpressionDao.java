package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableMap;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Named
public class GeneExpressionDao {

    private final TSnePlotDao tSnePlotDao;

    @Inject
    public GeneExpressionDao(TSnePlotDao tSnePlotDao) {
        this.tSnePlotDao = tSnePlotDao;
    }

    public ImmutableMap<String, Optional<Double>> fetchGeneExpression(String experimentAccession, String geneId) {
        return ImmutableMap.copyOf(
                tSnePlotDao.fetchTSnePlotPoints(experimentAccession)
                        .values().stream()
                        .collect(
                                Collectors.toMap(
                                        TSnePoint::name,
                                        p -> isBlank(geneId) ?
                                                Optional.empty() : Optional.of(ThreadLocalRandom.current().nextDouble(0.0, 10000.0)))));
    }
}
