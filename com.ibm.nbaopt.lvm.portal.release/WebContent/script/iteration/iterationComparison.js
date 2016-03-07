$(document).ready(function() {
	$("#home").html($.i18n.prop("iterationComparison.home"));
	$("#model_viewer_navigater").html($.i18n.prop("iterationComparison.model_viewer_navigater"));
	$("#model_selection_navigater").html($.i18n.prop("iterationComparison.model_selection_navigater"));
	$("#iteration_selection").html($.i18n.prop("iterationComparison.iteration_selection"));
	$("#feature_count_navigater").html($.i18n.prop("iterationComparison.feature_count_navigater"));
	$("#feature_count").html($.i18n.prop("iterationComparison.feature_count"));
	$("#segments_navigater").html($.i18n.prop("iterationComparison.segments_navigater"));
	$("#businessinsight_navigater").html($.i18n.prop("iterationComparison.businessinsight_navigater"));
	$("#note").html($.i18n.prop("iterationComparison.note"));
	$("#note1").html($.i18n.prop("iterationComparison.note1"));
	$("#segment_action_navigater").html($.i18n.prop("iterationComparison.segment_action_navigater"));
	$("#segment_action").html($.i18n.prop("iterationComparison.segment_action"));
	$("#description").html($.i18n.prop("iterationComparison.description"));
	$("#description1").html($.i18n.prop("iterationComparison.description1"));
	$("#segments").html($.i18n.prop("iterationComparison.segments"));
	$("#featureBarChart.Feature_Count").html($.i18n.prop("iterationComparison.featureBarChart.Feature_Count"));
	$("#iterationComparison.iteration").html($.i18n.prop("iterationComparison.iterationComparison.iteration"));
	$("#iterationComparison.sample").html($.i18n.prop("iterationComparison.iterationComparison.sample"));
	$("#iterationComparison.segment_id").html($.i18n.prop("iterationComparison.iterationComparison.segment_id"));
	$("#iterationComparison.matched_sample").html($.i18n.prop("iterationComparison.iterationComparison.matched_sample"));
	$("#iterationComparison.estimated_reward").html($.i18n.prop("iterationComparison.iterationComparison.estimated_reward"));
	$("#iterationComparison.historical_reward").html($.i18n.prop("iterationComparison.iterationComparison.historical_reward"));
	$("#iterationComparison.gain").html($.i18n.prop("iterationComparison.iterationComparison.gain"));
	$("#iterationComparison.seg_id").html($.i18n.prop("iterationComparison.iterationComparison.seg_id"));
	$("#select_segment").html($.i18n.prop("iterationComparison.select_segment"));
	$("#iterationComparison.iteration_segact").html($.i18n.prop("iterationComparison.iterationComparison.iteration_segact"));
	$("#iterationComparison.iteration_seg").html($.i18n.prop("iterationComparison.iterationComparison.iteration_seg"));
	$("#iterationComparison.iteration_FeaCou").html($.i18n.prop("iterationComparison.iterationComparison.iteration_FeaCou"));
	 $.ajaxSetup({  
		    async : false  
		});

	// Default Action
	$(".tab_content").hide(); // Hide all content
	$("ul.tabs li:first").addClass("active").show(); // Activate first tab
	$(".tab_content:first").show(); // Show first tab content
	var iterationsArray = sessionStorage.getItem("iterations").split(",");
	var modelName = sessionStorage.getItem("modelName");
	displayIterationFeature(modelName, iterationsArray);
	displayIterationSegment(modelName, iterationsArray);
	displayIterationTransaction(modelName, iterationsArray);
	displayFeatureBarChartsSingle(modelName,iterationsArray);
	$(".flot-text .flot-y-axis").css('left','-20px');
	displayIterationActionAllocation(modelName, iterationsArray);
	displayIterationResourceAllocation(modelName, iterationsArray);
	displaySegmentAction(modelName,iterationsArray);
	sortFeatureCount(iterationsArray,1);

//	// Default Action
//	$(".tab_content").hide(); // Hide all content
//	$("ul.tabs li:first").addClass("active").show(); // Activate first tab
//	$(".tab_content:first").show(); // Show first tab content

	// On Click Event
	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active"); // Remove any "active" class
		$(this).addClass("active"); // Add "active" class to selected tab
		$(".tab_content").hide(); // Hide all tab content
		var activeTab = $(this).find("a").attr("href"); // Find the rel attribute
		// value to identify the active tab + content
		$(activeTab).fadeIn(1); // Fade in the active content
		return false;
	});
	
	$("#viewPieChart").click(function(event){
		if ($("#pieChartList").children().length == 0) {
			displayPieCharts(modelName,iterationsArray);
		}
		$("#pieChartGroup").css("display", "block");
	});
	$("#viewMostUsedFeature").click(function(event){
		if ($("#featureBarChartList").children().length == 0) {
			displayFeatureBarCharts(modelName,iterationsArray);
		}
		$("#topUsedfeaturesGroup").css("display", "block");
	});
	
	$("[role='table-ctxt'] tr").hover(function(){
		$(this).css("background","#8CC7F2");
	},function(){
		$(this).css("background","");
	})
});

