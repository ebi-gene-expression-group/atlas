package uk.ac.ebi.atlas.web.common;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.web.OldGeneQuery;
import uk.ac.ebi.atlas.web.SemanticQuery;
import uk.ac.ebi.atlas.web.SemanticQueryPropertyEditor;
import uk.ac.ebi.atlas.web.OldGeneQueryPropertyEditor;

@ControllerAdvice
public class CommonBindings {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(OldGeneQuery.class, new OldGeneQueryPropertyEditor());
        binder.registerCustomEditor(SemanticQuery.class, new SemanticQueryPropertyEditor());
    }

}
