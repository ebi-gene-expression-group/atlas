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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class AssayGroup implements Iterable<String>{

    private String id;

    private Set<String> assayAccessions;

    public AssayGroup(String id, String... assayAccessions) {
        checkArgument(StringUtils.isNotBlank(id));

        this.id = id;
        this.assayAccessions = Sets.newHashSet(assayAccessions);
    }

    @Override
    public Iterator<String> iterator() {
        return assayAccessions.iterator();
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {return Objects.hash(id, assayAccessions);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final AssayGroup other = (AssayGroup) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.assayAccessions, other.assayAccessions);
    }
}
