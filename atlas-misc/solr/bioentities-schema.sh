#!/usr/bin/env bash

HOST="localhost:8983"
CORE="bioentities"

printf "\n\nDelete field type text_en_tight"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field-type":
  {
    "name": "text_en_tight"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\Create field type text_en_tight"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field-type": {
    "name": "text_en_tight",
    "class": "solr.TextField",
    "positionIncrementGap": "100",
    "analyzer" : {
      "tokenizer": {
        "class":"solr.WhitespaceTokenizerFactory"
      },
      "filters":[
        {
          "class":"solr.LowerCaseFilterFactory"
        },
        {
          "class":"solr.EnglishPossessiveFilterFactory"
        },
        {
          "class":"solr.PorterStemFilterFactory"
        }
      ]
    }
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nDelete field bioentity_identifier"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name":"bioentity_identifier" 
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field bioentity_dientifier (lowercase)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"bioentity_identifier",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field bioentity_type"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name":"bioentity_type" 
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field bioentity_type (lowercase)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"bioentity_type",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field property_name"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name":"property_name" 
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field property_name (lowercase)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"property_name",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field property_value"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"property_value" 
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field property_value (text_en_tight)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"property_value",
    "type":"text_en_tight"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nDelete field species"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name":"species"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field species (lowercase)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"species",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema


printf "\n\nDelete field property_weight"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field":
  {
    "name":"property_weight"
  }
}' http://$HOST/solr/$CORE/schema

printf "\n\nCreate field property_weight (int)"
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"property_weight",
    "type":"int"
  }
}' http://$HOST/solr/$CORE/schema

