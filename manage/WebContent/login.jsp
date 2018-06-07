<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html public "-//w3c//dtd html 4.01 transitional//en" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>用户管理系统</title>
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
</style>
<LINK href="${pageContext.request.contextPath }/css/bootstrap.min.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>

<script type="text/javascript">

$(function(){
	$('#signupButton').click(function(){
    	$.ajax({
    		url:"${pageContext.request.contextPath }/student",
    		type:"post",
    		data:$('#signupForm').serialize(),
    		success:function(mess){
				alert(mess.message);
    		},
			error:function(){
				alert("请求失败");
			},
    		datatype:"json"
    	});
    });
});
	
	function check(){
		return checkId()&&checkPwd();
	}
	
	function checkId(){
		var id = $("#id").val();
		
		var error = $("#idError");
		if(id==""){
			error.css("visibility", "visible");
			return false;
		}
		error.css("visibility", "hidden");
		return true;
	}
	function checkPwd(){
		
		var pwd = $("#pwd").val();
		var error =  $("#pwdError");
		
		if(pwd==""){
			error.css("visibility", "visible");
			return false;
		}
		error.css("visibility", "hidden");
		return true;
	}
	
	

</script>

</head>
<body style="    BACKGROUND-COLOR: #ffffff;">

	<form action="${pageContext.request.contextPath }/student" onsubmit="return check();" method="post">
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
															<input id="id" type="text" style="width: 130px; color:black;" name="id" onblur="checkId()" value="${param.id }"></td>
														<td style="height: 28px" width=370>
															<span id="idError"
															style="font-weight: bold; visibility: hidden; color: white" >请输入登录号</span></td>
													</tr>
													<tr>
														<td style="height: 28px">登录密码：</td>
														<td style="height: 28px">
															<input id="pwd" style="width: 130px; color:black;" type="password" name="pwd" ></td>
														<td style="height: 28px">
															<span id="pwdError"
															style="font-weight: bold; visibility: hidden; color: white">请输入密码</span></td>
													</tr>
													<tr>
														<td style="height: 18px;color: red;" colspan="2">${error }</td>
														<td style="height: 18px"></td>
													</tr>
													<tr>
														<td>
															<input id=signUp data-toggle="modal" data-target="#myModal"
																style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px"
																type=image src="images/regist_button.jpg" name=btn>
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
						<table cellspacing=0 cellpadding=5  border=0 >
						  
						    <tr><td><span style="color:red;">${addError }</span></td></tr>
							<tr>
								<td style="color:black;">用户学号 ：</td>
								<td>
								<input class=textbox id="id"
														style="width: 180px" maxlength=50 name="id">
								</td>
								<td style="color:black;">用户名称 ：</td>
								<td>
								<input class=textbox id="name"
														style="width: 180px" maxlength=50 name="name">
								</td>
							</tr>
							
							<tr>
								
								<td style="color:black;">用户密码 ：</td>
								<td>
								<input class=textbox id="pwd"
														style="width: 180px" maxlength=50 name="pwd">
								</td>
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
								</td>
								<td> </td>
								<td>
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