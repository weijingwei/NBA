function createBarChart(data,ticks,targetDOMID) {
	//console.log(data);
//	console.log(ticks);
    var dataSet = [{data: data}];

    var options = {
        series: {
            bars: {
                show: true,
                fillColor: "#428BCA",
                align: "center",
                horizontal: true,
                lineWidth:0,
                barWidth: 0.7              
            }
        },
        xaxis: {
            axisLabel: $.i18n.prop("iterationComparison.featureBarChart.Feature_Count"),
            axisLabelUseCanvas: false,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Verdana, Arial',
            autoscaleMargin: 0.1,
//            tickColor: "#5E5E5E",
//			tickFormatter : function(val, axis) {
//				return val.toFixed(0);
//			},
            position: "top"
        },
        yaxis: {
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 20,
            axisLabelFontFamily: 'Verdana, Arial',
//            tickColor: "#5E5E5E",
            ticks: ticks,
        },
        grid: {
        	show: true,
        	borderColor: null,
        	borderWidth:0
        }
//        legend: {
//            noColumns: 0,
//            labelBoxBorderColor: "#858585",
//            position: "ne",
//            margin:3,
//        },
		
    };

	$.plot($("#"+targetDOMID), dataSet, options);
//	$("#"+targetDOMID).UseTooltip(tooltipName);
}

function gainFeatureHover(event,pos,item){
//	console.log(item);
	if(!item){
		return;
	}
	if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
		previousPoint = item.dataIndex;
        previousLabel = item.series.label;
        $("#tooltip").remove();

        var x = item.datapoint[0];
        var y = item.datapoint[1];

        var color = item.series.color;
        var FeatureCount = "Feature Count";
        showBarTooltip(item.pageX+150,
                item.pageY+50,
                color,
                "<strong>" + FeatureCount + " : <br>" + x +
                 "</strong>","myFeatureModal");
	} else {
		$("#tooltip").remove();
		previousPoint = null;
	}
}