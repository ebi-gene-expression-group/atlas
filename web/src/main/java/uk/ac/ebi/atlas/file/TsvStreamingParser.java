package uk.ac.ebi.atlas.file;

import com.google.common.collect.ImmutableMap;
import com.xlson.groovycsv.CsvIterator;
import com.xlson.groovycsv.CsvParser;
import com.xlson.groovycsv.PropertyMapper;

import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Parses tsv lazily, each iteration returning a map with keys = header, values = line
 */
public class TsvStreamingParser implements Iterable<ImmutableMap<String, String>> {

    private final ImmutableMap<String, Character> args = ImmutableMap.of("separator", '\t');
    private Reader reader;

    public TsvStreamingParser(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Iterator<ImmutableMap<String, String>> iterator() {
        return new CsvIteratorAsMap(new CsvParser().parse(args, reader));
    }

    /*
     * Takes a CsvIterator (which returns PropertyMapper) and returns a ImmutableMap instead.
     * Map keys are the header, and values the line values.
    */
    private class CsvIteratorAsMap implements Iterator<ImmutableMap<String, String>> {

        private final CsvIterator csvIterator;

        public CsvIteratorAsMap(Iterator csvIterator) {
            this.csvIterator = (CsvIterator)csvIterator;
        }

        @Override
        public boolean hasNext() {
            return csvIterator.hasNext();
        }

        @Override
        public ImmutableMap<String, String> next() {
            PropertyMapper propertyMapper = (PropertyMapper)csvIterator.next();
            return asMap(propertyMapper);
        }

        private ImmutableMap<String, String> asMap(PropertyMapper propertyMapper) {
            String[] values = (String[])propertyMapper.getValues();

            ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();

            LinkedHashMap<String, Integer> columns = (LinkedHashMap<String, Integer>) (propertyMapper.getColumns());

            for (Map.Entry<String,Integer> entry: columns.entrySet()) {
                String header = entry.getKey();
                int index = entry.getValue();
                mapBuilder.put(header, values[index]);
            }

            return mapBuilder.build();
        }

        @Override
        public void remove() {
            csvIterator.remove();
        }
    }
}
