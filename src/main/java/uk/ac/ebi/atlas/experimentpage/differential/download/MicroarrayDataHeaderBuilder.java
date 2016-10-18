package uk.ac.ebi.atlas.experimentpage.differential.download;

public class MicroarrayDataHeaderBuilder extends AnalyticsDataHeaderBuilder {
    @Override
    protected int getFixedColumnNumber() {
        return 3;
    }
}
