function toggleOrganismPartColor(svg, organism_part, evt) {

    function togglePathColor(path) {

        function setHilighting(color, opacity) {
            path.style.fill = color;
            path.style.fillOpacity = opacity;
        }

        if (evt == undefined || evt.type != 'mouseenter') {
            setHilighting("gray", 0.5);
        } else {
            setHilighting("red", 0.9);
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

function scaleAnatomogram(svg) {
    var elementById = svg.getElementById('group_all');
    elementById.setAttribute('transform', 'scale(1.6)');
}

function initAnatomogram(organismParts) {

    if ($('#anatomogramBody').length == 0) {
        return;
    }

    var svg = $('#anatomogramBody').svg().svg('get');

    loadAnatomogram("resources/svg/human_male.svg");

    $('#heatmap-table td, #heatmap-table th').hover(function (evt) {
        var organismPart = $(this).find('div').attr("data-organism-part");
        if (organismPart != undefined){
            toggleOrganismPartColor(svg, organismPart , evt);
        }
    });

    //load anatomogram from given location and display given organism parts
    function displayOrganismParts() {
        $.each(organismParts, function () {
            toggleOrganismPartColor(svg, this);
        });
    }

    function prepareAnatomogram() {
        displayOrganismParts();
        scaleAnatomogram(svg);
    }

    //load anatomogram from given location and display given organism parts
    function loadAnatomogram(location) {
        svg.load(location, {onLoad:prepareAnatomogram});
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

