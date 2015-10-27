/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentsTablePage;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;
import uk.ac.ebi.atlas.utils.ExperimentInfo;
import uk.ac.ebi.atlas.web.controllers.rest.ExperimentsListController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentsTablePageSIT extends SinglePageSeleniumFixture {

    private static final Logger LOGGER = LogManager.getLogger(ExperimentsTablePageSIT.class);

    private static final String EXPERIMENTS_REST_URI = "/gxa/json/experiments";

    private ExperimentsListController.ExperimentInfoWrapper experimentInfoWrapper;

    private List<ExperimentInfo> baselineInfos = Lists.newArrayList();

    private List<ExperimentInfo> differentialInfos = Lists.newArrayList();

    private int totalExperiments = 0;

    private int numberResults = 0;

    private ExperimentsTablePage subject;
    private String defaultFirstDescription;
    private String defaultLastDescription;

    @Override
    protected void getStartingPage() {

        readJsonEndpoint();

        subject = new ExperimentsTablePage(driver);
        subject.get();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.pollingEvery(5, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !subject.getExperimentsTableInfo().startsWith("Showing 0");
            }
        });
    }

    private void readJsonEndpoint() {
        URLBuilder urlBuilder = new URLBuilder(EXPERIMENTS_REST_URI);
        String s = urlBuilder.buildURL(null);
        try {
            URL url = new URL(s);
            InputStream inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String json = reader.readLine();
            Gson gson = new Gson();
            experimentInfoWrapper = gson.fromJson(json, ExperimentsListController.ExperimentInfoWrapper.class);
            for (ExperimentInfo experimentInfo : experimentInfoWrapper.getAaData()) {
                if (experimentInfo.getExperimentType().isBaseline()) {
                    baselineInfos.add(experimentInfo);
                } else {
                    differentialInfos.add(experimentInfo);
                }
            }
            totalExperiments = experimentInfoWrapper.getAaData().size();
            Collections.sort(baselineInfos);
            Collections.sort(differentialInfos);
            defaultFirstDescription = "RNA-seq of long poly adenylated RNA and long non poly adenylated RNA from ENCODE cell lines"; //baselineInfos.get(0).getExperimentDescription();

            if (baselineInfos.size() < 10) {
                defaultLastDescription = differentialInfos.get(9 - baselineInfos.size()).getExperimentDescription();
            } else {
                defaultLastDescription = baselineInfos.get(9).getExperimentDescription();
            }
            numberResults = totalExperiments < 10 ? totalExperiments : 10;
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Test
    public void testExperimentInfoWrapper() {
        List<ExperimentInfo> aaData = experimentInfoWrapper.getAaData();
        assertThat(aaData.size(), is(greaterThan(0)));
    }

    @Test
    public void defaultExperimentsPage() {
        assertThat(subject.getExperimentsTableHeader().size(), is(9));
        assertThat(subject.getExperimentsTableInfo(), startsWith("Showing 1 to " + numberResults + " of " + totalExperiments + " entries"));
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstDescription));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastDescription));
    }

    @Test
    public void filterBySearch() {
        assertThat(subject.getSearchFieldValue(), is(""));
        subject.setSearchFieldValue("baseline");
        assertThat(subject.getSearchFieldValue(), is("baseline"));
        int baselineResults = baselineInfos.size() < 10 ? baselineInfos.size() : 10;
        assertThat(subject.getExperimentsTableInfo(), startsWith("Showing 1 to " + baselineResults + " of " + baselineInfos.size() + " entries"));
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstDescription));
        if (baselineInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(baselineInfos.get(baselineInfos.size() - 1).getExperimentDescription()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(baselineInfos.get(9).getExperimentDescription()));
        }
    }

    //ToDo: these three tests are failing because the table is ajax... needs fix, must use Selenium Wait condition

    @Test
    public void sortOnFirstColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstDescription));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastDescription));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem(differentialInfos.get(0).getExperimentDescription()));
        if (differentialInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(baselineInfos.get(9 - differentialInfos.size()).getExperimentDescription()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(differentialInfos.get(9).getExperimentDescription()));
        }
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstDescription));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastDescription));
    }

    //TODO: FIX THIS TEST sortOnLoadedColumn
    @Test
    @Ignore
    public void     sortOnLoadedColumn() {
        subject.clickSecondColumnHeader();
        List<ExperimentInfo> allInfos = Lists.newArrayList(baselineInfos);
        allInfos.addAll(differentialInfos);
        Collections.sort(allInfos, new Comparator<ExperimentInfo>() {
            @Override
            public int compare(ExperimentInfo o1, ExperimentInfo o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    java.util.Date o1Loaded = dateFormat.parse(o1.getLastUpdate());
                    java.util.Date o2Loaded = dateFormat.parse(o2.getLastUpdate());
                    int compare = o1Loaded.compareTo(o2Loaded);
                    return (compare != 0) ? compare : Integer.compare(o1.getNumberOfAssays(), o2.getNumberOfAssays());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstDescription));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentDescription()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentDescription()));
        }
        subject.clickSecondColumnHeader();
        Collections.reverse(allInfos);
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentDescription()));
    }

    @Test
    public void sortOnDescriptionColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstDescription));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastDescription));
        subject.clickThirdColumnHeader();
        List<ExperimentInfo> allInfos = Lists.newArrayList(baselineInfos);
        allInfos.addAll(differentialInfos);
        Collections.sort(allInfos, new Comparator<ExperimentInfo>() {
            @Override
            public int compare(ExperimentInfo o1, ExperimentInfo o2) {
                return o1.getExperimentDescription().compareToIgnoreCase(o2.getExperimentDescription());
            }
        });
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentDescription()));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentDescription()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentDescription()));
        }
        subject.clickThirdColumnHeader();
        Collections.reverse(allInfos);
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentDescription()));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentDescription()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentDescription()));
        }
    }

    @Test
    public void selectPlantsExperiments() {
        subject.selectPlantsExperiments();
        subject.getExperimentsTableInfo().contains("of " + NumberOfExperiments.NUMBER_OF_PLANTS_EXPERIMENTS + " entries");
    }

    @Test
    public void selectAnimalsAndFungiExperiments() {
        subject.selectAnimalsAndFungiExperiments();
        subject.getExperimentsTableInfo().contains("of " + NumberOfExperiments.NUMBER_OF_ANIMALS_AND_FUNGI_EXPERIMENTS + " entries");
    }

    @Test
    public void selectPlantsAnimalsAndFungiExperiments() {
        subject.selectPlantAnimalAndFungiExperiments();
        subject.getExperimentsTableInfo().contains("of " + NumberOfExperiments.ALL + " entries");
    }


}