function enterSegmentSearching(event){
	if(event && event.keyCode == 13){
		displayBusinessInsight(event.target.id.replace("searchSegment", ""));
	}
}

function displayBusinessInsight(div) {
	arrowForce.arrowForceFilter.selfConnection = $("#btn_hideShowSelfConnection" + div).val();
	arrowForce.arrowForceFilter.searchSegment = $("#searchSegment" + div).val();
	var divId = "businessInsightContent" + div;
	var iteration = sessionStorage.getItem("iterations").split(",");
	var modelName = sessionStorage.getItem("modelName");
	// arrowForceFilter: $("#select1  option:selected").text();
	arrowForce.finished = function(data) {
		var category = data.category;
		$("#segmentCategroy" + div).empty();
		var content = "<tbody>";
		content += "<tr>";
		content += "<td>" + category.greatPercent + "</td>";
		content += "<td>" + category.goodPercent + "</td>";
		content += "<td>" + category.mediumPercent + "</td>";
		content += "<td>" + category.worsePercent + "</td>";
		content += "<td>" + category.poorPercent + "</td>";
		content += "</tr>";
		content += "<tr>";
		content += "<td style='text-align: left;'>&nbsp;&nbsp;" + $.i18n.prop("iterationComparison.arrowforce.category.AVG") + ": " + category.greatAvgLTV + "</td>";
		content += "<td style='text-align: left;'>&nbsp;&nbsp;" + $.i18n.prop("iterationComparison.arrowforce.category.AVG") + ": " + category.goodAvgLTV + "</td>";
		content += "<td style='text-align: left;'>&nbsp;&nbsp;" + $.i18n.prop("iterationComparison.arrowforce.category.AVG") + ": " + category.mediumAvgLTV + "</td>";
		content += "<td style='text-align: left;'>&nbsp;&nbsp;" + $.i18n.prop("iterationComparison.arrowforce.category.AVG") + ": " + category.worseAvgLTV + "</td>";
		content += "<td style='text-align: left;'>&nbsp;&nbsp;" + $.i18n.prop("iterationComparison.arrowforce.category.AVG") + ": " + category.poorAvgLTV + "</td>";
		content += "</tr>";
		var line = 0;
		while(true) {
			var breakout = true;
			if (category.great.length > line) {
				content += "<tr>";
				content += "<td style='text-align: left;' title='LTV: " + category.great[line].LTV + ", Size: " + category.great[line].count + "'>&nbsp;&nbsp;" + category.great[line].id + ": " + category.great[line].LTV + "</td>";
				breakout = false;
			} else {
				content += "<tr>";
				content += "<td> &nbsp; </td>";
			}
			if (category.good.length > line) {
				content += "<td style='text-align: left;' title='LTV: " + category.good[line].LTV + ", Size: " + category.good[line].count + "'>&nbsp;&nbsp;" + category.good[line].id + ": " + category.good[line].LTV + "</td>";
				breakout = false;
			} else {
				content += "<td> &nbsp; </td>";
			}
			if (category.medium.length > line) {
				content += "<td style='text-align: left;' title='LTV: " + category.medium[line].LTV + ", Size: " + category.medium[line].count + "'>&nbsp;&nbsp;" + category.medium[line].id + ": " + category.medium[line].LTV + "</td>";
				breakout = false;
			} else {
				content += "<td> &nbsp; </td>";
			}
			if (category.worse.length > line) {
				content += "<td style='text-align: left;' title='LTV: " + category.worse[line].LTV + ", Size: " + category.worse[line].count + "'>&nbsp;&nbsp;" + category.worse[line].id + ": " + category.worse[line].LTV + "</td>";
				breakout = false;
			} else {
				content += "<td> &nbsp; </td>";
			}
			if (category.poor.length > line) {
				content += "<td style='text-align: left;' title='LTV: " + category.poor[line].LTV + ", Size: " + category.poor[line].count + "'>&nbsp;&nbsp;" + category.poor[line].id + ": " + category.poor[line].LTV + "</td>";
				content += "</tr>";
				breakout = false;
			} else {
				content += "<td> &nbsp; </td>";
				content += "</tr>";
			}
			if (breakout) {
				break;
			}
			line++;
		}
		content += "</tbody>";
		$("#segmentCategroy" + div).append(content);
		$("#best" + div).attr("title", "Avg LTV: " + category.greatAvgLTV);
		$("#better" + div).attr("title", "Avg LTV: " + category.goodAvgLTV);
		$("#normal" + div).attr("title", "Avg LTV: " + category.mediumAvgLTV);
		$("#worse" + div).attr("title", "Avg LTV: " + category.worseAvgLTV);
		$("#worst" + div).attr("title", "Avg LTV: " + category.poorAvgLTV);
	}
	arrowForce.clickCallBack = function(d, i){
		$("#selectedSegmentId" + div).empty();
		$("#selectedSegmentId" + div).append(d.name);
		arrowForce.highlightNode(divId, d.name);
		$.get("../../nba/business/"+modelName+"/"+iteration+"/" + d.name + "/segmentInfo", function(data){
			$("#rule"+div).empty();
			$("#rule"+div).append(data.rule);
			var content = "<tbody>";
			$(data.action).each(function(index, d){
				content += "<tr>" +
				"<td>" + d.action+"</td>" +
				"<td>" + d.coefficient + "</td>" +
				"<td>" + d.actualCount + "</td>" +
				"<td>" + d.allocationCount + "</td>" +
				"</tr>";
			});
			content += "</tbody>";
			$("#segmentActionStat"+div).empty();
			$("#segmentActionStat"+div).append(content);
			content = "<tbody>";
			$(data.info).each(function(index, d){
				for (var k in d) {
					content += "<tr>" +
					"<td>" + k +"</td>" +
					"<td>" + d[k] + "</td>" +
					"</tr>";
				}
			});
			content += "</tbody>";
			$("#segmentInfo"+div).empty();
			$("#segmentInfo"+div).append(content);
		}, "json");
	};
	arrowForce.drawArrowForce(modelName, iteration, $("input[name='forceDataType" + div + "']:checked").val(), divId);
}

