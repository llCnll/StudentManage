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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>

<style type="text/css">
.error{
	color:red;
}
</style>
<script type="text/javascript">
	
	$(function(){
		
		$('#form1').validate({
			rules:{
				"opwd":{
					"required":true
				},
				"npwd":{
					"required":true
				},
				"repwd":{
					"required":true,
					"equalTo":"#npwd"
				}
			},
			messages:{
				"opwd":{
					"required":"请输入原密码",
				},
				"npwd":{
					"required":"请输入新密码",
				},
				"repwd":{
					"required":"请输入确认密码",
					"equalTo":"两次密码不一致"
				}
			}
			
		});
		
	});
	
</script>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id=form1 name=form1
		action="${pageContext.request.contextPath }/student"
		method=post>
		<input type="hidden" name="id" value="${param.id }"/>
		<input type="hidden" name="method" value="editPwd"/>

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
								<TD class=manageHead>当前位置：用户管理 &gt; 修改密码</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<table cellspacing=0 cellpadding=5  border=0>
						
							<tr><td colspan="2"><span style="color:red;">${error }${message }</span></td></tr>
							<c:if test="${param.id == student.id || student.roleId != 1 }">
								<tr>
									<td>原密码：</td>
									<td>
									<input class=textbox id="opwd"
															style="width: 180px" maxlength=50 name="opwd">
									</td>
								</tr>
							</c:if>
							<tr>
								
								<td>新密码 ：</td>
								<td>
								<input class=textbox id="npwd"
														style="width: 180px" maxlength=50 name="npwd">
								</td>
							</tr>
							
							<tr>
								<td>确认密码：</td>
								<td>
								<input class=textbox id="repwd"
														style="width: 180px" maxlength=50 name="repwd">
								</td>
							</tr>
							
							<tr>
								<c:if test="${param.id == student.id}">
									<td rowspan=2>
									<input class=button id=save type=submit
															value=" 保存 " name=sbutton2>
									</td>
								</c:if>
								<c:if test="${param.id != student.id && student.roleId == 1 }">
									<td rowspan=2>
									<input class=button id=qsave type=submit
															value=" 强制修改 " name=sbutton2>
									</td>
								</c:if>
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
