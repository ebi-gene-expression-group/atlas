package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ExperimentOpsExecutionService {
    List<String> findAllExperiments();

    Optional<JsonElement> attemptExecuteOneStatelessOp(String accession, Op op);

    Optional<? extends List<Pair<String, ? extends JsonElement>>> attemptExecuteForAllAccessions(Collection<Op> ops);

    Optional<? extends List<Pair<String, ? extends JsonElement>>> attemptExecuteForAllAccessions(Op op);

    JsonPrimitive attemptExecuteStatefulOp(String accession, Op op) throws IOException;
}