function hideShowSelfConnection(div) {
	if ($("#btn_hideShowSelfConnection" + div).val() == "showing") {
		$("#btn_hideShowSelfConnection" + div).val("hidding");
		$("#btn_hideShowSelfConnection" + div).text($.i18n.prop("iterationComparison.iterationComparison.show_self_connection"));
	} else {
		$("#btn_hideShowSelfConnection" + div).val("showing");
		$("#btn_hideShowSelfConnection" + div).text($.i18n.prop("iterationComparison.iterationComparison.hide_self_connection"));
	}
	displayBusinessInsight(div);
}

function displayFeatureBarCharts(modelName,iterationsArray){
	$(iterationsArray).each(function(index,iteration){
		$.get("../../nba/feature/"+modelName+"/"+iteration+"/featureMostUsed", function(featureData){
			console.log(featureData);
			if(index==0){
				$("#featureBarChartList").append(
						"<div class='item active'>" +
						"<div class='col-xs-8 col-xs-offset-3'>" +
						"<h4>"+ "Iteration "+(iteration) + "</h4>" +
							"<div style='width:500px;height:450px' id="+ "FeatureBarChart"+(index+1)+"></div>" +
						 "</div>" +
						"</div>");
				}
				else{
					$("#featureBarChartList").append(
							"<div class='item'>" +
							"<div class='col-xs-8 col-xs-offset-3'>" +
							"<h4>"+ "Iteration "+(iteration) + "</h4>" +
							"<p>" +
								"<div style='width:500px;height:450px' id="+ "FeatureBarChart"+(index+1)+"></div>" +
							"</p>" + "</div>" +
							"</div>");
				}
			createBarChart(featureData.Count,featureData.Name,"FeatureBarChart"+(index+1));
			$("#FeatureBarChart"+(index+1)).live("plothover",gainFeatureHover);
		}, "json");
	});
}

