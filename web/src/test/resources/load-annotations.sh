#!/bin/sh

#miRNA annotations
curl "http://localhost:9090/gxa/updateMiRnaAnnotations"

#biomart annotations - biomart dataset names
curl "http://localhost:9090/gxa/updateAnnotations?species=homo sapiens"
curl "http://localhost:9090/gxa/updateAnnotations?species=gallus gallus"
curl "http://localhost:9090/gxa/updateAnnotations?species=monodelphis domestica"
curl "http://localhost:9090/gxa/updateAnnotations?species=ornithorhynchus anatinus"
curl "http://localhost:9090/gxa/updateAnnotations?species=gorilla gorilla"
curl "http://localhost:9090/gxa/updateAnnotations?species=macaca mulatta"
curl "http://localhost:9090/gxa/updateAnnotations?species=mus musculus"
curl "http://localhost:9090/gxa/updateAnnotations?species=pan troglodytes"
curl "http://localhost:9090/gxa/updateAnnotations?species=pongo abelii"
curl "http://localhost:9090/gxa/updateAnnotations?species=drosophila melanogaster"
curl "http://localhost:9090/gxa/updateAnnotations?species=arabidopsis thaliana"
