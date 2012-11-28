package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.cache.CacheLoader;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named("histogramCountLoader")
public class BarChartTraderLoader extends CacheLoader<String, BarChartTrader> {

    private GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder;
    private BarChartTrader.Builder barChartTraderBuilder;


    @Inject
    public BarChartTraderLoader(BarChartTrader.Builder barChartTraderBuilder, GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder) {
        this.geneProfilesInputStreamBuilder = geneProfilesInputStreamBuilder;
        this.barChartTraderBuilder = barChartTraderBuilder;
    }


    @Override
    public BarChartTrader load(String experimentAccession) {

        return barChartTraderBuilder.forExperiment(experimentAccession).create();

    }

}
