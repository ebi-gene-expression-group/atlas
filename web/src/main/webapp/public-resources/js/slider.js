function initSlider(cutoff, experimentAccession) {

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

    function scaledCutoffs(maxValue) {
        var scaledCutoffs = [];
        for (var i = 0; i < maxValue; i++) {
            var scaledCutoff = getNthScaledCutoff(i, 1);
            scaledCutoffs.push([scaledCutoff]);
        }
        return scaledCutoffs;
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

                label:"Y = number of genes expressed above the selected FPKM cutoff in any organism part",

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


    $.getJSON("json/gene-by-cutoff/" + experimentAccession + ".all.txt", function (data) {

        var scaledCutoffTicks = scaledCutoffs(data.length);

        var ticksMap = [];

        $.each(scaledCutoffTicks, function (index, scaledCutoff) {
            ticksMap.push([index, index % 2 === 0 ? magnifiedValue(scaledCutoff) : ""]);
        })

        var genesByCutoffPlot = plotCutoffBarChart(data, ticksMap);

        function hideGeneDistribution(img, isFast){
            $('#gene-distribution').hide(isFast? null:'slow');
//            $(img).attr('src', 'resources/images/chart-bar-add-icon.png');
            $("#display-chart").tooltip({content:"Display gene distribution"});
            $("#prefForm #displayGeneDistribution").val("false");
        }

        function displayGeneDistribution(img, isFast){
            $('#gene-distribution').show(isFast?null:'slow');
//            $(img).attr('src', 'resources/images/chart-bar-delete-icon.png');
            $("#display-chart").tooltip({content:"Hide gene distribution"});
            $("#prefForm #displayGeneDistribution").val("true");
        }

        $("#chart-button").button().toggle(
            function(event, fast){
                hideGeneDistribution(this, fast == true ? true : false);
            }
            , function(event, fast){
                displayGeneDistribution(this, fast == true ? true : false);
            }
        ).tooltip();


        if ($("#prefForm #displayGeneDistribution").val() == "false"){
            $("#chart-button").trigger('click', [true]);
        }


        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                position:'absolute',
                display:'none',
                top:y - 25,
                left:x - 6,
                border:'2px solid rgb(238,195,46)',
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

                        $("#tooltip").remove();
                        var content = item.datapoint[1].toFixed(0);

                        //now show tooltip
                        showTooltip(item.pageX, item.pageY, content);
                    }
                }
                else {
                    $("#tooltip").remove();
                    previousPoint = null;
                }


            }).bind("plotclick", function (event, pos, item) {

                if (item) {
                    $("#cutoff").val(getNthScaledCutoff(item.datapoint[0], 1));
                    $("form#prefForm").submit();
                }
            });
        ;

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
            max:data.length - 1,

            value:scaledCutoffPosition,

            slide:function (event, ui) {
                genesByCutoffPlot.unhighlight();
                genesByCutoffPlot.highlight(0, ui.value);
                var scaledCutoff = getNthScaledCutoff(ui.value, 1);
                $("#cutoff").val(scaledCutoff);
            },
            change:function (event, ui) {
                $("form#prefForm").submit();
            }
        });

    });


}