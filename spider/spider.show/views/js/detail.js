/**
 * 
 */
$(document).ready(function(){
	var pageParamStr = window.location.search.substr(1);
	var paramArr = pageParamStr.split("&");
	var paramMap = new Map();
	$.each(paramArr,function(i,item){
		var arr = item.split("=");
		paramMap.set(arr[0],arr[1]);
	});
	// 渲染影片详情
	$.post("../queryDetial",{"mvid" : paramMap.get("mvid")}, function(resp){
		var mvSources = JSON.parse(resp);
		var mvInfo = mvSources[0];
		$("#idPosterImg").attr("src",mvInfo.poster);
		$("#idMvName").text(mvInfo.mvName);
		$("#idBreadcrumbMvName").text(mvInfo.mvName);
		$("#idDirector").text(mvInfo.director);
		$("#idPlayer").text(mvInfo.player);
		$("#idShowDate").text(mvInfo.showDate);
		$("#idDesc").text(mvInfo.details);
		
		$.each(mvSources,function(i,item){
			var li = [];
			var tabC = [];
			if(i === 0){
				// 选项卡菜单
				li.push('<li class="active">');
				li.push('<a href="#'+item.websiteId+'" data-toggle="tab">'+item.websiteName+'</a>');
				li.push('</li>');
				// 选项卡内容
				tabC.push('<div class="tab-pane fade in active" id="'+item.websiteId+'">');
				tabC.push('<div class="btn-play"><a class="btn btn-primary" href="'+item.sourceUrl+'" role="button" target="_blank">播放</a></div>');
				tabC.push('</div>');
			}else{
				// 选项卡菜单
				li.push('<li>');
				li.push('<a href="#'+item.websiteId+'" data-toggle="tab">'+item.websiteName+'</a>');
				li.push('</li>');
				// 选项卡内容
				tabC.push('<div class="tab-pane fade" id="'+item.websiteId+'">');
				tabC.push('<div class="btn-play"><a class="btn btn-primary" href="'+item.sourceUrl+'" role="button" target="_blank">播放</a></div>');
				tabC.push('</div>');
			}
			$('#mvSourcesTab').append(li.join(" "));
			$('#mvSourcesTabContent').append(tabC.join(" "));
		});
	});
});