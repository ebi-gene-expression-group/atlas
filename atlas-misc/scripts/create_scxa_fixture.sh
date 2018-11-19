#!/usr/bin/env bash
# Set variables HOST, PORT, DATABASE and USER for your DB connection and choose the experiment accession from which to
# create a fixture in EXP_ID. E.g.
# HOST=localhost PORT=5432 DATABASE=gxpatlasloc USER=atlas3dev EXP_ID=E-MTAB-5061 ./create_scxa_fixture.sh
#
# It will create four files corresponding to each of the tables. The first 400 rows are regular genes, the next 100 are
# marker genes.

echo "SELECT * FROM scxa_analytics AS analytics
  LEFT JOIN scxa_tsne AS tsne
    ON tsne.experiment_accession=analytics.experiment_accession AND tsne.cell_id=analytics.cell_id
  LEFT JOIN scxa_cell_clusters AS clusters
    ON clusters.experiment_accession=analytics.experiment_accession AND clusters.cell_id=analytics.cell_id
  LEFT JOIN scxa_marker_genes AS marker_genes
    ON marker_genes.experiment_accession=analytics.experiment_accession AND marker_genes.gene_id=analytics.gene_id

  WHERE analytics.experiment_accession = '${EXP_ID}' AND marker_genes.marker_probability <= 0.05

  ORDER BY RANDOM()
  LIMIT 100;" | \
  psql -A -t -F"," -h $HOST -p $PORT -d $DATABASE -U $USER > tmp.csv

sed -E "s/^(.*),(.*),(.*),(.*),.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*$/INSERT INTO scxa_analytics(experiment_accession, gene_id, cell_id, expression_level) VALUES ('\1', '\2', '\3', \4);/" tmp.csv | uniq > analytics.sql

sed -E "s/^.*,.*,.*,.*,(.*),(.*),(.*),(.*),(.*),.*,.*,.*,.*,.*,.*,.*,.*,.*$/INSERT INTO scxa_tsne(experiment_accession, cell_id, x, y, perplexity) VALUES ('\1', '\2', \3, \4, \5);/" tmp.csv | uniq | grep -v ", ," > tsne.sql

sed -E "s/^.*,.*,.*,.*,.*,.*,.*,.*,.*,(.*),(.*),(.*),(.*),.*,.*,.*,.*,.*$/INSERT INTO scxa_cell_clusters(experiment_accession, cell_id, k, cluster_id) VALUES ('\1', '\2', \3, \4);/" tmp.csv | uniq | grep -v ", ," > cell_clusters.sql

sed -E "s/^.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,(.*),(.*),(.*),(.*),(.*)$/INSERT INTO scxa_marker_genes(experiment_accession, gene_id, k, cluster_id, marker_probability) VALUES ('\1', '\2', \3, \4, \5);/" tmp.csv | uniq | grep -v ", ," > marker_genes.sql

rm tmp.csv



echo "SELECT * FROM scxa_analytics AS analytics
  LEFT JOIN scxa_tsne AS tsne
    ON tsne.experiment_accession=analytics.experiment_accession AND tsne.cell_id=analytics.cell_id
  LEFT JOIN scxa_cell_clusters AS clusters
    ON clusters.experiment_accession=analytics.experiment_accession AND clusters.cell_id=analytics.cell_id
  LEFT JOIN scxa_marker_genes AS marker_genes
    ON marker_genes.experiment_accession=analytics.experiment_accession AND marker_genes.gene_id=analytics.gene_id

  WHERE analytics.experiment_accession = '${EXP_ID}' AND (marker_genes.marker_probability > 0.05 OR marker_genes.marker_probability IS NULL)

  ORDER BY RANDOM()
  LIMIT 400;" | \
  psql -A -t -F"," -h $HOST -p $PORT -d $DATABASE -U $USER > tmp.csv

sed -E "s/^(.*),(.*),(.*),(.*),.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*$/INSERT INTO scxa_analytics(experiment_accession, gene_id, cell_id, expression_level) VALUES ('\1', '\2', '\3', \4);/" tmp.csv | uniq >> analytics.sql

sed -E "s/^.*,.*,.*,.*,(.*),(.*),(.*),(.*),(.*),.*,.*,.*,.*,.*,.*,.*,.*,.*$/INSERT INTO scxa_tsne(experiment_accession, cell_id, x, y, perplexity) VALUES ('\1', '\2', \3, \4, \5);/" tmp.csv | uniq | grep -v ", ," >> tsne.sql

sed -E "s/^.*,.*,.*,.*,.*,.*,.*,.*,.*,(.*),(.*),(.*),(.*),.*,.*,.*,.*,.*$/INSERT INTO scxa_cell_clusters(experiment_accession, cell_id, k, cluster_id) VALUES ('\1', '\2', \3, \4);/" tmp.csv | uniq | grep -v ", ," >> cell_clusters.sql

sed -E "s/^.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,.*,(.*),(.*),(.*),(.*),(.*)$/INSERT INTO scxa_marker_genes(experiment_accession, gene_id, k, cluster_id, marker_probability) VALUES ('\1', '\2', \3, \4, \5);/" tmp.csv | uniq | grep -v ", ," >> marker_genes.sql

rm tmp.csv
