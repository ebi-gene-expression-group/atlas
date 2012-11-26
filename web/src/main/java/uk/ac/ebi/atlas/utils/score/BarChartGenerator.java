package uk.ac.ebi.atlas.utils.score;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public interface BarChartGenerator {


    public List<Integer> getChartValues(Set<String> organismParts);


}
