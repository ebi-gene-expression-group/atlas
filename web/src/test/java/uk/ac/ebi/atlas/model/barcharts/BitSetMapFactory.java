package uk.ac.ebi.atlas.model.barcharts;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

class BitSetMapFactory {
    public BitSetMapFactory() { }

    Map<String, BitSet> createChartSize5() {
        Map<String, BitSet> map = new HashMap<String, BitSet>();
        map.put("op1", initBitSet(1, 3, 5));
        map.put("op2", initBitSet(0, 3));
        map.put("op3", initBitSet(1, 0, 4));

        return map;
    }

    Map<String, BitSet> createChartSize2() {
        Map<String, BitSet> map = new HashMap<String, BitSet>();
        map.put("op1", initBitSet(3, 5));
        map.put("op2", initBitSet(3));
        map.put("op3", initBitSet(5));

        return map;
    }

    BitSet initBitSet(int... setPositions) {
        BitSet bs = new BitSet();
        for (int setPosition : setPositions) {
            bs.set(setPosition);
        }
        return bs;
    }
}