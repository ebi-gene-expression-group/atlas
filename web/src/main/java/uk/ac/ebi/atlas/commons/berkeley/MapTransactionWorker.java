package uk.ac.ebi.atlas.commons.berkeley;

import com.sleepycat.collections.TransactionWorker;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class MapTransactionWorker implements TransactionWorker {

    private final ConcurrentMap<String, String> dest;
    private final Map<String, String> src;

    public MapTransactionWorker(ConcurrentMap<String, String> dest, Map<String, String> src) {
        this.dest = dest;
        this.src = src;
    }

    @Override
    public void doWork() {
        try {
            for (String key : src.keySet()) {
                String value = src.get(key);
                if(!StringUtils.isBlank(value)) {
                    dest.put(key, value);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error while writing gene annotations DB: " + e.getMessage());
        }
    }
}
