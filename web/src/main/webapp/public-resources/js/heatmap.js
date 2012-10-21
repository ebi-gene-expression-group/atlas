function initHeatmapDisplayValueToggle(){

    $("#display-levels").button()
               .toggle(function() {
                        $(this).button('option', 'label','hide levels');
                            $("div[data-color]").each(function(){
                                $(this).attr('style','font-size:9px;background-color:white;margin:4px;padding:2px;');
                            });
                            $(".gradient-level").attr("style",'');
                        },
                        function() {
                            $(this).button('option', 'label','display levels');
                            $("div[data-color]").each(function(){
                                $(this).attr('style','font-size:1px');
                            });
                            $(".gradient-level").attr("style",'color:white');
                        });

    $("#heatmap-table td:has(div[data-color])").toggle(function() {
            $(this).find("div").attr('style','font-size:9px;background-color:white;margin:4px;padding:2px;');
        },
        function() {
            $(this).find("div").attr('style','font-size:1px');

        });



}