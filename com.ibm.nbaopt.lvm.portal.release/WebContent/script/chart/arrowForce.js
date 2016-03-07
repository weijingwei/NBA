arrowForce = {
	arrowForceFilter: {},
	selectedNode: "none",
	clickCallBack: function(d, i){},
	finished: function (data){},
	links:[],
	drawArrowForce: function (modelName, iteration, dataType, div) {
		d3.json(i18n.rootPath() + "/nba/business/"+modelName+"/"+iteration+"/"+dataType+"/"+buildFilter()+"/businessChart", drawArrowForce);
		function dataFormat(data) {
			var nodes = {};
			arrowForce.links = data.links;
			arrowForce.links.forEach(function(link) {
				if(nodes[link.segmentId_from] == null) {
					nodes[link.segmentId_from] = {name: link.segmentId_from, r: link.sourceNodeSize, color: link.sourceNodeColor, LTV: link.from_LTV, count: link.from_segmentCount, prob: link.prob, gain: link.gain};
				}
				link.source = nodes[link.segmentId_from];
				
				if(nodes[link.segmentId_to] == null) {
					nodes[link.segmentId_to] = {name: link.segmentId_to, r: link.targetNodeSize, color: link.targetNodeColor, LTV: link.to_LTV, count: link.to_segmentCount, prob: link.prob, gain: link.gain};
				}
				link.target = nodes[link.segmentId_to];
				link.type="suit";
			});
			return {nodes:nodes, links:arrowForce.links, category:data.category};
		}
		function buildFilter() {
			var filter = "";
			if (arrowForce.arrowForceFilter.selfConnection) {
				filter += "selfConnection:" + arrowForce.arrowForceFilter.selfConnection + "::";
			}
			if (arrowForce.arrowForceFilter.searchSegment) {
				filter += "searchSegment:" + arrowForce.arrowForceFilter.searchSegment + "::";
			}
			if (filter == "") {
				filter = "none";
			}
			return filter;
		}
		function drawArrowForce(error, data) {
			if (error) {
				return console.log(error);
			}
			if (data.links == null || data.links.length == 0) {
				$("#" + div).empty();
				$("#" + div).append($.i18n.prop("iterationComparison.businessInsight.nodata"));
				return;
			}
			data = dataFormat(data);
			var h = $("#" + div).height();
			var w = $("#" + div).width();
			$("#" + div).empty();
			var svg = d3.select("#" + div).append("svg:svg").attr("width", w).attr("height", h);
			var links = data.links;
			var nodes = data.nodes;
			var force = d3.layout.force()
			.nodes(d3.values(nodes))
			.links(links)
			.size([w, h])
			.linkStrength(1)
			.linkDistance(div.indexOf("Modal") > 0 ? 400 : 200) // 30  links.length
			.charge(-70) // -60  links.length
			.on("tick", tick)
			.start(0);
			var drag = force.drag()
			.on("drag",function(d,i){})
			.on("dragstart",function(d,i){d.fixed = true;}) //拖拽开始后设定被拖拽对象为固定
			.on("dragend",function(d,i){});
			svg.append("svg:defs").selectAll("marker")
			.data(force.links())
			.enter().append("svg:marker")
			.attr("id", function(d) {return "marker_" + d.source.name +  "_" + d.target.name})
			.attr("viewBox", "0 -5 10 10")
			.attr("refX", 10)
			.attr("refY", -1.5)
			.attr("markerWidth", 6)
			.attr("markerHeight", 6)
			.attr("orient", "auto")
			.append("svg:path")
			.attr("d", "M0,-5 L10,0 L0,5")
			.attr("fill", function(d,i){return d.linkColor;});
			var path = svg.append("svg:g").selectAll("path")
			.data(force.links())
			.enter().append("svg:path")
			.attr("id", function(d){return "path_" + d.source.name + "_" + d.target.name})
			.attr("stroke", function(d,i){return d.linkColor;})
			.attr("stroke-width", "1.5px")
			.attr("fill", "none")
			.attr("marker-end", function(d) { return "url(#" + "marker_" + d.source.name + "_" + d.target.name + ")"; });
			var node = svg.append("svg:g").selectAll("circle")
			.data(force.nodes())
			.enter().append("svg:circle")
			.attr("r", function(d){return Math.sqrt(d.r / 1.5 * 100);})
			.attr("stroke", function(d,i){return d.color;})
			.attr("fill", function(d,i){return d.color;});
			svg.selectAll("circle")
			.on("mouseover",function(d,i){d.fixed = true;})
			.on("mouseout",function(d,i){})
			.on("click",function(d,i){arrowForce.clickCallBack(d, i);})
			.on("dblclick",function(d,i){}).call(drag);
			node.append("svg:title").text(function(d) {return "Segment " + d.name + ": Size " + d.count + ", LTV " + d.LTV + ", Gain " + d.gain});
			path.append("svg:title").text(function(d) {return d.source.name + "~" + d.target.name + ": Prob " + d.prob;});
			var text = svg.append("svg:g").selectAll("g").data(force.nodes()).enter().append("svg:g");
			
			// A copy of the text with a thick white stroke for legibility.
			text.append("svg:text")
			.attr("x", 8)
			.attr("y", ".31em")
			.attr("class", "shadow")
			.style("font", "10px sans-serif")
			.style("pointer-events", "none")
			.style("stroke", "#fff")
			.style("stroke-width", "3px")
			.style("stroke-opacity", ".8")
			.text(function(d) { return d.name; });
			text.append("svg:text")
			.attr("x", 8)
			.attr("y", ".31em")
			.style("font", "10px sans-serif")
			.style("pointer-events", "none")
			.text(function(d) { return d.name; });
			//使用椭圆弧路径段双向编码。
			function tick() {
				//(3)打点path格式是：Msource.x, source.yArr 0 0, 1 target.x, target.y
				path.attr("d", function(d) {
					if (d.source.name == d.target.name) {
						var revise = Math.sqrt(2) * Math.sqrt(d.source.r / 1.5 * 100);
						return "M" + d.source.x + "," + d.source.y
						+ " T" + (d.source.x - Math.sqrt(3) * 8 - revise / 4) + "," + (d.source.y + 3 * 4 + revise / 16)
						+ " T" + (d.source.x - Math.sqrt(3) * 16 - revise / 4) + "," + (d.source.y - 3 * 2 - revise / 16)
						+ " T" + (d.source.x - revise / 2) + "," + (d.source.y - revise / 1.5);
					} else {
						var dx = d.target.x - d.source.x,//增量
							dy = d.target.y - d.source.y,
							dr = Math.sqrt(dx * dx + dy * dy);
						var revise = dr/(Math.sqrt(d.target.r / 1.5 * 100)); // 修正
						return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1" +
						" " + (d.target.x - dx/revise) + "," + (d.target.y - dy/revise);
					}
				});
				
				node.attr("transform", function(d) {
					return "translate(" + d.x + "," + d.y + ")";
				});
				
				text.attr("transform", function(d) {
					return "translate(" + d.x + "," + d.y + ")";
				});
			}
			arrowForce.finished(data);
		}
	},
	highlightNode: function (divId, nodeId) {
		// 判断被点击节点是否是已选中节点，如果是取消全局高亮，否则开启全局高亮
		var isHighLight = nodeId != arrowForce.selectedNode;
		var isNewHighLight = true;
		// 如果开启全局高亮效果，判断点击节点的相邻节点是否高亮，已高亮就不是一次新的全局高亮，没有高亮就是一次新的全局高亮
		if (isHighLight) {
			arrowForce.links.forEach(function(link) {
				if ((link.source.name == nodeId && link.target.isHighLight) || (link.target.name == nodeId && link.source.isHighLight)) {
					isNewHighLight = false;
				}
			});
		}
		// 如果全局高亮效果关闭，重置所有节点高亮为是，全亮
		// 如果全局高亮效果开启，如果是一次新高亮，设定所有节点状态为否
		if (!isHighLight) {
			arrowForce.links.forEach(function(link) {
				link.source.isHighLight = false;
				link.target.isHighLight = false;
			});
		} else {
			if (isNewHighLight) {
				arrowForce.links.forEach(function(link) {
					link.source.isHighLight = false;
					link.target.isHighLight = false;
				});
			}
			
			// 开启被点击节点和相邻节点高亮效果，添加到高亮集合
			arrowForce.links.forEach(function(link) {
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
						return d.linkColor;
					} else {
						return "#f3f3f3";
					}
				}
			})
			.attr("marker-end", function(d) { 
				if (d.source && d.target) {
					if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
						return "url(#" + "marker_" + d.source.name + "_" + d.target.name + ")";
					} else {
						return "url(#ignore)"; //这个id的marker并不存在，箭头会消失
					}
				}
			})
			.attr("stroke-opacity", function(d,i){
				if (d.source && d.target) {
					if (d.source.isHighLight && d.target.isHighLight || !isHighLight) {
						return 1;
					} else {
						return 0;
					}
				}
			});
		svg.selectAll("circle")
			.attr("stroke", function(d,i){
				if (d.isHighLight || !isHighLight) {
					return d.color;
				} else {
					return "#f3f3f3";
				}
			})
			.attr("fill", function(d,i){
				if (d.isHighLight || !isHighLight) {
					return d.color;
				} else {
					return "#f3f3f3";
				}
			});
		if (arrowForce.selectedNode == nodeId) {
			arrowForce.selectedNode = "";
		} else {
			arrowForce.selectedNode = nodeId;
		}
		return isHighLight;
	}
}