package uk.ac.ebi.atlas.file;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.sun.istack.internal.Nullable;
import uk.ac.ebi.atlas.utils.AutoCloseableIterable;
import uk.ac.ebi.atlas.utils.AutoCloseableIterator;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public final class TsvDataStreamingParser implements AutoCloseableIterable<TsvData> {

    private final AutoCloseableIterator<ImmutableMap<String, String>> tsvIterator;

    public TsvDataStreamingParser(TsvStreamingParser tsvStreamingParser) {
        this.tsvIterator = tsvStreamingParser.iterator();
    }

    @Override
    public Iterator<TsvData> iterator() {
        return Iterators.transform(tsvIterator, new Function<Map<String, String>, TsvData>() {

            @Override
            public TsvData apply(@Nullable Map<String, String> line) {
                return new TsvData(line);
            }

        });
    }

    @Override
    public void close() throws IOException {
        tsvIterator.close();
    }

}
