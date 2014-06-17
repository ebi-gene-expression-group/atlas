var TranscriptPopup = (function ($) {

    var $transcript = $('#transcript-breakdown');

    // init tooltips on the actual popup itself
    $('#transcript-breakdown-geneid').tooltip();
    $('#transcript-breakdown-title-help').tooltip();

    function buildPlotData(transcriptRates) {
        var data = [],
            index = 0;
        $.each(transcriptRates, function (key, value) {
            data[index++] = {label:key, data:Math.abs(value.toFixed(1)), color:(value < 0 ? 'white' : undefined)};
        });
        return data;
    }

    function paintPieChart(plotData, geneId, ensemblSpecies) {

        $transcript.css('position', 'absolute').css('left', '-5000px');
        $transcript.show();

        $.plot('#transcripts-pie', plotData, {
            series:{
                pie:{stroke:{
                    color:"#d3d3d3"
                },
                    show:true
                }
            },
            legend:{
                show:true,
                labelFormatter:function (label) {
                    return label === "Others" ? "Others" :
                        "<a class='transcriptid' href='http://www.ensembl.org/" + ensemblSpecies + "/Transcript/Summary?g=" + geneId + ";t="
                        + label + "' target='_blank'" + "title='View transcript in Ensembl'" + ">" +
                        label + "</a>";
                }
            }
        });

        //next lines are required because the div is configured to stay in an invisible position (position:absolute; left:-5000px)
        //in order to make it invisible during the show-up of fancybox
        //all of this is required because of IE8 :( . It doesn' t allow painting canvas in a hidden div, so we need to first show the div, then paint in it, then reposition it, then fancybox it...
        $transcript.hide();
        $transcript.css('position', 'relative')
            .css('left', '0px');

    }

    function showTranscriptBreakdownFancyBox() {
        $.fancybox({
                href:'#transcript-breakdown',
                padding:0,
                openEffect:'elastic',
                closeEffect:'elastic',
                helpers:{
                    overlay:{
                        locked:false
                    }
                }
            }
        );
    }

    //NB: ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
    var display = function display(contextRoot, experimentAccession, geneId, geneName, factorType, factorValue, selectedFilterFactorsJson, ensemblSpecies) {
        $.ajax({
            url: contextRoot + "/json/transcripts/" + experimentAccession,
            type: "GET",
            data: {
                'geneId': geneId,
                'factorType': factorType,
                'factorValue': factorValue,
                'selectedFilterFactorsJson': JSON.stringify(selectedFilterFactorsJson)
            },
            datatype: 'json',
            success: function (data) {
                var totalCount = data.totalTranscriptsCount,
                    expressedCount = data.expressedTranscriptsCount,
                    plotData = buildPlotData(data.transcriptExpressions);

                paintPieChart(plotData, geneId, ensemblSpecies);

                showTranscriptBreakdownFancyBox();

                var s = '';
                if (totalCount > 1) {
                    s = 's';
                }

                var is = 'is';
                if (totalCount > 1) {
                    is = 'are';
                }

                $('#transcript-breakdown-title').html("Expression Level Breakdown for " +
                    "<a id='transcript-breakdown-geneid' href='http://www.ensembl.org/" + ensemblSpecies + "/Gene/Summary?g=" + geneId +
                    "' target='_blank'" + "title='View gene in Ensembl'" + ">" + geneName + "</a> in " + factorValue +
                    "<br/>(" + expressedCount + " out of " + totalCount + " transcript" + s +
                    " " + is + " expressed):");

            }
        }).fail(function (data) {
            console.error("ERROR:  " + data);
        });
    };

    return {
        display: display
    }

})(jQuery);
