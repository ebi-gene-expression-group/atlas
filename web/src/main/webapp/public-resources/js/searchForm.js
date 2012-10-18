function initSearchForm(homePageURL){

    $(".chzn-select").chosen();

    $("#reset-button").click(function (event) {
        location.replace(homePageURL);
    })

}