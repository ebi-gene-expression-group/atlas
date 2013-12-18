package uk.ac.ebi.atlas.web.controllers.rest.api;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.dao.ArrayDesignDao;

import javax.inject.Inject;
import java.util.List;

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
    public String getAllArrayDesigns() {

        List<String> arrayDesignAccessions = arrayDesignDao.getArrayDesignAccessions();

        return Joiner.on("\n").join(arrayDesignAccessions);
    }
}
