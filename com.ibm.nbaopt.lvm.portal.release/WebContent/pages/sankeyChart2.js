sankeyChart2 = {
	sankeyFilter: {},
	selectedNode: "none",
	clickCallBack: function(d, i){},
	dbClickCallBack: function(d, i){},
	links:[],
	drawSankey: function (modelName, iteration, dataType, div) {
		d3.json(i18n.rootPath() + "/nba/business/"+modelName+"/"+iteration+"/"+dataType+"/"+buildFilter()+"/businessChart", drawSankey);
		function dataFormat(data) {
			var nodes = [];
			var links = [];
			data.links.forEach(function(link) {
				if(getNode(nodes, link.segmentId_from) == null) {
					var index = nodes.length;
					nodes.push({name: index, nodeId: link.segmentId_from, color: link.sourceNodeColor, r: link.sourceNodeSize, LTV: link.from_LTV, count: link.from_segmentCount, prob: link.prob, gain: link.gain});
				}
				if(getNode(nodes, link.segmentId_to) == null) {
					var index = nodes.length;
					nodes.push({name: index, nodeId:link.segmentId_to, color: link.targetNodeColor, r: link.targetNodeSize, LTV: link.to_LTV, count: link.to_segmentCount, prob: link.prob, gain: link.gain});
				}
				links.push({source:getNode(nodes, link.segmentId_from), target:getNode(nodes, link.segmentId_to), value:1, color:link.linkColor, prob: link.prob});
			});
			sankeyChart2.links = links;
			return {nodes:nodes, links:sankeyChart2.links};
		}
		function getNode(nodes, nodeId) {
			var _node;
			nodes.forEach(function(node){
				if (node.nodeId == nodeId) {
					_node = node;
				}
			})
			return _node;
		}
		function buildFilter() {
			var filter = "";
			if (sankeyChart2.sankeyFilter.selfConnection) {
				filter += "selfConnection:" + sankeyChart2.sankeyFilter.selfConnection + "::";
			}
			if (sankeyChart2.sankeyFilter.searchSegment) {
				filter += "searchSegment:" + sankeyChart2.sankeyFilter.searchSegment + "::";
			}
			if (filter == "") {
				filter = "none";
			}
			return filter;
		}
		function getHeight(data) {
			var nodes = data.nodes;
			var links = data.links;
			var height = 0;
			nodes.forEach(function(node) {
				var hasInput = false;
				var outputCount = 0;
				links.forEach(function(link) {
					if (link.target.nodeId == node.nodeId) {
						hasInput = true;
					}
					if (link.source.nodeId == node.nodeId) {
						outputCount += 12;
					}
				})
				if (!hasInput) {
					// 每个output占5 + 控制节点单位高度
					height += outputCount + 1;
				}
			})
			return height;
		}
		function drawSankey(error, data) {
			if (error) {
				return console.error(error);
			}
			if (data.links == null || data.links.length == 0) {
				$("#" + div).empty();
				$("#" + div).append("No Data!");
				return;
			}
			$("#" + div).empty();
			data = dataFormat(data);
			var h = getHeight(data);
			$("#" + div).height(h);
			var w = $("#" + div).width();
			var margin = {top: 1, right: 1, bottom: 6, left: 1},
		    w = w - margin.left - margin.right,
		    h = h - margin.top - margin.bottom;
			
			var svg = d3.select("#" + div).append("svg:svg").attr("width", w + margin.left + margin.right).attr("height", h + margin.top + margin.bottom)
				.append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
			var sankey = d3.sankey()
			.nodeWidth(1)
			.nodePadding(1)
			.nodes(d3.values(data.nodes))
			.links(data.links)
			.size([w, h])
			.layout(50);
			
			// 路径数据生成器
			var path = sankey.link();
			
		  var link = svg.append("g").selectAll("path")
		      .data(data.links)
		      .enter().append("path")
		      .attr({
			    	fill: function(d){return d.color;},   //填充色
			    	stroke: function(d,i){ return d.color; },  //描边色
			    	"fill-opacity": 0.5,  //描边透明度
			        "stroke-opacity": 0.1,  //描边透明度
			        "stroke-width": function (d) {  //连线的宽度
			          return Math.max(1, d.value);
			        },
			        d: path,  //路径数据
			        id: function(d,i){ return 'link' +i },  //ID
			    	title: function(d,i){ return "Prob: " + d.prob + " From: " + d.source.nodeId + " To: " + d.target.nodeId + " Value: " + d.value},
			     })
			     .sort(function(a, b) { 
			    	 return b.dy - a.dy;
			     });
		      
		  link.append("title")
		      .text(function(d) { return d.source.nodeId + " → " + d.target.nodeId + "\n" + d.prob; });

		  var node = svg.append("g").selectAll(".node")
		      .data(data.nodes)
		      .enter().append("g")
		      .attr("class", "node")
		      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
		      .call(d3.behavior.drag()
		      .origin(function(d) { return d; })
		      .on("dragstart", function() { this.parentNode.appendChild(this); })
		      .on("drag", dragmove))
		      .on("click",function(d,i){
		    	sankeyChart2.clickCallBack(d, i);
		      });
		  /*node
		      .filter(function(d){return d.x <= 1})
		      .attr("transform", function(d) { return "translate(" + (d.x + d.dy / 2) + "," + d.y + ")"; });
		  node
		      .filter(function(d){return d.x >= (w-2)})
		      .attr("transform", function(d) { return "translate(" + (d.x - d.dy / 2) + "," + d.y + ")"; });
		  node
		      .filter(function(d){return d.x > 1 && d.x < (w-2)})
		      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });*/

		  /*node.append("rect")
		      .attr("height", function(d) { return d.dy; })
		      .attr("width", sankey.nodeWidth())
		      .style("fill", function(d) { return d.color; })
		      .style("stroke", function(d) { return d.color; })
		      .on("click",function(d,i){
		    	  sankeyChart2.clickCallBack(d, i);
		      })
		      .append("title")
		      .text(function(d) { return d.nodeId; });*/
		  
		  node.append("circle")
			.attr("r", function(d) {return d.dy / 2;})
			.attr("cx", function(d) {return d.dx / 2;})
			.attr("cy", function(d) {return d.dy / 2;})
			.style("fill", function(d){return d.color;})
			.style("stroke", function(d){return d.color;})
			.append("title").text(function(d) {return "Segment " + d.nodeId + ": Size " + d.count + ", LTV " + d.LTV + ", Gain " + d.gain;});

		  node.append("text")
		  	.attr("x", -6)
			.attr("y", function(d) { return d.dy / 2; })
			.attr("dy", ".35em")
			.attr("text-anchor", "end")
			.attr("transform", null)
			.text(function(d) { return d.nodeId; })
			.filter(function(d) { return d.x < w / 2; })
			.attr("x", 6 + sankey.nodeWidth())
			.attr("text-anchor", "start");

		  function dragmove(d) {
			  d3.select(this).attr("transform", "translate(" + d.x + "," + (d.y = Math.max(0, Math.min(h - d.dy, d3.event.y))) + ")");
			  sankey.relayout();
			  link.attr("d", path);
		  }
		}
	},
	highlightNode: function (divId, nodeId) {
		// 判断被点击节点是否是已选中节点，如果是取消全局高亮，否则开启全局高亮
		var isHighLight = nodeId != sankeyChart2.selectedNode;
		var isNewHighLight = true;
		// 如果开启全局高亮效果，判断点击节点的相邻节点是否高亮，已高亮就不是一次新的全局高亮，没有高亮就是一次新的全局高亮
		if (isHighLight) {
			sankeyChart2.links.forEach(function(link) {
				if ((link.source.name == nodeId && link.target.isHighLight) || (link.target.name == nodeId && link.source.isHighLight)) {
					isNewHighLight = false;
				}
			});
		}
		// 如果全局高亮效果关闭，重置所有节点高亮为是，全亮
		// 如果全局高亮效果开启，如果是一次新高亮，设定所有节点状态为否
		if (!isHighLight) {
			sankeyChart2.links.forEach(function(link) {
				link.source.isHighLight = false;
				link.target.isHighLight = false;
			});
		} else {
			if (isNewHighLight) {
				sankeyChart2.links.forEach(function(link) {
					link.source.isHighLight = false;
					link.target.isHighLight = false;
				});
			}
			
			// 开启被点击节点和相邻节点高亮效果，添加到高亮集合
			sankeyChart2.links.forEach(function(link) {
				if (link.source.name == nodeId || link.target.name == nodeId) {
					link.source.isHighLight = true;
					link.target.isHighLight = true;
				}
			});
		}
		
		var svg = d3.select("#" + divId);
		svg.selectAll("path")
			.attr("stroke", function(d,i){
				if (d.source && d.target) {
					if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
						return d.color;
					} else {
						return "#f3f3f3";
					}
				}
			})
			.attr("fill-opacity", function(d,i){
				if (d.source && d.target) {
					if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
						return 0.5;
					} else {
						return 0;
					}
				}
			})
			.attr("stroke-opacity", function(d,i){
				if (d.source && d.target) {
					if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
						return 0.1;
					} else {
						return 0;
					}
				}
			});
		svg.selectAll("circle")
			.style("stroke", function(d,i){
				if (d.isHighLight || !isHighLight) {
					return d.color;
				} else {
					return "#f3f3f3";
				}
			})
			.style("fill", function(d,i){
				if (d.isHighLight || !isHighLight) {
					return d.color;
				} else {
					return "#f3f3f3";
				}
			});
		if (sankeyChart2.selectedNode == nodeId) {
			sankeyChart2.selectedNode = "none";
		} else {
			sankeyChart2.selectedNode = nodeId;
		}
		return isHighLight;
	}
}