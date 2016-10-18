package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.*;
import uk.ac.ebi.atlas.model.AnatomogramType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.web.ApplicationProperties;

public class AnatomogramFactory {

    private final ApplicationProperties applicationProperties;

    public AnatomogramFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public JsonElement get(String queryFactorType, Species species, Iterable<AssayGroupFactor>
            filteredAssayGroupFactors,
                           String contextRoot) {
        if ("ORGANISM_PART".equals(queryFactorType)) {
            return getAnatomogramProperties(species.mappedName,filteredAssayGroupFactors,contextRoot);
        } else {
            return JsonNull.INSTANCE;
        }
    }

    private JsonObject getAnatomogramProperties(String species, Iterable<AssayGroupFactor> filteredAssayGroupFactors,
                                                String contextRoot) {
        JsonObject anatomogramProperties = new JsonObject();
        deprecatedHacksLeftThereForBackwardsCompatibilityForExternalPartiesUsingNotUpToDateWidget(species,
                applicationProperties.getAnatomogramFileName(species, AnatomogramType.MALE), applicationProperties
                        .getAnatomogramFileName(species, AnatomogramType.FEMALE), applicationProperties
                        .getAnatomogramFileName(species, AnatomogramType.BRAIN), anatomogramProperties, contextRoot);

        anatomogramProperties.addProperty("species",species);
        anatomogramProperties.add("allSvgPathIds", extractOntologyTerm(filteredAssayGroupFactors));

        return anatomogramProperties;
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

    private void deprecatedHacksLeftThereForBackwardsCompatibilityForExternalPartiesUsingNotUpToDateWidget
            (String species, String maleAnatomogramFileName, String femaleAnatomogramFileName, String
                    brainAnatomogramFileName, JsonObject anatomogramProperties,String contextRoot) {
        anatomogramProperties.addProperty("contextRoot", contextRoot);

        //TODO if no-one is using pre-August 2016 widget, delete all of this.
        if (species.equals("oryza sativa") || species.equals("oryza sativa japonica group")) {
            addPropertiesForPlant(anatomogramProperties, maleAnatomogramFileName, femaleAnatomogramFileName, brainAnatomogramFileName);
        } else {
            addPropertiesForAnimal(anatomogramProperties, maleAnatomogramFileName, femaleAnatomogramFileName,
                    brainAnatomogramFileName);
        }
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

}
