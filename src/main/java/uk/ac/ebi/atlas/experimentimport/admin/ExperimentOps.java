package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.gson.*;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentCRUD;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ExperimentMetadataCRUD;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/*
consider support for async:
make a cousin of
Executors.newCachedThreadPool();
with a very large queue (that would take all experiments if needed), and 0 to say 4 worker threads

 */
@Named
public class ExperimentOps {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentOps.class);

    private ExperimentCRUD experimentCRUD;
    private ExperimentMetadataCRUD experimentMetadataCRUD;
    private ExperimentOpLogWriter experimentOpLogWriter;
    private BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;
    private AnalyticsIndexerManager analyticsIndexerManager;


    @Inject
    public ExperimentOps(ExperimentCRUD experimentCRUD, ExperimentMetadataCRUD experimentMetadataCRUD,
                         ExperimentOpLogWriter experimentOpLogWriter, BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader, AnalyticsIndexerManager analyticsIndexerManager) {
        this.experimentCRUD = experimentCRUD;
        this.experimentMetadataCRUD = experimentMetadataCRUD;
        this.experimentOpLogWriter = experimentOpLogWriter;
        this.baselineCoexpressionProfileLoader = baselineCoexpressionProfileLoader;
        this.analyticsIndexerManager = analyticsIndexerManager;
    }

    static Long UNFINISHED = new Long(-1);

    private enum OpResult {SUCCESS, FAILURE}
    private static JsonPrimitive DEFAULT_SUCCESS_RESULT = new JsonPrimitive("success");


    public JsonArray perform(Optional<? extends Collection<String>> accessions, Collection<Op> ops){
        if(ops.size()==1){
            if(accessions.isPresent()){
                return perform(accessions.get(), ops.iterator().next());
            } else {
                return perform(ops.iterator().next());
            }
        } else {
            if(accessions.isPresent()){
                return perform(accessions.get(), ops);
            } else {
                return perform(ops);
            }
        }
    }



    private JsonArray perform(Collection<Op> ops){
        List<ExperimentDTO> dtos = experimentMetadataCRUD.findAllExperiments();
        //default case of all experiments and a request to list them, optimised here
        if (ops.equals(Collections.singleton(Op.LIST))) {
            JsonArray array = new JsonArray();
            for (ExperimentDTO dto : dtos) {
                array.add(showResult(dto.getExperimentAccession(),OpResult.SUCCESS, dto.toJson()));
            }
            return array;
        } else {
            List<String> accessions = new ArrayList<>();
            for (ExperimentDTO dto : dtos) {
                accessions.add(dto.getExperimentAccession());
            }
            return perform(accessions, ops);
        }
    }

    private JsonArray perform(Op op){
        List<ExperimentDTO> dtos = experimentMetadataCRUD.findAllExperiments();
        //default case of all experiments and a request to list them, optimised here
        if (op.equals(Op.LIST)) {
            JsonArray array = new JsonArray();
            for (ExperimentDTO dto : dtos) {
                array.add(showResult(dto.getExperimentAccession(),OpResult.SUCCESS, dto.toJson()));
            }
            return array;
        } else {
            List<String> accessions = new ArrayList<>();
            for (ExperimentDTO dto : dtos) {
                accessions.add(dto.getExperimentAccession());
            }
            return perform(accessions, op);
        }
    }

    private JsonArray perform(Collection<String> accessions, Op op) {
        JsonArray result = new JsonArray();
        for (String accession : accessions) {
            Pair<OpResult, ? extends JsonElement> r = performOneOp(accession, op);
            result.add(showResult(accession, r.getLeft(), r.getRight()));
        }
        return result;
    }

    private JsonArray perform(Collection<String> accessions, Collection<Op> ops) {
        JsonArray result = new JsonArray();
        for (String accession : accessions) {
            result.add(packageResultIntoJsonObject(accession, performManyOpsAndReturnResultingErrorsAndResults
                    (accession, ops)));
        }
        return result;
    }

    private JsonObject packageResultIntoJsonObject(String accession, Pair<JsonArray, JsonArray> r){
        JsonObject resultForOneAccession = new JsonObject();
        resultForOneAccession.add("accession", new JsonPrimitive(accession));
        if(r.getRight().size()>0){
            resultForOneAccession.add("result", r.getRight());
        }
        if(r.getLeft().size()>0){
            resultForOneAccession.add("error", r.getLeft());
        }

        return resultForOneAccession;
    }

    //wrong ones on the left, right ones on the right. :)
    private Pair<JsonArray, JsonArray> performManyOpsAndReturnResultingErrorsAndResults(String accession,
                                                                                        Iterable<Op> ops){
        JsonArray opsWithErrors = new JsonArray();
        JsonArray opsWithResults = new JsonArray();
        boolean failed = false;
        boolean setFailed = false;
        JsonElement resultOfPreviousOperations = JsonNull.INSTANCE;
        JsonElement resultOfCurrentOperation;
        List<Op> opsThatProducedTheSameResult = new ArrayList<>();
        for(Op op: ops){
            if(failed){
                resultOfCurrentOperation = new JsonPrimitive("Not started");
            } else {
                Pair<OpResult, ? extends JsonElement> r = performOneOp(accession, op);
                if(r.getLeft().equals(OpResult.FAILURE)){
                    setFailed = true;
                }
                resultOfCurrentOperation = r.getRight();
            }

            if(resultOfPreviousOperations.equals(resultOfCurrentOperation)){
                opsThatProducedTheSameResult.add(op);
            } else {
                if(! resultOfPreviousOperations.equals(JsonNull.INSTANCE)) {
                    (failed? opsWithErrors : opsWithResults).add(aggregatedResultsObject
                            (opsThatProducedTheSameResult,
                                    resultOfPreviousOperations));
                }
                opsThatProducedTheSameResult =new ArrayList<>();
                opsThatProducedTheSameResult.add(op);
                resultOfPreviousOperations = resultOfCurrentOperation;
            }
            failed |= setFailed;
        }
        (failed? opsWithErrors : opsWithResults).add(aggregatedResultsObject(opsThatProducedTheSameResult,
                resultOfPreviousOperations));
        return Pair.of(opsWithErrors, opsWithResults);
    }

    private JsonObject aggregatedResultsObject(Collection<Op> ops, JsonElement result){
        JsonObject objectBeingReturned = new JsonObject();
        String niceEnoughName = ops.size()==1
                ? ops.iterator().next().name()
                : niceEnoughKeyName(ops);
        objectBeingReturned.add(niceEnoughName, result);
        return objectBeingReturned;
    }

    private String niceEnoughKeyName(Collection<Op> ops){
        Collection<String> names = new ArrayList<>();
        for(Op op: ops){
            names.add(op.name());
        }
        return Joiner.on(',').join(names);
    }

    private Pair<OpResult, ? extends JsonElement> performOneOp(String accession, Op op){
        switch (op) {
            case LIST:
                return Pair.of(OpResult.SUCCESS, experimentMetadataCRUD.findExperiment(accession).toJson());
            case LOG:
                return Pair.of(OpResult.SUCCESS, getCurrentOpLogAsJson(accession));
            case STATUS:
                return Pair.of(OpResult.SUCCESS, readStatusFromOpLog(accession));
            case CLEAR_LOG:
                return Pair.of(OpResult.SUCCESS, clearOpLog(accession));
            default:
                return performStatefulOp(accession, op);
        }
    }



    private JsonElement showResult(String accession, OpResult opResult, JsonElement opValue){
        JsonObject resultObject = new JsonObject();
        resultObject.add("accession", new JsonPrimitive(accession));
        if(opResult.equals(OpResult.SUCCESS)){
            resultObject.add("result", opValue);
        } else {
            resultObject.add("error", opValue);
        }
        return resultObject;
    }

    private Pair<OpResult,JsonPrimitive> performStatefulOp(String accession, Op op) {
        Pair<OpResult,JsonPrimitive> result;
        List<Pair<String, Pair<Long, Long>>> opRecords = experimentOpLogWriter.getCurrentOpLog(accession);

        Pair<String, Pair<Long, Long>> lastOp = opRecords.isEmpty() ? null : opRecords.get(opRecords.size() - 1);
        if (lastOp != null && lastOp.getRight().getRight().equals(UNFINISHED)) {
            StringBuilder sb = new StringBuilder("Operation ");
            sb.append(lastOp.getLeft());
            sb.append(" started at ");
            sb.append(new DateTime(lastOp.getRight().getLeft()).toString());
            sb.append(" and not finished. Refusing to start ");
            sb.append(op);
            result= Pair.of(OpResult.FAILURE, new JsonPrimitive(sb.toString()));
        } else {
            Pair<String, Pair<Long, Long>> newOpRecord = Pair.of(op.name(), Pair.of(System.currentTimeMillis(), UNFINISHED));
            opRecords.add(newOpRecord);
            experimentOpLogWriter.persistOpLog(accession, opRecords);
            try {
                result = Pair.of(OpResult.SUCCESS, attemptExecuteStatefulOp(accession, op));
                updateOpRecordsWithNewOp(OpResult.SUCCESS, opRecords, newOpRecord);
            } catch (Exception e) {
                String text = e.getMessage()!=null? e.getMessage() : e.toString();
                LOGGER.error(text,e);
                result = Pair.of(OpResult.FAILURE, new JsonPrimitive(text));
                updateOpRecordsWithNewOp(OpResult.FAILURE, opRecords, newOpRecord);
            } finally {
                experimentOpLogWriter.persistOpLog(accession, opRecords);
            }
        }
        return result;
    }

    private void updateOpRecordsWithNewOp(OpResult result, List<Pair<String, Pair<Long, Long>>> opRecords,
                                          Pair<String, Pair<Long, Long>> newOpRecord){
        if (!opRecords.isEmpty()) {
            opRecords.remove(opRecords.size() - 1);
        }
        opRecords.add(Pair.of((result.equals(OpResult.FAILURE)?"FAILED: ":"")
                +newOpRecord.getLeft(), Pair.of(newOpRecord.getRight().getLeft(),
                System.currentTimeMillis())));
    }

    private JsonPrimitive attemptExecuteStatefulOp(String accession, Op op) throws Exception {
        JsonPrimitive resultOfTheOp = DEFAULT_SUCCESS_RESULT;
        boolean isPrivate = true;
        int deleteCount;
        int loadCount;
        switch (op) {
            case UPDATE_PRIVATE:
                experimentMetadataCRUD.makeExperimentPrivate(accession);
                break;
            case UPDATE_PUBLIC:
                experimentMetadataCRUD.makeExperimentPublic(accession);
                break;
            case UPDATE_DESIGN_ONLY:
                experimentMetadataCRUD.updateExperimentDesign(accession);
                break;
            case IMPORT_PUBLIC:
                isPrivate = false;
            case IMPORT:
                UUID accessKeyUUID = experimentCRUD.importExperiment(accession, isPrivate);
                resultOfTheOp = new JsonPrimitive("success, access key UUID: " + accessKeyUUID);
                break;
            case SERIALIZE:
                resultOfTheOp = new JsonPrimitive(experimentCRUD.serializeExpressionData(accession));
                break;
            case DELETE:
                experimentCRUD.deleteExperiment(accession);
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
            default:
                break;
        }
        return resultOfTheOp;
    }

    private JsonElement clearOpLog(String accession) {
        experimentOpLogWriter.persistOpLog(accession, new ArrayList<Pair<String, Pair<Long, Long>>>());
        return DEFAULT_SUCCESS_RESULT;
    }

    private JsonElement getCurrentOpLogAsJson(String accession) {
        JsonArray opsArray = new JsonArray();
        for (Pair<String, Pair<Long, Long>> opLogEntry : experimentOpLogWriter.getCurrentOpLog(accession)) {
            JsonObject opObject = new JsonObject();
            opObject.add("op", new JsonPrimitive(opLogEntry.getLeft()));
            opObject.add("started", new JsonPrimitive(opLogEntry.getRight().getLeft()));
            opObject.add("finished", new JsonPrimitive(opLogEntry.getRight().getRight()));
            opsArray.add(opObject);
        }
        return opsArray;
    }

    private JsonElement readStatusFromOpLog(String accession) {
        JsonObject result = new JsonObject();
        List<Pair<String, Pair<Long, Long>>> currentOpLog = experimentOpLogWriter.getCurrentOpLog(accession);
        if (currentOpLog.isEmpty()) {
            return result;
        }
        Pair<String, Pair<Long, Long>> lastEntry = currentOpLog.get(currentOpLog.size() - 1);
        result.add("op", new JsonPrimitive(lastEntry.getLeft()));
        Long started = lastEntry.getRight().getLeft();
        Long finished = lastEntry.getRight().getRight();
        result.add("started", new JsonPrimitive(new DateTime(started).toString()));
        result.add("in-progress", new JsonPrimitive(finished.equals(UNFINISHED)));
        result.add("elapsed", new JsonPrimitive(
                new Period((
                        finished.equals(UNFINISHED)
                                ? System.currentTimeMillis()
                                : finished)
                        - started).toStandardSeconds().getSeconds() + "s"));
        return result;
    }

}
