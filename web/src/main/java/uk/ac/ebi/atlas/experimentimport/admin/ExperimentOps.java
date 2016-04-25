package uk.ac.ebi.atlas.experimentimport.admin;

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
consider support for async

make a cousin of
Executors.newCachedThreadPool();
with a very large queue (that would take all experiments if needed), and 0 to say 4 worker threads

consider allowing multiple operations in sequence - e.g. experiment/<accession>/delete,clear_log
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
    private static JsonElement DEFAULT_SUCCESS_RESULT = new JsonPrimitive("success");


    public JsonArray perform(Op op) {
        List<ExperimentDTO> dtos = experimentMetadataCRUD.findAllExperiments();
        if (op.equals(Op.LIST)) {
            JsonArray array = new JsonArray();
            for (ExperimentDTO dto : dtos) {
                array.add(showResult(dto.getExperimentAccession(), dto.toJson()));
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

    public JsonArray perform(Collection<String> accessions, Op op) {
        JsonArray result = new JsonArray();

        for (String accession : accessions) {
            switch (op) {
                case LIST:
                    result.add(showResult(accession, experimentMetadataCRUD.findExperiment(accession).toJson()));
                    break;
                case LOG:
                    result.add(showResult(accession, getCurrentOpLogAsJson(accession)));
                    break;
                case STATUS:
                    result.add(showResult(accession, readStatusFromOpLog(accession)));
                    break;
                case CLEAR_LOG:
                    result.add(showResult(accession, clearOpLog(accession)));
                    break;
                default:
                    result.add(performStatefulOp(accession, op));
                    break;
            }
        }
        return result;
    }

    private JsonElement clearOpLog(String accession) {
        experimentOpLogWriter.persistOpLog(accession, new ArrayList<Pair<String, Pair<Long, Long>>>());
        return DEFAULT_SUCCESS_RESULT;
    }

    private JsonElement showResult(String accession, JsonElement result) {
        JsonObject resultForThis = new JsonObject();
        resultForThis.add("accession", new JsonPrimitive(accession));
        resultForThis.add("result", result);
        return resultForThis;
    }

    private JsonElement performStatefulOp(String accession, Op op) {
        JsonObject result = new JsonObject();
        result.add("accession", new JsonPrimitive(accession));
        List<Pair<String, Pair<Long, Long>>> opRecords = experimentOpLogWriter.getCurrentOpLog(accession);

        Pair<String, Pair<Long, Long>> lastOp = opRecords.isEmpty() ? null : opRecords.get(opRecords.size() - 1);
        if (lastOp != null && lastOp.getRight().getRight().equals(UNFINISHED)) {
            StringBuilder sb = new StringBuilder("Operation ");
            sb.append(lastOp.getLeft());
            sb.append(" started at ");
            sb.append(new DateTime(lastOp.getRight().getLeft()).toString());
            sb.append(" and not finished. Refusing to start ");
            sb.append(op);
            result.add("error", new JsonPrimitive(sb.toString()));
        } else {
            Pair<String, Pair<Long, Long>> newOpRecord = Pair.of(op.name(), Pair.of(System.currentTimeMillis(), UNFINISHED));
            opRecords.add(newOpRecord);
            experimentOpLogWriter.persistOpLog(accession, opRecords);

            try {
                JsonElement resultOfTheOp = DEFAULT_SUCCESS_RESULT;
                boolean isPrivate = true;
                int deleteCount = 0;
                int loadCount = 0;
                switch (op) {
                    case UPDATE_PUBLIC:
                        isPrivate = false;
                    case UPDATE:
                        experimentMetadataCRUD.updateExperiment(accession, isPrivate);
                        break;
                    case UPDATE_DESIGN:
                        experimentMetadataCRUD.updateExperimentDesign(accession);
                        break;
                    case IMPORT_PUBLIC:
                        isPrivate = false;
                    case IMPORT:
                        UUID accessKeyUUID = experimentCRUD.importExperiment(accession, isPrivate);
                        resultOfTheOp = new JsonPrimitive("success, access key UUID: " + accessKeyUUID);
                        break;
                    case SERIALIZE:
                        experimentCRUD.serializeExpressionData(accession);
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

                result.add("result", resultOfTheOp);
                if (!opRecords.isEmpty()) {
                    opRecords.remove(opRecords.size() - 1);
                }
                opRecords.add(Pair.of(newOpRecord.getLeft(), Pair.of(newOpRecord.getRight().getLeft(),
                        System.currentTimeMillis())));
            } catch (Exception e) {
                String text = e.getMessage();
                LOGGER.error(text);
                result.add("error", new JsonPrimitive(text));
                if (!opRecords.isEmpty()) {
                    opRecords.remove(opRecords.size() - 1);
                }
                opRecords.add(Pair.of("FAILED: "+newOpRecord.getLeft(), Pair.of(newOpRecord.getRight().getLeft(),
                        System.currentTimeMillis())));
            } finally {
                experimentOpLogWriter.persistOpLog(accession, opRecords);
            }
        }
        return result;
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

    public enum Op {
        LIST, LOG, STATUS, CLEAR_LOG, UPDATE, UPDATE_PUBLIC, UPDATE_DESIGN, IMPORT, IMPORT_PUBLIC, DELETE,
        SERIALIZE, COEXPRESSION_IMPORT, COEXPRESSION_UPDATE, COEXPRESSION_DELETE, ANALYTICS_IMPORT, ANALYTICS_DELETE
    }
}
