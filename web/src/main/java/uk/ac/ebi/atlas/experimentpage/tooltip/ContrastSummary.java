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

package uk.ac.ebi.atlas.experimentpage.tooltip;

import java.util.Iterator;
import java.util.SortedSet;

@SuppressWarnings("UnusedDeclaration")  //fields serialized by Gson
public class ContrastSummary implements Iterable<AssayProperty> {

    private final SortedSet<AssayProperty> properties;
    private final String experimentDescription;
    private final String contrastDescription;
    private final int testReplicates;
    private final int referenceReplicates;

    public ContrastSummary(String experimentDescription, String contrastDescription, int testReplicates, int referenceReplicates, SortedSet<AssayProperty> properties) {
        this.testReplicates = testReplicates;
        this.referenceReplicates = referenceReplicates;
        this.properties = properties;
        this.experimentDescription = experimentDescription;
        this.contrastDescription = contrastDescription;
    }

    @Override
    public Iterator<AssayProperty> iterator() {
        return properties.iterator();
    }
}