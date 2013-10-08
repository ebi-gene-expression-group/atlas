package uk.ac.ebi.atlas.commands.download;

public class MicroarrayDataHeaderBuilder extends AnalyticsDataHeaderBuilder {
    @Override
    protected int getFixedColumnNumber() {
        return 3;
    }
}
