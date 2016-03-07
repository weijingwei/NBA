function createAdvantageChart(advantage, targetDOMId) {
	var dataset = [ {
		label : $.i18n.prop("modelDetail.advantagechart.advantage"),
		data : advantage,
		points : {
			symbol : "square"// triangle
		}
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
		xaxis : {
			tickSize : 1,
			tickFormatter : function(val, axis) {
				return val.toFixed(0);
			},
			autoscaleMargin : 0.08,
			axisLabel : $.i18n.prop("modelDetail.advantagechart.iteration"),
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 10
		},
		yaxis : {
			axisLabel : $.i18n.prop("modelDetail.advantagechart.advantage_reward"),
			axisLabelUseCanvas : true,
			axisLabelFontSizePixels : 12,
			axisLabelFontFamily : 'Verdana, Arial',
			axisLabelPadding : 15
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
		colors : [ "#00FF00", "#0000FF" ]
	};

	var plot = $.plot($("#" + targetDOMId), dataset, options);
	$("#" + targetDOMId).UseTooltip();
}