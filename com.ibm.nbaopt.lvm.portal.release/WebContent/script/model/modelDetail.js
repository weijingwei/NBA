$(document).ready(function() {
	$("#reset").html($.i18n.prop("modelDetail.reset"));
	$("#reward_lift_chart").html($.i18n.prop("modelDetail.reward_lift_chart"));
	$("#start_time").html($.i18n.prop("modelDetail.start_time"));
	$("#end_time").html($.i18n.prop("modelDetail.end_time"));
	$("#duration").html($.i18n.prop("modelDetail.duration"));
    $("#Convergence_Chart").html($.i18n.prop("modelDetail.Convergence_Chart"));
    $("#description").html($.i18n.prop("modelDetail.description"));
    $("#introduction").html($.i18n.prop("modelDetail.introduction"));
    $("#model_viewer_navigater").html($.i18n.prop("modelDetail.model_viewer_navigater")); 
    $("#model_selection_navigater").html($.i18n.prop("modelDetail.model_selection_navigater")); 
    $("#model_selection").html($.i18n.prop("modelDetail.model_selection"));
    $("#introduction_iteration").html($.i18n.prop("modelDetail.introduction_iteration"));
    $("#home").html($.i18n.prop("modelDetail.home"));
    $("#advantage").html($.i18n.prop("modelDetail.advantage"));
    $("#details").html($.i18n.prop("modelDetail.details"));
    $("#modelTitle").html($.i18n.prop("modelDetail.modelTitle"));
    $("#advantageChart option:eq(0)").html($.i18n.prop("modelDetail.advantagechart.advantage"));
    $("#advantageChart option:eq(1)").html($.i18n.prop("modelDetail.advantagechart.iteration"));
    $("#advantageChart option:eq(2)").html($.i18n.prop("modelDetail.advantagechart.advantage_reward"));
    $("#iteration_label").html($.i18n.prop("modelDetail.iteration_label"));
    $("#modelDetail.warning").html($.i18n.prop("modelDetail.modelDetail.warning"));
    
    
 
	var iterationCount = getAgrsFromURL("iterationCount");
	var modelName = getAgrsFromURL("modelname");
	//$("#modelTitle").html(modelName);
	displayModelDetailInfor(modelName);
	createSidebar(iterationCount);
	limitCheckboxCount(1);
	displayModelChart(modelName);
	displayIterationAdvantageChart(modelName);

	$("#resetBtn").click(function() {
		$("input.iteration").attr("checked", false);
		$("input.iteration").attr("disabled", false);
	});
	$("#iterationComparison").click(function(event) {
		var flag = iterationComparison(modelName);
		console.log(flag);
		if(!flag){
			$("input.iteration").attr("checked", false);
			event.preventDefault();
		}
		else{
			location.href = "../iteration/iterationComparison.html";
		}
	});

});


function limitCheckboxCount(maxSelectedBox){
	 $('input[type=checkbox]').click(function() {
         $("input[name='iterationCheckBtn']").attr('disabled', true);
         if ($("input[name='iterationCheckBtn']:checked").length >= maxSelectedBox) {
             $("input[name='iterationCheckBtn']:checked").attr('disabled', false);
             /*$("#selectedThreeWarning").html(maxSelectedBox + " iterations at most.");
             $("#selectedThreeWarning").css("display","block");
             timename=setTimeout("$('#selectedThreeWarning').css('display','none')",3000);*/
         } else {
             $("input[name='iterationCheckBtn']").attr('disabled', false);
         }
     });
}

function createSidebar(iterationCount) {
	for (var i = 1; i <= iterationCount; i++) {
		$("#modelIterationList").append(
				"<li class='list-group-item'><label for='iterationCheckBtn" + i + "'>"+ $.i18n.prop("modelDetail.iteration")+"\ " + i + "&nbsp;&nbsp;&nbsp;</label>" +
				"<input id='iterationCheckBtn" + i + "' class='iteration' value='" + i + "' type='checkbox' name='iterationCheckBtn'></input></li>");
	}
}

function displayModelChart(modelName) {
	$.get("../../nba/model/" + modelName, function(model) {
		var zoomPanBoolean=true;
		createSpcifiedModelChart(model, "modelChart",zoomPanBoolean);
		displayModelDetailInfor(model);
	}, "json");
}

function displayIterationAdvantageChart(modelName) {
	$.get("../../nba/action/" + modelName + "/advantage", function(model) {
//		console.log("AD: " + model);
		createAdvantageChart(model, "advantageChart");
	}, "json");
}

function iterationComparison(modelName){
	var selectedIterations = [];
	$("#modelIterationList").find("input:checked").each(function() {
		selectedIterations.push($(this).val());
	});
	if(selectedIterations.length <= 0){
		 $("#selectedThreeWarning").html($.i18n.prop("modelDetail.modelDetail.warning"));
         $("#selectedThreeWarning").css("display","block");
     	timename=setTimeout("$('#selectedThreeWarning').css('display','none')",3000);
		return false;
	}else{
		sessionStorage.setItem("modelName", modelName);
		sessionStorage.setItem("iterations", selectedIterations);
		if(selectedIterations.length!=0){
			
			return true;
		}
		else return false;
	}
}

function displayModelDetailInfor(model){
	if (model != undefined) {
		var iteration = model["iterationCount"];
		var desc = model["description"];
		var start = model["startTime"];
		var end = model["endTime"];
		var timeCost = model["timeCost"];
		
		var contents = "Starts at " + start + ".<br><br>";
		contents += "Ends at " + end + ".<br><br>";
		contents += "Time cost is " + timeCost + ".";
//		console.log(contents)
//		$("#modelDesc").html(model["name"] + " has " + iteration + " iteration(s). " + desc);
		$("#modelDesc").html(model["name"]);
		$("#StartT").html( model["startTime"]);
		$("#EndT").html(model["endTime"]);
		$("#DurationT").html(model["timeCost"]);
		$("#detail").bind("click",function(event){
			 $('<div id="tooltip">' + contents + '</div>').css({
			        position: 'absolute',
			        display: 'none',
			        border: '2px solid '+'red',
			        padding: '3px',
			        'font-size': '15px',
			        'left':'65%',
			        'top':'0',
			        'width:':'20%',
			        //'height':'10%',
			        'border-radius': '5px',
			        'background-color': '#fff',
			        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
			        opacity: 1
			    }).appendTo("#modelTitle").fadeIn(1);
		});
	}
}
