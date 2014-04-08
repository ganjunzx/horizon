function menuHover() {
	// 菜单鼠标进入效果
	$('.menu-item').hover(function() {
		$(this).stop().animate({
			paddingLeft : "25px"
		}, 200, function() {
			$(this).addClass("orange");
		});
	}, function() {
		$(this).stop().animate({
			paddingLeft : "15px"
		}, function() {
			$(this).removeClass("orange");
		});
	});
}

function listVender(title) {
	var _this = $("#listVender");
	var title = _this.text();
	var params = {
		"url" : 'venderuser.html',
		"title" : title,
		"iconCls" : "",
		"name" : title
	};
	addTab(params);
}

function listBusiness(title) {
	var _this = $("#listBusiness");
	var title = _this.text();
	var params = {
		"url" : 'user.html',
		"title" : title,
		"iconCls" : "",
		"name" : title
	};
	addTab(params);
	var str = "iframe[name='" + getCurrentPageTitle() + "']";
	var tabWindow = $(str, parent.document.body)[0].contentWindow;
	tabWindow.listVender();
}

function addTab(params) {
	var iframe = '<iframe scrolling="auto" name="' + params.name + '" src="'
			+ params.url
			+ '" frameborder="0" style="width:100%;height:100%;"></iframe>';
	var t = $('#index_tabs');
	var opts = {
		title : params.title,
		closable : true,
		iconCls : params.iconCls,
		content : iframe,
		border : false,
		fit : true
	};
	if (t.tabs('exists', opts.title)) {
		t.tabs('select', opts.title);
		parent.$.messager.progress('close');
	} else {
		t.tabs('add', opts);
	}
}

function getCurrentPageTitle() {
	var tab = $('#index_tabs').tabs('getSelected');
	return tab.panel('options').title;
}

$(function() {
	// 菜单鼠标进入效果
	menuHover();
});