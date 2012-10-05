function fillOrganismPart(svg, organism_part, color) {
    var path = svg.getElementById(organism_part);
    if (path != null) {
        path.style.fill = color;
    }
}

function toggleOrganismPartColor(svg, organism_part) {
    var path = svg.getElementById(organism_part);
    if (path != null) {
        path.style.fill = ("red"===path.style.fill ? "grey":"red");
    }
}

function toggleOrganismPartColorByHeatmapRowSelection(svg, evt) {
    var row = $(evt.target).parent('tr');  // Get the parent row

    toggleOrganismPartColor(svg, row.find("td:last").text());
}

function initAnatomogram(organismParts) {
    var svg = $('#anatomogramBody').svg().svg('get');

    loadAnatomogram("resources/svg/Human_web.svg");

    $('.heatmaprow').hover(function (evt) {
        changeOrganismPartColorByHeatmapRowSelection(svg, evt)
    });

    //load anatomogram from given location and display given organism parts
    function displayOrganismParts() {
        $.each(organismParts, function () {
            fillOrganismPart(svg, this, "gray");
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
        } else{
            $(this).attr("class", "female");
            loadAnatomogram("resources/svg/fly_web.svg");
        }
    });

}