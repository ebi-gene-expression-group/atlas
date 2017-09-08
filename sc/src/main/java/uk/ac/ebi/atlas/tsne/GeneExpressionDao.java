package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableMap;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Named
public class GeneExpressionDao {

    private final TSnePlotDao tSnePlotDao;

    @Inject
    public GeneExpressionDao(TSnePlotDao tSnePlotDao) {
        this.tSnePlotDao = tSnePlotDao;
    }

    public ImmutableMap<String, Double> fetchGeneExpression(String experimentAccession, String geneId) {
        return ImmutableMap.copyOf(
                tSnePlotDao.fetchTSnePlotPoints(experimentAccession)
                        .values().stream()
                        .collect(
                                Collectors.toMap(
                                        TSnePoint::name, p -> ThreadLocalRandom.current().nextDouble(0.0, 10000.0))));
    }
}
