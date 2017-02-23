package uk.ac.ebi.atlas.profiles.tsv;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

public class MicroarrayExpressionsRowDeserializer extends
        DifferentialExpressionsRowDeserializer<MicroarrayExpression> {

    private Iterator<Contrast> expectedContrasts;

    public MicroarrayExpressionsRowDeserializer(List<Contrast> orderedContrasts) {
        this.expectedContrasts = Iterables.cycle(orderedContrasts).iterator();
    }

    public MicroarrayExpression nextExpression(Queue<String> tsvRow) {
        String pValueString = tsvRow.poll();
        if (pValueString == null) {
            return null;
        }

        String tStatisticString = tsvRow.poll();
        checkState(tStatisticString != null, "missing tStatistic column in the analytics file");

        String foldChangeString = tsvRow.poll();
        checkState(foldChangeString != null, "missing fold change column in the analytics file");


        if ("NA".equalsIgnoreCase(pValueString) || "NA".equalsIgnoreCase(tStatisticString) || "NA".equalsIgnoreCase(foldChangeString)) {
            expectedContrasts.next();
            return nextExpression(tsvRow);
        }

        double pValue = parseDouble(pValueString);
        double tStatistic = parseDouble(tStatisticString);
        double foldChange = parseDouble(foldChangeString);

        Contrast contrast = expectedContrasts.next();
        return new MicroarrayExpression(pValue, foldChange, tStatistic, contrast);
    }

}
