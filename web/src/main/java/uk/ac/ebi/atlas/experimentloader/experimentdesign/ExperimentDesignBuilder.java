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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

//ToDo (B) remove the MageTabParser hirarchy and introduce an ExperimentDesignBuilder hierarchy,
//ToDo (B) because it is not the "parsing" of magetab that change depending on the experiment type, but is the way we build the Experiment that changes.
//ToDo (B) Here we should have dependency with PropertyMergeService, not in the MageTabParser
//ToDo (B) The implementing subclasses could be moved to an impl package

public abstract class ExperimentDesignBuilder {

    //ExperimentDesignBuilder withCharacteristics();



}
