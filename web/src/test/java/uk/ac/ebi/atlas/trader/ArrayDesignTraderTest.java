package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayDesignTraderTest {

    Map<String, String> arrayDesigns =
            ImmutableMap.of("A-AFFY-106","Affymetrix Custom Array - Ciona intestinalis",
                            "A-AFFY-128","Affymetrix GeneChip Bovine Genome Array [Bovine]",
                            "A-AFFY-60","Affymetrix GeneChip C. elegans Genome Array [Celegans]");

    ArrayDesignTrader trader = new ArrayDesignTrader(arrayDesigns);

    @Test
    public void canGetObjectsFromTheMapInBothWays(){
        assertEquals("Affymetrix Custom Array - Ciona intestinalis", trader.getArrayDesignByName("A-AFFY-106"));
        assertEquals("A-AFFY-128", trader.getArrayDesignAccession("Affymetrix GeneChip Bovine Genome Array [Bovine]"));
    }

    @Test
    public void keepOrder(){
        SortedSet<String> arrayDesignAccessions = Sets.newTreeSet();
        arrayDesignAccessions.add("A-AFFY-128");
        arrayDesignAccessions.add("A-AFFY-60");

        SortedSet<String> out = trader.getArrayDesignNames(arrayDesignAccessions);

        Iterator<String> it1 = arrayDesignAccessions.iterator();
        Iterator<String> it2 = out.iterator();
        while(it1.hasNext()){
            assertTrue(it2.hasNext());
            assertEquals(it2.next(), trader.getArrayDesignByName(it1.next()));
        }
    }
}
