package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ExpressionAtlasExperimentOpsExecutionService implements ExperimentOpsExecutionService {

    private final ExperimentCrud experimentCrud;
    private final BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;
    private final AnalyticsIndexerManager analyticsIndexerManager;
    private final ExpressionSerializerService expressionSerializerService;
    private final ExperimentTrader experimentTrader;
    private final Gson gson = new Gson();

    public ExpressionAtlasExperimentOpsExecutionService(ExperimentCrud experimentCrud,
                                                        BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader,
                                                        AnalyticsIndexerManager analyticsIndexerManager,
                                                        ExpressionSerializerService expressionSerializerService,
                                                        ExperimentTrader experimentTrader) {
        this.experimentCrud = experimentCrud;
        this.baselineCoexpressionProfileLoader = baselineCoexpressionProfileLoader;
        this.analyticsIndexerManager = analyticsIndexerManager;
        this.expressionSerializerService = expressionSerializerService;
        this.experimentTrader = experimentTrader;
    }

    private Stream<ExperimentDTO> allDtos(){
        return experimentCrud.findAllExperiments().stream().filter(experimentDTO -> !experimentDTO.getExperimentType().isSingleCell());
    }

    @Override
    public List<String> findAllExperiments(){
        return allDtos().map(new Function<ExperimentDTO, String>() {
            @Nullable
            @Override
            public String apply(ExperimentDTO experimentDTO) {
                return experimentDTO.getExperimentAccession();
            }
        }).collect(toList());
    }


    @Override
    public Optional<JsonElement> attemptExecuteOneStatelessOp(String accession, Op op){
        switch (op) {
            case LIST:
                return Optional.of(experimentCrud.findExperiment(accession).toJson());
            case CACHE_READ:
                return Optional.of(gson.toJsonTree(getAnyExperimentWithAdminAccess(accession).getAttributes()));
            default:
                return Optional.empty();
        }
    }

    private Experiment<?> getAnyExperimentWithAdminAccess(String accession){
        return experimentTrader.getExperiment(accession,
                experimentCrud.findExperiment(accession).getAccessKey());
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
        return allDtos().map(
                (Function<ExperimentDTO, Pair<String, ? extends JsonElement>>) experimentDTO ->
                        Pair.of(experimentDTO.getExperimentAccession(), experimentDTO.toJson())).collect(toList());
    }

    @Override
    public JsonPrimitive attemptExecuteStatefulOp(String accession, Op op) throws Exception {
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
            case SERIALIZE:
                resultOfTheOp = new JsonPrimitive(expressionSerializerService.kryoSerializeExpressionData(getAnyExperimentWithAdminAccess(accession)));
                break;
            case DELETE:
                experimentTrader.removeExperimentFromCache(accession);
                analyticsIndexerManager.deleteFromAnalyticsIndex(accession);
                experimentCrud.deleteExperiment(accession);
                expressionSerializerService.removeKryoFile(getAnyExperimentWithAdminAccess(accession));
                break;
            case COEXPRESSION_UPDATE:
                deleteCount = baselineCoexpressionProfileLoader.deleteCoexpressionsProfile(accession);
                loadCount = baselineCoexpressionProfileLoader.loadBaselineCoexpressionsProfile(accession);
                resultOfTheOp = new JsonPrimitive(String.format(" deleted %,d and loaded %,d " +
                        "coexpression profiles", deleteCount, loadCount));
                break;
            case COEXPRESSION_IMPORT:
                loadCount = baselineCoexpressionProfileLoader.loadBaselineCoexpressionsProfile(accession);
                resultOfTheOp = new JsonPrimitive(String.format(" loaded %,d " +
                        "coexpression profiles", loadCount));
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
            case CACHE_REMOVE:
                experimentTrader.removeExperimentFromCache(accession);
            default:
                throw new RuntimeException("Op not supported in Expression Atlas: " + op.name());
        }
        return resultOfTheOp;
    }


}
