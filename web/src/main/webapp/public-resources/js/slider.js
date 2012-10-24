function initSlider(cutoff) {

    function getTickIndex(val){

        if(val < 0.1) {
            return 0;
        }

        if(val >= 1) {

            // Remove decimal places and replace all but first digit with zeros.
            val = Math.floor(val);
            var x = Math.pow(10, val.toString().length-1);
            val = Math.floor(val / x) * x;

        } else {

            // val is somewhere from 0.1 to 0.9999...
            val = Math.floor(val * 10) * 0.1;
        }


        // value after zero in the sequence is 0.1
        var start = 0.1;


// define our own log10() function because JavaScript doesn't have one(?)
        function log10(x) {
            return Math.log(x) / Math.log(10);
        }


        if((10*val) % 9 != 0) {

            // The sequence is in rows of 9, e.g. 0.1-0.9, 1-9, 10-90, 100-900, ...
            // The first part gets the index of the top value in the row we need, e.g. 9, 18, 27, ...
            var num1 = 9 * (Math.floor( log10( val/start ) ) + 1);

            // The second part works out the index in that row
            var num2 = 9 - ((10*val) % 9);

            // The third part gives the index in the whole sequence
            var position = num1 - num2;

            return position;
        }

// Special case for multiples of 9
        else {

            var position = 9 * (Math.floor( log10( val/start ) ) + 1);

            return position;
        }


    }


    function getTickValue(indexValue) {
        if (indexValue == 0) {
            return "0";
        }

        var remainder = indexValue % 9;

        //The next if - else is fine for a starting step of 0.1.
        //For a starting step of 0.01 the subtracted values must change to -2 and -3
        if (remainder != 0) {
            var power = Math.floor(indexValue / 9) - 1;
            var retVal = Math.pow(10, power) * remainder;
        } else {
            var power = Math.floor(indexValue / 9) - 2;
            var retVal = Math.pow(10, power) * 9;
        }

        if (retVal < 1) {
            retVal = retVal.toFixed(1)
        }
        return retVal;
    }

    function tickValues(axis) {
        var ticks = [];
        for (var i = 0; i < axis.max; i++) {
            if(i%2 == 0){
                var tick = magnifiedValue(getTickValue(i));
            } else {
                tick="";
            }
            ticks.push([i, tick]);
        }
        return ticks;
    }

    function magnifiedValue(value){
        if(value >= 1000){
            return value/1000 + "K";
        }
        return value;
    }

    function plotCutoffBarChart(data){
        return $.plot($("#gene-distribution"), [ data ], {
            series:{
                highlightColor:"red",

                label: "y = nr of genes with specificity 1 above cutoff",

                bars: { show: true,barWidth:.8,align:"center"}
            },
            xaxis: {
                tickLength:3,
                ticks:tickValues
            },
            yaxis: {
            },
            grid: {
                borderColor:"#CDCDCD",
                borderWidth: 1,
                hoverable: true
            }
        });
    }

    $.getJSON("json/gene-by-cutoff/specificity-one.txt", function(data){

        var genesByCutoffPlot = plotCutoffBarChart(data);

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                position: 'absolute',
                display: 'none',
                top: y - 25,
                left: x - 13,
                border: '2px solid rgb(238,195,46)',
                padding: '2px',
                'font-family': 'Verdana, Helvetica, Arial, sans-serif',
                'font-size': 'smaller',
                'background-color': 'rgb(249,232,176)',
                opacity: 1
            }).appendTo("body").fadeIn(150);
        }

        var previousPoint = null;
        $("#gene-distribution").bind("plothover", function(event, pos, item) {
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

        });

        var tickIndex = getTickIndex(cutoff);

        genesByCutoffPlot.highlight(0,tickIndex);

        $("#slider-range-max").slider({
            range:"max",
            min:0,
            max:data.length-1,

            value:tickIndex,

            slide:function (event, ui) {
                genesByCutoffPlot.unhighlight();
                genesByCutoffPlot.highlight(0,ui.value);
                $("#cutoff").val(getTickValue(ui.value));
            },
            change:function (event, ui) {
                $("form#prefForm").submit();
            }
        });

    });


}