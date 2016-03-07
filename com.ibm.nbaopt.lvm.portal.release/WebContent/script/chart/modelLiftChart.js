var getAgrsFromURL = function GetArgsFromHref(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var results = regex.exec(window.document.location.href);
	if (results == null)
		return "";
	else
		return results[1];
};


function createSpcifiedModelChart(model, targetId,zoomPanBoolean) {
	
	var q_data = model.q;
	var v_data = model.v;
	// console.log(q_data.length);
	var dataset = [ {
		label : $.i18n.prop("trailsList.modelListChart.qValue"),
		data : q_data,
		points : {
			symbol : "square"// triangle
		}
	}, {
		label : $.i18n.prop("trailsList.modelListChart.vValue"),
		data : v_data
	} ];

	var options = {
		series : {
			lines : {
				show : true
			},
			points : {
				radius : 3,
				fill : true,
				show : true
			}
		},
		crosshair: {
			mode: "x"
		},
		xaxis : {
			tickSize : 1,
			tickFormatter : function(val, axis) {
				return val.toFixed(0);
			},
			autoscaleMargin : 0.08,
			axisLabel : $.i18n.prop("trailsList.modelListChart.iteration"),
			axisLabelUseCanvas : false,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 5,
			zoomRange : [ q_data.length, q_data.length ]
		},
		yaxis : {
			autoscaleMargin : 0.8,
			axisLabel : $.i18n.prop("trailsList.modelListChart.expectedReward"),
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 5,
			zoomRange : [ 0, 5000 ]
		},
		legend : {
			noColumns : 1,
			labelBoxBorderColor : "#000000",
			position : "ne",
			margin : 0,
			backgroundOpacity : 0.5
		},
		grid : {
			hoverable : true,
			borderWidth : 1,
			borderColor : "#633200",
			backgroundColor : {
				colors : [ "#ffffff", "#EDF5FF" ]
			},
			mouseActiveRadius : 10
		},
		zoom : {
			interactive : zoomPanBoolean,
			amount : 2
		},
		pan : {
			interactive : zoomPanBoolean
		},
		colors : [ "#00FF00", "#0000FF" ]
	};
	var plot = $.plot($("#" + targetId), dataset, options);
	// var minaxes = plot.getAxes().yaxis.min;
	// var maxaxes = plot.getAxes().yaxis.max;
	$("#" + targetId).UseTooltip();

}

