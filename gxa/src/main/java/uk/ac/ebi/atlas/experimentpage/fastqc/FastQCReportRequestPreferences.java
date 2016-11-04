package uk.ac.ebi.atlas.experimentpage.fastqc;


import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastQCReportRequestPreferences {

    private String selectedReport;

    private String selectedSpecies;

    public enum FastQCReportType {
        QC("qc"),
        MAPPING("mapping");

        private String qcReport;

        FastQCReportType(String qc) {
            qcReport = qc;
        }

        public String getQcReport() {
            return String.valueOf(qcReport);
        }
    }

    public List<FastQCReportType> fastQCReportsList() {
        return new ArrayList<>(Arrays.asList(FastQCReportType.values()));
    }

    public String getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(String selectedReport) {
        this.selectedReport = selectedReport;
    }

    public String getSelectedSpecies() {
        return selectedSpecies;
    }

    public void setSelectedSpecies(String selectedSpecies) {
        this.selectedSpecies = parseSpecies(selectedSpecies);
    }

    private String parseSpecies(String species) {
        if(species.split("\\s+").length > 1) {
            String[] arrayString = species.split("\\s+");
            return WordUtils.capitalize(arrayString[0]) + " " + arrayString[1];
        }
        else {
            if(species.split("_").length > 1) {
                String[] arrayString = species.split("_");
                return WordUtils.capitalize(arrayString[0]) + " " + arrayString[1];
            } else { return species; }
        }
    }

}
