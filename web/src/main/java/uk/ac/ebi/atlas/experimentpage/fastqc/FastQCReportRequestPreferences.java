package uk.ac.ebi.atlas.experimentpage.fastqc;


import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastQCReportRequestPreferences {

    private String selectedReport;

    private String selectedSpecie;

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

    public String getSelectedSpecie() {
        return selectedSpecie;
    }

    public void setSelectedSpecie(String selectedSpecie) {
        this.selectedSpecie = parseSpecie(selectedSpecie);
    }

    private String parseSpecie(String specie) {
        if(specie.split("\\s+").length > 1) {
            String[] arrayString = specie.split("\\s+");
            return WordUtils.capitalize(arrayString[0]) + " " + arrayString[1];
        }
        else {
            if(specie.split("_").length > 1) {
                String[] arrayString = specie.split("_");
                return WordUtils.capitalize(arrayString[0]) + " " + arrayString[1];
            } else { return specie; }
        }
    }

}
