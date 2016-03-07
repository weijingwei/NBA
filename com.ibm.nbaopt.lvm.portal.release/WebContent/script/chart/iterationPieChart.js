var previousPoint = null, previousLabel = null;
function createIterationPieChart(dataSet,labeltoggle,targetDOMID) {
	var options = {
		series : {
			pie : {
				show : true,
				offset: {
					right:10
				},
				innerRadius:0.4,
	            label: {
	                show: labeltoggle,
	                radius: 1,
	            },
			}
		},
		legend : {
			radius: 1,
			show : true
		},
		grid : {
			hoverable : true,
		},
		//colors : [ "#005CDE", "#DE000F", "#ED7B00", "#00A36A", "#7D0096" ]
		colors:["#F7A35B","#7CB5EC","#8085E9","#90EC7D","#444348","#F25C82","#FF9E01","#F8FF01","#04D215"],
	};

	 $.plot($("#"+ targetDOMID), dataSet, options);
}

function multiplePieHover(event,pos,item){
	if(!item){
		$("#tooltip").remove();
		previousPoint = null;
		return;
	}
	if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
		previousPoint = item.dataIndex;
		previousLabel = item.series.label;
		$("#tooltip").remove();
		var percent = parseFloat(item.series.percent).toFixed(2);
		var color = item.series.color;
		//console.log(event.pageX,event.pageY);
		showBarTooltip(pos.pageX, pos.pageY, color, "<strong>"
				+ item.series.label + " : " + percent + "%</strong>","pieCharts");
	} else {
		
	}
}

function singlePieHover(event,pos,item){
	if(!item){
		return;
	}
	if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
		previousPoint = item.dataIndex;
		previousLabel = item.series.label;
		$("#tooltip").remove();
		var percent = parseFloat(item.series.percent).toFixed(2);
		var color = item.series.color;
		//console.log(event.pageX,event.pageY);
		showBarTooltip(pos.pageX+50, pos.pageY, color, "<strong>"
				+ item.series.label + " : " + percent + "%</strong>","myModal");
	} else {
		$("#tooltip").remove();
		previousPoint = null;
	}
}


