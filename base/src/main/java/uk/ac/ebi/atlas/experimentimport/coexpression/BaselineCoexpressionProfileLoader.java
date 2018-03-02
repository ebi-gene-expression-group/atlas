package uk.ac.ebi.atlas.experimentimport.coexpression;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineCoexpressionProfileLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionProfileInputStream.class);

    private BaselineCoexpressionProfileDao baselineCoexpressionProfileDao;
    private DataFileHub dataFileHub;

    @Inject
    public BaselineCoexpressionProfileLoader(BaselineCoexpressionProfileDao baselineCoexpressionProfileDao) {
        this.baselineCoexpressionProfileDao = baselineCoexpressionProfileDao;
    }

    @Inject
    public void setDataFileHub(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    @Transactional
    public int loadBaselineCoexpressionsProfile(String experimentAccession) {
        AtlasResource<CSVReader> coexpressions =
                dataFileHub.getBaselineExperimentFiles(experimentAccession).coexpressions;

        if (coexpressions.exists()) {
            try (BaselineCoexpressionProfileInputStream is =
                         new BaselineCoexpressionProfileInputStream(coexpressions.get())) {
                return baselineCoexpressionProfileDao.loadCoexpressionsProfile(experimentAccession, is);
            } catch (IOException | IllegalStateException e) {
                LOGGER.error("Error reading coexpression file for experiment {}", experimentAccession);
                LOGGER.error(e.getMessage(), e);
            }
        }

        //it doesn't make sense to calculate coexpressions for all experiments and we allow the file to be missing
        return 0;
    }

    @Transactional
    public int deleteCoexpressionsProfile(String accession) {
        return baselineCoexpressionProfileDao.deleteCoexpressionsProfile(accession);
    }

}
