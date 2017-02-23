package uk.ac.ebi.atlas.profiles.tsv;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

public class RnaSeqDifferentialExpressionsRowDeserializer extends DifferentialExpressionsRowDeserializer<DifferentialExpression> {

    private Iterator<Contrast> expectedContrasts;

    public RnaSeqDifferentialExpressionsRowDeserializer(List<Contrast> orderedContrasts) {
        this.expectedContrasts = Iterables.cycle(orderedContrasts).iterator();
    }

    public DifferentialExpression nextExpression(Queue<String> tsvRow) {
        String pValueString = tsvRow.poll();
        if (pValueString == null) {
            return null;
        }
        String foldChangeString = tsvRow.poll();
        checkState(foldChangeString != null, "missing fold change column in the analytics file");
        if ("NA".equalsIgnoreCase(pValueString) || "NA".equalsIgnoreCase(foldChangeString)) {
            expectedContrasts.next();
            return nextExpression(tsvRow);
        }
        double pValue = parseDouble(pValueString);
        double foldChange = parseDouble(foldChangeString);

        Contrast contrast = expectedContrasts.next();
        return new DifferentialExpression(pValue, foldChange, contrast);
    }

}
