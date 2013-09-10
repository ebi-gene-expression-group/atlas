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

package uk.ac.ebi.atlas.geneannotation;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class ArrayDesignDaoIT {

    @Inject
    private ArrayDesignDao subject;

    private Map<String, String> annotations = Maps.newHashMap();

    private static final String ARRAY_DESIGN = "A-AFFY-666";

    @Before
    public void setup() {
        annotations.put("de1", "ens1");
        annotations.put("de2", "ens2");

        subject.deleteMappings(ARRAY_DESIGN);
    }

    @Test
    public void testSaveGetMappings() throws Exception {
        subject.saveMappings(annotations, ARRAY_DESIGN, "ensembl");
        assertThat(subject.getGeneIdentifier(ARRAY_DESIGN, "de1"), is("ens1"));
        assertThat(subject.getGeneIdentifier("NOT EXISRING AD", "de1"), is(StringUtils.EMPTY));
        assertThat(subject.getGeneIdentifier(ARRAY_DESIGN, "not there"), is(StringUtils.EMPTY));
    }

    @Test
    public void testIsArrayDesignPresent() throws Exception {
        assertThat(subject.isArrayDesignPresent(ARRAY_DESIGN), is(false));
        subject.saveMappings(annotations, ARRAY_DESIGN, "ensembl");
        assertThat(subject.isArrayDesignPresent(ARRAY_DESIGN), is(true));

    }
}
