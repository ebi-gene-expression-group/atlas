package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class ExpressionAtlasExperimentOpsExecutionService implements ExperimentOpsExecutionService {
    private final ExperimentCrud experimentCrud;
    private final BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;
    private final AnalyticsIndexerManager analyticsIndexerManager;
    private final ExperimentTrader experimentTrader;
    private final ExperimentAttributesService experimentAttributesService;

    public ExpressionAtlasExperimentOpsExecutionService(ExperimentCrud experimentCrud,
                                                        BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader,
                                                        AnalyticsIndexerManager analyticsIndexerManager,
                                                        ExperimentTrader experimentTrader,
                                                        ExperimentAttributesService experimentAttributesService) {
        this.experimentCrud = experimentCrud;
        this.baselineCoexpressionProfileLoader = baselineCoexpressionProfileLoader;
        this.analyticsIndexerManager = analyticsIndexerManager;
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
    }

    @Override
    public List<String> findAllExperiments(){
        return allDtos().map(ExperimentDTO::getExperimentAccession).collect(toList());
    }

    @Override
    public Optional<JsonElement> attemptExecuteOneStatelessOp(String accession, Op op) {
        switch (op) {
            case LIST:
                return Optional.of(experimentCrud.findExperiment(accession).toJson());
            case CACHE_READ:
                return Optional.of(
                        GSON.toJsonTree(
                                experimentAttributesService.getAttributes(getAnyExperimentWithAdminAccess(accession))));
            case CACHE_REMOVE:
                experimentTrader.removeExperimentFromCache(accession);
                return Optional.of(ExperimentOps.DEFAULT_SUCCESS_RESULT);
            case CHECK:
                experimentCrud.checkFiles(accession);
                return Optional.of(ExperimentOps.DEFAULT_SUCCESS_RESULT);
            default:
                return Optional.empty();
        }
    }

    @Override
    public Optional<? extends List<Pair<String, ? extends JsonElement>>> attemptExecuteForAllAccessions(Collection<Op> ops) {
        if (ops.equals(Collections.singleton(Op.LIST))) {
            return Optional.of(list());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<? extends List<Pair<String, ? extends JsonElement>>> attemptExecuteForAllAccessions(Op op) {
        if (op.equals(Op.LIST)) {
            return Optional.of(list());
        } else {
            return Optional.empty();
        }
    }

    private Experiment<?> getAnyExperimentWithAdminAccess(String accession) {
        return experimentTrader.getExperiment(
                accession,
                experimentCrud.findExperiment(accession).getAccessKey());
    }

    private Stream<ExperimentDTO> allDtos() {
        return experimentCrud.findAllExperiments().stream();
    }

    private List<Pair<String, ? extends JsonElement>> list(){
        return allDtos()
                .map(experimentDTO -> Pair.of(experimentDTO.getExperimentAccession(), experimentDTO.toJson()))
                .collect(toList());
    }

    @Override
    public JsonPrimitive attemptExecuteStatefulOp(String accession, Op op) throws IOException {
        JsonPrimitive resultOfTheOp = ExperimentOps.DEFAULT_SUCCESS_RESULT;
        boolean isPrivate = true;
        int deleteCount;
        int loadCount;

        switch (op) {
            case UPDATE_PRIVATE:
                analyticsIndexerManager.deleteFromAnalyticsIndex(accession);
                experimentCrud.makeExperimentPrivate(accession);
                break;
            case UPDATE_PUBLIC:
                experimentCrud.makeExperimentPublic(accession);
                break;
            case UPDATE_DESIGN:
                experimentTrader.removeExperimentFromCache(accession);
                experimentCrud.updateExperimentDesign(accession);
                break;
            case IMPORT_PUBLIC:
                isPrivate = false;
            case IMPORT:
                experimentTrader.removeExperimentFromCache(accession);
                UUID accessKeyUUID = experimentCrud.importExperiment(accession, isPrivate);
                resultOfTheOp = new JsonPrimitive("Success, access key UUID: " + accessKeyUUID);
                break;
            case DELETE:
                experimentTrader.removeExperimentFromCache(accession);
                analyticsIndexerManager.deleteFromAnalyticsIndex(accession);
                experimentCrud.deleteExperiment(accession);
                break;
            case COEXPRESSION_UPDATE:
                deleteCount = baselineCoexpressionProfileLoader.deleteCoexpressionsProfile(accession);
                loadCount = baselineCoexpressionProfileLoader.loadBaselineCoexpressionsProfile(accession);
                resultOfTheOp =
                        new JsonPrimitive(
                                String.format(
                                        " deleted %,d and loaded %,d coexpression profiles", deleteCount, loadCount));
                break;
            case COEXPRESSION_IMPORT:
                loadCount = baselineCoexpressionProfileLoader.loadBaselineCoexpressionsProfile(accession);
                resultOfTheOp = new JsonPrimitive(String.format(" loaded %,d coexpression profiles", loadCount));
                break;
            case COEXPRESSION_DELETE:
                deleteCount = baselineCoexpressionProfileLoader.deleteCoexpressionsProfile(accession);
                resultOfTheOp = new JsonPrimitive(String.format(" deleted %,d coexpression profiles", deleteCount));
                break;
            case ANALYTICS_IMPORT:
                loadCount = analyticsIndexerManager.addToAnalyticsIndex(accession);
                resultOfTheOp = new JsonPrimitive(String.format("(re)indexed %,d documents", loadCount));
                break;
            case ANALYTICS_DELETE:
                analyticsIndexerManager.deleteFromAnalyticsIndex(accession);
                break;
            default:
                throw new IllegalArgumentException("Op not supported in Expression Atlas: " + op.name());
        }
        return resultOfTheOp;
    }

}
