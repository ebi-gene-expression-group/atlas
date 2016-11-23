package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

//ToDo: duplicate code with RnaSeqDiffExpressionsQueue
public class ExpressionsRowTsvDeserializerMicroarray extends ExpressionsRowTsvDeserializer<MicroarrayExpression> {

    private Iterator<Contrast> expectedContrasts;

    private List<Contrast> orderedContrasts;

    ExpressionsRowTsvDeserializerMicroarray(List<Contrast> orderedContrasts) {
        this.expectedContrasts = Iterables.cycle(orderedContrasts).iterator();
        this.orderedContrasts = orderedContrasts;
    }

    public List<Contrast> getOrderedContrasts() {
        return orderedContrasts;
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

    private double parseDouble(String value) {
        if (value.equalsIgnoreCase("inf")) {
            return Double.POSITIVE_INFINITY;
        }
        if (value.equalsIgnoreCase("-inf")) {
            return Double.NEGATIVE_INFINITY;
        }
        return Double.parseDouble(value);
    }

}
