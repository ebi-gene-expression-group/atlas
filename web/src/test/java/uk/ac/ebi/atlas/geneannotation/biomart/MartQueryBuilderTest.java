/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.geneannotation.biomart;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.utils.Files;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class MartQueryBuilderTest {
    private final static String XML_TEMPLATE =
     "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
   + "<!DOCTYPE Query>\n"
   + "  <Query virtualSchemaName=\"{0}\" formatter=\"TSV\" header=\"0\" uniqueRows=\"1\" count=\"0\">\n"
   + "  <Dataset name = \"{1}\" interface = \"default\" >\n"
   + "  {2}"
   + "  </Dataset>\n"
   + "</Query>"  ;

    private final static String XML_ATTRIBUTE_TEMPLATE = "<Attribute name = \"{0}\" />\n";

    private MartQueryBuilder subject;

    private String xmlRequestMock = Files.readTextFileFromClasspath(this.getClass(), "biomart.query.xml");

    @Before
    public void setUp() throws Exception {
        subject = new MartQueryBuilder(XML_TEMPLATE, XML_ATTRIBUTE_TEMPLATE);
    }

    @Test
    public void testBuild() throws Exception {
        //given
        subject.setDataset("dataset")
                .addAttribute("attribute1")
                .addAttribute("attribute2");
        String xmlQuery = subject.build();
        //then
        assertThat(StringUtils.remove(xmlQuery, " "), is(StringUtils.remove(xmlRequestMock, " ")));
    }
}
