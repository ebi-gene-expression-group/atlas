package uk.ac.ebi.atlas.web.common;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryPropertyEditor;

@ControllerAdvice
public class CommonBindings {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SemanticQuery.class, new SemanticQueryPropertyEditor());
    }

}
