package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.*;
import uk.ac.ebi.atlas.model.AnatomogramType;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.web.ApplicationProperties;

public class AnatomogramFactory {

    private final ApplicationProperties applicationProperties;

    public AnatomogramFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public JsonElement get(String queryFactorType, String species, Iterable<AssayGroupFactor> filteredAssayGroupFactors,
                           String contextRoot) {
        if ("ORGANISM_PART".equals(queryFactorType)) {
            String maleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, AnatomogramType.MALE);
            String femaleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, AnatomogramType.FEMALE);
            String brainAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, AnatomogramType.BRAIN);
            return maleAnatomogramFileName != null || femaleAnatomogramFileName != null || brainAnatomogramFileName != null
                    ? getAnatomogramProperties(species, filteredAssayGroupFactors, maleAnatomogramFileName,
                    femaleAnatomogramFileName, brainAnatomogramFileName, contextRoot)
                    : JsonNull.INSTANCE;
        } else {
            return JsonNull.INSTANCE;
        }
    }

    private JsonObject getAnatomogramProperties(String species, Iterable<AssayGroupFactor> filteredAssayGroupFactors,
                                                String maleAnatomogramFileName, String femaleAnatomogramFileName,
                                                String brainAnatomogramFileName, String contextRoot) {
        JsonObject anatomogramProperties = new JsonObject();
        //TODO this is obviously a hack, if you are improving how we are linking anatomogram graphics for different
        // species don't be attached to this logic too much
        if (species.equals("oryza sativa") || species.equals("oryza sativa japonica group")) {
            addPropertiesForPlant(anatomogramProperties, maleAnatomogramFileName, femaleAnatomogramFileName, brainAnatomogramFileName);
        } else {
            addPropertiesForAnimal(anatomogramProperties, maleAnatomogramFileName, femaleAnatomogramFileName,
                    brainAnatomogramFileName);
        }

        anatomogramProperties.add("allSvgPathIds", extractOntologyTerm(filteredAssayGroupFactors));
        anatomogramProperties.addProperty("contextRoot", contextRoot);

        return anatomogramProperties;
    }

    private void addPropertiesForAnimal(JsonObject anatomogramProperties, String maleAnatomogramFileName, String
            femaleAnatomogramFileName, String brainAnatomogramFileName) {
        anatomogramProperties.addProperty("maleAnatomogramFile", maleAnatomogramFileName);
        anatomogramProperties.addProperty("femaleAnatomogramFile", femaleAnatomogramFileName);
        anatomogramProperties.addProperty("brainAnatomogramFile", brainAnatomogramFileName);

        anatomogramProperties.addProperty("toggleButtonMaleImageTemplate", "/resources/images/male");
        anatomogramProperties.addProperty("toggleButtonFemaleImageTemplate", "/resources/images/female");
        anatomogramProperties.addProperty("toggleButtonBrainImageTemplate", "/resources/images/brain");
    }

    private void addPropertiesForPlant(JsonObject anatomogramProperties, String maleAnatomogramFileName, String
            femaleAnatomogramFileName, String brainAnatomogramFileName) {
        anatomogramProperties.addProperty("maleAnatomogramFile", maleAnatomogramFileName);
        anatomogramProperties.addProperty("femaleAnatomogramFile", femaleAnatomogramFileName);
        anatomogramProperties.addProperty("brainAnatomogramFile", brainAnatomogramFileName);

        anatomogramProperties.addProperty("toggleButtonMaleImageTemplate", "/resources/images/whole_plant");
        anatomogramProperties.addProperty("toggleButtonFemaleImageTemplate", "/resources/images/flower_parts");

    }

    private JsonElement extractOntologyTerm(Iterable<AssayGroupFactor> filteredAssayGroupFactors) {
        JsonArray ontologyTerms = new JsonArray();

        for (AssayGroupFactor assayGroupFactor : filteredAssayGroupFactors) {
            String valueOntologyTermId = assayGroupFactor.getValueOntologyTermId();
            if (valueOntologyTermId != null) {
                ontologyTerms.add(new JsonPrimitive(valueOntologyTermId));
            }
        }
        return ontologyTerms.size() == 0 ? JsonNull.INSTANCE : ontologyTerms;
    }

}
