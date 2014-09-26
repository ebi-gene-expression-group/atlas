package uk.ac.ebi.atlas.web.controllers.rest.api;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.dao.ArrayDesignDao;

import javax.inject.Inject;
import java.util.Map;

@Controller
@Scope("request")
public class AssayDesignController {

    private ArrayDesignDao arrayDesignDao;

    @Inject
    public AssayDesignController(ArrayDesignDao arrayDesignDao) {
        this.arrayDesignDao = arrayDesignDao;
    }

    @RequestMapping(value = "/api/arraydesigns.txt", method = RequestMethod.GET)
    @ResponseBody
    public String getAllArrayDesignsWithNames() {

        Map<String,String> arrayDesigns = arrayDesignDao.getArrayDesignMapNames();

        String keysAndValuesJoined = Joiner
                .on("\n")
                .withKeyValueSeparator("\t")
                .join(arrayDesigns.entrySet());

        return  keysAndValuesJoined;

    }

}
