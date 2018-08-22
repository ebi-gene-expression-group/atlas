package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/*
consider support for async:
make a cousin of Executors.newCachedThreadPool();
with a very large queue (that would take all experiments if needed), and 0 to say 4 worker threads
 */

public class ExperimentOps {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentOps.class);

    private final ExperimentOpLogWriter experimentOpLogWriter;
    private final ExperimentOpsExecutionService experimentOpsExecutionService;

    public ExperimentOps(ExperimentOpLogWriter experimentOpLogWriter,
                         ExperimentOpsExecutionService experimentOpsExecutionService) {
        this.experimentOpLogWriter = experimentOpLogWriter;
        this.experimentOpsExecutionService = experimentOpsExecutionService;
    }

    private enum OpResult { SUCCESS, FAILURE }
    public static final JsonPrimitive DEFAULT_SUCCESS_RESULT = new JsonPrimitive("success");


    public Iterable<JsonElement> dispatchAndPerform(Collection<String> accessions, Collection<Op> ops) {
        if (ops.size() == 1) {
            if (!accessions.isEmpty()) {
                return perform(accessions, ops.iterator().next());
            } else {
                return perform(ops.iterator().next());
            }
        } else {
            if (!accessions.isEmpty()) {
                return perform(accessions, ops);
            } else {
                return perform(ops);
            }
        }
    }

    List<String> findAllExperiments() {
        return experimentOpsExecutionService.findAllExperiments();
    }

    private Iterable<JsonElement> perform(Collection<Op> ops) {
        Optional<? extends List<Pair<String, ? extends JsonElement>>> r =
                experimentOpsExecutionService.attemptExecuteForAllAccessions(ops);
        if (r.isPresent()) {
            JsonArray array = new JsonArray();
            for (Pair<String, ? extends JsonElement> p : r.get()) {
                array.add(showSuccess(p));
            }
            return array;
        } else {
            return perform(findAllExperiments(), ops);
        }
    }

    private Iterable<JsonElement> perform(Op op) {
        Optional<? extends List<Pair<String, ? extends JsonElement>>> r =
                experimentOpsExecutionService.attemptExecuteForAllAccessions(op);
        if (r.isPresent()) {
            JsonArray array = new JsonArray();
            for (Pair<String, ? extends JsonElement> p : r.get()) {
                array.add(showSuccess(p));
            }
            return array;
        } else {
            return perform(findAllExperiments(), op);
        }
    }

    private Iterable<JsonElement> perform(final Collection<String> accessions, final Op op) {
        return accessions
                .stream()
                .map(accession -> {
                    Pair<OpResult, ? extends JsonElement> r = performOneOp(accession, op);
                    return showResult(accession, r.getLeft(), r.getRight());
                })
                .collect(toList());
    }

    private Iterable<JsonElement> perform(Collection<String> accessions, Collection<Op> ops) {
        JsonArray result = new JsonArray();
        for (String accession : accessions) {
            result.add(
                    packageResultIntoJsonObject(
                            accession,
                            performManyOpsAndReturnResultingErrorsAndResults(accession, ops)));
        }
        return result;
    }

    private JsonObject packageResultIntoJsonObject(String accession, Pair<JsonArray, JsonArray> r) {
        JsonObject resultForOneAccession = new JsonObject();
        resultForOneAccession.add("accession", new JsonPrimitive(accession));
        if (r.getRight().size() > 0) {
            resultForOneAccession.add("result", r.getRight());
        }
        if (r.getLeft().size() > 0) {
            resultForOneAccession.add("error", r.getLeft());
        }

        return resultForOneAccession;
    }

    // Wrong ones on the left, right ones on the right. :)
    private Pair<JsonArray, JsonArray> performManyOpsAndReturnResultingErrorsAndResults(String accession,
                                                                                        Iterable<Op> ops) {
        JsonArray opsWithErrors = new JsonArray();
        JsonArray opsWithResults = new JsonArray();
        boolean failed = false;
        boolean setFailed = false;
        JsonElement resultOfPreviousOperations = JsonNull.INSTANCE;
        JsonElement resultOfCurrentOperation;
        List<Op> opsThatProducedTheSameResult = new ArrayList<>();
        for (Op op: ops) {
            if (failed) {
                resultOfCurrentOperation = new JsonPrimitive("Not started");
            } else {
                Pair<OpResult, ? extends JsonElement> r = performOneOp(accession, op);
                if (r.getLeft().equals(OpResult.FAILURE)) {
                    setFailed = true;
                }
                resultOfCurrentOperation = r.getRight();
            }

            if (resultOfPreviousOperations.equals(resultOfCurrentOperation)) {
                opsThatProducedTheSameResult.add(op);
            } else {
                if (!resultOfPreviousOperations.equals(JsonNull.INSTANCE)) {
                    (failed ? opsWithErrors : opsWithResults)
                            .add(aggregatedResultsObject(opsThatProducedTheSameResult, resultOfPreviousOperations));
                }
                opsThatProducedTheSameResult = new ArrayList<>();
                opsThatProducedTheSameResult.add(op);
                resultOfPreviousOperations = resultOfCurrentOperation;
            }
            failed |= setFailed;
        }
        (failed ? opsWithErrors : opsWithResults)
                .add(aggregatedResultsObject(opsThatProducedTheSameResult, resultOfPreviousOperations));
        return Pair.of(opsWithErrors, opsWithResults);
    }

    private JsonObject aggregatedResultsObject(Collection<Op> ops, JsonElement result) {
        JsonObject objectBeingReturned = new JsonObject();
        String niceEnoughName = ops.size() == 1 ?
                ops.iterator().next().name() :
                niceEnoughKeyName(ops);
        objectBeingReturned.add(niceEnoughName, result);
        return objectBeingReturned;
    }

    private String niceEnoughKeyName(Collection<Op> ops) {
        Collection<String> names = ops.stream().map(Op::name).collect(Collectors.toCollection(ArrayList::new));
        return Joiner.on(',').join(names);
    }

    private Pair<OpResult, ? extends JsonElement> performOneOp(final String accession, final Op op) {
        Optional<JsonElement> opResult = attemptPerformOneOpRelatedToTheOpLog(accession, op);
        if (!opResult.isPresent()) {
            opResult = experimentOpsExecutionService.attemptExecuteOneStatelessOp(accession, op);
        }

        return opResult
                .<Pair<OpResult, ? extends JsonElement>>map(jsonElement -> Pair.of(OpResult.SUCCESS, jsonElement))
                .orElseGet(() -> performStatefulOp(accession, op));
    }

    private Optional<JsonElement> attemptPerformOneOpRelatedToTheOpLog(String accession, Op op) {
        switch (op) {
            case LOG:
                return Optional.of(getCurrentOpLogAsJson(accession));
            case STATUS:
                return Optional.of(readStatusFromOpLog(accession));
            case CLEAR_LOG:
                return Optional.of(clearOpLog(accession));
            default:
                return Optional.empty();
        }
    }

    private JsonElement showSuccess(Pair<String, ? extends JsonElement> accessionAndOpValue) {
        return showResult(accessionAndOpValue.getLeft(), OpResult.SUCCESS, accessionAndOpValue.getRight());
    }

    private JsonElement showResult(String accession, OpResult opResult, JsonElement opValue) {
        JsonObject resultObject = new JsonObject();
        resultObject.add("accession", new JsonPrimitive(accession));
        if (opResult.equals(OpResult.SUCCESS)) {
            resultObject.add("result", opValue);
        } else {
            resultObject.add("error", opValue);
        }
        return resultObject;
    }

    private Pair<OpResult, JsonPrimitive> performStatefulOp(String accession, Op op) {
        Pair<OpResult, JsonPrimitive> result;

        ImmutableList<OpLogEntry> opRecords = experimentOpLogWriter.getCurrentOpLog(accession);
        OpLogEntry lastOp = opRecords.isEmpty() ? null : opRecords.get(opRecords.size() - 1);

        if (lastOp != null && lastOp.isInProgress()) {
            result =
                    Pair.of(
                            OpResult.FAILURE,
                            new JsonPrimitive(lastOp.statusMessage() + " Refusing to start " + op));
        } else {
            OpLogEntry newOpRecord = OpLogEntry.newlyStartedOp(op);
            experimentOpLogWriter.persistOpLog(
                    accession,
                    ImmutableList.<OpLogEntry>builder().addAll(opRecords).add(newOpRecord).build());

            try {
                result =
                        Pair.of(OpResult.SUCCESS,
                                experimentOpsExecutionService.attemptExecuteStatefulOp(accession, op));

                experimentOpLogWriter.persistOpLog(
                        accession,
                        ImmutableList.<OpLogEntry>builder().addAll(opRecords).add(newOpRecord.toSuccess()).build());

            } catch (Exception e) {
                String text = e.getMessage() != null ? e.getMessage() : e.toString();
                LOGGER.error(text, e);
                result = Pair.of(OpResult.FAILURE, new JsonPrimitive(text));

                experimentOpLogWriter.persistOpLog(
                        accession,
                        ImmutableList.<OpLogEntry>builder().addAll(opRecords).add(newOpRecord.toFailure(text)).build());
            }
        }

        return result;
    }

    private JsonElement clearOpLog(String accession) {
        experimentOpLogWriter.persistOpLog(accession, ImmutableList.of());
        return DEFAULT_SUCCESS_RESULT;
    }

    private JsonElement getCurrentOpLogAsJson(String accession) {
        JsonArray opsArray = new JsonArray();
        for (OpLogEntry opLogEntry : experimentOpLogWriter.getCurrentOpLog(accession)) {
            opsArray.add(opLogEntry.toJson());
        }
        return opsArray;
    }

    private JsonElement readStatusFromOpLog(String accession) {
        List<OpLogEntry> currentOpLog = experimentOpLogWriter.getCurrentOpLog(accession);
        if (currentOpLog.isEmpty()) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(currentOpLog.get(currentOpLog.size() - 1).statusMessage());
        }
    }

}
