function hideGeneDistribution(img, isFast) {
    $('#gene-distribution').hide(isFast ? null : 'slow');
    $("#display-chart").tooltip({content:"Display gene distribution"});
}

function displayGeneDistribution(img, isFast) {
    $('#gene-distribution').show(isFast ? null : 'slow');
    $("#display-chart").tooltip({content:"Hide gene distribution"});
}

function hideOrDisplayGeneDistribution(isFast) {
    var isDisplayEnabled = $("#prefForm #displayGeneDistribution").val();
    if (isDisplayEnabled == "true") {
        displayGeneDistribution(this, isFast);
    } else {
        hideGeneDistribution(this, isFast);
    }

}

function initBarChartButton(){

    $("#display-chart").button().click(function () {

        var isDisplayEnabled = $("#prefForm #displayGeneDistribution").val();
        if (isDisplayEnabled == "true") {
            $("#prefForm #displayGeneDistribution").val("false");
        } else {
            $("#prefForm #displayGeneDistribution").val("true");
        }

        hideOrDisplayGeneDistribution(false);

        return false;
    }).tooltip();

}

function loadSliderAndPlot(cutoff, experimentAccession, organismParts) {

    var op = "";
    if (organismParts) {
        op = "?" + organismParts;
    }

    function buildLegendaText(){
        return "Y = number of genes expressed above the given FPKM cutoff "
            + (organismParts? "for the selected organism parts" : "in any organism part");
    }

    function nearestScaledCutoff(cutoff) {
        if (cutoff >= 1) {
            // Remove decimal places and replace all but first digit with zeros.
            cutoff = Math.floor(cutoff);

            var x = Math.pow(10, cutoff.toString().length - 1);
            return (Math.floor(cutoff / x) * x).toFixed(0);

        } else {

            // val is somewhere from 0.1 to 0.9999...
            return cutoff == 0 ? "0" : cutoff.toFixed(1);
        }
    }


    function getNthScaledCutoff(position, fractionalDigits) {

        if (position == 0) {
            return "0";
        }

        var remainder = position % 9;

        //The next if - else is fine for a starting step of 0.1.
        //For a starting step of 0.01 the subtracted values must change to -2 and -3
        if (remainder != 0) {
            var power = Math.floor(position / 9) - fractionalDigits;
            var retVal = Math.pow(10, power) * remainder;
        } else {
            var power = Math.floor(position / 9) - (fractionalDigits + 1);
            var retVal = Math.pow(10, power) * 9;
        }

        if (retVal < 1) {
            retVal = retVal.toFixed(fractionalDigits);
        } else {
            retVal = retVal.toFixed(fractionalDigits - 1);
        }
        return retVal;
    }

    function magnifiedValue(value) {
        if (value >= 1000000) {
            return value / 1000000 + "M";
        }
        if (value >= 1000) {
            return value / 1000 + "K";
        }
        return value;
    }

    function plotCutoffBarChart(data, magnifiedScaledCutoffs) {
        return $.plot($("#gene-distribution"), [ data ], {
            series:{
                highlightColor:"red",

                label:buildLegendaText(),

                bars:{
                    show:true,
                    barWidth:.8
                }
            },
            xaxis:{
                tickLength:3,
                ticks:magnifiedScaledCutoffs
            },
            grid:{
                borderColor:"#CDCDCD",
                borderWidth:1,
                hoverable:true,
                clickable:true
            }
        });
    }

    $.getJSON("json/barchart/" + experimentAccession + op, function (data) {

        //this is required because if you load the plot when the div is hidden
        //and then you display the div later the plot Y axis will be overlapping the Y ticks
        displayGeneDistribution(this,true);

        var keys = Object.keys(data);
        var scaledCutoffTicks = [];
        var dataArray = [];

        for (var i = 0; i < keys.length; i++) {
            if (keys[i] > 0 && keys[i] < 1) {
                scaledCutoffTicks.push(keys[i]);
            } else {
                scaledCutoffTicks.push(Math.round(keys[i]));
            }
            dataArray.push([i, data[keys[i]]]);
        }


        var ticksMap = [];

        $.each(scaledCutoffTicks, function (index, scaledCutoff) {
            ticksMap.push([index, index % 2 === 0 ? magnifiedValue(scaledCutoff).toString() : ""]);
        })

        var genesByCutoffPlot = plotCutoffBarChart(dataArray, ticksMap);

        hideOrDisplayGeneDistribution(true);

        function showTooltip(x, y, contents) {
            $('<div id="barChartTooltip">' + contents + '</div>').css({
                position:'absolute',
                display:'none',
                top:y - 25,
                left:x - 6,
                border:'2px solid rgb(238,195,46)',
                'border-radius':'4px',
                padding:'2px',
                'font-family':'Verdana, Helvetica, Arial, sans-serif',
                'font-size':'smaller',
                'background-color':'rgb(249,232,176)',
                opacity:1
            }).appendTo("body").fadeIn(150);
        }

        var previousPoint = null;
        $("#gene-distribution")
            .bind("plothover",function (event, pos, item) {
                $("#x").text(pos.x.toFixed(2));
                $("#y").text(pos.y.toFixed(2));

                if (item) {
                    if (previousPoint != item.datapoint) {
                        previousPoint = item.datapoint;

                        $("#barChartTooltip").remove();
                        var content = item.datapoint[1].toFixed(0);

                        //now show tooltip
                        showTooltip(item.pageX, item.pageY, content);
                    }
                }
                else {
                    $("#barChartTooltip").remove();
                    previousPoint = null;
                }


            }).bind("plotclick", function (event, pos, item) {

                if (item) {
                    $("#cutoff").val(getNthScaledCutoff(item.datapoint[0], 1));
                    $("form#prefForm").submit();
                }
            });


        var scaledCutoff = nearestScaledCutoff(cutoff);

        var scaledCutoffPosition = function () {
            for (var i = 0; i < scaledCutoffTicks.length; i++) {
                if (scaledCutoffTicks[i] == scaledCutoff) {
                    return i;
                }
            }//otherwise we are out of scale... and we position the slider on the last tick
            return scaledCutoffTicks.length - 1;
        }();

        genesByCutoffPlot.highlight(0, scaledCutoffPosition);

        $("#slider-range-max").slider({
            range:"max",
            min:0,
            max:scaledCutoffTicks.length - 1,

            value:scaledCutoffPosition,

            slide:function (event, ui) {
                genesByCutoffPlot.unhighlight();
                genesByCutoffPlot.highlight(0, ui.value);
                var scaledCutoff = getNthScaledCutoff(ui.value, 1);
                $("#cutoff").val(scaledCutoff);
            },
            stop:function (event, ui) {
                $("form#prefForm").submit();
            }
        });






    });



}