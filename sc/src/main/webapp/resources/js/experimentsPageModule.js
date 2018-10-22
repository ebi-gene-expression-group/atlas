"use strict"

let experimentsPageModule = (function ($) {

  function replaceZeroAndLinkExpDesign(data, type, full) {
    if (data === 0) {
      return `<span title="` + data + `"/>`
    }
    return `<span title="` + data + `">` +
            `<a href="experiments/` + full.experimentAccession + `/Experiment Design" title="View experiment design in Expression Atlas">` + data +
            `</a></span>`
  }

  function withLineBreaks(data) {
    let i, html
    html = ``
    for (i = 0; i < data.length; i += 1) {
      html += data[i] + `<br/>`
    }
    return html
  }

  function formatExperimentType(data, contextPath) {
    if (data === `SINGLE_CELL_RNASEQ_MRNA_BASELINE`) {
      return `<span class="baseline button-rd has-tip" title="Baseline experiment">B</span>`
    }
    return data
  }

  function formatExperimentAccession(data) {
    return `<a href="http://www.ebi.ac.uk/arrayexpress/experiments/` + data + `" title="View in Array Express">` +
            `<span class="icon icon-generic" data-icon="L"></span>` + `</a>`
  }

  function formatLastUpdate(data) {
    return data
  }

  function formatExperimentDescription(data, full) {
    return `<a href="experiments/` + full.experimentAccession + `" title="View in Expression Atlas">` + data + `</a>`
  }

  function formatArrayDesign(data, full) {
    let result = ``
    $(data).each(function (index, elem) {
      result = result + `<a href="http://www.ebi.ac.uk/arrayexpress/arrays/` + elem + `" title="View in Array Express">` + full.arrayDesignNames[index] + `</a><br/>`
    })

    return result
  }

  let asInitVals = []

  function _init(experimentType, kingdom, species, experimentSet, contextPath) {

    /* Sort on the 'alt' tags of images in a column */
    $.extend($.fn.dataTableExt.oSort, {
      // "alt-string-pre": function ( a ) {
      //     return a.match(/alt="(.*?)"/)[1].toLowerCase();
      // },

      "alt-string-asc": function( a, b ) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0))
      },

      "alt-string-desc": function(a,b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0))
      }
    } )

    /* This was taken from datatables examples
         * This sorting function pair will use the * 'title' attribute of en empty span element (or anything else)
         * to sort numerically (for example `<span title="1000000"><span>1'000'000`) */
    $.extend($.fn.dataTableExt.oSort, {
      "title-numeric-pre":function (a) {
        let x = a.match(/title="*(-?[0-9\.]+)/)[1]
        return parseFloat(x)
      },

      "title-numeric-asc":function (a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0))
      },

      "title-numeric-desc":function (a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0))
      }
    })

    /* Sorting by date */
    $.extend($.fn.dataTableExt.oSort, {
      "date-eu-pre": function ( date ) {
        date = date.replace(` `, ``)

        /*date a, format dd/mn/(yyyy) ; (year is optional)*/
        let eu_date = date.split(`-`)

        /*year (optional)*/
        let year = eu_date[2] ? eu_date[2] : 0

        /*month*/
        let month = eu_date[1]
        if (month.length === 1) {
          month = 0 + month
        }

        /*day*/
        let day = eu_date[0]
        if (day.length === 1) {
          day = 0 + day
        }

        return (year + month + day) * 1
      },

      "date-eu-asc": function ( a, b ) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0))
      },

      "date-eu-desc": function ( a, b ) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0))
      }
    } )

    let $experimentsTable = $(`#experiments-table`)
    //reset empty data message to avoid showing "Showing 0 to 0 of 0 entries"
    $experimentsTable.dataTable.defaults.oLanguage.sInfoEmpty = ` `

    let oTable = $experimentsTable.DataTable({
      "autoWidth": false,
      "processing": true,
      "ajax": contextPath + `/json/experiments`,
      "columns":[
        { "title":`Type`, "data":`experimentType`, "className":`center`, "type":`alt-string`, "width": `7%`, "visible": false,
          "render": function (data, type, full) {
            return formatExperimentType(data, contextPath)
          } },
        { "title":`Loaded`, "data":`lastUpdate`, "className":`center nowrap`, 'type': `date-eu`, "width": `6%`,
          "render": function (data, type, full) {
            return formatLastUpdate(data)
          } },
        { "title":`Experiment`, "data":`experimentDescription`, "className":`center`,
          "render": function (data, type, full) {
            return formatExperimentDescription(data, full)
          } },
        { "title":`Assays`, "data":`numberOfAssays`, "className":`center`, "type":`title-numeric`, "width":`5%`,
          "render": function (data, type, full) {
            return replaceZeroAndLinkExpDesign(data, type, full)
          } },
        { "title":`Comparisons`, "data":`numberOfContrasts`, "className":`center`, "type":`title-numeric`, "width":`5%`, "visible": false,
          "render": function (data, type, full) {
            return replaceZeroAndLinkExpDesign(data, full)
          } },
        { "title":`Species`, "data":`species`, "className":`center italic`, "width":`10%`,
          "render": function (data, type, full) {
            return data
          } },
        { "title":`Experimental Variables`, "data":`experimentalFactors`, "className":`center`,
          "render": function (data, type, full) {
            return withLineBreaks(data)
          } },
        { "title":`ArrayExpress`, "data":`experimentAccession`, "className":`center`, "visible": false,
          "render": function (data, type, full) {
            return formatExperimentAccession(data)
          } },
        { "title":`Kingdom`, "data":`kingdom`, "visible": false }
      ],
      "lengthMenu":[
        [10, 25, 50, 100, -1],
        [10, 25, 50, 100, `All`]
      ],
      "language":{
        "search":`Search all columns:`
      }
    })

    $(`#gxaExperimentsTableDescriptionInput`).on(`keyup`, function () {
      oTable
        .columns(2)
        .search(this.value)
        .draw()
    })

    $(`#gxaExperimentsTableSpeciesInput`).on(`keyup`, function () {
      oTable
        .columns(5)
        .search(this.value)
        .draw()
    })

    $(`#gxaExperimentsTableFactorsInput`).on(`keyup`, function () {
      oTable
        .columns(6)
        .search(this.value)
        .draw()
    })

    let $experimentsTableKingdomSelect = $(`#gxaExperimentsTableKingdomSelect`)
    $experimentsTableKingdomSelect.val(kingdom.toLowerCase())
    let $experimentsTableTypeSelect = $(`#gxaExperimentsTableExperimentTypeSelect`)
    $experimentsTableTypeSelect.val(experimentType.toLowerCase())

    /*
         * Filter by experiment type
         */
    let hiddenTypeSelected = $(`#hiddenTypeSelected`).val()
    if(experimentType.toLowerCase() !== ``) {
      hiddenTypeSelected = experimentType.toLowerCase()
    }

    $experimentsTableTypeSelect.change(function () {
      let selected = $experimentsTableTypeSelect.find(`:selected`).val()

      if(hiddenTypeSelected !== selected) {
        hiddenTypeSelected = selected
        $(`#hiddenTypeSelected`).val(selected)
        $experimentsTableTypeSelect.val(hiddenTypeSelected)
      }
      filterByExperimentType(this.value, this)
    })

    $experimentsTableTypeSelect.val(hiddenTypeSelected)
    if(hiddenTypeSelected !== undefined) {
      filterByExperimentType(hiddenTypeSelected, $experimentsTableTypeSelect)
    }

    function filterByExperimentType(value, selectionId) {
      /* same for drop down filter */
      oTable.columns(0)
        .search(value)
        .draw()
    }

    /*
         * Filter by kingdom
         */
    let hiddenKingdomSelected = $(`#hiddenKingdomSelected`).val()
    if(kingdom.toLowerCase() !== ``) {
      hiddenKingdomSelected = kingdom.toLowerCase()
    }

    $experimentsTableKingdomSelect.change(function () {
      let selected = $experimentsTableKingdomSelect.find(`:selected`).val()

      if(hiddenKingdomSelected !== selected) {
        hiddenKingdomSelected = selected
        $(`#hiddenKingdomSelected`).val(selected)
        $(`#gxaExperimentsTableKingdomSelect`).val(hiddenKingdomSelected)
      }
      filterByKingdom()
    })

    $experimentsTableKingdomSelect.val(hiddenKingdomSelected)
    if(hiddenKingdomSelected !== undefined) {
      filterByKingdom()
    }

    function filterByKingdom() {
      if (hiddenKingdomSelected === `plants`) {
        oTable
          .columns(8)
          .search(`plants`)
          .draw()
      }
      else if (hiddenKingdomSelected === `animals-fungi`) {
        oTable
          .columns(8)
          .search(`animals|fungi`, true)
          .draw()
      }
      else if (hiddenKingdomSelected === `animals`) {
        oTable
          .columns(8)
          .search(`animals`, true)
          .draw()
      }
      else if (hiddenKingdomSelected === `fungi`) {
        oTable
          .columns(8)
          .search(`fungi`, true)
          .draw()
      }
      else {
        oTable
          .columns(8)
          .search(``)
          .draw()
      }
    }

    /*
         * Support functions to provide a little bit of 'user friendliness' to the text boxes in
         * the footer
         */
    $experimentsTable.find(`tfoot input`).each(function (i) {
      asInitVals[i] = this.value
    })

    $experimentsTable.find(`tfoot input`).focus(function () {
      if (this.className === `search_init`) {
        this.className = ``
        this.value = ``
      }
    })

    $experimentsTable.find(`tfoot input`).blur(function () {
      if (this.value === ``) {
        this.className = `search_init`
        this.value = asInitVals[$experimentsTable.find(`tfoot input`).index(this)]
      }
    })

    if (species) {
      $(`#gxaExperimentsTableSpeciesInput`).val(species).keyup()
    }

    oTable.search(experimentSet)
  }

  return {
    init: _init
  }

}(jQuery))
