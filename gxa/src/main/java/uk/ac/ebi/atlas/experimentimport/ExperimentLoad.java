package uk.ac.ebi.atlas.experimentimport;

import java.util.UUID;

public class ExperimentLoad {

    void makePublicExperimentPrivate(String accession){

    }

    void makePrivateExperimentPublic(String accession){

    }

    void readExperimentFromDiskIntoDatabase(String accession){

    }

    void updateDesignFile(String accession){

    }

    /*
    case UPDATE_PRIVATE:
            experimentMetadataCRUD.makeExperimentPrivate(accession);
    break;
    case UPDATE_PUBLIC:
            experimentMetadataCRUD.makeExperimentPublic(accession);
    break;
    case UPDATE_DESIGN_ONLY:
            experimentMetadataCRUD.updateExperimentDesign(accession);
    break;
    case IMPORT_PUBLIC:
    isPrivate = false;
    case IMPORT:
    UUID accessKeyUUID = experimentCRUD.importExperiment(accession, isPrivate);
    resultOfTheOp = new JsonPrimitive("success, access key UUID: " + accessKeyUUID);
    break;
    case SERIALIZE:
    resultOfTheOp = new JsonPrimitive(experimentCRUD.serializeExpressionData(accession));
    break;
    case DELETE:
            experimentCRUD.deleteExperiment(accession);

     */
}
