package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.*;

public class DifferentialExperiment extends Experiment<Contrast> {

    private static final Gson gson = new Gson();

    public DifferentialExperiment(String accession, Date lastUpdate, List<Contrast> contrasts, String description,
                                  boolean hasRData, Species species, Collection<String> pubMedIds,
                                  ExperimentDesign experimentDesign) {

        this(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, accession, lastUpdate, contrasts, description, hasRData,
                species, pubMedIds, experimentDesign);

    }

    protected DifferentialExperiment(ExperimentType experimentType, String accession, Date lastUpdate,
                                     List<Contrast> contrasts, String description, boolean hasRData, Species species,
                                     Collection<String> pubMedIds, ExperimentDesign experimentDesign) {

        super(experimentType, accession, lastUpdate,null, description, "", hasRData, species, pubMedIds,
                experimentDesign, Collections.<String>emptyList(), Collections.<String>emptyList(),
                Collections.<String>emptyList(), Collections.<String>emptyList(), contrasts, ExperimentDisplayDefaults.create());
    }

    @Override
    public Map<String, Object> getAttributes() {

        Map<String, Object> result = new HashMap<>();
        result.putAll(super.getAttributes());
        result.put("regulationValues", Regulation.values());
        result.put("isFortLauderdale", false);
        result.put("contrasts", this.getDataColumnDescriptors());

        return result;
    }

    public Map<String, ?> getDifferentialAttributes() {
        //you only have a selected contrast when you're on an experiment design page
        return getDifferentialAttributes("");
    }

    public Map<String, ?> getDifferentialAttributes(String selectedContrast) {

        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(selectedContrast)) {
            selectedContrast = getDataColumnDescriptors().iterator().next().getId();
        }

        Contrast contrast = getDataColumnDescriptor(selectedContrast);
        result.put("referenceAssays", gson.toJson(Sets.newHashSet(contrast.getReferenceAssayGroup())));
        result.put("testAssays", gson.toJson(Sets.newHashSet(contrast.getTestAssayGroup())));

        return result;

    }

    @Override
    public ExperimentInfo buildExperimentInfo() {
        ExperimentInfo experimentInfo = super.buildExperimentInfo();
        experimentInfo.setNumberOfContrasts(getDataColumnDescriptors().size());
        return  experimentInfo;
    }

    @Override
    protected JsonObject propertiesForAssay(String runOrAssay) {
        JsonObject result = new JsonObject();
        String contrastName = "None";
        String referenceOrTest = "";

        for(Contrast contrast : getDataColumnDescriptors()){
            if(contrast.getReferenceAssayGroup().assaysAnalyzedForThisDataColumn().contains(runOrAssay)){
                contrastName = contrast.getDisplayName();
                referenceOrTest = "reference";
                break;
            } else if(contrast.getTestAssayGroup().assaysAnalyzedForThisDataColumn().contains(runOrAssay)) {
                contrastName = contrast.getDisplayName();
                referenceOrTest = "test";
                break;
            }
        }

        result.addProperty("contrastName", contrastName);
        result.addProperty("referenceOrTest", referenceOrTest);
        return result;
    }

    @Override
    public JsonArray groupingsForHeatmap() {
        ExperimentDesign experimentDesign = getExperimentDesign();
        ExperimentDisplayDefaults experimentDisplayDefaults = getDisplayDefaults();

        LinkedListMultimap<String, LinkedListMultimap<String, String>> filtersByType = LinkedListMultimap.create();

        filtersByType.put("Comparison Name", LinkedListMultimap.<String, String>create());

        //populate the keys in the order we want later (Linked map preserves insertion order)
        for(String factorHeader: experimentDisplayDefaults.prescribedOrderOfFilters()){
            filtersByType.put(Factor.normalize(factorHeader), LinkedListMultimap.<String, String>create());
        }
        for(String factorHeader: experimentDesign.getFactorHeaders()){
            if(! experimentDisplayDefaults.prescribedOrderOfFilters().contains(Factor.normalize(factorHeader))){
                filtersByType.put(Factor.normalize(factorHeader), LinkedListMultimap.<String, String>create());
            }
        }
        for(String sampleHeader: experimentDesign.getSampleHeaders()){
            if(! experimentDesign.getFactorHeaders().contains(sampleHeader)){
                filtersByType.put(Factor.normalize(sampleHeader), LinkedListMultimap.<String, String>create());
            }
        }

        // add the information about which headers go to which categories
        for(Contrast dataColumnDescriptor: getDataColumnDescriptors()){
            filtersByType.get("Comparison Name").get(0)
                    .put((dataColumnDescriptor).getDisplayName(), dataColumnDescriptor.getId());

            for(String assayAnalyzedForThisDataColumn : dataColumnDescriptor.assaysAnalyzedForThisDataColumn()){
                for(Factor factor : experimentDesign.getFactors(assayAnalyzedForThisDataColumn)){
                    filtersByType.get(Factor.normalize(factor.getHeader())).get(0)
                            .put(factor.getValue(), dataColumnDescriptor.getId());
                }
                for(SampleCharacteristic sampleCharacteristic
                        : experimentDesign.getSampleCharacteristics(assayAnalyzedForThisDataColumn)){
                    if(filtersByType.containsKey(Factor.normalize(sampleCharacteristic.header()))){
                        filtersByType.get(Factor.normalize(sampleCharacteristic.header())).get(0)
                                .put(sampleCharacteristic.value(), dataColumnDescriptor.getId());
                    }
                }
            }
        }
        JsonArray result = new JsonArray();
        for(Map.Entry<String, LinkedListMultimap<String, String>> e : filtersByType.entries()){
            result.add(groupForFilterType(e.getKey(), experimentDisplayDefaults.defaultFilterValuesForFactor(e.getKey
                    ()), e.getValue().asMap()));
        }


        return result;
    }

}
