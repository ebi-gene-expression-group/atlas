package uk.ac.ebi.atlas.experimentimport.admin;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class ExperimentAdminHelpPage {

    public static final String HELP_URL = "admin/experiments/help";

    private String TITLE = "Experiment admin- list of operations";

    private String message = "";

    public String getMessage(){
        if(message.isEmpty()){
            message = buildMessage();
        }
        return message;
    }

    private String buildMessage(){
        StringBuilder result = new StringBuilder();
        addLine(result,TITLE);
        addLine(result, StringUtils.repeat('-',TITLE.length()));
        addLine(result,"");
        addLine(result, "### Operations by name");
        addLine(result,"");
        for(Op op: Op.values()){
            addLine(result, "#### ", op.name());
            addLine(result, op.getDescription());
            addLine(result,"");
        }
        addLine(result, "### Synonyms for operations");
        addLine(result,"");
        for(Map.Entry<String,?> e: Op.synonyms.entrySet()){
            addLine(result, "+ ",e.getKey(), ": ", e.getValue().toString());
        }
        addLine(result,"");
        addLine(result, "### Technical terms explained");
        addLine(result,"");
        for(Map.Entry<String,String> e: Op.JARGON_ELABORATED.entrySet()){
            addLine(result, "#### ",StringUtils.capitalize(e.getKey()));
            addLine(result, e.getValue());
            addLine(result,"");
        }
        return result.toString();
    }

    private void addLine(StringBuilder sb, CharSequence... lines){
        for(CharSequence line: lines) {
            sb.append(line);
        }
        sb.append('\n');
    }
}
