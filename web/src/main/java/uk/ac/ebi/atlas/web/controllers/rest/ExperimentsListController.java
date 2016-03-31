package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.utils.ExperimentInfo;
import uk.ac.ebi.atlas.utils.ExperimentInfoListBuilder;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;


@Controller
@Scope("request")
public class ExperimentsListController {

    private ExperimentInfoListBuilder experimentInfoListBuilder;

    @Inject
    public ExperimentsListController(ExperimentInfoListBuilder experimentInfoListBuilder) {
        this.experimentInfoListBuilder = experimentInfoListBuilder;
    }

    /**
     * Used by experiments table page
     */
    @RequestMapping(value = "/json/experiments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getExperimentsList() {

        List<ExperimentInfo> experimentInfos = experimentInfoListBuilder.build();
        Collections.sort(experimentInfos);
        ExperimentInfoWrapper experimentInfoWrapper = new ExperimentInfoWrapper(experimentInfos);

        Gson gson = new Gson();
        return gson.toJson(experimentInfoWrapper);
    }

    /**
     *  This is a wrapper class used via Gson to produce the right JSON input for DataTables.
     */
    public class ExperimentInfoWrapper {

        private List<ExperimentInfo> aaData;

        public ExperimentInfoWrapper(List<ExperimentInfo> list) {
            this.aaData = list;
        }

        //DataTables requires table data in the aaData property
        public List<ExperimentInfo> getAaData() {
            return aaData;
        }
    }

}