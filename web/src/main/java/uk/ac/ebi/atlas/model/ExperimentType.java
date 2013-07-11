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


import uk.ac.ebi.atlas.expdesign.ExperimentDesignWriter;
import uk.ac.ebi.atlas.expdesign.ExperimentDesignWriterFactory;

public enum ExperimentType {

    BASELINE(null) {
        @Override
        public ExperimentDesignWriter createExperimentDesignWriter(ExperimentDesignWriterFactory factory) {
            return factory.getRnaseqWriter();
        }
    }, DIFFERENTIAL(null) {
        @Override
        public ExperimentDesignWriter createExperimentDesignWriter(ExperimentDesignWriterFactory factory) {
            return factory.getRnaseqWriter();
        }
    }, MICROARRAY(null) {
        @Override
        public ExperimentDesignWriter createExperimentDesignWriter(ExperimentDesignWriterFactory factory) {
            return factory.getMicroarrayWriter();
        }
    }, TWOCOLOUR(MICROARRAY) {
        @Override
        public ExperimentDesignWriter createExperimentDesignWriter(ExperimentDesignWriterFactory factory) {
            return factory.getMicroarrayWriter();
        }
    }, MICRORNA(MICROARRAY) {
        @Override
        public ExperimentDesignWriter createExperimentDesignWriter(ExperimentDesignWriterFactory factory) {
            return factory.getMicroarrayWriter();
        }
    };

    private ExperimentType parent;

    private ExperimentType(ExperimentType parent) {
        this.parent = parent;
    }

    public boolean isMicroarray() {
        return getParent().equals(MICROARRAY);
    }

    public boolean isBaseline() {
        return equals(BASELINE);
    }

    public boolean isMicroRna() {
        return equals(MICRORNA);
    }

    public boolean isDifferential() {
        return equals(DIFFERENTIAL);
    }

    public boolean isTwoColour() {
        return equals(TWOCOLOUR);
    }

    public ExperimentType getParent() {
        return parent == null ? this : parent;
    }

    public abstract ExperimentDesignWriter createExperimentDesignWriter(ExperimentDesignWriterFactory factory);
}
