var allModel = [];
$(document).ready(function() {
	var modelNamesArray = sessionStorage.getItem("models").split(",");

	displayModelsChart(modelNamesArray);
	displayIterationAdvantageChart(modelNamesArray);
	firstClick=true;
	// Default Action
	$(".tab_content").hide(); // Hide all content
	$("ul.tabs li:first").addClass("active").show(); // Activate first tab
	$(".tab_content:first").show(); // Show first tab content

	// On Click Event
	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active"); // Remove any "active" class
		$(this).addClass("active"); // Add "active" class to selected tab
		$(".tab_content").hide(); // Hide all tab content
		var activeTab = $(this).find("a").attr("href"); // Find the rel
														// attribute
		// value to identify the
		// active tab + content
		$(activeTab).fadeIn(1); // Fade in the active content
		if(firstClick==true){
			$(modelNamesArray).each(function(index, modelName) {
				$.get("../../nba/action/" + modelName + "/advantage", function(model) {
					createAdvantageChart(model, modelName + "advantage");
				}, "json");
			});
			
		}
		firstClick=false;
		return false;
	});
	$("#viewGain").click(function(event){
		var text = $(this).text();
		if ($("#gainChartList").children().length == 0) {
			displayIterationDifference(modelNamesArray);
			$(".flot-y-axis").css('left','-30px');
		}
		$("#gainChartGroup").css("display", "block");
	});
});

function displayIterationDifference(modelsArray){
	//while(allModel.length == 0);
	var data = [];
	var iterationLength = allModel[0]["q"].length;
	var modelLength = allModel.length;
	
	for(var i = 0; i < iterationLength; i++){
		for(var j = 0; j< modelLength; j++){
			//var modelName = modelsArray[j];
			var Q = allModel[j]["q"];
			var V = allModel[j]["v"];
			var difference = (Q[i][1]) - (V[i][1]);
			data.push([j+1,difference]);
		}
		var dataset = [ {
			label : "Q-V",
			data : data,
			color : "#0077FF"
		}];
		console.log(data);
		if(i==0){
		$("#gainChartList").append(
				"<div class='item active'>" +
				"<div class='col-xs-8 col-xs-offset-3'>" +
				"<h4>"+ "Iteration "+(i+1) + "</h4>" +
				
					"<div style='width:500px;height:450px' name='sliderChart' id="+ "Iteration"+(i+1)+"chart" +"></div>" +
				 "</div>" +
				"</div>");
		}
		else{
			$("#gainChartList").append(
					"<div class='item'>" +
					"<div class='col-xs-8 col-xs-offset-3'>" +
					"<h4>"+ "Iteration "+(i+1) + "</h4>" +
					"<p>" +
						"<div style='width:500px;height:450px' name='sliderChart' id="+ "Iteration"+(i+1)+"chart" +"></div>" +
					"</p>" + "</div>" +
					"</div>");
		}
		createSpcifiedStackChart(dataset, "Iteration"+(i+1)+"chart","Model Name","Difference",modelsArray);
		$("#Iteration"+(i+1)+"chart").live("plothover",gainStackHover);
		data = [];
	}	
}

function displayModelsChart(modelsArray) {
	for ( var i = 0; i < modelsArray.length; i++) {
		var modelName = modelsArray[i];
		$("#tabs-1").append(
				"<div class='col-xs-4 col-xs-offset-1'><h4>" + modelName
						+ "</h4><div style='width:380px;height:280px' id='"
						+ modelName + "chart'></div></div>");
	}
	$(modelsArray).each(function(index, modelName) {
		$.get("../../nba/model/" + modelName, function(model) {
			var zoomPanBoolean=true
			createSpcifiedModelChart(model, modelName + "chart",zoomPanBoolean);
			allModel.push(model);
		}, "json");
	});
}

function displayIterationAdvantageChart(modelsArray) {
	for ( var i = 0; i < modelsArray.length; i++) {
		var modelName = modelsArray[i];
		$("#tabs-2").append(
				"<div class='col-xs-4 col-xs-offset-1'><h4>" + modelName
						+ "</h4><div style='width:380px;height:280px' id='"
						+ modelName + "advantage'></div></div>");
	}
//	$(modelsArray).each(function(index, modelName) {
//		$.get("../../nba/action/" + modelName + "/advantage", function(model) {
//			createAdvantageChart(model, modelName + "advantage");
//		}, "json");
//	});
}
