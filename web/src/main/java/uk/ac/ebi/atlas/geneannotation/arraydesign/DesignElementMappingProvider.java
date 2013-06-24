package uk.ac.ebi.atlas.geneannotation.arraydesign;

import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named("designElementMappingProvider")
public class DesignElementMappingProvider {

    private ArrayDesignDao arrayDesignDao;

    @Inject
    public DesignElementMappingProvider(ArrayDesignDao arrayDesignDao) {
        this.arrayDesignDao = arrayDesignDao;
    }

    public String getEnsGeneId(String arrayDesign, String designElement) {
        return arrayDesignDao.getIdentifier(arrayDesign, designElement);
    }

}
