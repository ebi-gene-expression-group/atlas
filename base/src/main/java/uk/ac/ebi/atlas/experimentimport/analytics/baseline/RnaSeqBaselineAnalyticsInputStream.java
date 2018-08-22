package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class RnaSeqBaselineAnalyticsInputStream implements ObjectInputStream<BaselineAnalytics>, AutoCloseable {

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private final Optional<Reader> tpms;
    private final Optional<Reader> fpkms;
    private final Optional<Iterator<String[]>> tsvIteratorTpm;
    private final Optional<Iterator<String[]>> tsvIteratorFpkm;
    private final Queue<BaselineAnalytics> queue = new LinkedList<>();
    private final String[] assayGroupIds;

    public RnaSeqBaselineAnalyticsInputStream(Optional<Reader> tpms, Optional<Reader> fpkms) {
        this.tpms = tpms;
        this.fpkms = fpkms;
        tsvIteratorTpm = tpms.map(reader -> new TsvStreamer(reader).get().iterator());
        tsvIteratorFpkm = fpkms.map(reader -> new TsvStreamer(reader).get().iterator());

        Pair<Optional<String[]>, Optional<String[]>> headerLines = readNextLines();
        if (headerLines.getLeft().isPresent() && headerLines.getRight().isPresent()) {
            String[] leftHeader = headerLines.getLeft().get();
            String[] rightHeader = headerLines.getRight().get();

            checkArgument(
                    Arrays.deepEquals(
                            Arrays.copyOfRange(leftHeader, FIRST_EXPRESSION_LEVEL_INDEX, leftHeader.length),
                            Arrays.copyOfRange(rightHeader, FIRST_EXPRESSION_LEVEL_INDEX, leftHeader.length)),
                    "TPMs and FPKMs header lines don’t match!");
        }
        String[] headers = headerLines.getLeft().orElseGet(() -> headerLines.getRight().get());
        this.assayGroupIds = ArrayUtils.subarray(headers, FIRST_EXPRESSION_LEVEL_INDEX, headers.length);
    }

    private boolean hasNext() {
        // If both files are present, their hasNext() method must match
        if (tsvIteratorTpm.isPresent() && tsvIteratorFpkm.isPresent()) {
            checkArgument(tsvIteratorTpm.get().hasNext() == tsvIteratorFpkm.get().hasNext(),
                    "Number of lines of FPKM and TPM files don’t match");
        }

        // We checked in the constructor that at least one TsvStreamer is present, so either the first clause will be
        // evaluated if we have both or only TPMs, or the second if only FPKMs are available
        return tsvIteratorTpm.isPresent() && tsvIteratorTpm.get().hasNext() ||
                tsvIteratorFpkm.isPresent() && tsvIteratorFpkm.get().hasNext();
    }

    private Pair<Optional<String[]>, Optional<String[]>> readNextLines() {
        return Pair.of(tsvIteratorTpm.map(Iterator::next), tsvIteratorFpkm.map(Iterator::next));
    }

    @Override
    public BaselineAnalytics readNext() {
        if (queue.isEmpty()) {
            ImmutableList<BaselineAnalytics> baselineAnalytics = readNextNonZeroLine();

            if (baselineAnalytics == null) {
                //EOF
                return null;
            }

            queue.addAll(baselineAnalytics);
        }

        return queue.remove();
    }

    @Override
    public void close() {
        tpms.ifPresent(reader -> {
            try {
                reader.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        fpkms.ifPresent(reader -> {
            try {
                reader.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

    }

    private ImmutableList<BaselineAnalytics> readNextNonZeroLine() {
        if (!hasNext()) {
            return null;
        }

        Pair<Optional<String[]>, Optional<String[]>> lines = readNextLines();
        if (lines.getLeft().isPresent() && lines.getRight().isPresent()) {
            checkArgument(
                    lines.getLeft().get()[GENE_ID_COLUMN_INDEX].equals(lines.getRight().get()[GENE_ID_COLUMN_INDEX]),
                    String.format("Gene IDs %s and %s in the same line of TPM and FPKM file don’t match",
                            lines.getLeft().get()[GENE_ID_COLUMN_INDEX],
                            lines.getRight().get()[GENE_ID_COLUMN_INDEX]));
        }

        String geneId = lines.getLeft().orElseGet(() -> lines.getRight().get())[GENE_ID_COLUMN_INDEX];

        Optional<String[]> expressionLevelsTpm =
                lines.getLeft().map(line -> ArrayUtils.subarray(line, FIRST_EXPRESSION_LEVEL_INDEX, line.length));
        Optional<String[]> expressionLevelsFpkm =
                lines.getRight().map(line -> ArrayUtils.subarray(line, FIRST_EXPRESSION_LEVEL_INDEX, line.length));

        ImmutableList<BaselineAnalytics> baselineAnalytics =
                createList(
                        geneId,
                        expressionLevelsTpm.orElse(new String[0]),
                        expressionLevelsFpkm.orElse(new String[0]));

        if (baselineAnalytics.isEmpty()) {
            return readNextNonZeroLine();
        }

        return baselineAnalytics;
    }

    private ImmutableList<BaselineAnalytics> createList(String geneId,
                                                        String[] expressionLevelsTpm,
                                                        String[] expressionLevelsFpkm) {
        checkArgument(StringUtils.isNotBlank(geneId), "Cannot read baseline analytics: gene ID is blank");
        checkArgument(
                expressionLevelsTpm.length == 0 || expressionLevelsTpm.length == assayGroupIds.length,
                String.format(
                        "Incorrect number of TPM expression levels, expected %d but got [%s]",
                        assayGroupIds.length,
                        Arrays.deepToString(expressionLevelsTpm)));
        checkArgument(
                expressionLevelsFpkm.length == 0 || expressionLevelsFpkm.length == assayGroupIds.length,
                String.format(
                        "Incorrect number of FPKM expression levels, expected %d but got [%s]",
                        assayGroupIds.length,
                        Arrays.deepToString(expressionLevelsFpkm)));

        BaselineExpression[] baselineExpressionsTpm =
                Stream.of(expressionLevelsTpm).map(BaselineExpression::create).toArray(BaselineExpression[]::new);

        BaselineExpression[] baselineExpressionsFpkm =
                Stream.of(expressionLevelsFpkm).map(BaselineExpression::create).toArray(BaselineExpression[]::new);

        ImmutableList.Builder<BaselineAnalytics> builder = ImmutableList.builder();
        for (int i = 0; i < Math.max(baselineExpressionsTpm.length, baselineExpressionsFpkm.length); i++) {
            if (baselineExpressionsTpm.length > 0 && baselineExpressionsTpm[i].getLevel() > 0.0 ||
                    baselineExpressionsFpkm.length > 0 && baselineExpressionsFpkm[i].getLevel() > 0.0) {
                builder.add(BaselineAnalytics.create(
                        geneId,
                        assayGroupIds[i],
                        baselineExpressionsTpm.length > 0 ?
                                baselineExpressionsTpm[i].getLevel() :
                                0.0,
                        baselineExpressionsFpkm.length > 0 ?
                                baselineExpressionsFpkm[i].getLevel() :
                                0.0,
                        baselineExpressionsTpm.length > 0 ?
                                baselineExpressionsTpm[i].getQuartiles() :
                                new double[0],
                        baselineExpressionsFpkm.length > 0 ?
                                baselineExpressionsFpkm[i].getQuartiles() :
                                new double[0]));
            }
        }
        return builder.build();
    }

}
