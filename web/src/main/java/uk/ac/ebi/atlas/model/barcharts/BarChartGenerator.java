package uk.ac.ebi.atlas.model.barcharts;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public interface BarChartGenerator {

    public Map<Double, Integer> getChart(Set<String> selectedOrganismParts);

}
