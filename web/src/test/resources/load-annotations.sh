#!/bin/sh

#miRNA annotations
curl -u test:test "http://localhost:9090/gxa/admin/load/mirna"

#biomart annotations - biomart dataset names
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=homo%20sapiens"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=gallus%20gallus"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=monodelphis%20domestica"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=ornithorhynchus%20anatinus"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=gorilla%20gorilla"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=macaca%20mulatta"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=mus%20musculus"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=pan%20troglodytes"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=pongo%20abelii"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=drosophila%20melanogaster"
curl -u test:test "http://localhost:9090/gxa/admin/load/gene-names?species=arabidopsis%20thaliana"