function displayFeatureBarChartsSingle(modelName,iterationsArray){
	$(iterationsArray).each(function(index,iteration){
		$.get("../../nba/feature/"+modelName+"/"+iteration+"/featureMostUsed", function(featureData){
			if(index==0){
				$("#featureContent").append(
						"<div class='featureChart' style='margin-top:5px;'>" +
						"<div class='col-xs-6 col-lg-5 col-xs-offset-3 col-lg-offset-1'>" +
//						"<h4 class='table-header'>"+ $.i18n.prop("iterationComparison.iterationComparison.iteration")+(iteration) + "</h4>" +
							"<div style='width:450px;' id='"+ "FeatureBarChart"+(index+1)+"'></div>" +
						 "</div>" +
						"</div>");
				} else {
					$("#featureContent").append(
						"<div class='item' style='margin-top:5px;'>" +
						"<div class='col-xs-6 col-lg-5 col-xs-offset-3 col-lg-offset-1'>" +
//						"<h4>"+ "Iteration "+(iteration) + "</h4>" +
						"<p>" +
							"<div style='width:450px;' id='"+ "FeatureBarChart"+(index+1)+"'></div>" +
						"</p>" + "</div>" +
						"</div>");
				}
			$("#FeatureBarChart"+(index+1)).height($("#featureGrid").height() - 10);
			createBarChart(featureData.Count,featureData.Name,"FeatureBarChart"+(index+1));
			$("#FeatureBarChart"+(index+1)).live("plothover",gainFeatureHover);
		}, "json");
	});
}

function displayPieCharts(modelName,iterationsArray){
		$(iterationsArray).each(function(index,iteration){
			$.get("../../nba/optimizer/" + modelName + "/"+iteration+"/actionPie", function(data){
				if(index==0){
					$("#pieChartList").append(
							"<div class='item active'>" +
							"<div class='col-xs-8 col-xs-offset-3'>" +
							"<h4>"+ "Iteration "+(iteration) + "</h4>" +
							
								"<div style='width:500px;height:450px' id="+ "PieChart"+(index+1)+"></div>" +
							 "</div>" +
							"</div>");
					}
					else{
						$("#pieChartList").append(
								"<div class='item'>" +
								"<div class='col-xs-8 col-xs-offset-3'>" +
								"<h4>"+ "Iteration "+(iteration) + "</h4>" +
								"<p>" +
									"<div style='width:500px;height:450px' id="+ "PieChart"+(index+1)+"></div>" +
								"</p>" + "</div>" +
								"</div>");
					}
				createIterationPieChart(data,false,"PieChart"+(index+1));
				$("#"+"PieChart"+(index+1)).live("plothover",singlePieHover);
			}, "json");
		});
}


