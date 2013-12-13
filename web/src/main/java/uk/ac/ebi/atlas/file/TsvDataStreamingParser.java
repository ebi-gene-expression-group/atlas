package uk.ac.ebi.atlas.file;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.sun.istack.internal.Nullable;

import java.util.Map;

public final class TsvDataStreamingParser {

    private TsvDataStreamingParser() {}

    public static Iterable<TsvData> create(TsvStreamingParser tsvStreamingParser) {
        return Iterables.transform(tsvStreamingParser, new Function<Map<String, String>, TsvData>() {

            @Override
            public TsvData apply(@Nullable java.util.Map<String, String> line) {
                return new TsvData(line);
            }

        });
    }
}
