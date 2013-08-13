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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.FeedbackHomePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormAndBarChartPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;

import java.lang.reflect.Type;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class PrivateExperimentWithAccessKeyIT extends SeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-698";

    private HeatmapTableWithSearchFormAndBarChartPage subject;

    private String accessKey;

    @Before
    public void init() {
        EndPoint endPoint = new EndPoint("/gxa/updateExperiment?accession=" + EXPERIMENT_ACCESSION + "&private=true");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString(EXPERIMENT_ACCESSION));

        endPoint = new EndPoint("/gxa/listExperiments?accession=" + EXPERIMENT_ACCESSION);

        Type t = new TypeToken<List<ExperimentDTO>>() {}.getType();
        List<ExperimentDTO> experiments = new Gson().fromJson(endPoint.getResponseBody().asString(), t);
        assertThat(experiments.size(), is(1));
        accessKey = experiments.get(0).getAccessKey();

        subject = new HeatmapTableWithSearchFormAndBarChartPage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        subject.get();
    }

    @After
    public void cleanup() {
        EndPoint endPoint = new EndPoint("/gxa/updateExperiment?accession=" + EXPERIMENT_ACCESSION + "&private=false");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString(EXPERIMENT_ACCESSION));
    }

    @Test(expected = NoSuchElementException.class)
    public void pageShouldNotBeAvailableWithoutAccessKey() {
        HeatmapTableWithSearchFormAndBarChartPage page = new HeatmapTableWithSearchFormAndBarChartPage(driver, EXPERIMENT_ACCESSION);
        page.get();
        page.getExperimentDescription();
    }

    @Test
    public void pageShouldBeAvailableWithAccessKey() {
        assertThat(subject.getExperimentDescription(), is("RNA-seq of vomeronasal tissue from adult male and female mice"));
    }

    @Test
    public void buttonLinksShouldContainAccessKeyQueryString() {
        assertThat(subject.getDisplayExperimentLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentDesignLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentAnalysisLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadRawCountsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
    }

    @Test(expected = NoSuchElementException.class)
    public void experimentDesignPageWillFailWithoutAccessKey() {
        ExperimentDesignTablePage experimentDesignPage = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

    @Test
    public void experimentDesignPageWillBeAvailableWithAccessKey() {
        ExperimentDesignTablePage experimentDesignPage =
                new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

}