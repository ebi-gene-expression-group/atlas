/*global $,console: false */

var geneDistribution = (function ($) {

    //MagnifiedScale: object constructor and function prototypes

    /*
     fractionalDigits determines the minimal scale granularity,
     1 digit means scale starts from 0.0, 0.1, ... 0.9
     2 digits means scale starts from 0.01, 0.02, ... 0.09, 0.1 ... 0.9
     */
    function MagnifiedScale(fractionalDigits) {
        "use strict";

        this.fractionalDigits = 'undefined' === typeof fractionalDigits ? 1 : fractionalDigits;

        if (!(this instanceof MagnifiedScale)) {
            return new MagnifiedScale();
        }
    }

    MagnifiedScale.prototype.getNearestScaleValue = function (cutoff) {
        "use strict";

        if (cutoff >= 1) {
            // Remove decimal places and replace all but first digit with zeros.
            cutoff = Math.floor(cutoff);

            var x = Math.pow(10, cutoff.toString().length - 1);
            return (Math.floor(cutoff / x) * x).toFixed(0);

        }

        // val is somewhere from 0.1 to 0.9999...
        return cutoff === 0 ? "0" : cutoff.toFixed(1);
    };

    MagnifiedScale.prototype.toString = function (value) {
        "use strict";

        if (value >= 1000000) {
            return value / 1000000 + "M";
        }
        if (value >= 1000) {
            return value / 1000 + "K";
        }
        return value.toString();
    };

    MagnifiedScale.prototype.getNthScaleValue = function (atPosition) {
        "use strict";

        var remainder = atPosition % 9, scaleValue, power;

        if (atPosition === 0) {
            return "0";
        }

        //The next if - else is fine for a starting step of 0.1.
        //For a starting step of 0.01 the subtracted values must change to -2 and -3
        if (remainder !== 0) {
            power = Math.floor(atPosition / 9) - this.fractionalDigits;
            scaleValue = Math.pow(10, power) * remainder;
        } else {
            power = Math.floor(atPosition / 9) - (this.fractionalDigits + 1);
            scaleValue = Math.pow(10, power) * 9;
        }

        if (scaleValue < 1) {
            scaleValue = scaleValue.toFixed(this.fractionalDigits);
        } else {
            scaleValue = scaleValue.toFixed(this.fractionalDigits - 1);
        }
        return scaleValue;
    };


    //---------------------------------------------------


    function hideGeneDistribution(isFast) {
        "use strict";

        $("#gxaGeneDistributionPanel").hide(isFast ? null : "slow");
        $("#gxaDisplayChart").tooltip({
            tooltipClass: "gxaHelpTooltip",
            content: "Display gene distribution"
        });
    }

    function displayGeneDistribution(isFast) {
        "use strict";

        $("#gxaGeneDistributionPanel").show(isFast ? null : "slow");
        $("#gxaDisplayChart").tooltip({
            tooltipClass: "gxaHelpTooltip",
            content: "Hide gene distribution"
        });
    }


    function hideOrDisplayGeneDistribution(isFast) {

        "use strict";

        var isDisplayEnabled = $("#prefForm #displayGeneDistribution").val();

        if (isDisplayEnabled === "true") {
            displayGeneDistribution(isFast);
        } else {
            hideGeneDistribution(isFast);
        }
    }


    function initBarChartButton() {

        "use strict";

        $("#gxaDisplayChart").button().click(function () {

            var isDisplayEnabled = $("#prefForm #displayGeneDistribution").val();
            if (isDisplayEnabled === "true") {
                $("#prefForm #displayGeneDistribution").val("false");
            } else {
                $("#prefForm #displayGeneDistribution").val("true");
            }

            hideOrDisplayGeneDistribution(false);

            return false;
        }).tooltip();

    }

    function buildLegendText(isAnyQueryFactorValueSelected) {
        "use strict";
        return "Y = number of genes expressed above the given expression cutoff " +
            (isAnyQueryFactorValueSelected ? "for the selected experimental variables" : "in any experimental variable");
    }

    function buildProteomicsLegendText(isAnyQueryFactorValueSelected) {
        "use strict";
        return "Y = number of genes expressed above the given expression level cutoff " +
            (isAnyQueryFactorValueSelected ? "for the selected experimental variables" : "in any experimental variable");
    }

    function plotCutoffBarChart(label, data, magnifiedScaledCutoffs) {
        "use strict";

        return $.plot($("#gene-distribution"), [ data ], {
            series: {
                //highlightColor : "rgb(240,205,95)",

                label: label,

                bars: {
                    show: true,
                    barWidth: 0.8
                }
            },
            xaxis: {
                tickLength: 3,
                ticks: magnifiedScaledCutoffs
            },
            grid: {
                borderColor: "#CDCDCD",
                borderWidth: 1,
                hoverable: true,
                clickable: true
            },
            legend: {
                show: true,
                position: "ne"
            }
        });
    }

    function showBarChartTooltip(x, y, contents) {
        "use strict";

        $("#gxaBarChartTooltip").text(contents).css({
            display: 'block',
            position: 'absolute',
            'z-index': 1,
            top: y - 280,
            left: x - 292 - contents.length / 2
        });
    }

    function loadSliderAndPlot(cutoff, experimentAccession, selectedQueryFactorValues, queryFactorType, serializeFilterFactors, accesskey, buildLegendText) {
        "use strict";

        $.ajax({
            url: "json/barchart/" + experimentAccession,
            data: {queryFactorValues: selectedQueryFactorValues,
                queryFactorType: queryFactorType,
                serializedFilterFactors: serializeFilterFactors,
                accesskey: accesskey},
            datatype: 'json',
            success: function (data) {

                var magnifiedScale = new MagnifiedScale(1),
                    previousPoint = null,
                    scaledCutoff = magnifiedScale.getNearestScaleValue(cutoff),
                    keys = $.map(data, function (value, key) {
                        return key;
                    }),
                    scaledCutoffTicks = [],
                    dataArray = [];

                //this is required because if you load the plot when the div is hidden
                //and then you display the div later the plot Y axis will be overlapping the Y ticks
                displayGeneDistribution(true);

                for (var i = 0; i < keys.length; i++) {
                    dataArray.push([i, data[keys[i]]]);
                    var scaledCutoffTickValue = parseFloat(keys[i]);
                    if (scaledCutoffTickValue === 0 || scaledCutoffTickValue >= 1) {
                        scaledCutoffTickValue = Math.round(scaledCutoffTickValue);
                    }
                    scaledCutoffTicks.push(scaledCutoffTickValue.toString());

                }

                var ticksMap = [];

                $.each(scaledCutoffTicks, function (index, scaledCutoff) {
                        ticksMap.push([index, index % 2 === 0 ? magnifiedScale.toString(scaledCutoff) : ""]);
                    }
                );

                var genesByCutoffPlot = plotCutoffBarChart(buildLegendText(selectedQueryFactorValues), dataArray, ticksMap);

                hideOrDisplayGeneDistribution(true);

                $("#gene-distribution")
                    .bind("plothover", function (event, pos, item) {
                        $("#x").text(pos.x.toFixed(2));
                        $("#y").text(pos.y.toFixed(2));

                        if (item) {
                            if (previousPoint !== item.datapoint) {
                                previousPoint = item.datapoint;

                                showBarChartTooltip(item.pageX, item.pageY, item.datapoint[1].toFixed(0));
                            }
                        }
                        else {
                            $("#gxaBarChartTooltip").css({display: 'none'});
                        }


                    }).bind("plotclick", function (event, pos, item) {

                        if (item) {
                            $("#cutoff").val(magnifiedScale.getNthScaleValue(item.datapoint[0]));
                            $("form#prefForm").submit();
                        }
                    });

                var scaledCutoffPosition = function () {
                    for (var i = 0; i < scaledCutoffTicks.length; i++) {
                        if (scaledCutoffTicks[i] === scaledCutoff) {
                            return i;
                        }
                    }//otherwise we are out of scale... and we position the slider on the last tick
                    return scaledCutoffTicks.length - 1;
                }();

                genesByCutoffPlot.highlight(0, scaledCutoffPosition);

                $("#slider-range-max").slider({
                    range: "max",
                    min: 0,
                    max: scaledCutoffTicks.length - 1,
                    value: scaledCutoffPosition,
                    slide: function (event, ui) {
                        genesByCutoffPlot.unhighlight();
                        genesByCutoffPlot.highlight(0, ui.value);
                        var scaledCutoff = magnifiedScale.getNthScaleValue(ui.value);
                        $("#cutoff").val(scaledCutoff);
                    },
                    stop: function (event, ui) {
                        $("form#prefForm").submit();
                    }
                });

                $("#sliderAndChart").show();


            }
        }).fail(function (data) {
            console.log("ERROR:  " + data);
        });
    }

    return {
        loadSliderAndPlot: function (cutoff, experimentAccession, selectedQueryFactorValues, queryFactorType, serializeFilterFactors, accessKey) {
            loadSliderAndPlot(cutoff, experimentAccession, selectedQueryFactorValues, queryFactorType, serializeFilterFactors, accessKey, buildLegendText);
        },
        loadProteomicsSliderAndPlot: function (cutoff, experimentAccession, selectedQueryFactorValues, queryFactorType, serializeFilterFactors, accessKey) {
            loadSliderAndPlot(cutoff, experimentAccession, selectedQueryFactorValues, queryFactorType, serializeFilterFactors, accessKey, buildProteomicsLegendText);
        },
        initBarChartButton: initBarChartButton
    }
})(jQuery);