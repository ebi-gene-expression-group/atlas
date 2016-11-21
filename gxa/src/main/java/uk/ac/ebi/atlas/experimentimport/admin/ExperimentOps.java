package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.gson.*;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
consider support for async:
make a cousin of
Executors.newCachedThreadPool();
with a very large queue (that would take all experiments if needed), and 0 to say 4 worker threads

 */
@Named
public class ExperimentOps {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentOps.class);

    private final ExperimentOpLogWriter experimentOpLogWriter;
    private final ExperimentOpsExecutionService experimentOpsExecutionService;

    @Inject
    public ExperimentOps(ExperimentOpLogWriter experimentOpLogWriter,
                         ExperimentOpsExecutionService experimentOpsExecutionService) {
        this.experimentOpLogWriter = experimentOpLogWriter;
        this.experimentOpsExecutionService = experimentOpsExecutionService;
    }

    static Long UNFINISHED = new Long(-1);

    private enum OpResult {SUCCESS, FAILURE}
    public static final JsonPrimitive DEFAULT_SUCCESS_RESULT = new JsonPrimitive("success");


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
        Optional<? extends List<Pair<String,? extends JsonElement>>> r =
                experimentOpsExecutionService.attemptExecuteForAllAccessions(ops);
        if(r.isPresent()){
            JsonArray array = new JsonArray();
            for(Pair<String,? extends JsonElement> p : r.get()){
                array.add(showSuccess(p));
            }
            return array;
        } else {
            return perform(experimentOpsExecutionService.findAllExperiments(), ops);
        }
    }

    private JsonArray perform(Op op){
        Optional<? extends List<Pair<String,? extends JsonElement>>> r =
                experimentOpsExecutionService.attemptExecuteForAllAccessions(op);
        if(r.isPresent()){
            JsonArray array = new JsonArray();
            for(Pair<String,? extends JsonElement> p : r.get()){
                array.add(showSuccess(p));
            }
            return array;
        } else {
            return perform(experimentOpsExecutionService.findAllExperiments(), op);
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

    private Pair<OpResult, ? extends JsonElement> performOneOp(final String accession, final Op op){
        return attemptPerformOneOpRelatedToTheOpLog(accession, op)
                .or(
                        experimentOpsExecutionService.attemptExecuteOneStatelessOp(accession, op)
                ).transform(new Function<JsonElement, Pair<OpResult, ? extends JsonElement>>() {
                    @Override
                    public Pair<OpResult, ? extends JsonElement> apply(@Nullable JsonElement jsonElement) {
                        return Pair.of(OpResult.SUCCESS, jsonElement);
                    }
                }).or(new Supplier<Pair<OpResult, ? extends JsonElement>>() {
                    @Override
                    public Pair<OpResult, ? extends JsonElement> get() {
                        return performStatefulOp(accession, op);
                    }
                });
    }

    private Optional<JsonElement> attemptPerformOneOpRelatedToTheOpLog(String accession, Op op){
        switch (op) {
            case LOG:
                return Optional.of(getCurrentOpLogAsJson(accession));
            case STATUS:
                return Optional.of(readStatusFromOpLog(accession));
            case CLEAR_LOG:
                return Optional.of(clearOpLog(accession));
            default:
                return Optional.absent();
        }
    }

    private JsonElement showSuccess(Pair<String,? extends JsonElement> accessionAndOpValue){
        return showResult(accessionAndOpValue.getLeft(), OpResult.SUCCESS, accessionAndOpValue.getRight());
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
                result = Pair.of(OpResult.SUCCESS,
                        experimentOpsExecutionService
                                .attemptExecuteStatefulOp(accession, op));
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
