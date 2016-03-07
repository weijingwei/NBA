sankeyChart = {
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
			sankeyChart.links = links;
			return {nodes:nodes, links:sankeyChart.links};
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
			if (sankeyChart.sankeyFilter.selfConnection) {
				filter += "selfConnection:" + sankeyChart.sankeyFilter.selfConnection + "::";
			}
			if (sankeyChart.sankeyFilter.searchSegment) {
				filter += "searchSegment:" + sankeyChart.sankeyFilter.searchSegment + "::";
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
						outputCount++;
					}
				})
				if (!hasInput) {
					height += outputCount + 12;
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
			var svg = d3.select("#" + div).append("svg:svg").attr("width", w).attr("height", h);
			var links = data.links;
			var nodes = data.nodes;
			var sankey = d3.sankey()
						.nodeWidth(50)
						.nodePadding(1)
						.nodes(d3.values(nodes))
						.links(links)
						.size([w, h])
						.layout(100);
			// 路径数据生成器
			var path = sankey.link();
			// 绘制连接数据
			var links = svg.append("g").selectAll("path").data(data.links).enter();
			// 绑定节点数据
			var nodes = svg.append("g").selectAll(".node").data(data.nodes).enter();
			// 绘制连接线
			var paths = links.append("path")
					    .attr({
					    	fill: "none",   //填充色
					    	stroke: function(d,i){ return d.color; },  //描边色
					        "stroke-opacity": 0.5,  //描边透明度
					        "stroke-width": function (d) {return Math.max(1, d.dy);},  //连线的宽度
					        d: path,  //路径数据
					        id: function(d,i){ return 'link' +i },  //ID
					    	title: function(d,i){ return "Prob: " + d.prob + " From: " + d.source.nodeId + " To: " + d.target.nodeId + " Value: " + d.value},
					     })
					     .sort(function(a, b) {return b.dy - a.dy;});

			// 绘制圆形节点   
			/*nodes.append("circle")
			    .attr("transform",function (d) {
			          return "translate(" + d.x + "," + d.y + ")";
			       })
		        .attr("r", function(d) { return d.dy / 2; })
		        .attr("cx", function(d) { return d.dx / 2; })
		        .attr("cy", function(d) { return d.dy / 2; })
		        .style("fill", function(d){return d.color})
		        .style("stroke", function(d){return d.color})
		        .call(d3.behavior.drag()
                .origin(function(d) { return d; })
                .on("drag", dragmove));*/
			// 绘制矩形节点   
			nodes.append("rect").attr("class", "node")
				.attr({
					x: function (d) { return d.x; },
	                y: function (d) { return d.y; },
	                height: function (d) {return d.dy;},
	                width: function(d){return sankey.nodeWidth() + sankey.nodeWidth() * (d.r - 2) * 0.015},
	                fill: function(d){return d.color},
	                title: function(d,i){return "Segment " + d.nodeId + ": Size " + d.count + ", LTV " + d.LTV + ", Gain " + d.gain},
				})
				.call(d3.behavior.drag().origin(function(d) { return d; }).on("drag", dragmove))
				.on("click",function(d,i){sankeyChart.clickCallBack(d, i);});
			// 绘制节点文本
			  var text = nodes.append("text")
				        .attr({
				            x: function (d) { return d.x + sankey.nodeWidth() / 2 - 12; },
				            y: function (d) { return d.y + d.dy / 2 + 6; }
				        }).text(function (d) { return d.nodeId; });
			// 绘制连接文本
			  /*links.append('text')
			        .append('textPath')
			        .attr('xlink:href', function (d,i) { return '#link' + i; })
			        .attr('startOffset','50%')
			        .text(function (d) { return 'Prob:' + d.prob; });*/
			  
			// 拖动事件响应函数
			  function dragmove(d) {
				  d3.select(this).attr({
			    	   // 矩形节点拖拽
//			    	   "x": (d.x = Math.max(0, Math.min(w - d.dx, d3.event.x))),
			    	   "y": (d.y = Math.max(0, Math.min(h - d.dy, d3.event.y))),
			    	   
			    	   // 圆形节点拖拽
				      /*"transform": function (d) {
				    	  d.x = Math.max(0, Math.min(w - d.dx, d3.event.x)); 
				    	  d.y = Math.max(0, Math.min(h - d.dy, d3.event.y)); 
				    	  return "translate(" + d.x + "," + d.y + ")";
				      }*/
			       });
			       sankey.relayout();
			       paths.attr('d',path);
			       text.attr({
			    	   x: function (d) { return d.x + sankey.nodeWidth() / 2 - 12; },
			           y: function (d) { return d.y + d.dy / 2 + 6; },
			    	   /*x: function (d) { return d.x + 6; },
			           y: function (d) { return d.y+d.dy / 2 + 6; }*/
			       })
			  };
		}
	},
	highlightNode: function (divId, nodeId) {
		// 判断被点击节点是否是已选中节点，如果是取消全局高亮，否则开启全局高亮
		var isHighLight = nodeId != sankeyChart.selectedNode;
		var isNewHighLight = true;
		// 如果开启全局高亮效果，判断点击节点的相邻节点是否高亮，已高亮就不是一次新的全局高亮，没有高亮就是一次新的全局高亮
		if (isHighLight) {
			sankeyChart.links.forEach(function(link) {
				if ((link.source.name == nodeId && link.target.isHighLight) || (link.target.name == nodeId && link.source.isHighLight)) {
					isNewHighLight = false;
				}
			});
		}
		// 如果全局高亮效果关闭，重置所有节点高亮为是，全亮
		// 如果全局高亮效果开启，如果是一次新高亮，设定所有节点状态为否
		if (!isHighLight) {
			sankeyChart.links.forEach(function(link) {
				link.source.isHighLight = false;
				link.target.isHighLight = false;
			});
		} else {
			if (isNewHighLight) {
				sankeyChart.links.forEach(function(link) {
					link.source.isHighLight = false;
					link.target.isHighLight = false;
				});
			}
			
			// 开启被点击节点和相邻节点高亮效果，添加到高亮集合
			sankeyChart.links.forEach(function(link) {
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
			.attr("stroke-opacity", function(d,i){
				if (d.source && d.target) {
					if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
						return 0.5;
					} else {
						return 0;
					}
				}
			});
		svg.selectAll("rect")
			.attr("fill", function(d,i){
				if (d.isHighLight || !isHighLight) {
					return d.color;
				} else {
					return "#f3f3f3";
				}
			});
		if (sankeyChart.selectedNode == nodeId) {
			sankeyChart.selectedNode = "none";
		} else {
			sankeyChart.selectedNode = nodeId;
		}
		return isHighLight;
	},
	highlightNodeToTop: function (divId, nodeId) {
		// 判断被点击节点是否是已选中节点，如果是取消全局高亮，否则开启全局高亮
		var isHighLight = nodeId != sankeyChart.selectedNode;
		var isNewHighLight = true;
		// 如果开启全局高亮效果，判断点击节点的相邻节点是否高亮，已高亮就不是一次新的全局高亮，没有高亮就是一次新的全局高亮
		if (isHighLight) {
			sankeyChart.links.forEach(function(link) {
				if ((link.source.name == nodeId && link.target.isHighLight) || (link.target.name == nodeId && link.source.isHighLight)) {
					isNewHighLight = false;
				}
			});
		}
		// 如果全局高亮效果关闭，重置所有节点高亮为是，全亮
		// 如果全局高亮效果开启，如果是一次新高亮，设定所有节点状态为否
		if (!isHighLight) {
			sankeyChart.links.forEach(function(link) {
				link.source.isHighLight = false;
				link.target.isHighLight = false;
			});
		} else {
			if (isNewHighLight) {
				sankeyChart.links.forEach(function(link) {
					link.source.isHighLight = false;
					link.target.isHighLight = false;
				});
			}
			
			// 开启被点击节点和相邻节点高亮效果，添加到高亮集合
			sankeyChart.links.forEach(function(link) {
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
		.attr("stroke-opacity", function(d,i){
			if (d.source && d.target) {
				if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
					return 0.5;
				} else {
					return 0;
				}
			}
		});
		svg.selectAll("rect")
		.attr("fill", function(d,i){
			if (d.isHighLight || !isHighLight) {
				return d.color;
			} else {
				return "#f3f3f3";
			}
		});
		if (sankeyChart.selectedNode == nodeId) {
			sankeyChart.selectedNode = "none";
		} else {
			sankeyChart.selectedNode = nodeId;
		}
		return isHighLight;
	}
}