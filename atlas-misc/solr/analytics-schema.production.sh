#!/usr/bin/env bash

HOST="localhost:8983"
CORE="analytics"

printf "\n\nDelete field type text_en_tight"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field-type":
  {
    "name": "text_en_tight"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\Create field type text_en_tight"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field-type" : {
     "name":"text_en_tight",
     "class":"solr.TextField",
     "positionIncrementGap":"100",
     "analyzer" : {
        "tokenizer":{
           "class":"solr.WhitespaceTokenizerFactory" },
        "filters":[{
           "class":"solr.LowerCaseFilterFactory"},
           {
           "class":"solr.EnglishPossessiveFilterFactory"},
           {
           "class":"solr.PorterStemFilterFactory"}]}}
}' http://$HOST/solr/$CORE/schema

echo "Delete field bioentity_identifier"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "bioentity_identifier"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field bioentity_identifier (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "bioentity_identifier",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field species"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "species"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field species (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "species",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field kingdom"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "kingdom"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field kingdom (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "kingdom",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field experiment_accession"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "experiment_accession"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field experiment_accession (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "experiment_accession",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field experiment_type"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "experiment_type"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field experiment_type (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "experiment_type",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field default_query_factor_type"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "default_query_factor_type"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field default_query_factor_type (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "default_query_factor_type",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field assay_group_id"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name": "assay_group_id"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field assay_group_id (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "assay_group_id",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field contrast_id"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "contrast_id"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field contrast_id (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "contrast_id",
    "type": "string"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field factors"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "factors"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field factors (string, multiValued)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "factors",
    "type": "string",
    "multiValued": true,
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field expression_level"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "expression_level"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field expression_level (double)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "expression_level",
    "type": "double"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field num_replicates"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "num_replicates"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field num_replicates (int)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "num_replicates",
    "type": "int",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field fold_change"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "fold_change"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field fold_change (double)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "fold_change",
    "type": "double"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field p_value"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "p_value"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field p_value (double)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "p_value",
    "type": "double"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field t_statistic"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "t_statistic"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field t_statistic (double)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "t_statistic",
    "type": "double"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field regulation"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name": "regulation"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field regulation (string)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "regulation",
    "type": "string",
    "docValues": true
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete dynamic field rule keyword_*"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-dynamic-field": {
     "name": "keyword_*"}
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-dynamic-field": {
     "name": "keyword_*",
     "type": "lowercase",
     "multiValued": true}
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field identifier_search"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name": "identifier_search"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field identifier_search (text_en)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "identifier_search",
    "type": "text_en",
    "stored": false
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field conditions_search"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name": "conditions_search"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field conditions_search (text_en)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name": "conditions_search",
    "type": "text_en_tight",
    "stored": false
  }
}' http://$HOST/solr/$CORE/schema
