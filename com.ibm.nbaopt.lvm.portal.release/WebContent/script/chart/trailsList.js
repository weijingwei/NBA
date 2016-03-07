var allModel=[];
var searchResult = [];
$(document).ready(function() {
	//i18n
	$("#home").html($.i18n.prop("trailsList.home"));
	$("#modelViewer").html($.i18n.prop("trailsList.modelViewer"));
	$("#description").html($.i18n.prop("trailsList.description"));
	$("#selectModelCriteria option:eq(0)").html($.i18n.prop("trailsList.selectModelCriteria.name"));
	$("#selectModelCriteria option:eq(1)").html($.i18n.prop("trailsList.selectModelCriteria.iteration"));
	$("#selectModelCriteria option:eq(2)").html($.i18n.prop("trailsList.selectModelCriteria.description"));
	$("#noDataInDB").html($.i18n.prop("trailsList.noDataInDB"));
	$("#errorMessage").html($.i18n.prop("trailsList.errorMessage"));
	$("#modelListChart option:eq(0)").html($.i18n.prop("trailsList.modelListChart.iteration"));
	$("#modelListChart option:eq(1)").html($.i18n.prop("trailsList.modelListChart.expectedReward"));
	$("#modelListChart option:eq(2)").html($.i18n.prop("trailsList.modelListChart.qValue"));
	$("#modelListChart option:eq(3)").html($.i18n.prop("trailsList.modelListChart.vValue"));
	$("#selectModelAlert1").html($.i18n.prop("trailsList.selectModelAlert1"));
	$("#selectModelAlert2").html($.i18n.prop("trailsList.selectModelAlert2"));
	$("#detail").html($.i18n.prop("trailsList.detail"));
	$("#select").html($.i18n.prop("trailsList.select"));
	$("#trailsList.results_totally").html($.i18n.prop("trailsList.trailsList.results_totally"));
	
	jQuery.ajax({
	  	url:"../nba/model/all",
	  	type:"get",
	  	dataType: "json",
	  	success: function(data){
		  if(data==''){
			  var errorMsg = "<div class='item active'>"+
						    	"<div class='col-xs-3 col-xs-offset-2' style='width:1000px;height:500px;text-align:center; line-height:500;'>"+
						    		"<h3>" + $.i18n.prop("trailsList.noDataInDB") + "</h3>"+
						    	"</div>"+
						     "</div>";
			  $("#carousel-inner").append(errorMsg);
		  } else {
			  allModel = data;
			  searchResult = data;
			  createDivElement(data);
			  $(".item~div").find("div[name='chartDiv']").css("width","405px");
			  $(".item~div").find("div[name='chartDiv']").css("height","220px");
			  drawModelChart(data);
			  reRangeYAix();
			  modelComparisonListener();
			  $("#searchModel").click(function(){
				  searchModel(allModel);
			  });
			  $("#searchKeyWord").click(function(){
				  $("#searchKeyWord").val("");
				  $("#searchKeyWord").attr("class","inputText black");
				  resetModelList();
			  });}
	  	},
		error: function(httpException) {
			var errorMsg = "<div class='item active'>"+
								"<div class='col-xs-3 col-xs-offset-2' style='width:1000px;height:500px;text-align:center; line-height:500;'>"+
									"<h3>" + $.i18n.prop("trailsList.errorMessage") +"</h3>"+
								"</div>"+
						   "</div>";
			$("#carousel-inner").append(errorMsg);
		},
    });
		
});

function reRangeYAix(){
	$(".item~div").find("div[name='chartDiv']").css("height","250px");
	$(".item~div .flot-y-axis").css('left','-20px');
}

