package uk.ac.ebi.atlas.utils.score;

import java.util.*;

public class HistogramCounter {

    public static final int GENE_COUNT = 45000;

    private Map<Double, List<BitSet>> scoreMap;

    private List<Double> scores;
    private List<String> organismParts;

    public HistogramCounter(List<Double> marks, List<String> organismParts) {
        this.scores = marks;
        this.organismParts = organismParts;
        initEmptyMap();
    }

    public HistogramCounter(Map<Double, List<BitSet>> scoreMap) {
        this.scoreMap = scoreMap;
    }

    private void initEmptyMap() {
        scoreMap = new HashMap<Double, List<BitSet>>(scores.size());
        for (Double score : scores) {
            List<BitSet> tissueSets = new ArrayList<BitSet>(organismParts.size());

            for (String organismPart : organismParts) {
                BitSet bitSet = new BitSet(GENE_COUNT);
                tissueSets.add(bitSet);
            }
            scoreMap.put(score, tissueSets);
        }
    }

    public void addValues(List<Double> values, int lineNumber) {
        if (values.size() != organismParts.size()) {
            System.out.println("wrong input size");
            return;
        }

        for (Map.Entry<Double, List<BitSet>> entry : scoreMap.entrySet()) {

            for (int i = 0; i < values.size(); i++) {

                if (Double.compare(values.get(i), entry.getKey()) > 0) {
                    entry.getValue().get(i).set(lineNumber);
                }
            }
        }
    }

    public Map<Double, Integer> countAll(List<Integer> selected) {
        Map<Double, Integer> result = new TreeMap<>();

        for (Double mark : scoreMap.keySet()) {
            if (selected == null || selected.isEmpty()) {
                //ToDo: confirm requirements
//                result.put(mark, countSingleSpecificity(scoreMap.get(mark)));
                result.put(mark, countAllGenes(scoreMap.get(mark)));
            } else {
                result.put(mark, countPerMark(scoreMap.get(mark), selected));
            }
        }

        return result;
    }

    protected int countPerMark(List<BitSet> tissueSets, List<Integer> selected) {
        BitSet andSet = new BitSet(GENE_COUNT);
        BitSet notSet = new BitSet(GENE_COUNT);

        for (int i = 0; i < tissueSets.size(); i++) {
            BitSet bitSet = tissueSets.get(i);
            if (selected.contains(i)) {
                //add
                andSet.or(bitSet);
            } else {
                notSet.or(bitSet);
            }
        }
        andSet.andNot(notSet);
        return andSet.cardinality();
    }


    protected int countSingleSpecificity(List<BitSet> tissueSets) {
        BitSet result = new BitSet(GENE_COUNT);

        for (BitSet bitSet : tissueSets) {
            result.xor(bitSet);
        }
        return result.cardinality();
    }

    protected int countAllGenes(List<BitSet> tissueSets) {
        BitSet result = new BitSet(GENE_COUNT);

        for (BitSet bitSet : tissueSets) {
            result.or(bitSet);
        }
        return result.cardinality();
    }

    public Map<Double, List<BitSet>> getScoreMap() {
        return scoreMap;
    }

//    public static void main(String[] args) {
//        BitSet bs1 = new BitSet(3);
////        bs1.set(1);
//
//        BitSet bs2 = new BitSet(3);
//        bs2.set(0);
//        bs2.set(1);
//
//        bs1.xor(bs2);
//        System.out.println("bs1 = " + bs1);
//
//        HistogramCounter builder = new HistogramCounter(scoreList(), orgParts());
//
//        builder.initEmptyMap();
//
//        builder.addValues(Lists.newArrayList(1d, 0d, 3d), 0);
//        builder.addValues(Lists.newArrayList(0.3d, 6d, 0d), 1);
//        builder.addValues(Lists.newArrayList(0.9d, 1.5d, 100d), 2);
//
//        Map<Double, List<BitSet>> scoreMap1 = builder.getScoreMap();
//        System.out.println("scoreMap1.size() = " + scoreMap1.size());

//        long timeStart = System.currentTimeMillis();
//        builder.initSets(50, 16, 60000);
//        long timeFinished = System.currentTimeMillis();
//        System.out.println("init = " + (timeFinished - timeStart));
//
//        timeStart = System.currentTimeMillis();
//        ArrayList<Integer> selected = new ArrayList<Integer>();
//        selected.add(1);
//        selected.add(6);
//        selected.add(8);
//        for (List<BitSet> tissueSets : builder.sets) {
//            int count = builder.countPerMark(tissueSets, selected);
//            System.out.println("count = " + count);
//        }
//        timeFinished = System.currentTimeMillis();
//        System.out.println("count time = " + (timeFinished - timeStart));
//    }
//
//    private static List<Double> scoreList() {
//        List<Double> list = new ArrayList<Double>();
//        list.add(0d);
//        list.add(0.5d);
//        list.add(1d);
//        list.add(10d);
//
//        return list;
//    }
//
//    private static List<String> orgParts() {
//        List<String> list = new ArrayList<String>();
//        list.add("h");
//        list.add("l");
//        list.add("b");
//
//        return list;
//    }


}
