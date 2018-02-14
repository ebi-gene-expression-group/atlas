package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.markergenes.MarkerGeneDao;
import uk.ac.ebi.atlas.markergenes.RandomMarkerGeneInputStream;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SingleCellOpsExecutionService implements ExperimentOpsExecutionService {

    private final ExperimentCrud experimentCrud;
    private final ExperimentTrader experimentTrader;
    private final AnalyticsLoaderFactory analyticsLoaderFactory;
    private final MarkerGeneDao markerGeneDao;
    private final Gson gson = new Gson();

    public SingleCellOpsExecutionService(ExperimentCrud experimentCrud,
                                         ExperimentTrader experimentTrader,
                                         AnalyticsLoaderFactory analyticsLoaderFactory,
                                         MarkerGeneDao markerGeneDao) {
        this.experimentCrud = experimentCrud;
        this.experimentTrader = experimentTrader;
        this.analyticsLoaderFactory = analyticsLoaderFactory;
        this.markerGeneDao = markerGeneDao;
    }

    private Stream<ExperimentDTO> allDtos(){
        return experimentCrud.findAllExperiments().stream()
                .filter(experimentDTO -> experimentDTO.getExperimentType().isSingleCell());
    }

    @Override
    public List<String> findAllExperiments(){
        return allDtos().map(ExperimentDTO::getExperimentAccession).collect(toList());
    }

    @Override
    public Optional<JsonElement> attemptExecuteOneStatelessOp(String accession, Op op){
        switch (op) {
            case LIST:
                return Optional.of(experimentCrud.findExperiment(accession).toJson());
            case CACHE_READ:
                return Optional.of(gson.toJsonTree(experimentTrader.getExperiment(accession,
                        experimentCrud.findExperiment(accession).getAccessKey()).getAttributes()));
            default:
                return Optional.empty();
        }
    }

    @Override
    public Optional<? extends List<Pair<String,? extends JsonElement>>> attemptExecuteForAllAccessions(Collection<Op> ops){
        if (ops.equals(Collections.singleton(Op.LIST))) {
            return Optional.of(list());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<? extends List<Pair<String,? extends JsonElement>>> attemptExecuteForAllAccessions(Op op){
        if (op.equals(Op.LIST)) {
            return Optional.of(list());
        } else {
            return Optional.empty();
        }
    }

    private List<Pair<String,? extends JsonElement>> list(){
        return allDtos()
                .map((Function<ExperimentDTO, Pair<String, ? extends JsonElement>>) experimentDTO ->
                        Pair.of(experimentDTO.getExperimentAccession(), experimentDTO.toJson())).collect(toList());
    }

    @Override
    public JsonPrimitive attemptExecuteStatefulOp(String accession, Op op) throws IOException {
        JsonPrimitive resultOfTheOp = ExperimentOps.DEFAULT_SUCCESS_RESULT;
        boolean isPrivate = true;
        switch (op) {
            case UPDATE_PRIVATE:
                experimentCrud.makeExperimentPrivate(accession);
                break;
            case UPDATE_PUBLIC:
                experimentCrud.makeExperimentPublic(accession);
                break;
            case UPDATE_DESIGN_ONLY:
                experimentTrader.removeExperimentFromCache(accession);
                experimentCrud.updateExperimentDesign(accession);
                break;
            case IMPORT_PUBLIC:
                isPrivate = false;
            case IMPORT:
                experimentTrader.removeExperimentFromCache(accession);
                UUID accessKeyUUID = experimentCrud.importExperiment(accession, isPrivate);
                resultOfTheOp = new JsonPrimitive("success, access key UUID: " + accessKeyUUID);
                break;
            case DELETE:
                experimentTrader.removeExperimentFromCache(accession);
                experimentCrud.deleteExperiment(accession);
                break;
            /*
            ANALYTICS_IMPORT and ANALYTICS_DELETE
            are currently different than the Expression Atlas equivalents (uses database not Solr)
             */
            case ANALYTICS_IMPORT:
                analyticsLoaderFactory.getLoader(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE)
                        .loadAnalytics(accession);
                break;
            case ANALYTICS_DELETE:
                analyticsLoaderFactory.getLoader(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE)
                        .deleteAnalytics(accession);
                break;
            case CACHE_REMOVE:
                experimentTrader.removeExperimentFromCache(accession);
            case POPULATE_MARKER_GENES:
                markerGeneDao.loadMarkerGenes(new RandomMarkerGeneInputStream(accession, 5000));
                break;
            case DELETE_MARKER_GENES:
                markerGeneDao.delete(accession);
                break;
            case DELETE_ALL_MARKER_GENES:
                markerGeneDao.deleteAll();
                break;

            default:
                throw new RuntimeException("Op not supported in Single Cell: "+op.name());
        }
        return resultOfTheOp;
    }

}