function addToInfoListener(data){
	$(data).each(
	   function(index, model) {
				$("#addBtn"+index).bind("click", function(){
					var addBtnName=$(this).parent().parent().find("h4").html();
					var l = $("#infoTable").find("tbody tr").length;
					if(l == 0){
						runEffect();
					}
					if(l<3){
						if($("#addSpan"+index).html() != "Unselect"){
							$("#addSpan"+index).html("Unselect");
							$("#addSpan"+index).attr("class","glyphicon glyphicon-record");
							$("#infoTable").append(
									"<tr class='bg-success'>"
									+"<td>"+model.name+"</td>"
									+"<td>"+model.iterationCount+"</td>" 
								
									+"<td><button id='delBtn_"+addBtnName+"' type='button' name='delBtn' class='btn btn-danger btn-xs'><span class='glyphicon glyphicon-remove'></span></button>"+
							"</td></tr>");
							l++;
						}else{
							$("#addSpan"+index).html("Select");
							$("#addSpan"+index).attr("class","glyphicon glyphicon-unchecked");
							$("#delBtn_"+addBtnName).parent().parent().remove();
							l--;
							$("#selectedThreeWarning").css("display","none");
							if(l<3){
								if(l<0){
									setTimeout(function() {
								        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
								      }, 500 );
								}
							}
							
						}
					}
					else{
						if($("#addSpan"+index).html() == "Unselect"){
							$("#addSpan"+index).html("Select");
							$("#addSpan"+index).attr("class","glyphicon glyphicon-unchecked");
							$("#delBtn_"+addBtnName).parent().parent().remove();
							l--;
							$("#selectedThreeWarning").css("display","none");
							if(l==0){
								setTimeout(function() {
							        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
							      }, 500 );
							}

						}
						if(l>=3){
							 $("#selectedThreeWarning").html("3 iterations at most.");
				             $("#selectedThreeWarning").css("display","block");
						}

					}
		
				}); 
	   });
				$("#infoTable").on('click', "button", function (event) {
					var selectedDelBtn=$(this).parent().parent().children(":first").html();
					$("#"+selectedDelBtn).parent().children(":last").find("a span").html("Select");
					$("#"+selectedDelBtn).parent().children(":last").find("a span").attr("class","glyphicon glyphicon-unchecked");
					$(this).parent().parent().remove();
					 $("#selectedThreeWarning").css("display","none");
					var l = $("#infoTable").find("tbody tr").length;
					l--;
					if(l<0){
						setTimeout(function() {
					        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
					      }, 500 );
					}
				});
	 
}

function viewDetailListener(data){
	$(data).each(
		function(index, model) {
			$("#detail"+index).bind('click', function () {
				location.href="model/modelDetail.html?modelname="+model.name+"&iterationCount="+model.iterationCount;		 
			});
			$("#modelChart_"+index).bind('click', function () {
				location.href="model/modelDetail.html?modelname="+model.name+"&iterationCount="+model.iterationCount;		 
			});
	});
}

function modelComparisonListener(){
	 $("#modelComparison").click(function(event) {
		 var modelArray = [];
		 var modelCount = 0;
		 var iterationCount = 0;
		 $(searchResult).each(
			function(index, model) {
				if($("#addSpan"+index).html() === "Unselect"){
					modelArray.push(model.name);
					if(modelCount == 0){
						iterationCount = model.iterationCount;
						modelCount++;
					}else{
						if(iterationCount == model.iterationCount){
							modelCount++;
						}
					}					
				}	
		});
		sessionStorage.setItem("models", modelArray);
		console.log(modelArray.length,modelCount);
		if(modelArray.length <= 1){
			event.preventDefault();
			alert($.i18n.prop("trailsList.selectModelAlert1"));
		}else if(modelArray.length != modelCount){
			event.preventDefault();
			alert($.i18n.prop("trailsList.selectModelAlert2"));
		}else{
			location.href="model/modelComparison.html";	
		}
	 });
}

