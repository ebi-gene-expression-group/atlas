#!/bin/sh

#miRNA annotations
curl "http://localhost:9090/gxa/updateMiRnaAnnotations"

#biomart annotations - biomart dataset names
curl "http://localhost:9090/gxa/updateAnnotations?species=homo%20sapiens"
curl "http://localhost:9090/gxa/updateAnnotations?species=gallus%20gallus"
curl "http://localhost:9090/gxa/updateAnnotations?species=monodelphis%20domestica"
curl "http://localhost:9090/gxa/updateAnnotations?species=ornithorhynchus%20anatinus"
curl "http://localhost:9090/gxa/updateAnnotations?species=gorilla%20gorilla"
curl "http://localhost:9090/gxa/updateAnnotations?species=macaca%20mulatta"
curl "http://localhost:9090/gxa/updateAnnotations?species=mus%20musculus"
curl "http://localhost:9090/gxa/updateAnnotations?species=pan%20troglodytes"
curl "http://localhost:9090/gxa/updateAnnotations?species=pongo%20abelii"
curl "http://localhost:9090/gxa/updateAnnotations?species=drosophila%20melanogaster"
curl "http://localhost:9090/gxa/updateAnnotations?species=arabidopsis%20thaliana"
