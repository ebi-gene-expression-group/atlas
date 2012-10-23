function initSlider(defaultCutoff){

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
/*
        $("#gene-distribution").bind("plothover", function (event, pos, item) {
            alert("You clicked at " + pos.x + ", " + pos.y);

            if (item) {
                highlight(item.series, item.datapoint);
                alert("You clicked a point!");
            }
        });
*/
        genesByCutoffPlot.highlight(0,0);

        $("#slider-range-max").slider({
            range:"max",
            min:0,
            max:data.length-1,

            value:0,

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