function createDivElement(data) {
	var content = "";
	function detailSpan(index) {
		return 	"<a id='detail" + index + "' role='button' href='javascript:void(0)'> " +
					"<span id='detailSpan" + index + "' class='glyphicon glyphicon-dashboard' aria-hidden='true'>" + $.i18n.prop("trailsList.detail") + "</span>" +
				"</a>";
	}
	function selectSpan(index) {
		return  "<a id='addBtn" + index + "' role='button' href='javascript:void(0)'> " +
					"<span id='addSpan" + index + "' class='glyphicon glyphicon-unchecked hidden' aria-hidden='true'>" + $.i18n.prop("trailsList.select") + "</span>" +
				"</a>";
	}
	for(var index = 0; index < data.length; index++){
		//6 charts every page.
		if (index == 0) {
			content += "<div class='item active'>" +
							"<div id='modelChart_" + index + "' class='col-lg-3 col-xs-5 col-xs-offset-1'>" +
								"<h4>"+ data[index].name + "</h4>" +
								"<p>" +
									"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
								"</p>" +
								"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
								detailSpan(index) +
								"</div>"+
								"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
								selectSpan(index) +
								"</div>"+
							"</div>";
			
		} else if (index % 6 == 0) {
			content += "<div class='item '>" +
							"<div id='modelChart_" + index + "' class='col-lg-3 col-xs-5 col-xs-offset-1'>" +
								"<h4>"+ data[index].name + "</h4>" +
								"<p>" +
									"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
								"</p>" +
								"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
								detailSpan(index) +
								"</div>"+
								"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
								selectSpan(index) +
								"</div>"+
							"</div>";
		} else if (index % 6 == 5) {
			content += "<div id='modelChart_" + index + "' class='col-lg-3 col-xs-5'>" +
							"<h4>"+ data[index].name+ "</h4>" +
							"<p>" +
								"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
							"</p>" +
							"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
							detailSpan(index) +
							"</div>"+
							"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
							selectSpan(index) +
							"</div>"+
						"</div>" +
					"</div>";
		} else if(index % 6 == 3){
			content += "<div id='modelChart_" + index + "' class='col-lg-3 col-lg-offset-1 col-xs-5'>" +
							"<h4>"+ data[index].name + "</h4>" +
							"<p>" +
								"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
							"</p>" +
							"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
							detailSpan(index) +
							"</div>"+
							"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
							selectSpan(index) +
							"</div>"+
					   "</div>";
		} else if(index % 6 == 1){
			content += "<div id='modelChart_" + index + "' class='col-lg-3 col-xs-5'>" +
							"<h4>"+ data[index].name + "</h4>" +
							"<p>" +
								"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
							"</p>" +
							"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
							detailSpan(index) +
							"</div>"+
							"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
							selectSpan(index) +
							"</div>"+
					   "</div>";
		} else if(index % 6 == 2){
			content += "<div id='modelChart_" + index + "' class='col-lg-3 col-lg-offset-0 col-xs-5 col-xs-offset-1'>" +
							"<h4>"+ data[index].name + "</h4>" +
							"<p>" +
								"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
							"</p>" +
							"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
							detailSpan(index) +
							"</div>"+
							"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
							selectSpan(index) +
							"</div>"+
					   "</div>";
		} else {
			content += "<div id='modelChart_" + index + "' class='col-lg-3 col-lg-offset-0 col-xs-5 col-xs-offset-1'>" +
							"<h4>"+ data[index].name + "</h4>" +
							"<p>" +
								"<div name='chartDiv' style='width:390px;height:240px' id="+ data[index].name +"></div>" +
							"</p>" +
							"<div class='col-xs-4 col-xs-offset-2' style='margin-top: -32px;'>"+
							detailSpan(index) +
							"</div>"+
							"<div class='col-xs-2 col-xs-offset-6' style='margin-top: -32px;'>"+
							selectSpan(index) +
							"</div>"+
						"</div>";
		}
	}
	
	//4 charts every page.
//	$(data).each(
//			function(index, model) {	
//				if (index == 0) {
//					content += "<div class='item active'>" +
//									"<div class='col-xs-3 col-xs-offset-1'>" +
//									"<h4>"+ model.name + "</h4>" +
//									"<p>" +
//										"<div style='width:330px;height:210px' id="+ model.name +"></div>" +
//									"</p>" +
//									"<div class='col-xs-3'>"+
//									"<button id='detail"+index+"' type='button' class='btn btn-default'>View details</button>" +
//									"</div>"+
//									"<div class='col-xs-3'>"+
//									"<button id='addBtn"+index+"' type='button' class='btn btn-default'>Add it to Info</button>" +
//									"</div>"+
//								"</div>";
//				} else if (index % 4 == 0) {					
//					content += "<div class='item '>" +
//									"<div class='col-xs-3 col-xs-offset-1'>" +
//									"<h4>"+ model.name + "</h4>" +
//									"<p>" +
//										"<div style='width:330px;height:210px' id="+ model.name +"></div>" +
//									"</p>" +
//									"<div class='col-xs-3'>"+
//									"<button id='detail"+index+"' type='button' class='btn btn-default'>View details</button>" +
//									"</div>"+
//									"<div class='col-xs-3'>"+
//									"<button id='addBtn"+index+"' type='button' class='btn btn-default'>Add it to Info</button>" +
//									"</div>"+
//								"</div>";
//				} else if (index % 4 == 1) {
//					content += "<div class='col-xs-3 col-xs-offset-1'>" +
//									"<h4>"+ model.name + "</h4>" +
//									"<p>" +
//										"<div style='width:330px;height:210px' id="+ model.name +"></div>"+
//									"</p>" +
//									"<div class='col-xs-3'>"+
//									"<button id='detail"+index+"' type='button' class='btn btn-default'>View details</button>" +
//									"</div>"+
//									"<div class='col-xs-3'>"+
//									"<button id='addBtn"+index+"' type='button' class='btn btn-default'>Add it to Info</button>" +
//									"</div>"+
//								"</div>";
//				} else if (index % 4 == 3) {
//					content += "<div class='col-xs-3 col-xs-offset-1'>" +
//									"<h4>"+ model.name+ "</h4>" +
//									"<p>" +
//										"<div style='width:330px;height:210px' id="+ model.name +"></div>"+
//									"</p>" +
//									"<div class='col-xs-3'>"+
//									"<button id='detail"+index+"' type='button' class='btn btn-default'>View details</button>" +
//									"</div>"+
//									"<div class='col-xs-3'>"+
//									"<button id='addBtn"+index+"' type='button' class='btn btn-default'>Add it to Info</button>" +
//									"</div>"+
//							   "</div></div>";
//				} else {
//					content += "<div class='col-xs-3 col-xs-offset-1'>" +
//									"<h4>"+ model.name + "</h4>" +
//									"<p>" +
//										"<div style='width:330px;height:210px' id="+ model.name +"></div>"+
//									"</p>" +
//									"<div class='col-xs-3'>"+
//										"<button id='detail"+index+"' type='button' class='btn btn-default'>View details</button>" +
//									"</div>"+
//									"<div class='col-xs-3'>"+
//									"<button id='addBtn"+index+"' type='button' class='btn btn-default'>Add it to Info</button>" +
//									"</div>"+
//							   "</div>";
//				}
//			});
	if ((data.length % 6) >= 0 && (data.length % 6) < 5) {
		content += "</div>";
	}
	$("#carousel-inner").children().remove();
	$("#carousel-inner").append(content);
	addToInfoListener(data);
	viewDetailListener(data);
}


