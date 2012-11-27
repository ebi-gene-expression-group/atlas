package uk.ac.ebi.atlas.utils.score;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Function;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

@Named("histogramCountLoader")
public class BarChartIndexesLoader extends CacheLoader<String, BarChartIndexes> {

    private ExperimentsCache experimentsCache;
    private CutoffScale cutoffScale;

    @Value("#{configuration['magetab.tsvfile.url.template']}")
    private String tsvFileUrlTemplate;

    @Inject
    public BarChartIndexesLoader(ExperimentsCache experimentsCache, CutoffScale cutoffScale) {
        this.experimentsCache = experimentsCache;
        this.cutoffScale = cutoffScale;
    }


    @Override
    public BarChartIndexes load(String experimentAccession) {
        String tsvFileUrl = String.format(tsvFileUrlTemplate, experimentAccession);
        return generateScoreMap(tsvFileUrl, experimentAccession);
    }

    public BarChartIndexes generateScoreMap(String file, String experimentAccession)  {

        try ( Reader dataFileReader = new InputStreamReader(new FileInputStream(file));
                CSVReader csvReader = new CSVReader(dataFileReader, '\t')) {
            List<String> organismParts = Lists.newArrayList(experimentsCache.getExperiment(experimentAccession).getAllOrganismParts());

            BarChartIndexes barChartIndexes = new BarChartIndexes(generateCutoffs(), organismParts);


            String[] values;
            int count = 0;
            while ((values = csvReader.readNext()) != null) {
                barChartIndexes.addValues(convertToDoubles(values), count);
                count++;
            }

            return barChartIndexes;
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
