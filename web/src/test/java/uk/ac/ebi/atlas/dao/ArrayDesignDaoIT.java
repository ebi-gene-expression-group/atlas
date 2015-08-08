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

package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ArrayDesignDaoIT {

    @Inject
    private ArrayDesignDAO subject;

    private static final String ARRAY_DESIGN = "A-AFFY-8";


    @Test
    public void testIsArrayDesignPresent() throws Exception {
        assertThat(subject.isArrayDesignPresent(ARRAY_DESIGN), is(true));

    }

    @Test
    public void testGetDesignElements() throws Exception {
        List<String> designElements = subject.getDesignElements("ENSG00000109929");
        assertThat(designElements, hasItem("211423_s_at"));

    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        List<String> arrayDesignAccessions = subject.getArrayDesignAccessions();
        assertThat(arrayDesignAccessions, hasItem("A-AFFY-44"));
    }

    @Test
    public void testGetAllArrayDesignMapNames() throws Exception {
        Map<String,String> arrayDesigns = subject.getArrayDesignMapNames();
        assertThat(arrayDesigns.size(), is(72));
    }

}
