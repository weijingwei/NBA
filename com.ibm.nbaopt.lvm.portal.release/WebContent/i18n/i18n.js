i18n = {
	callback : function(callback) {
		var value = "";
		$.i18n.properties({
			name : 'strings', // 资源文件名称
			path : this.rootPath() + '/i18n/', // 资源文件路径
			mode : 'both', // 用Map的方式使用资源文件中的值
			cache : false,
			language : $.i18n.browserLang(),
			callback : callback
		});
	},

	init : function() {
		this.callback(function() {console.debug("i18n has initialized. Use $.i18n.prop(key) to get the value.")});
	},

	byId : function(id, key) {
		this.callback(function() {$(id).html($.i18n.prop(key))});
	},

	rootPath : function() {
		// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		var curWwwPath = window.document.location.href;
		// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		// 获取主机地址，如： http://localhost:8083
		var localhostPaht = curWwwPath.substring(0, pos);
		// 获取带"/"的项目名，如：/uimcardprj
		var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
		return (localhostPaht + projectName);
	}

};

i18n.init();