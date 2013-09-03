#!/bin/sh
#BASELINE
TARGET_HOST=http://localhost:9090

curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-513&type=BASELINE&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-599&type=BASELINE&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-26284&type=BASELINE&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-30352&type=BASELINE&private=false"
#DIFFERENTIAL
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-22351&type=DIFFERENTIAL&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-38400&type=DIFFERENTIAL&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-21860&type=DIFFERENTIAL&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-698&type=DIFFERENTIAL&private=false"
#MICROARRAY
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-1066&type=MICROARRAY&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-TABM-51&type=MICROARRAY&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-3779&type=MICROARRAY&private=false"
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MEXP-1276&type=MICROARRAY&private=false"
#MICRORNA
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-TABM-713&type=MICRORNA&private=false"
#TWOCOLOUR
curl -u bennyfio:drink "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-43049&type=TWOCOLOUR&private=false"