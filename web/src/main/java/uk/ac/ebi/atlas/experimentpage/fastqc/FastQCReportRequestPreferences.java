package uk.ac.ebi.atlas.experimentpage.fastqc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastQCReportRequestPreferences {

    private String selectedReport;

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
}
