#!/bin/sh
#BASELINE
TARGET_HOST=http://localhost:9090

curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-513&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-599&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-26284&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-30352&private=false"
#DIFFERENTIAL
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-22351&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-38400&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-21860&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-698&private=false"
#MICROARRAY
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MTAB-1066&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-TABM-51&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-3779&private=false"
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-MEXP-1276&private=false"
#MICRORNA
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-TABM-713&private=false"
#TWOCOLOUR
curl -u test:test "$TARGET_HOST/gxa/admin/loadExperiment?accession=E-GEOD-43049&private=false"