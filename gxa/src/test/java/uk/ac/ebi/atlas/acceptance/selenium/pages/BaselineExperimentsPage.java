
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class BaselineExperimentsPage extends GlobalSearchPage {

    @FindBy(id = "species-nav")
    private WebElement speciesNav;

    private static final String DEFAULT_PAGE_URI = "/gxa/baseline/experiments";

    public BaselineExperimentsPage(WebDriver driver) {
        super(driver);
    }

    public BaselineExperimentsPage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public List<WebElement> getAllSpeciesItems() {
        return speciesNav.findElements(By.className("specie_item"));
    }

    public String getNameOfSpecies(int i) {
        WebElement species = getAllSpeciesItems().get(i);
        return species.findElement(By.xpath("h3")).getText();
    }

    public List<WebElement> getAllExperimentsOfSpecies(int i) {
        WebElement species = getAllSpeciesItems().get(i);
        return species.findElements(By.xpath("ul/li"));
    }

    public List<String> getAllExperimentLinksOfSpecies(int i){
        List<String> result = new ArrayList<>();
        List<WebElement> allExperimentsOfSpecies = getAllExperimentsOfSpecies(i);
        for (WebElement link : allExperimentsOfSpecies) {
            result.add(link.findElement(By.xpath("a")).getAttribute("href"));
        }

        return result;
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

}