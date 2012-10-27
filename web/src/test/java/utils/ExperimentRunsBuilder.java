//package utils;
//
//import org.apache.commons.lang.RandomStringUtils;
//import uk.ac.ebi.atlas.model.ExperimentRun;
//
//public class ExperimentRunsBuilder {
//
//    private ExperimentRun experimentRun;
//
//    public static ExperimentRunsBuilder forRunAccession(String runAccession) {
//        return new ExperimentRunsBuilder(runAccession);
//
//    }
//
//    private ExperimentRunsBuilder(String runAccession){
//        this.experimentRun = new ExperimentRun(runAccession);
//    }
//
//    public ExperimentRun create() {
//
//        experimentRun.addOrganismPartFactorValue("heart")
//                     .addFactorValue("factor_name", RandomStringUtils.ra);
//
//        return experimentRun;
//    }
//
//
//}