function sortFeatureCount(iterationsArray,flag){
	$(iterationsArray).each(function(index,iteration){
		$("#featureBtn"+iteration).live("click",function(){
			
			var items=$("#ul"+iteration).find("li").get();
			
			if(flag==1){
				items.sort(function(a,b){
					var eleA=parseInt($(a).find("span").text());
					var eleB=parseInt($(b).find("span").text());
					flag=0;
					$("#featureBtn"+iteration).find('span').attr("class","glyphicon glyphicon-sort-by-attributes");
					return eleA-eleB;
				});
			}
			else{
				items.sort(function(a,b){
					var eleA=parseInt($(a).find("span").text());
					var eleB=parseInt($(b).find("span").text());
					flag=1;
					$("#featureBtn"+iteration).find('span').attr("class","glyphicon glyphicon-sort-by-attributes-alt");
					return eleB-eleA;
				});
			}
			$("#ul"+iteration).empty();
			$.each(items,function(i,li)       
			  {  
				$("#ul"+iteration).append(li);  
			  });
		});
	});
}


function displayIterationFeature(modelName, iterationsArray){
	$(iterationsArray).each(function(index,iteration){
		$.get("../../nba/feature/"+modelName+"/"+iteration+"/featureCount", function(data){
			var content = "<div id='featureGrid' class='col-xs-8 col-lg-5 col-xs-offset-3 col-lg-offset-0'><h4 class='table-header'>"+ $.i18n.prop("iterationComparison.iterationComparison.iteration_FeaCou")+iteration+"</h4>" +
							"<ul id='ul" +iteration+"' class='list-group'>";
			$(data).each(function(index , d){
				content+='<li class="list-group-item" style="padding: 2px 2px;">'+d.featureName+'<span class="badge" style="float:right;">'+d.featureCount+'</span></li>';
			});
			content += "</ul></div>";
			$("#featureContent").append(content);
		}, "json");
	});
}

function displayIterationSegment(modelName, iterationsArray){
	$(iterationsArray).each(function(index,iteration){
		$.get("../../nba/segment/"+modelName+"/"+iteration+"/segmentstat", function(data){
			var content = "<div class='segment-Table'>" +
						"<h4 class='table-header'>"+ $.i18n.prop("iterationComparison.iterationComparison.iteration_seg")+iteration+"</h4>" +
						"<table id='segTable"+iteration+ "' class='table table-striped table-hover' style='margin-bottom:0;'>" +
							"<thead>" +
								"<tr>" +
									"<th>"+$.i18n.prop("iterationComparison.iterationComparison.segment_id")+"</th>" +
									"<th>"+$.i18n.prop("iterationComparison.iterationComparison.sample")+"</th>" +
									"<th>"+$.i18n.prop("iterationComparison.iterationComparison.matched_sample")+"</th>" +
									"<th>"+$.i18n.prop("iterationComparison.iterationComparison.estimated_reward")+"</th>" +
									"<th>"+$.i18n.prop("iterationComparison.iterationComparison.historical_reward")+"</th>" +
									"<th>"+$.i18n.prop("iterationComparison.iterationComparison.gain")+"</th>" +
								"</tr>" +
							"</thead>" +
							"<tbody>"+
							"</tbody></table>"+
							"<table id='segTable_trs"+iteration+ "'class='table table-striped table-hover' role='table-ctxt'><tbody>";
			$(data.items).each(function(index, d){
				content += "<tr>" +
						"<td>"+d.segmentID+"</td>" +
						"<td>"+ parseFloat(d.segmentSize).toLocaleString()+
						"</td><td>"+parseFloat(d.matchedSampleSize).toLocaleString()+
						"</td><td>"+parseFloat(d.qEstValueModel).toLocaleString()+
						"</td><td>"+parseFloat(d.vActual).toLocaleString()+
						"</td><td>"+parseFloat(d.gain).toLocaleString()+
						"</tr>";
			});
			content += "</tbody></table></div>";
			$("#segmentContent").append(content);
			//displayIterationOptimization(data, iteration);
		}, "json");
	});
}

