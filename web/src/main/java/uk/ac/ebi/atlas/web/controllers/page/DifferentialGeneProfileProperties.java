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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;

import javax.inject.Named;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Named("differentialGeneProfileProperties")
@Scope("request")
public class DifferentialGeneProfileProperties {

    private Map<String, DifferentialProfilesList<DifferentialProfile>> experimentToDifferentialProfilesListMap = Maps.newHashMap();

    private String geneQuery;

    private double fdrCutoff;

    public Set<String> getAllExperimentAccessions() {
        return experimentToDifferentialProfilesListMap.keySet();
    }

    public DifferentialProfilesList getDifferentialProfilesListForExperiment(String experimentAccession) {
        return experimentToDifferentialProfilesListMap.get(experimentAccession);
    }

    public DifferentialProfilesList putDifferentialProfilesListForExperiment(String experimentAccession, DifferentialProfilesList differentialProfilesList) {
        return experimentToDifferentialProfilesListMap.put(experimentAccession, differentialProfilesList);
    }

    public void clear() {
        experimentToDifferentialProfilesListMap.clear();
    }

    public double getMaxUpRegulatedExpressionLevel() {
        double maxUpRegulatedExpressionLevel = 0;
        for (DifferentialProfilesList<DifferentialProfile> differentialProfilesList : experimentToDifferentialProfilesListMap.values()) {
            for (DifferentialProfile differentialProfile : differentialProfilesList) {
                maxUpRegulatedExpressionLevel = max(maxUpRegulatedExpressionLevel, differentialProfile.getMaxUpRegulatedExpressionLevel());
            }
        }
        return maxUpRegulatedExpressionLevel == 0 ? Double.NaN : maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel() {
        double minUpRegulatedExpressionLevel = Double.MAX_VALUE;
        for (DifferentialProfilesList<DifferentialProfile> differentialProfilesList : experimentToDifferentialProfilesListMap.values()) {
            for (DifferentialProfile differentialProfile : differentialProfilesList) {
                minUpRegulatedExpressionLevel = min(minUpRegulatedExpressionLevel, differentialProfile.getMinUpRegulatedExpressionLevel());
            }
        }
        return minUpRegulatedExpressionLevel == Double.MAX_VALUE ? Double.NaN : minUpRegulatedExpressionLevel;
    }


    public double getMaxDownRegulatedExpressionLevel() {
        double maxDownRegulatedExpressionLevel = 0;
        for (DifferentialProfilesList<DifferentialProfile> differentialProfilesList : experimentToDifferentialProfilesListMap.values()) {
            for (DifferentialProfile differentialProfile : differentialProfilesList) {
                maxDownRegulatedExpressionLevel = max(maxDownRegulatedExpressionLevel, differentialProfile.getMaxDownRegulatedExpressionLevel());
            }
        }
        return maxDownRegulatedExpressionLevel == 0 ? Double.NaN : maxDownRegulatedExpressionLevel;
    }

    public double getMinDownRegulatedExpressionLevel() {
        double minDownRegulatedExpressionLevel = Double.MAX_VALUE;
        for (DifferentialProfilesList<DifferentialProfile> differentialProfilesList : experimentToDifferentialProfilesListMap.values()) {
            for (DifferentialProfile differentialProfile : differentialProfilesList) {
                minDownRegulatedExpressionLevel = min(minDownRegulatedExpressionLevel, differentialProfile.getMinDownRegulatedExpressionLevel());
            }
        }
        return minDownRegulatedExpressionLevel == Double.MAX_VALUE ? Double.NaN : minDownRegulatedExpressionLevel;
    }

    /*
    * used in bioEntity.jsp
    */
    public List<DifferentialGeneProfileLink> getDifferentialGeneProfileLinks() {

        List<DifferentialGeneProfileLink> differentialGeneProfileLinks = Lists.newArrayList();

        for (String experimentAccession : experimentToDifferentialProfilesListMap.keySet()) {
            DifferentialProfilesList differentialProfilesList = experimentToDifferentialProfilesListMap.get(experimentAccession);
            for (Object item : differentialProfilesList) {
                DifferentialProfile profile = (DifferentialProfile) item;
                for (Object condition : profile.getConditions()) {
                    Contrast contrast = (Contrast) condition;
                    DifferentialGeneProfileLink differentialGeneProfileLink = new DifferentialGeneProfileLink(geneQuery,
                            contrast, experimentAccession, (DifferentialExpression) profile.getExpression(contrast));
                    differentialGeneProfileLinks.add(differentialGeneProfileLink);
                }
            }
        }

        Collections.sort(differentialGeneProfileLinks, new Comparator<DifferentialGeneProfileLink>() {
            @Override
            public int compare(DifferentialGeneProfileLink o1, DifferentialGeneProfileLink o2) {
                return o1.getValue() - o2.getValue() < 0 ? -1 : 1;
            }
        });

        return differentialGeneProfileLinks;
    }

    /*
    * used in bioEntity.jsp
    */
    public int getTotalNumberOfResults() {
        int count = 0;
        for (String experimentAccession : experimentToDifferentialProfilesListMap.keySet()) {
            DifferentialProfilesList differentialProfilesList = experimentToDifferentialProfilesListMap.get(experimentAccession);
            for (Object item : differentialProfilesList) {
                DifferentialProfile profile = (DifferentialProfile) item;
                count += profile.getConditions().size();
            }
        }
        return count;
    }

    public void setGeneQuery(String geneQuery) {
        this.geneQuery = geneQuery;
    }

    public void setFdrCutoff(double fdrCutoff) {
        this.fdrCutoff = fdrCutoff;
    }

    /*
     * used in bioEntity.jsp
     */
    public double getFdrCutoff() {
        return fdrCutoff;
    }
}