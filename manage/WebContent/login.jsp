<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html public "-//w3c//dtd html 4.01 transitional//en" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>用户登陆系统</title>
<style type=text/css>
body {
	font-size: 12px;
	color: #ffffff;
	font-family: 宋体
}

td {
	font-size: 12px;
	color: #ffffff;
	font-family: 宋体
}
.error{
	color:red;
}
</style>
<LINK href="${pageContext.request.contextPath }/css/bootstrap.min.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.validate.min.js"></script>

<script type="text/javascript">

$(function(){
	$('#signupButton').click(function(){
		if(valContent()){
	    	$.ajax({
	    		url:"${pageContext.request.contextPath }/student",
	    		type:"post",
	    		data:$('#signupForm').serialize(),
	    		success:function(mess){
					alert(mess.message);
					$('#myModal').modal('hide');
	    		},
				error:function(){
					alert("注册请求失败");
				},
	    		datatype:"json"
	    	});
		}
    });
	
	$('#loginForm').validate({
		rules:{
			"id":{
				"required":true
			},
			"pwd":{
				"required":true
			}
		}
	});
	
});
	function valContent(){
		return $('#signupForm').validate({
			rules:{
				"id":{
					"required":true,
					"rangelength":[8,8]
				},
				"name":{
					"required":true
				},
				"pwd":{
					"required":true
				},
				"repwd":{
					"required":true,
					"equalTo":"#rpwd"
				},
				"classesId":{
					"min":0
				}
			},
			messages:{
				"id":{
					"required":"请输入学号",
					"rangelength":"学号为8位"
				},
				"name":{
					"required":"请输入名称",
				},
				"pwd":{
					"required":"请输入密码",
				},
				"repwd":{
					"required":"请输入确认密码",
					"equalTo":"两次密码不一致"
				},
				"classesId":{
					"min":"不能为0"
				}
			}
			
		}).form();
	}
	function selectClassesFunction(){
		//班级下拉框
		$.ajax({
			url:"${pageContext.request.contextPath}/classes",
			data:{"method":"select", "signup":"true"},
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

</script>

</head>
<body style="    BACKGROUND-COLOR: #ffffff;">

	<form action="${pageContext.request.contextPath }/student"  method="post" id="loginForm">
		<input type="hidden" name="method" value="login"/>
		<div>&nbsp;&nbsp;</div>
		<div>
			<table cellspacing=0 cellpadding=0 width=900 align=center border=0>
				<tbody>
					<tr>
						<td style="height: 105px"><img src="images/login_1.gif"
							border=0></td>
					</tr>
					<tr>
						<td background=images/login_2.jpg height=300>
							<table height=300 cellpadding=0 width=900 border=0>
								<tbody>
									<tr>
										<td colspan=2 height=35></td>
									</tr>
									<tr>
										<td width=360></td>
										<td>
											<table cellspacing=0 cellpadding=2 border=0>
												<tbody>
													<tr>
														<td style="height: 28px" width=100>登 录 号：</td>
														<td style="height: 28px" width=150>
															<input id="id" type="text" style="width: 130px; color:black;" name="id" value="${param.id }"></td>
														<td style="height: 28px" width=370>
															<label for="id" class="error" style="font-weight: bold; color: white;display:none;">请输入登陆号</label></td>
													</tr>
													<tr>
														<td style="height: 28px">登录密码：</td>
														<td style="height: 28px">
															<input id="pwd" style="width: 130px; color:black;" type="password" name="pwd" ></td>
														<td style="height: 28px">
															<label for="pwd" class="error" style="font-weight: bold; color: white;display:none;">请输入密码</label></td>
															
													</tr>
													<tr>
														<td style="height: 18px;color: red;" colspan="2">${error }</td>
														<td style="height: 18px"></td>
													</tr>
													<tr>
														<td>
															<input id=signUp data-toggle="modal" data-target="#myModal"
																style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px"
																type=image src="images/regist_button.jpg" name=btn onclick="selectClassesFunction(); return false;">
														</td>
														<td><input id=btn
															style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px"
															type=image src="images/login_button.gif" name=btn>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td><img src="images/login_3.jpg" border=0></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						注册
					</h4>
				</div>
				<form id="signupForm">
					<div class="modal-body" style="text-align: center;">
						<input type="hidden" name="method" value="signup">
						<input type="hidden" name="signup" value="true"/>
						<table cellspacing=0 cellpadding=5  border=0 >
						  
						    <tr><td><span style="color:red;">${error }</span></td></tr>
							<tr>
								<td style="color:black;">用户学号 ：</td>
								<td>
								<input class=textbox id="id"
														style="width: 180px" maxlength=50 name="id">
								</td>
															
							</tr>
							<tr>
								<td style="color:black;">用户名称 ：</td>
								<td>
								<input class=textbox id="name"
														style="width: 180px" maxlength=50 name="name">
								</td>
							</tr>
							
							<tr>
								
								<td style="color:black;">用户密码 ：</td>
								<td>
								<input class=textbox id="rpwd"
														style="width: 180px" maxlength=50 name="pwd">
								</td>
							</tr>
							<tr>
								<td style="color:black;">确认密码：</td>
								<td>
								<input class=textbox id="repwd"
														style="width: 180px" maxlength=50 name="repwd">
								</td>
							</tr>
							<tr>
								
								<td style="color:black;">用户班级 ：</td>
								<td>
								<select class=textbox id="classes"
														style="width: 180px; height: 24px;" name="classesId">
									<option value="-1">加载中...</option>
								 </select>
								<input type="hidden" name="roleId" value="0">
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="signupButton">
							注册
						</button>	
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>