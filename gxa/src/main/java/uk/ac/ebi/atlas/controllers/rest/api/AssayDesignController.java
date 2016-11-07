package uk.ac.ebi.atlas.controllers.rest.api;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;

import javax.inject.Inject;
import java.util.Map;

@Controller
@Scope("request")
public class AssayDesignController {

    private ArrayDesignDAO arrayDesignDAO;

    @Inject
    public AssayDesignController(ArrayDesignDAO arrayDesignDAO) {
        this.arrayDesignDAO = arrayDesignDAO;
    }

    @RequestMapping(value = "/api/arraydesigns.txt", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllArrayDesignsWithNames() {

        Map<String,String> arrayDesigns = arrayDesignDAO.getArrayDesignMapNames();

        return Joiner
                .on("\n")
                .withKeyValueSeparator("\t")
                .join(arrayDesigns.entrySet());

    }

}
