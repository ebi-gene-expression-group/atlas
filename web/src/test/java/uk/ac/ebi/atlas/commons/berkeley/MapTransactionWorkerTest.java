package uk.ac.ebi.atlas.commons.berkeley;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;


public class MapTransactionWorkerTest {

    private MapTransactionWorker subject;

    private Map<String, String> src = Maps.newHashMap();

    private ConcurrentMap<String, String> dest = Maps.newConcurrentMap();

    @Before
    public void initSubject() {
        src.put("de1", "gene1");
        src.put("de2", "gene2");

        subject = new MapTransactionWorker(src, dest);
    }

    @Test
    public void testDoWork() throws Exception {
        subject.doWork();
        assertThat(dest.keySet().size(), is(2));
        assertThat(dest.keySet(), contains("de1", "de2"));
        assertThat(dest.values(), containsInAnyOrder("gene1", "gene2"));
    }

}
