package uk.ac.ebi.atlas.experimentimport.admin;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class ExperimentAdminHelpPage {

    private String message = "";

    public String getMessage() {
        if (message.isEmpty()) {
            message = buildMessage();
        }
        return message;
    }

    private String buildMessage() {
        StringBuilder result = new StringBuilder();
        String title = "Experiment admin- list of operations";
        addLine(result, title);
        addLine(result, StringUtils.repeat('-', title.length()));
        addLine(result, "");
        addLine(result, "### Usage");
        addLine(result, "");
        addLine(result, "admin/experiments/<accessions>/<ops>");
        addLine(result, "e.g.");
        addLine(result, "admin/experiments/E-MTAB-513/update_design");
        addLine(result, "");
        addLine(result, "### Operations by name");
        addLine(result, "");
        for (Op op: Op.values()) {
            addLine(result, "#### ", op.name());
            addLine(result, op.getDescription());
            addLine(result, "");
        }
        addLine(result, "### Synonyms for operations");
        addLine(result, "");
        for (Map.Entry<String, ?> e: Op.SYNONYMS.entrySet()) {
            addLine(result, "+ ", e.getKey(), ": ", e.getValue().toString());
        }
        addLine(result, "");
        addLine(result, "### Technical terms explained");
        addLine(result, "");
        for (Map.Entry<String, String> e: Op.JARGON_ELABORATED.entrySet()) {
            addLine(result, "#### ", StringUtils.capitalize(e.getKey()));
            addLine(result, e.getValue());
            addLine(result, "");
        }
        return result.toString();
    }

    private void addLine(StringBuilder sb, CharSequence... lines) {
        for (CharSequence line: lines) {
            sb.append(line);
        }
        sb.append('\n');
    }
}
