#!/usr/bin/env bash

HOST="localhost:8983"
CORE="bioentities"

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"bioentity_identifier" 
  }
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"bioentity_identifier",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema




curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"bioentity_type" 
  }
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"bioentity_type",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema




curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"property_name" 
  }
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"property_name",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema





curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"property_value" 
  }
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"property_value",
    "type":"text_en"
  }
}' http://$HOST/solr/$CORE/schema




curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"species" 
  }
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"species",
    "type":"lowercase"
  }
}' http://$HOST/solr/$CORE/schema




curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" :
  {
    "name":"species_suggester_filter" 
  }
}' http://$HOST/solr/$CORE/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":
  {
    "name":"species_suggester_filter",
    "type":"suggesterFilterTextField"
  }
}' http://$HOST/solr/$CORE/schema

