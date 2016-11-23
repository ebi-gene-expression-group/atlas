package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SingleCellOpsExecutionService implements ExperimentOpsExecutionService {

    private final ExperimentCrud experimentCrud;
    private final ExperimentTrader experimentTrader;
    private final Gson gson = new Gson();

    public SingleCellOpsExecutionService(ExperimentCrud experimentCrud,
                                         ExperimentTrader experimentTrader) {
        this.experimentCrud = experimentCrud;
        this.experimentTrader = experimentTrader;
    }

    private FluentIterable<ExperimentDTO> allDtos(){
        return FluentIterable.from(experimentCrud.findAllExperiments()).filter(new Predicate<ExperimentDTO>() {
            @Override
            public boolean apply(@Nullable ExperimentDTO experimentDTO) {
                return experimentDTO.getExperimentType().isSingleCell();
            }
        });
    }

    @Override
    public ImmutableList<String> findAllExperiments(){
        return allDtos().transform(new Function<ExperimentDTO, String>() {
            @Nullable
            @Override
            public String apply(ExperimentDTO experimentDTO) {
                return experimentDTO.getExperimentAccession();
            }
        }).toList();
    }

    @Override
    public Optional<JsonElement> attemptExecuteOneStatelessOp(String accession, Op op){
        switch (op) {
            case LIST:
                return Optional.of((JsonElement) experimentCrud.findExperiment(accession).toJson());
            case CACHE_READ:
                return Optional.of(gson.toJsonTree(experimentTrader.getExperiment(accession,
                        experimentCrud.findExperiment(accession).getAccessKey()).getAttributes()));
            default:
                return Optional.absent();
        }
    }

    @Override
    public Optional<? extends List<Pair<String,? extends JsonElement>>> attemptExecuteForAllAccessions(Collection<Op> ops){
        if (ops.equals(Collections.singleton(Op.LIST))) {
            return Optional.of(list());
        } else {
            return Optional.absent();
        }
    }

    @Override
    public Optional<? extends List<Pair<String,? extends JsonElement>>> attemptExecuteForAllAccessions(Op op){
        if (op.equals(Op.LIST)) {
            return Optional.of(list());
        } else {
            return Optional.absent();
        }
    }

    private List<Pair<String,? extends JsonElement>> list(){
        return allDtos().transform(new Function<ExperimentDTO,
                Pair<String,? extends JsonElement>>() {
            @Nullable
            @Override
            public Pair<String,? extends JsonElement> apply(ExperimentDTO experimentDTO) {
                return Pair.of(experimentDTO.getExperimentAccession(), experimentDTO.toJson());
            }
        }).toList();
    }

    @Override
    public JsonPrimitive attemptExecuteStatefulOp(String accession, Op op) throws Exception {
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
            case CACHE_REMOVE:
                experimentTrader.removeExperimentFromCache(accession);
            default:
                throw new RuntimeException("Op not supported in Single Cell: "+op.name());
        }
        return resultOfTheOp;
    }
}
