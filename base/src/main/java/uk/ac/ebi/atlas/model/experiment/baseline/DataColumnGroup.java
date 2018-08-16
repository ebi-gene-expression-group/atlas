package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class DataColumnGroup<D extends DescribesDataColumns> {
    private final String name;
    private final boolean primary;

    private final ImmutableList<String> defaultSelectionSpecifiedByCurators;
    private final Multimap<String, D> groupingValuesPerGrouping;

    public DataColumnGroup(String name, List<String> defaultValues, boolean primary) {
        this.name = name;
        this.primary = primary;
        this.defaultSelectionSpecifiedByCurators = ImmutableList.copyOf(defaultValues);
        this.groupingValuesPerGrouping = LinkedListMultimap.create();
    }

    public void addValueToGrouping(String grouping, D dataColumnDescriptor) {
        groupingValuesPerGrouping.put(grouping, dataColumnDescriptor);
    }

    public JsonObject asJson() {
        JsonObject result = new JsonObject();
        result.addProperty("name", name);
        result.addProperty("primary", primary);
        result.add("selected", defaultSelectionSpecifiedByCurators.size() == 0 ?
                new JsonPrimitive("all") :
                GSON.toJsonTree(defaultSelectionSpecifiedByCurators));

        JsonArray groupings = new JsonArray();
        for (Map.Entry<String, Collection<D>> e: groupingValuesPerGrouping.asMap().entrySet()) {
            JsonArray grouping = new JsonArray();
            grouping.add(new JsonPrimitive(e.getKey()));
            JsonArray groupingValues = new JsonArray();
            for (D groupingValue : uniqueSublist(e.getValue())) {
                groupingValues.add(new JsonPrimitive(groupingValue.getId()));
            }
            grouping.add(groupingValues);
            groupings.add(grouping);
        }

        result.add("groupings", groupings);


        return result;
    }

    private static <T> List<T> uniqueSublist(Collection<T> collection) {
        List<T> result = new ArrayList<>();
        collection.stream().filter(element -> !result.contains(element)).forEach(result::add);
        return result;
    }

    public static class DataColumnGroupList<D extends DescribesDataColumns> {
        private final ExperimentDisplayDefaults experimentDisplayDefaults;
        private final LinkedHashMap<String, DataColumnGroup<D>> dataColumnGroupsByType;

        public DataColumnGroupList(ExperimentDisplayDefaults experimentDisplayDefaults) {
            this.experimentDisplayDefaults = experimentDisplayDefaults;
            this.dataColumnGroupsByType = new LinkedHashMap<>();
        }

        public void addDataColumnGroupIfNotPresent(String factorOrSampleHeaderFromTheDesignFileOrFactorsXml,
                                                   boolean primary) {
            String name = Factor.normalize(factorOrSampleHeaderFromTheDesignFileOrFactorsXml);
            if (!dataColumnGroupsByType.containsKey(name)) {
                dataColumnGroupsByType.put(
                        name,
                        new DataColumnGroup<>(
                                name,
                                experimentDisplayDefaults.defaultFilterValuesForFactor(name),
                                primary));
            }
        }

        public void addValueToGroupingInGroup(String factorOrSampleHeaderFromTheDesignFileOrFactorsXml,
                                              String grouping,
                                              D dataColumnDescriptor) {
            dataColumnGroupsByType.get(Factor.normalize(factorOrSampleHeaderFromTheDesignFileOrFactorsXml))
                    .addValueToGrouping(grouping, dataColumnDescriptor);
        }

        public JsonArray asJson() {
            JsonArray result = new JsonArray();
            for (Map.Entry<String, DataColumnGroup<D>> e: dataColumnGroupsByType.entrySet()) {
                result.add(e.getValue().asJson());
            }

            return result;
        }
    }

}
