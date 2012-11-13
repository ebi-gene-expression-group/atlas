function toggleOrganismPartColor(svg, organism_part, evt) {

    function togglePathColor(path) {

        function setHilighting(color, opacity) {
            path.style.fill = color;
            path.style.fillOpacity = opacity;
        }

        if (evt == undefined || evt.type != 'mouseenter') {
            setHilighting("gray", 0.5);
        } else {
            setHilighting("red", 0.7);
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

function initAnatomogram(organismParts, fileNameMale, fileNameFemale) {

    if ($('#anatomogramBody').length == 0) {
        return;
    }

    var svg = $('#anatomogramBody').svg().svg('get');

    loadAnatomogram("resources/svg/" + fileNameMale);

    //hover on first column, to show all organism parts involved on a single gene profile
    $("#heatmap-table").delegate("td:first-child","hover", function(evt){ //hover on cells of the first table column
        var geneExpressions = $(this).parents("tr").find("div[data-organism-part!='']");

        var organismParts = geneExpressions.map(function(){return $(this).attr('data-organism-part')}).get();

        organismParts.forEach(function(entry) {
            toggleOrganismPartColor(svg, entry , evt);
        });

    });


    $('#heatmap-table').delegate("td,th","hover", function (evt) {
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

    if (fileNameMale != fileNameFemale){
        //switch sex toggle button
        $('#sexToggle').toggle(function () {
            $(this).attr("class", "female");
            loadAnatomogram("resources/svg/" + fileNameFemale);
        },function() {
            $(this).attr("class", "male");
            loadAnatomogram("resources/svg/" + fileNameMale);
        });
    }
}

