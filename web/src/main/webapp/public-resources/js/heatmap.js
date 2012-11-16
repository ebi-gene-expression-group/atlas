function initHeatmapDisplayValueToggle(){

    function showValues(button){
        $(button).button('option','label','Hide levels');
        $("div[data-color]").each(function(){
            $(this).attr('style','font-size:9px;background-color:white;margin:4px;padding:2px;');
        });
        $(".gradient-level").attr("style",'');
    }

    function hideValues(button){
        $(button).button('option','label','Display levels');
        $("div[data-color]").each(function(){
            $(this).attr('style','font-size:1px');
        });
        $(".gradient-level").attr("style",'color:white');
    }

    $("#display-levels").button()
               .toggle(function() {
                            showValues(this);
                            $("#prefForm #displayLevels").val("true");
                        },
                        function() {
                            hideValues(this);
                            $("#prefForm #displayLevels").val("false");
                        });

    if ($("#prefForm #displayLevels").val() == "true"){
        $("#display-levels").click();
    }


    $("#heatmap-table td:has(div[data-color])").click(function() {
        var div = $(this).find("div");
        var style = div.attr("style");
        $(div).attr('style',style == "font-size:1px"?"font-size:9px;background-color:white;margin:4px;padding:2px;":"font-size:1px");
    });

    $('#download-profiles').button().tooltip({content: "Download query results"});



}