<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>修改用户</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$.ajax({
			url:"${pageContext.request.contextPath}/student?method=edit&id=<%=request.getParameter("id")%>",
			async:true,
			type:"get",
			success:function(student){
				$("#id").val(student.id);
				$("#name").val(student.name);
				$("#pwd").val(student.pwd);
				$("#repwd").val(student.pwd);
				$("#classes").val(student.classes.id);
				$("#roleId").val(student.roleId);
			},
			error:function(){
				alert("请求失败");
			},
			dataType:"json"
		});
		
		$.ajax({
			url:"${pageContext.request.contextPath}/classes",
			data:{"method":"select"},
			async:true,
			type:"POST",
			success:function(list){
				var content = "<option value='-1'>--------请选择--------</option>";
				for(var i = 0; i < list.length; ++i){
					content += "<option value='"+list[i].id+"'>"+list[i].name+"</option>";
				}
				$("#classes").text('');
				$('#classes').html($('#classes').html()+content);
				
			},
			error:function(){
				alert("班级列表请求失败");
			},
			dataType:"json"
		});
		
	})
	
</script>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id=form1 name=form1
		action="${pageContext.request.contextPath }/student?method=editSumbit"
		method=post>
		<input type="hidden" name="custId" value="${customer.custId }"/>

		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg"
						border=0></TD>
					<TD width="100%" background=${pageContext.request.contextPath }/images/new_020.jpg
						height=20></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_021.jpg"
						border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15 background=${pageContext.request.contextPath }/images/new_022.jpg><IMG
						src="${pageContext.request.contextPath }/images/new_022.jpg" border=0></TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD class=manageHead>当前位置：用户管理 &gt; 修改用户</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<table cellspacing=0 cellpadding=5  border=0>
						
							<tr><td><span style="color:red;">${error }</span></td></tr>
							<tr>
								<td>用户学号：</td>
								<td>
								<input class=textbox id="id"
														style="width: 180px" maxlength=50 name="id" readonly>
								</td>
								<td>用户名称 ：</td>
								<td>
								<input class=textbox id="name"
														style="width: 180px" maxlength=50 name="name">
								</td>
							</tr>
							
							<tr>
								
								<td>用户密码 ：</td>
								<td>
								<input class=textbox id="pwd"
														style="width: 180px" maxlength=50 name="pwd">
								</td>
								<td>确认密码：</td>
								<td>
								<input class=textbox id="repwd"
														style="width: 180px" maxlength=50 name="repwd">
								</td>
							</tr>
							
							<tr>
								<td>用户班级 ：</td>
								<td>
								<select class=textbox id="classes"
														style="width: 180px; height: 24px;" name="classesId">
									<option value="-1">加载中...</option>
								</select>
								</td>
								<td>用户权限 ：</td>
								<td>
								<c:if test="${student.roleId == 0 }">
									<select class=textbox id="roleId"
															style="width: 180px; height: 24px;" name="roleId" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
								</c:if>
								<c:if test="${student.roleId == 1 }">
									<select class=textbox id="roleId"
															style="width: 180px; height: 24px;" name="roleId">
								</c:if>
									<option value="-1" selected>--------请选择--------</option>
									<option value="0">普通用户</option>
									<option value="1">管理员</option>
								</select>
							</tr>
							
							<tr>
								<td rowspan=2>
								<input class=button id=save type=submit
														value=" 保存 " name=sbutton2>
								</td>
							</tr>
						</table>
						
					</TD>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg">
					<IMG src="${pageContext.request.contextPath }/images/new_023.jpg" border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_024.jpg"
						border=0></TD>
					<TD align=middle width="100%"
						background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg"
						border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
	</FORM>
</BODY>
</HTML>
