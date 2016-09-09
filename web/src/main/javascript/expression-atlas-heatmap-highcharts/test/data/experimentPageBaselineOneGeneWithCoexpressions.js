module.exports = {
  actual: {
    "config": {
      "atlasHost": "http://localhost:8080",
      "contextRoot": "/gxa",
      "experimentAccession": "E-MTAB-3028",
      "geneQuery": "%5B%7B%22value%22%3A%22TUBA3%22%7D%5D",
      "conditionQuery": "",
      "accessKey": "",
      "species": "Zea mays",
      "ensemblDB": "plants",
      "columnType": "strain",
      "enableEnsemblLauncher": true,
      "showMaPlotButton": true,
      "gseaPlots": null,
      "downloadProfilesURL": "/experiments/E-MTAB-3028.tsv?accessKey=&serializedFilterFactors=&queryFactorType=STRAIN&rootContext=&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=%5B%7B%22value%22%3A%22TUBA3%22%7D%5D&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5",
      "disclaimer": ""
    },
    "columnHeaders": [
      {
        "assayGroupId": "g2",
        "factorValue": "B73"
    },
      {
        "assayGroupId": "g1",
        "factorValue": "Mo17"
    }
  ],
    "profiles": {
      "searchResultTotal": 1,
      "rows": [
        {
          "id": "AC195340.3_FG001",
          "name": "TUBA3",
          "expressions": [
            {
              "factorName": "B73",
              "color": "#C0C0C0",
              "value": 184,
              "quartiles": {
                "min": 177,
                "lower": 181,
                "median": 184,
                "upper": 189,
                "max": 194
              }
          },
            {
              "factorName": "Mo17",
              "color": "#0000FF",
              "value": 267,
              "quartiles": {
                "min": 250,
                "lower": 259,
                "median": 267,
                "upper": 288,
                "max": 308
              }
          }
        ]
      }
    ]
    },
    "geneSetProfiles": null,
    "jsonCoexpressions": [
      {
        "geneName": "TUBA3",
        "geneId": "AC195340.3_FG001",
        "jsonProfiles": {
          "searchResultTotal": 0,
          "rows": [
            {
              "id": "AC148152.3_FG005",
              "name": "AC148152.3_FG005",
              "expressions": [
                {
                  "factorName": "B73",
                  "color": "#BCBDC0",
                  "value": 5,
                  "quartiles": {
                    "min": 5,
                    "lower": 5,
                    "median": 5,
                    "upper": 6,
                    "max": 7
                  }
              },
                {
                  "factorName": "Mo17",
                  "color": "#7C7CC0",
                  "value": 58,
                  "quartiles": {
                    "min": 56,
                    "lower": 57,
                    "median": 58,
                    "upper": 66,
                    "max": 74
                  }
              }
            ]
          },
            {
              "id": "AC149475.2_FG005",
              "name": "AC149475.2_FG005",
              "expressions": [
                {
                  "factorName": "B73",
                  "color": "#BABAC0",
                  "value": 7,
                  "quartiles": {
                    "min": 4,
                    "lower": 6,
                    "median": 7,
                    "upper": 7,
                    "max": 7
                  }
              },
                {
                  "factorName": "Mo17",
                  "color": "#B8B9C0",
                  "value": 8,
                  "quartiles": {
                    "min": 5,
                    "lower": 6,
                    "median": 8,
                    "upper": 8,
                    "max": 8
                  }
              }
            ]
          }
        ]
        }
    }
  ],
    "anatomogram": null,
    "experiment": {
      "URL": "/experiments/E-MTAB-3028?geneQuery=%5B%7B%22value%22%3A%22TUBA3%22%7D%5D&serializedFilterFactors=",
      "description": "RNA-seq of coding RNA from two inbred lines of maize, B73 and Mo17",
      "species": "Zea mays"
    }
  },
  expected:
{"xAxisCategories":[{"label":"B73","id":"","info":{"trackId":"g2"}},{"label":"Mo17","id":"","info":{"trackId":"g1"}}],"yAxisCategories":[{"label":"TUBA3","id":"AC195340.3_FG001","info":{"trackId":"AC195340.3_FG001"}},{"label":"AC148152.3_FG005","id":"AC148152.3_FG005","info":{"trackId":"AC148152.3_FG005"}},{"label":"AC149475.2_FG005","id":"AC149475.2_FG005","info":{"trackId":"AC149475.2_FG005"}}],"dataSeries":[{"info":{"name":"Below cutoff","colour":"#808080"},"data":[]},{"info":{"name":"Low","colour":"#8cc6ff"},"data":[{"x":0,"y":1,"value":5,"info":{"unit":"","index":1}},{"x":0,"y":2,"value":7,"info":{"unit":"","index":2}}]},{"info":{"name":"Medium","colour":"#0000ff"},"data":[{"x":1,"y":1,"value":58,"info":{"unit":"","index":1}},{"x":1,"y":2,"value":8,"info":{"unit":"","index":2}}]},{"info":{"name":"High","colour":"#0000b3"},"data":[{"x":0,"y":0,"value":184,"info":{"unit":"","index":0}},{"x":1,"y":0,"value":267,"info":{"unit":"","index":0}}]}]}
}
