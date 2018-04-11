/**
 * 
 */
$(document).on("click","#idSearchBtn",function(){
	var keyword = $("#idKeyword").val();
	window.open('search.html?keyword='+keyword);
});