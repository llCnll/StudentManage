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
 
 //班级下拉框
 function getClassesSelect(webContent){
	$.ajax({
		url:webContent+"/classes",
		data:{"method":"select"},
		async:true,
		type:"POST",
		success:function(list){
			var content = "<option value='-1'>--------请选择--------</option>";
			for(var i = 0; i < list.length; ++i){
				if(classes == list[i].id){
					content += "<option value='"+list[i].id+"' selected>"+list[i].name+"</option>";
				}else{
					content += "<option value='"+list[i].id+"'>"+list[i].name+"</option>";
				}
			}
			$("#classes").text('');
			$('#classes').html($('#classes').html()+content);
		},
		error:function(){
			alert("班级列表请求失败");
		},
		dataType:"json"
	});
 }