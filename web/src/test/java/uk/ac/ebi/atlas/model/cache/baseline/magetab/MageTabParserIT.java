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

package uk.ac.ebi.atlas.model.cache.baseline.magetab;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MageTabParserIT {

    @Inject
    private MageTabParserBuilder builder;

    private MageTabParser subject;


    @Before
    public void setUp() throws Exception {
        subject = builder.forExperimentAccession("E-GEOD-26284")
                .withRequiredFactorTypes(Sets.newHashSet("CELL_LINE", "CELLULAR_COMPONENT", "RNA"))
                .withProcessedRunAccessions(Collections.EMPTY_SET)
                .build();
    }

    @Test
    public void testExtractFactorNames() throws Exception {
        //given
        Map<String, String> factorNamesByType = subject.getFactorNamesByType();

        //then
        Assert.assertThat(factorNamesByType.keySet(), containsInAnyOrder("CELL_LINE", "CELLULAR_COMPONENT", "RNA"));
        //and
        Assert.assertThat(factorNamesByType.get("CELL_LINE"), is("cell line"));
        Assert.assertThat(factorNamesByType.get("CELLULAR_COMPONENT"), is("cellular component"));
        Assert.assertThat(factorNamesByType.get("RNA"), is("RNA"));
    }
}
