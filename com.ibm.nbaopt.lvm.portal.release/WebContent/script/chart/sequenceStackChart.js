function createSpcifiedStackChart(dataset, targetDOMID, xaxisName, yaxisName,tooltipName) {
	if(xaxisName == null){
		xaxisName = "Iteration";
	}
	if(yaxisName == null){
		yaxisName = "Segment Transaction";
	}
	var options = {
		series : {
			stack : true,
			bars : {
				show : true
			}
		},
		bars : {
			align : "center",
			barWidth : 0.4,
			lineWidth : 1
		},
		xaxis : {
			tickSize : 1,
			tickFormatter : function(val, axis) {
				return val.toFixed(0);
			},
			autoscaleMargin : 0.08,
			tickLength : 10,
			color : "black",
			axisLabel : xaxisName,
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 25
		},
		yaxis : {
			color : "black",
			axisLabel : yaxisName,
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 50
		},
		grid : {
			hoverable : true,
			borderWidth : 2,
			backgroundColor : {
				colors : [ "#EDF5FF", "#ffffff" ]
			}
		},
		colors : [ "#004078", "#207800", "#613C00" ]
	};

	$.plot($("#"+targetDOMID), dataset, options);
	//$("#"+targetDOMID).UseTooltip(tooltipName);

}

function transactionStackHover(event,pos,item){
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
        var ite = "Iteration " + parseInt(x);
        showBarTooltip(item.pageX,
                item.pageY,
                color,
                "<strong>" + iteration + " : <br>" + y +
                 "</strong>","transaction");
	} else {
		$("#tooltip").remove();
		previousPoint = null;
	}
}

function gainStackHover(event,pos,item){
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
        var iteration = "Iteration " + parseInt(x);
        showBarTooltip(item.pageX+50,
                item.pageY-50,
                color,
                "<strong>" + iteration + " : <br>" + y +
                 "</strong>","myModal");
	} else {
		$("#tooltip").remove();
		previousPoint = null;
	}
}
