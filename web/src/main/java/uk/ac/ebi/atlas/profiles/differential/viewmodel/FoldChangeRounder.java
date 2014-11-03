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

package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.apache.commons.math.util.MathUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.text.NumberFormat;

@Named
@Scope("singleton")
public class FoldChangeRounder {

    private final NumberFormat format1Dp = NumberFormat.getNumberInstance();

    public FoldChangeRounder() {
        format1Dp.setGroupingUsed(false);
        format1Dp.setMaximumFractionDigits(1);
    }

    public String format(double number) {
        return format1Dp.format(number);
    }

    public double round(double number) {
        return MathUtils.round(number, 1);
    }

}