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

import com.google.common.base.Objects;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AbstractSDRFNode;

public class AssayNode<T extends AbstractSDRFNode> {
    private String name;
    private T sdrfNode;

    public AssayNode(String name, T sdrfNode) {
        this.name = name;
        this.sdrfNode = sdrfNode;
    }

    public String getName() {
        return name;
    }

    public T getSdrfNode() {
        return sdrfNode;
    }

    @Override
    public boolean equals(Object other){
        if (other == null || getClass() != other.getClass()){
            return false;
        }
        AssayNode otherSdrfNode = (AssayNode) other;
        return Objects.equal(name, otherSdrfNode.name);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(name);
    }

    @Override
    public String toString(){
        return "AssayNode[name:" + name + ", sdrfNode:" + sdrfNode + "]";
    }
}
