package uk.ac.ebi.atlas.experimentimport.analytics.differential;

public interface DifferentialAnalytics {

    String getGeneId();

    String getContrastId();

    double getpValue();

    double getFoldChange();

    double getTStatistic();

}
