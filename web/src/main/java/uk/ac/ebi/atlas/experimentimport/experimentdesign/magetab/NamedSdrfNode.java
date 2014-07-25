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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import com.google.common.base.Objects;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AbstractSDRFNode;

public class NamedSdrfNode<T extends AbstractSDRFNode> {
    private final int channel;
    private String name;
    private T sdrfNode;

    public NamedSdrfNode(String name, T sdrfNode) {
        this(name, sdrfNode, 1);
    }

    public NamedSdrfNode(String name, T sdrfNode, int channel) {
        this.name = name;
        this.sdrfNode = sdrfNode;
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public T getSdrfNode() {
        return sdrfNode;
    }

    // used to determine factor value in multichannel (ie: Two Colour) experiments
    public int getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object other){
        if (other == null || getClass() != other.getClass()){
            return false;
        }
        NamedSdrfNode otherSdrfNode = (NamedSdrfNode) other;
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
