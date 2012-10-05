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

function initAnatomogram(organismParts) {
    var svg = $('#anatomogramBody').svg().svg('get');

    loadAnatomogram("resources/svg/Human_web.svg");

    $('#svgOne').click(function () {
        loadAnatomogram("resources/svg/fly_web.svg");
    });

    $('#svgTwo').click(function () {
        loadAnatomogram("resources/svg/Human_web.svg");
    });

    $('.heatmaprow').mouseover(function (evt) {
        changeOrganismPartColorByHeatmapRowSelection(svg, evt, "red")
    });

    $('.heatmaprow').mouseout(function (evt) {
        changeOrganismPartColorByHeatmapRowSelection(svg, evt, "grey")
    });

    //load anatomogram from given location and display given organism parts
    function displayOrganismParts() {
        $.each(organismParts, function () {
            setOrganismPartColor(svg, this, "grey");
        });
    }

    //load anatomogram from given location and display given organism parts
    function loadAnatomogram(location) {
        svg.load(location, {onLoad:displayOrganismParts});
    }

    //switch sex toggle button
    $('#sexToggle').click( function() {
        if ($(this).attr("class") == "female"){
            $(this).attr("class", "male");
            loadAnatomogram("resources/svg/Human_web.svg");
        }
        else{
            $(this).attr("class", "female");
            loadAnatomogram("resources/svg/fly_web.svg");
        }
    });

}