//working on Liang Ge
function displayIterationSegmentTable(modelName, iterationsArray){
	$(iterationsArray).each(function(index,iteration){
		$.get("../../nba/segment/"+modelName+"/"+iteration+"/segmentstat", function(data){
			var content = "<div class='segment-Table'>" +
						"<h4 class='table-header'>Iteration "+iteration+"</h4>" +
						"<table id='segTable"+iteration+ "'class='table table-striped' style='margin-bottom:0;'>" +
							"<thead>" +
								"<tr>" +
									"<th>Segment<br>ID</th>" +
									"<th>Sample</th>" +
									"<th>Matched<br>Sample</th>" +
									"<th>Estimated<br>Reward</th>" +
									"<th>Historical<br>Reward</th>" +
									"<th>Gain</th>" +
								"</tr>" +
							"</thead>" +
							"<tbody>"+
							"</tbody></table></div><div style='overflow-y: auto; height: 730px;font-size:20px;' >"+
							"<table id='segTable_trs"+iteration+ "'class='table' role='table-ctxt'><tbody>";
			$(data.items).each(function(index, d){
				content += "<tr>" +
						"<td>"+d.segmentID+"</td>" +
						"<td>"+ parseFloat(d.segmentSize).toLocaleString()+
						"</td><td>"+parseFloat(d.matchedSampleSize).toLocaleString()+
						"</td><td>"+parseFloat(d.qEstValueModel).toLocaleString()+
						"</td><td>"+parseFloat(d.vActual).toLocaleString()+
						"</td><td>"+parseFloat(d.gain).toLocaleString()+
						"</tr>";
			});
			content += "</tbody></table></div>";
			$("#segmentContent").append(content);
			//displayIterationOptimization(data, iteration);
		}, "json");
	});
}

function displayIterationTransaction(modelName, iterationsArray){
	var data1 = [];
	var data2 = [];
	$(iterationsArray).each(function(index,iteration){
		$.ajax({
            url: "../../nba/segment/"+modelName+"/"+iteration+"/sequence",
            dataType: "json",
            async : false,
            success: function(data){
            	data1.push([iteration, data['T2-T1']]);
    			data2.push([iteration+'.4', data['T3-T2']]);
            }
        });
	});
	var dataset = [ {
		label : "T2-T1",
		data : data1,
		color : "#0077FF"
	}
	, {
		label : "T3-T2",
		data : data2,
		color : "#7D0096"
	}
	];

	createSpcifiedStackChart(dataset, "transactionChart");
	$("#transactionChart").live("plothover",transactionStackHover);
}

function displayIterationActionAllocation(modelName, iterationsArray){
	var allData = [];
	var iterations = [];
	$(iterationsArray).each(function(index,iteration){
//		$.get("../../nba/optimizer/" + modelName + "/"+iteration+"/actionPie", function(data){
//			var targetDomId = "actionAllocationchart" + iteration;
//			$("#pieCharts").append(
//				"<div class='col-xs-4 '><h2 class='page-header'>Iteration " + iteration
//				+ "</h2><div style='width:250px;height:180px;margin:0 auto' id='"
//				+ targetDomId + "'></div></div>");
//			createIterationPieChart(data,false,targetDomId);
//		}, "json");
		$.ajax({
			url:"../../nba/optimizer/" + modelName + "/"+iteration+"/actionPie",
			async:false,
			type:"GET",
			dataType:"json",
			success:function(data){
				var targetDomId = "actionAllocationchart" + iteration;
				$("#pieCharts").append(
					"<div class='col-xs-4 '><h4 class='page-header'>Iteration " + iteration
					+ "</h4><div style='width:380px;height:180px;margin:0 auto' id='"
					+ targetDomId + "'></div></div>");
				createIterationPieChart(data,false,targetDomId);
				$("#"+targetDomId).live("plothover",multiplePieHover);
				allData.push(data);
				iterations.push(iteration);
			}
		});
	});
	displayActAllocTable(iterations, allData);
}