function drawModelChart(data) {
	$(data).each(function(index, model) {
		var zoomPanBoolean=false
		createSpcifiedModelChart(model, model.name,zoomPanBoolean);
	});
}

function searchModel(data){
	
	var searchCriteria = $("#selectModelCriteria").val();
	var keyWord = $("#searchKeyWord").val().toLowerCase();
	//console.log(searchCriteria + ": " + keyWord);
	if(keyWord != ""){
		console.log(searchCriteria + ": " + keyWord);
		$("#carousel-inner").children().remove();
		searchResult = filterModel(data,searchCriteria,keyWord);
		if (searchResult.length > 0) {
			$("#searchKeyWord").attr("class","inputText gray");
			$("#searchKeyWord").val(keyWord +"   " + searchResult.length +$.i18n.prop("trailsList.trailsList.results_totally"));
			createDivElement(searchResult);
			drawModelChart(searchResult);
			$("#infoTable tbody tr").each(function(i){
//				console.log($(this).find("td").eq(0).html());
				var selectedDivName=$(this).find("td").eq(0).html();
				$("#"+selectedDivName).parent().children(":last").find("a span").html("Unselect");
				$("#"+selectedDivName).parent().children(":last").find("a span").attr("class","glyphicon glyphicon-record");
				
			});
			modelComparisonListener(searchResult);
		}
	}
}

function resetModelList(){
	$("#carousel-inner").children().remove();
	searchResult = allModel;
	createDivElement(allModel);
	drawModelChart(allModel);
	$("#infoTable tbody tr").each(function(i){
//		console.log($(this).find("td").eq(0).html());
		var selectedDivName=$(this).find("td").eq(0).html();
		$("#"+selectedDivName).parent().children(":last").find("a span").html("Unselect");
		$("#"+selectedDivName).parent().children(":last").find("a span").attr("class","glyphicon glyphicon-record");
		
	});
}
function filterModel(data,criteria,keyWord){
	var result = [];
//	console.log("criteria: "+criteria)
	for(var i = 0; i< data.length; i++){
		var tmp = data[i][criteria];
//		console.log(tmp);
		if (criteria == "iterationCount") {
			if (tmp == keyWord){
				result.push(data[i]);
			}
		} else {
			tmp = tmp.toLowerCase();
			if (tmp.indexOf(keyWord) >= 0) {
				result.push(data[i]);
			}
		}
	}
	return result;
}


$(function() {
    // run the currently selected effect
   
 
    //callback function to bring a hidden box back
    function callback() {
      setTimeout(function() {
        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
      }, 10000 );
    };
 
    // set effect from select menu value
    $( "#button" ).click(function() {
      runEffect();
    });
    
    $("#closeTable").click(function(){
    	setTimeout(function() {
            $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
          }, 200 );
    	// $( "#effect" ).hide();
     });
 
    $( "#effect" ).hide();
  });
function runEffect() {
    
    // get effect type from
    var selectedEffect = "Slide";

    // most effect types need no options passed by default
    var options = {};
    // some effects have required parameters
     
    // run the effect
    $( "#effect" ).show( "slide", options, 500, callback );
  };
  function callback() {
      setTimeout(function() {
        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
      }, 100000 );
    };