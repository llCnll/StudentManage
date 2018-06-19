 function judgePage(page){
 	if(page){//通过上一页下一页执行
			$("#page").val(page);
			if(1*page >= 1*$('#totalPage').text()){
				$("#page").val($('#totalPage').text());
			}else if(1*page <= 1){
				$("#page").val(1);
			}
		}else{
			var toPage = $('#page').val();
			
			if(1*toPage > 1*$('#totalPage').text()){
				$("#page").val($('#totalPage').text());
			}else if(1*toPage < 1){
				$("#page").val(1);
			}
		}
 }