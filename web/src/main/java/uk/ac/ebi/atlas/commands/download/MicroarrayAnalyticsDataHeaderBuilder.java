package uk.ac.ebi.atlas.commands.download;

import javax.inject.Named;

@Named
public class MicroarrayAnalyticsDataHeaderBuilder extends AnalyticsDataHeaderBuilder {

    @Override
    protected String getSecondColumnName() {
        return DESIGN_ELEMENT;
    }
}
