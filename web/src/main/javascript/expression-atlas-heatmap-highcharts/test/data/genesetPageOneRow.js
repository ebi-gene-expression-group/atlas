//http://localhost:8080/gxa/widgets/heatmap/baselineAnalytics?geneQuery=%5B%7B%22value%22%3A%22zinc+finger%22%7D%5D&conditionQuery=%5B%5D&species=bos%20taurus&source=ORGANISM_PART
module.exports = {
  actual:
  {
    "config": {
      "atlasHost": "http://localhost:8080",
      "contextRoot": "/gxa",
      "experimentAccession": "",
      "geneQuery": "%5B%7B%22value%22%3A%22zinc+finger%22%7D%5D",
      "conditionQuery": "%5B%5D",
      "accessKey": "",
      "species": "bos taurus",
      "ensemblDB": "",
      "columnType": "",
      "enableEnsemblLauncher": false,
      "showMaPlotButton": true,
      "gseaPlots": null,
      "downloadProfilesURL": "/widgets/heatmap/baselineAnalytics.tsv?geneQuery=%5B%7B%22value%22%3A%22zinc+finger%22%7D%5D&conditionQuery=%5B%5D&species=bos%20taurus&source=ORGANISM_PART",
      "disclaimer": ""
    },
    "columnHeaders": [
      {
        "assayGroupId": "none",
        "factorValue": "brain",
        "factorValueOntologyTermId": "UBERON_0000955"
      },
      {
        "assayGroupId": "none",
        "factorValue": "colon",
        "factorValueOntologyTermId": "UBERON_0001155"
      },
      {
        "assayGroupId": "none",
        "factorValue": "heart",
        "factorValueOntologyTermId": "UBERON_0000948"
      },
      {
        "assayGroupId": "none",
        "factorValue": "kidney",
        "factorValueOntologyTermId": "UBERON_0002113"
      },
      {
        "assayGroupId": "none",
        "factorValue": "liver",
        "factorValueOntologyTermId": "UBERON_0002107"
      },
      {
        "assayGroupId": "none",
        "factorValue": "lung",
        "factorValueOntologyTermId": "UBERON_0002048"
      },
      {
        "assayGroupId": "none",
        "factorValue": "skeletal muscle",
        "factorValueOntologyTermId": "UBERON_0014892"
      },
      {
        "assayGroupId": "none",
        "factorValue": "spleen",
        "factorValueOntologyTermId": "UBERON_0002106"
      },
      {
        "assayGroupId": "none",
        "factorValue": "testis",
        "factorValueOntologyTermId": "UBERON_0000473"
      }
    ],
    "profiles": {
      "searchResultTotal": 1,
      "rows": [
        {
          "id": "E-MTAB-2798",
          "name": "9 Merkin",
          "experimentType": "RNASEQ_MRNA_BASELINE",
          "expressions": [
            {
              "factorName": "brain",
              "color": "#C0C0C0",
              "value": 3,
              "svgPathId": "UBERON_0000955"
            },
            {
              "factorName": "colon",
              "color": "#C0C0C0",
              "value": 3,
              "svgPathId": "UBERON_0001155"
            },
            {
              "factorName": "heart",
              "color": "#C0C0C0",
              "value": 3,
              "svgPathId": "UBERON_0000948"
            },
            {
              "factorName": "kidney",
              "color": "#B7B7C0",
              "value": 4,
              "svgPathId": "UBERON_0002113"
            },
            {
              "factorName": "liver",
              "color": "#A5A5C0",
              "value": 6,
              "svgPathId": "UBERON_0002107"
            },
            {
              "factorName": "lung",
              "color": "#2324DB",
              "value": 22,
              "svgPathId": "UBERON_0002048"
            },
            {
              "factorName": "skeletal muscle",
              "color": "#9C9CC0",
              "value": 7,
              "svgPathId": "UBERON_0014892"
            },
            {
              "factorName": "spleen",
              "color": "#0000FF",
              "value": 28,
              "svgPathId": "UBERON_0002106"
            },
            {
              "factorName": "testis",
              "color": "#6667C0",
              "value": 13,
              "svgPathId": "UBERON_0000473"
            }
          ],
          "serializedFilterFactors": ""
        }
      ]
    },
    "geneSetProfiles": null,
    "jsonCoexpressions": [],
    "anatomogram": {
      "contextRoot": "/gxa",
      "maleAnatomogramFile": "cow.svg",
      "femaleAnatomogramFile": null,
      "brainAnatomogramFile": null,
      "toggleButtonMaleImageTemplate": "/resources/images/male",
      "toggleButtonFemaleImageTemplate": "/resources/images/female",
      "toggleButtonBrainImageTemplate": "/resources/images/brain",
      "species": "bos taurus",
      "allSvgPathIds": [
        "UBERON_0000955",
        "UBERON_0001155",
        "UBERON_0000948",
        "UBERON_0002113",
        "UBERON_0002107",
        "UBERON_0002048",
        "UBERON_0014892",
        "UBERON_0002106",
        "UBERON_0000473"
      ]
    },
    "experiment": null
  },
  expected: {"xAxisCategories":[{"label":"brain","id":"UBERON_0000955","info":{"trackId":""}},{"label":"colon","id":"UBERON_0001155","info":{"trackId":""}},{"label":"heart","id":"UBERON_0000948","info":{"trackId":""}},{"label":"kidney","id":"UBERON_0002113","info":{"trackId":""}},{"label":"liver","id":"UBERON_0002107","info":{"trackId":""}},{"label":"lung","id":"UBERON_0002048","info":{"trackId":""}},{"label":"skeletal muscle","id":"UBERON_0014892","info":{"trackId":""}},{"label":"spleen","id":"UBERON_0002106","info":{"trackId":""}},{"label":"testis","id":"UBERON_0000473","info":{"trackId":""}}],"yAxisCategories":[{"label":"9 Merkin","id":"E-MTAB-2798?geneQuery=%5B%7B%22value%22%3A%22zinc+finger%22%7D%5D","info":{"trackId":""}}],"orderings":{"Default":{"columns":[0,1,2,3,4,5,6,7,8],"rows":[0]},"Alphabetical order":{"columns":[0,1,2,3,4,5,6,7,8],"rows":[0]},"Gene expression rank":{"columns":[7,5,8,6,4,3,0,1,2],"rows":[0]}},"dataSeries":[{"info":{"name":"Below cutoff","colour":"#eaeaea"},"data":[]},{"info":{"name":"Low","colour":"#45affd"},"data":[{"x":0,"y":0,"value":3,"info":{"unit":"FPKM","index":0}},{"x":1,"y":0,"value":3,"info":{"unit":"FPKM","index":0}},{"x":2,"y":0,"value":3,"info":{"unit":"FPKM","index":0}},{"x":3,"y":0,"value":4,"info":{"unit":"FPKM","index":0}},{"x":4,"y":0,"value":6,"info":{"unit":"FPKM","index":0}},{"x":6,"y":0,"value":7,"info":{"unit":"FPKM","index":0}}]},{"info":{"name":"Medium","colour":"#1E74CA"},"data":[{"x":5,"y":0,"value":22,"info":{"unit":"FPKM","index":0}},{"x":7,"y":0,"value":28,"info":{"unit":"FPKM","index":0}},{"x":8,"y":0,"value":13,"info":{"unit":"FPKM","index":0}}]},{"info":{"name":"High","colour":"#024990"},"data":[]}]}
}