function displayActAllocTable(iterations, allData){
	if(iterations.length != 0)
		createActAllocTableHead(iterations);
	if(allData.length != 0)
		addDataToActAllocTable(allData);
}

function createActAllocTableHead(iterations){
	var content = "<thead> <tr class='active'><td rowspan='2'>Action</td>";
	var tmp = "<tr class='success'>";
	for (var i = 0; i < iterations.length; i++){
		content += "<td colspan='2'>Iteration " + iterations[i] + "</td>"; 
		tmp += "<td>Value</td>";
		tmp += "<td>Percentage</td>";
	}
	tmp += "</tr>";
	content += "</tr>";
	content += tmp;
	content += "</thead><tbody></tbody>";
	$("#actionAllocTable").append(content);
}

function addDataToActAllocTable(data){
	var allSum = [];
	var total = "<tr class='info'><td>Total</td>";
	for(var i = 0; i< data.length; i++){
		var sum = 0;
		for(var j = 0; j<data[0].length; j++){
			sum += data[i][j]['data'];
		}
		allSum.push(sum);
		total += "<td>" + parseFloat(sum.toFixed(2)).toLocaleString() + "</td>" + "<td>100%</td>";		
	}
	total += "</tr>";
	//console.log(allSum);
	var content="";
	for(var i = 0; i< data[0].length; i++){
		content += "<tr><td>" + data[0][i]['label'] + "</td>";
		for(var j = 0; j< data.length; j++){			
			content += "<td>" + parseFloat(data[j][i]['data']).toLocaleString() + "</td>";
			content += "<td>" + (data[j][i]['data'] / allSum[j] * 100).toFixed(2).toLocaleString() + "%</td>";
		}
		content += "</tr>";
	}
	$("#actionAllocTable").append(content);
	$("#actionAllocTable").append(total);
}

function displayIterationResourceAllocation(modelName, iterationsArray){
	$(iterationsArray).each(function(index, iteration){
		$.get("../../nba/optimizer/"+modelName+"/"+iteration+"/resourceAllocation", function(data){
			var content = "<div class='col-sm-4'>" +
							"<h4 class='page-header'>Iteration "+iteration+"</h4>" +
							"<table class='table table-striped' id='resTable"+iteration+ "'>" +
								"<thead>" +
									"<tr>" +
										"<th>Name</th>" +
										"<th>Allocation</th>" +
										"<th>Ratio</th>" +
									"</tr>" +
								"</thead>" +
							"<tbody>";
			$(data).each(function(index, d){
				content += '<tr><td>'+d.resourceName+'</td><td>'+parseFloat(d.allocation).toLocaleString()+'</td><td>'+(d.ratio).toFixed(2)+'</td></tr>';
			});
			content += '</tbody></table></div>';
			$("#res-allocation").append(content);
		}, "json");
	});
}

