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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentsTablePage;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.utils.ExperimentInfo;
import uk.ac.ebi.atlas.web.controllers.rest.ExperimentsListController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentsTablePageIT extends SinglePageSeleniumFixture {

    private static final Logger LOGGER = Logger.getLogger(ExperimentsTablePageIT.class);

    private static final String EXPERIMENTS_REST_URI = "/gxa/json/experiments";

    private ExperimentsListController.ExperimentInfoWrapper experimentInfoWrapper;

    private List<ExperimentInfo> baselineInfos = Lists.newArrayList();

    private List<ExperimentInfo> differentialInfos = Lists.newArrayList();

    private int totalExperiments = 0;

    private int numberResults = 0;

    private String defaultFirstAccession;

    private String defaultLastAccession;

    private ExperimentsTablePage subject;

    @Override
    protected void getStartingPage() {

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
                if (experimentInfo.getExperimentType() == ExperimentType.BASELINE) {
                    baselineInfos.add(experimentInfo);
                } else {
                    differentialInfos.add(experimentInfo);
                }
            }
            totalExperiments = experimentInfoWrapper.getAaData().size();
            Collections.sort(baselineInfos);
            Collections.sort(differentialInfos);
            defaultFirstAccession = baselineInfos.get(0).getExperimentAccession();
            if (baselineInfos.size() < 10) {
                defaultLastAccession = differentialInfos.get(9 - baselineInfos.size()).getExperimentAccession();
            } else {
                defaultLastAccession = baselineInfos.get(9).getExperimentAccession();
            }
            numberResults = totalExperiments < 10 ? totalExperiments : 10;
        } catch (MalformedURLException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }

        subject = new ExperimentsTablePage(driver);
        subject.get();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !subject.getExperimentsTableInfo().startsWith("Showing 0");
            }
        });
    }

    @Test
    public void testExperimentInfoWrapper() {
        List<ExperimentInfo> aaData = experimentInfoWrapper.getAaData();
        assertThat(aaData.size(), is(greaterThan(0)));
    }

    @Test
    public void defaultExperimentsPage() {
        assertThat(subject.getExperimentsTableHeader().size(), is(8));
        assertThat(subject.getExperimentsTableInfo(), startsWith("Showing 1 to " + numberResults + " of " + totalExperiments + " entries"));
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstAccession));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastAccession));
    }

    @Test
    public void filterBySearch() {
        assertThat(subject.getSearchFieldValue(), is(""));
        subject.setSearchFieldValue("baseline");
        assertThat(subject.getSearchFieldValue(), is("baseline"));
        int baselineResults = baselineInfos.size() < 10 ? baselineInfos.size() : 10;
        assertThat(subject.getExperimentsTableInfo(), startsWith("Showing 1 to " + baselineResults + " of " + baselineInfos.size() + " entries"));
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstAccession));
        if (baselineInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(baselineInfos.get(baselineInfos.size() - 1).getExperimentAccession()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(baselineInfos.get(9).getExperimentAccession()));
        }
    }

    @Test
    public void sortOnFirstColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstAccession));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastAccession));
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem(differentialInfos.get(0).getExperimentAccession()));
        if (differentialInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(baselineInfos.get(9 - differentialInfos.size()).getExperimentAccession()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(differentialInfos.get(9).getExperimentAccession()));
        }
        subject.clickFirstColumnHeader();
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstAccession));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastAccession));
    }

    @Test
    public void sortOnSecondColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstAccession));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastAccession));
        subject.clickSecondColumnHeader();
        List<ExperimentInfo> allInfos = Lists.newArrayList(baselineInfos);
        allInfos.addAll(differentialInfos);
        Collections.sort(allInfos);
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentAccession()));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentAccession()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentAccession()));
        }
        subject.clickSecondColumnHeader();
        Collections.reverse(allInfos);
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentAccession()));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentAccession()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentAccession()));
        }
    }

    @Test
    public void sortOnThirdColumn() {
        assertThat(subject.getFirstExperimentInfo(), hasItem(defaultFirstAccession));
        assertThat(subject.getLastExperimentInfo(), hasItem(defaultLastAccession));
        subject.clickThirdColumnHeader();
        List<ExperimentInfo> allInfos = Lists.newArrayList(baselineInfos);
        allInfos.addAll(differentialInfos);
        Collections.sort(allInfos, new Comparator<ExperimentInfo>() {
            @Override
            public int compare(ExperimentInfo o1, ExperimentInfo o2) {
                return o1.getExperimentDescription().compareToIgnoreCase(o2.getExperimentDescription());
            }
        });
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentAccession()));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentAccession()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentAccession()));
        }
        subject.clickThirdColumnHeader();
        Collections.reverse(allInfos);
        assertThat(subject.getFirstExperimentInfo(), hasItem(allInfos.get(0).getExperimentAccession()));
        if (allInfos.size() < 10) {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(allInfos.size() - 1).getExperimentAccession()));
        } else {
            assertThat(subject.getLastExperimentInfo(), hasItem(allInfos.get(9).getExperimentAccession()));
        }
    }
}