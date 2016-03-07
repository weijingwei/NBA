var previousPoint = null, previousLabel = null;
$.fn.UseTooltip = function (name) {
    $(this).live("plothover", function (event, pos, item) {
        if (item) {
            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                previousPoint = item.dataIndex;
                previousLabel = item.series.label;
                $("#tooltip").remove();

                var x = item.datapoint[0];
                var y = item.datapoint[1];

                var color = item.series.color;
                var iteration = null;
                if(name == null){
                	iteration = $.i18n.prop("trailsList.modelListChart.iteration") + " " + parseInt(x);
                }else{
                	iteration = name[x-1]; 
                }
                showScatterTooltip(item.pageX,
                        item.pageY,
                        color,
                        "<strong>" + iteration + " : <br>" + y +
                         "</strong>");
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });
};