function displayIterationOptimization(data, iteration){
	var content = "<div class='col-sm-4'>" +
					"<h4 class='page-header'> Iteration "+iteration+"</h4>" +
						"<table class='table table-striped' id='optTable"+iteration+ "'>" +
							"<thead>" +
								"<tr>" +
									"<th>ID</th>" +
									"<th>VACTUAL</th>" +
									"<th>QESTVALUE</th>" +
								"</tr>" +
							"</thead>" +
							"<tbody>";
	$(data.items).each(function(index, d){
		content += "<tr>" +
						"<td>"+d.segmentID+"</td>" +
						"<td>"+parseFloat(d.vActual).toFixed(2).toLocaleString()+"</td>" +
						"<td>"+parseFloat(d.qEstValueModel).toFixed(2).toLocaleString()+"</td>" +
				   "</tr>";
	});
	content += '</tbody></table></div>';
	$("#segmentOptimizer").append(content);
}

function displaySegmentAction(modelName, iterationsArray){
	$(iterationsArray).each(function(index, iteration){
		var content="<div class='segment-Table'>"+
						"<h4 class='table-header'>"+ $.i18n.prop("iterationComparison.iterationComparison.iteration_segact")+iteration+"</h4>"+
							"<table class='table table-striped table-hover' id='segActTable"+iteration+ "'>"+
								"<thead>"+
									"<tr>"+
										"<th>"+$.i18n.prop("iterationComparison.iterationComparison.seg_id")+"</th>"+
									"</tr>"+
								"</thead>"+
								"<tbody id='tbody"+iteration+"'>"+
								"</tbody>"+
							"</table>"+
					"</div>";
		$("#segmentActionContent").append(content);
		
	});
		
	$.get("../../nba/action/"+modelName+"/actionstat", function(data){
		$(data).each(function(index,d){
//			console.log(d);
			var thead="<th>"+d+"</th>";
			$("#segmentActionContent").find("thead tr").append(thead);
		});
	}, "json");
	
	$(iterationsArray).each(function(index,iteration){
		$.get("../../nba/action/"+modelName+"/"+iteration+"/actionstat",function(data){
			$(data).each(function(index,d){
				var tr="<tr>";
				$(d.allocatedCount).each(function(i,td){
					var td = "<td>"+parseFloat(td).toLocaleString()+"</td>";
					tr+=td;
				});
				tr+="</tr>";
				$("#tbody"+iteration).append(tr);
			});
		});
	});


}

//function addActionAllocationTableContent(data){
//	$(data).each(
//	   function(index, model) {
//				$("#addBtn"+index).bind("click", function(){
//					if($("#addSpan"+index).html() != "Unselect"){
//						$("#addSpan"+index).html("Unselect");
//						$("#addSpan"+index).attr("class","glyphicon glyphicon-record");
//						$("#infoTable").append(
//								"<tr class='bg-success'>"
//								+"<td>"+model.name+"</td>"
//								+"<td>"+model.iterationCount+"</td>" 
//								//+"<td>"+model.description+"</td>" 
//								//+"<td>"+model.startTime+"</td>" 
//								//+"<td>"+model.endTime+"</td>"
//								//+"<td>"+model.timeCost+"</td>"
//								+"<td><button id='modeldel"+index+"' type='button' name='delBtn' class='btn btn-danger btn-xs'><span class='glyphicon glyphicon-remove'></span></button>"+
//						"</td></tr>");  
//						//if($("#effect").attr())
//						if(document.getElementById("infoTable").rows.length>1){
//						   runEffect();
//						}
//					}else{
//						$("#addSpan"+index).html("Select");
//						$("#addSpan"+index).attr("class","glyphicon glyphicon-unchecked");
//						$("#modeldel"+index).parent().parent().remove();
//						if(document.getElementById("infoTable").rows.length==1){
//							setTimeout(function() {
//						        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
//						      }, 500 );
//							//$("#effect").hide();
//						}
//					}
//				});
//				$("#infoTable").on('click', "#modeldel"+index, function (event) {
//					$("#addSpan"+index).html("Select");
//					$("#addSpan"+index).attr("class","glyphicon glyphicon-unchecked");
//					$(this).parent().parent().remove();  
//				});
//	  });
//}