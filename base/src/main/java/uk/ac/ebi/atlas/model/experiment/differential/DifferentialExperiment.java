package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.DataColumnGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DifferentialExperiment extends Experiment<Contrast> {

    private final Set<Contrast> contrastsWithCttvPrimaryAnnotation;

    public DifferentialExperiment(String accession, Date lastUpdate, List<Pair<Contrast, Boolean>> contrasts,
                                  String description, Species species, Collection<String> pubMedIds,
                                  Collection<String> dois, ExperimentDesign experimentDesign) {
        this(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, accession, lastUpdate, contrasts, description,
                species, pubMedIds, dois, experimentDesign);

    }

    private static final Function<Pair<Contrast, Boolean>, Contrast> unpack = Pair::getLeft;

    protected DifferentialExperiment(ExperimentType experimentType, String accession, Date lastUpdate,
                                     List<Pair<Contrast, Boolean>> contrasts, String description, Species species,
                                     Collection<String> pubMedIds, Collection<String> dois,
                                     ExperimentDesign experimentDesign) {

        super(experimentType, accession, lastUpdate,null, description, "", species, pubMedIds,
                dois, experimentDesign, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), contrasts.stream().map(unpack).collect(Collectors.toList()),
                ExperimentDisplayDefaults.simpleDefaults());
        this.contrastsWithCttvPrimaryAnnotation =
                contrasts.stream().filter(Pair::getRight).map(unpack).collect(Collectors.toSet());
    }

    public boolean doesContrastHaveCttvPrimaryAnnotation(Contrast contrast){
        return contrastsWithCttvPrimaryAnnotation.contains(contrast);
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

        DataColumnGroup.DataColumnGroupList dataColumnGroupList = new DataColumnGroup.DataColumnGroupList(experimentDisplayDefaults);

        //populate the keys in the order we want later
        dataColumnGroupList.addDataColumnGroupIfNotPresent("Comparison Name", true);
        for(String factorHeader: experimentDesign.getFactorHeaders()){
            dataColumnGroupList.addDataColumnGroupIfNotPresent(factorHeader, false);
        }
        for(String sampleHeader: experimentDesign.getSampleHeaders()){
            dataColumnGroupList.addDataColumnGroupIfNotPresent(sampleHeader, false);
        }

        // add the information about which headers go to which categories
        for(Contrast dataColumnDescriptor: getDataColumnDescriptors()){
            dataColumnGroupList.addValueToGroupingInGroup("Comparison Name",dataColumnDescriptor.getDisplayName(), dataColumnDescriptor);


            for(String assayAnalyzedForThisDataColumn : dataColumnDescriptor.assaysAnalyzedForThisDataColumn()){
                for(Factor factor : experimentDesign.getFactors(assayAnalyzedForThisDataColumn)){
                    dataColumnGroupList.addValueToGroupingInGroup(factor.getHeader(), factor.getValue(), dataColumnDescriptor);
                }
                for(SampleCharacteristic sampleCharacteristic
                        : experimentDesign.getSampleCharacteristics(assayAnalyzedForThisDataColumn)){
                    dataColumnGroupList.addValueToGroupingInGroup(sampleCharacteristic.header(), sampleCharacteristic.value(), dataColumnDescriptor);
                }
            }
        }
        return dataColumnGroupList.asJson();

    }

}
