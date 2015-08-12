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

package uk.ac.ebi.atlas.experimentpage.differential.rnaseq;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrivateExperimentWithAccessKeySIT extends SeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-698";

    private HeatmapTablePage subject;

    private String accessKey;

    @BeforeClass
    public static void initRestAssuredRequestSpec(){
        RestAssuredAuthenticatedFixture.initRestAssured();
    }

    @Before
    public void init() {

        expect().body(containsString(EXPERIMENT_ACCESSION)).when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=true");

        String jsonResponse = given().get("listExperiments?accession=" + EXPERIMENT_ACCESSION).body().asString();

        accessKey = from(jsonResponse).get("accessKey[0]");

        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        subject.get();
    }

    @After
    public void cleanup() {
        expect().body(containsString(EXPERIMENT_ACCESSION)).when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=false");
    }

    @Test(expected = NoSuchElementException.class)
    public void pageShouldNotBeAvailableWithoutAccessKey() {
        HeatmapTablePage page = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
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
        ExperimentDesignTablePage experimentDesignPage = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

}