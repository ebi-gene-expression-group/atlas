package uk.ac.ebi.atlas.utils.score;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named("histogramCountLoader")
public class HistogramCountLoader  extends CacheLoader<String, HistogramCounter> {

    private ExperimentsCache experimentsCache;


    @Value("#{configuration['magetab.tsvfile.url.template']}")
    private String tsvFileUrlTemplate;

    @Inject
    public HistogramCountLoader(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;

    }


    @Override
    public HistogramCounter load(String experimentAccession) {
        String tsvFileUrl = String.format(tsvFileUrlTemplate, experimentAccession);
        return generateScoreMap(tsvFileUrl, experimentAccession);
    }

    public HistogramCounter generateScoreMap(String file, String accession)  {

        try ( Reader dataFileReader = new InputStreamReader(new FileInputStream(file));
                CSVReader csvReader = new CSVReader(dataFileReader, '\t')) {

            List<String> organismParts = processHeader(csvReader.readNext(), accession);

            HistogramCounter histogramCounter = new HistogramCounter(generateCutoffs(), organismParts);


            String[] values;
            int count = 0;
            while ((values = csvReader.readNext()) != null) {
                histogramCounter.addValues(convertToDoubles(values), count);
                count++;
            }

            return histogramCounter;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read data file from " + file, e);
        }

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

    private List<String> processHeader(String[] values, String accession) {
        List<String> organismParts = new ArrayList<>(values.length - 1);

        List<ExperimentRun> experimentRuns = experimentsCache.getExperimentRuns(accession);
        List<String> runAccessions = Arrays.asList(ArrayUtils.remove(values, 0));

        for (ExperimentRun experimentRun : experimentRuns) {
            int index = runAccessions.indexOf(experimentRun.getRunAccession());
            if (index > 0) {
                organismParts.add(experimentRun.getOrganismPart());
            }
        }

        return organismParts;

    }

//    public Map<Double, Integer> countAll(List<Integer> selected) {
//        return scoreBuilder.countAll(selected);
//    }

//    public static void main(String[] args) throws FileNotFoundException {
//        HistogramCountLoader histogramCountLoader = new HistogramCountLoader(null);
//
//        long timeStart = System.currentTimeMillis();
//        Map<Double, List<BitSet>> doubleListMap = histogramCountLoader.generateScoreMap("/Users/nsklyar/Data/magetab/E-MTAB-513.tsv");
//        long timeFinished = System.currentTimeMillis();
//        System.out.println("init = " + (timeFinished - timeStart));
//
//        Map<Double, Integer> singleSpecificity = histogramCountLoader.countAll(Arrays.asList(0, 1));
////        Map<Double, Integer> singleSpecificity = histogramCountLoader.countAll(Arrays.asList("adipose", "adrenal"));
//        System.out.println("singleSpecificity = " + singleSpecificity);
//
//    }


    private List<Double> generateCutoffs() {
        //ToDo: we might need to find the biggest FPKM first, and then generate cautoffs
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
