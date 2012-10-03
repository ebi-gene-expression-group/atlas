
function setOrganismPartColor(svg, organism_part, color) {
    var path = svg.getElementById(organism_part);
    if (path != null) {
        path.attributes["style"].value = "fill:" + color
    }
}

function changeOrganismPartColorByHeatmapRowSelection(svg, evt, color) {
    var row = $(evt.target).parent('tr');  // Get the parent row

    setOrganismPartColor(svg, row.find("td:last").html(), color);
}

function initAnatomogram(organismParts){

    function showOrganismParts(){
        $.each(organismParts, function() {
            setOrganismPartColor(svg, this, "grey");
        });
    }

    $('#anatomogram').svg();

    var svg = $('#anatomogram').svg('get');

    svg.load("resources/svg/Human_web.svg", {onLoad:showOrganismParts});

    $('#svgOne').click(function () {
        svg.load("resources/svg/fly_web.svg", {onLoad:showOrganismParts});
    });

    $('#svgTwo').click(function () {
        svg.load("resources/svg/Human_web.svg", {onLoad:showOrganismParts});
    });

    $('#highlightPart').click(function () {
        var path = svg.getElementById($('#partToBeHighlighted').val());
        path.attributes["style"].value = "fill:red"
    });

    $('#clearPart').click(function () {
        var path = svg.getElementById($('#partToBeHighlighted').val());
        path.attributes["style"].value = "fill:none"
    });

    $('.heatmaprow').mouseover(function (evt) {
        changeOrganismPartColorByHeatmapRowSelection(svg, evt, "red")
    });

    $('.heatmaprow').mouseout(function (evt) {
        changeOrganismPartColorByHeatmapRowSelection(svg, evt, "grey")
    });

}