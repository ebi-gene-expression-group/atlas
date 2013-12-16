package uk.ac.ebi.atlas.dao.dto;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.file.TsvData;
import uk.ac.ebi.atlas.utils.AutoCloseableIterable;

import java.io.IOException;
import java.util.*;

/*
 * turns TsvData of:
 *
 * Gene ID, Gene Name, g1, g2, g3, g4, g5
 * mus1, musName, 1, 2, 3, 4, 5
 *
 * into BaselineExpressionDTOs of:
 *
 * mus1, musName, 1
 * mus1, musName, 2
 * mus1, musName, 3
 * mus1, musName, 4
 * mus1, musName, 5


 */
public class BaselineExpressionDtoIterable implements AutoCloseableIterable<BaselineExpressionDto>{

    AutoCloseableIterable<TsvData> tsvData;

    public BaselineExpressionDtoIterable(AutoCloseableIterable<TsvData> tsvData) {
        this.tsvData = tsvData;
    }

    @Override
    public void close() throws IOException {
        try {
            tsvData.close();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public Iterator<BaselineExpressionDto> iterator() {
        return new BaselineExpressionDTOIterator(tsvData.iterator());
    }

    private static class BaselineExpressionDTOIterator implements Iterator<BaselineExpressionDto> {

        private final Iterator<TsvData> tsvIterator;
        private final Queue<AssayGroupExpressionLevel> queue = new LinkedList<>();
        private TsvData currentTsvData;

        public BaselineExpressionDTOIterator(Iterator<TsvData> tsvIterator) {
            this.tsvIterator = tsvIterator;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty() || tsvIterator.hasNext();
        }

        @Override
        public BaselineExpressionDto next() {
            if (queue.isEmpty()) {
                currentTsvData = tsvIterator.next();
                queue.addAll(AssayGroupExpressionLevel.createList(currentTsvData));
            }

            AssayGroupExpressionLevel agel = queue.remove();

            return new BaselineExpressionDto(currentTsvData.getGeneId(), agel.getAssayGroupId(), agel.getExpressionLevel());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private static class AssayGroupExpressionLevel {

        private String assayGroupId;
        private Double expressionLevel;

        private AssayGroupExpressionLevel(String assayGroupId, Double expressionLevel) {
            this.assayGroupId = assayGroupId;
            this.expressionLevel = expressionLevel;
        }

        private String getAssayGroupId() {
            return assayGroupId;
        }

        private Double getExpressionLevel() {
            return expressionLevel;
        }

        static ImmutableList<AssayGroupExpressionLevel> createList(TsvData tsvData) {
            ImmutableList.Builder<AssayGroupExpressionLevel> builder = ImmutableList.builder();

            for (Map.Entry<String, String> column : tsvData.getEverythingElse().entrySet()) {
                Double value = Double.parseDouble(column.getValue());
                builder.add(new AssayGroupExpressionLevel(column.getKey(), value));
            }

            return builder.build();
        }
    }


}
