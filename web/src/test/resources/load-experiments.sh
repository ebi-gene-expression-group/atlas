#!/bin/sh
#BASELINE
curl "http://localhost:9090/gxa/loadExperiment?accession=E-MTAB-513&type=BASELINE"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-MTAB-599&type=BASELINE"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-GEOD-26284&type=BASELINE"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-GEOD-30352&type=BASELINE"
#DIFFERENTIAL
curl "http://localhost:9090/gxa/loadExperiment?accession=E-GEOD-22351&type=DIFFERENTIAL"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-GEOD-38400&type=DIFFERENTIAL"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-GEOD-21860&type=DIFFERENTIAL"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-MTAB-698&type=DIFFERENTIAL"
#MICROARRAY
curl "http://localhost:9090/gxa/loadExperiment?accession=E-MTAB-1066&type=MICROARRAY"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-TABM-51&type=MICROARRAY"
curl "http://localhost:9090/gxa/loadExperiment?accession=E-TABM-713&type=MICROARRAY"
#TWOCOLOUR
curl "http://localhost:9090/gxa/loadExperiment?accession=E-GEOD-43049&type=TWOCOLOUR"