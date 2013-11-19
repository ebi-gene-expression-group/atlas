package uk.ac.ebi.atlas.acceptance.selenium.pages;

import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;

public class BaselineBioEntitiesCountWithHref extends BaselineBioentitiesCount {

    private final String href;

    public BaselineBioEntitiesCountWithHref(String experimentName, String species, String experimentAccession, int count, String href){
        super(experimentName, species, experimentAccession, count);
        this.href = href;
    }

    public String getHref() {
        return href;
    }

}
