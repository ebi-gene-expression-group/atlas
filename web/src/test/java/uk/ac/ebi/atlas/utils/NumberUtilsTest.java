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

package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NumberUtilsTest {

    private NumberUtils subject = new NumberUtils();

    @Test
    public void testhtmlFormatDoubleWithLargeNumber() {
        assertThat(subject.htmlFormatDouble(9969209968386869000000000000000000000.000d), is("9.97 × 10<span style=\"vertical-align: super;\">36</span>"));
    }

    @Test
    public void testhtmlFormatDoubleWithLargeExponential() {
        assertThat(subject.htmlFormatDouble(9.97e36d), is("9.97 × 10<span style=\"vertical-align: super;\">36</span>"));
    }

    @Test
    public void testhtmlFormatDoubleWithSmallValues() {
        assertThat(subject.htmlFormatDouble(1.0d), is("1"));
        assertThat(subject.htmlFormatDouble(1.01d), is("1.01"));
        assertThat(subject.htmlFormatDouble(1.001d), is("1.001"));
        assertThat(subject.htmlFormatDouble(1.0001d), is("1"));
    }

    @Test
    public void testhtmlFormatDouble() {
        assertThat(subject.htmlFormatDouble(0.0d), is("0"));

        assertThat(subject.htmlFormatDouble(1.2E-11d), is("<10<span style=\"vertical-align: super;\">-10</span>"));
        assertThat(subject.htmlFormatDouble(-1.2E-11d), is("-1.2 × 10<span style=\"vertical-align: super;\">-11</span>"));

        assertThat(subject.htmlFormatDouble(1.2E-5d), is("1.2 × 10<span style=\"vertical-align: super;\">-5</span>"));
        assertThat(subject.htmlFormatDouble(-1.2E-5d), is("-1.2 × 10<span style=\"vertical-align: super;\">-5</span>"));

        assertThat(subject.htmlFormatDouble(123.456d), is("1.23 × 10<span style=\"vertical-align: super;\">2</span>"));
        assertThat(subject.htmlFormatDouble(-123.456d), is("-1.23 × 10<span style=\"vertical-align: super;\">2</span>"));

        assertThat(subject.htmlFormatDouble(123.567d), is("1.24 × 10<span style=\"vertical-align: super;\">2</span>"));
        assertThat(subject.htmlFormatDouble(-123.567d), is("-1.24 × 10<span style=\"vertical-align: super;\">2</span>"));

        assertThat(subject.htmlFormatDouble(10.0d), is("10<span style=\"vertical-align: super;\">1</span>"));
        assertThat(subject.htmlFormatDouble(-10.0d), is("-1 × 10<span style=\"vertical-align: super;\">1</span>"));

        assertThat(subject.htmlFormatDouble(10.123d), is("1.01 × 10<span style=\"vertical-align: super;\">1</span>"));
        assertThat(subject.htmlFormatDouble(-10.123d), is("-1.01 × 10<span style=\"vertical-align: super;\">1</span>"));

        assertThat(subject.htmlFormatDouble(10.623d), is("1.06 × 10<span style=\"vertical-align: super;\">1</span>"));
        assertThat(subject.htmlFormatDouble(-10.623d), is("-1.06 × 10<span style=\"vertical-align: super;\">1</span>"));

        assertThat(subject.htmlFormatDouble(0.123d), is("0.123"));
        assertThat(subject.htmlFormatDouble(-0.123d), is("-0.123"));

        assertThat(subject.htmlFormatDouble(0.1267d), is("0.127"));
        assertThat(subject.htmlFormatDouble(-0.1267d), is("-0.127"));

        assertThat(subject.htmlFormatDouble(1.32d), is("1.32"));
        assertThat(subject.htmlFormatDouble(-1.32d), is("-1.32"));

        assertThat(subject.htmlFormatDouble(1.356d), is("1.356"));
        assertThat(subject.htmlFormatDouble(-1.356d), is("-1.356"));

        assertThat(subject.htmlFormatDouble(1.3567d), is("1.357"));
        assertThat(subject.htmlFormatDouble(-1.3567d), is("-1.357"));

        assertThat(subject.htmlFormatDouble(9.02d), is("9.02"));
    }

}
