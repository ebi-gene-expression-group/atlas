function toggleOrganismPartColor(svg, organism_part) {

    function togglePathColor(path) {
        if (path.highlighted === undefined) {
            path.style.fill = "gray";
            path.highlighted = false;
        } else {
            path.style.fill = (path.highlighted === false ? "red" : "gray");
            path.highlighted = !path.highlighted;
        }
    }

    var element = svg.getElementById(organism_part);

    if (element != null) {
        if (element.nodeName === 'g') {
            $.each(element.getElementsByTagName('path'), function () {
                togglePathColor(this);
            });
        } else {
            togglePathColor(element);
        }

    }

}

function toggleOrganismPartColorByHeatmapRowSelection(svg, evt) {
    var row = $(evt.target).parent('tr');  // Get the parent row

    toggleOrganismPartColor(svg, row.find("td:last").text());
}

function scaleAnatomogram(svg) {
    var elementById = svg.getElementById('group_all');
    elementById.setAttribute('transform', 'scale(1.6)');
}

function initAnatomogram(organismParts) {
    var svg = $('#anatomogramBody').svg().svg('get');

    loadAnatomogram("resources/svg/human_male.svg");

    $('.heatmaprow').hover(function (evt) {
        toggleOrganismPartColorByHeatmapRowSelection(svg, evt)
    });

    //load anatomogram from given location and display given organism parts
    function displayOrganismParts() {
        $.each(organismParts, function () {
            toggleOrganismPartColor(svg, this);
        });

        scaleAnatomogram(svg);
    }

    //load anatomogram from given location and display given organism parts
    function loadAnatomogram(location) {
        svg.load(location, {onLoad:displayOrganismParts});
    }

    //switch sex toggle button
    $('#sexToggle').click(function () {
        if ($(this).attr("class") == "female") {
            $(this).attr("class", "male");
            loadAnatomogram("resources/svg/human_male.svg");
        } else {
            $(this).attr("class", "female");
            loadAnatomogram("resources/svg/human_female.svg");
        }
    });

}

