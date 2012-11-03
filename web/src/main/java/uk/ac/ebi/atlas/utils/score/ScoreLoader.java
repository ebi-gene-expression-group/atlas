package uk.ac.ebi.atlas.utils.score;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import java.io.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public class ScoreLoader {

    private List<Double> marks;
    private List<String> organisms;
    private ExperimentsCache experimentsCache;

    private HistogramCounter scoreBuilder;

    @Inject
    public ScoreLoader(ExperimentsCache experimentsCache) {

        this.experimentsCache = experimentsCache;


    }

    public void loadScoredForExperiment(String accession) {

    }

    public Map<Double, List<BitSet>> generateScoreMap(String file) throws FileNotFoundException {
        Reader dataFileReader = new InputStreamReader(new FileInputStream(file));

        try (CSVReader csvReader = new CSVReader(dataFileReader, '\t')) {

            processHeader(csvReader.readNext());

            scoreBuilder = new HistogramCounter(generateCutoffs(), organisms);


            String[] values;
            int count = 0;
            while ((values = csvReader.readNext()) != null) {
                scoreBuilder.addValues(convertToDoubles(values), count);
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scoreBuilder.getScoreMap();
    }

    private List<Double> convertToDoubles(String[] strings) {
        List<String> values = Arrays.asList(ArrayUtils.remove(strings, 0));
        return Lists.transform(values, new Function<String, Double>() {
            @Override
            public Double apply(String input) {
                return Double.valueOf(input);
            }
        });

    }

    private void processHeader(String[] strings) {
        //ToDo: generate map OrganismPartName->Position
        organisms = Arrays.asList(ArrayUtils.remove(strings, 0));


    }

    public Map<Double, Integer> countAll(List<Integer> selected) {
        return scoreBuilder.countAll(selected);
    }

    public static void main(String[] args) throws FileNotFoundException {
        ScoreLoader scoreLoader = new ScoreLoader(null);

        long timeStart = System.currentTimeMillis();
        Map<Double, List<BitSet>> doubleListMap = scoreLoader.generateScoreMap("/Users/nsklyar/Data/magetab/E-MTAB-513.tsv");
        long timeFinished = System.currentTimeMillis();
        System.out.println("init = " + (timeFinished - timeStart));

        Map<Double, Integer> singleSpecificity = scoreLoader.countAll(Arrays.asList(0, 1));
        System.out.println("singleSpecificity = " + singleSpecificity);

    }


    private static List<Double> generateCutoffs() {
        List<Double> list = Lists.newArrayList(0d,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6,
                0.7,
                0.8,
                0.9,
                1d,
                2d,
                3d,
                4d,
                5d,
                6d,
                7d,
                8d,
                9d,
                10d,
                20d,
                30d,
                40d,
                50d,
                60d,
                70d,
                80d,
                90d,
                100d,
                200d,
                300d,
                400d,
                500d,
                600d,
                700d,
                800d,
                900d,
                1000d,
                2000d,
                3000d,
                4000d,
                5000d,
                6000d,
                7000d,
                8000d,
                9000d,
                10000d);
        return list;
    }
}
