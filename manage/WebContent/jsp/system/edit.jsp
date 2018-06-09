<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>修改班级</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$.ajax({
			url:"${pageContext.request.contextPath}/classes?method=edit&id=<%=request.getParameter("id")%>",
			async:true,
			type:"get",
			success:function(classes){
				$("#id").val(classes.id);
				$("#name").val(classes.name);
				$("#grade").val(classes.grade);
				$("#major").val(classes.major);
			},
			error:function(){
				alert("请求失败");
			},
			dataType:"json"
		});
		
		/* $.ajax({
			url:"${pageContext.request.contextPath}/classes",
			data:{"method":"list"},
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
		}); */
		
	})
	
</script>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id=form1 name=form1
		action="${pageContext.request.contextPath }/classes?method=editSumbit"
		method=post>
		<input type="hidden" id="id" name="id"/>

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
								<TD class=manageHead>当前位置：系统管理 &gt; 修改班级</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<table cellspacing=0 cellpadding=5  border=0>
							<tr><td><span style="color:red;">${error }</span></td></tr>
							<tr>
								<td>班级名称：</td>
								<td>
								<input class=textbox id="name"
														style="width: 180px" maxlength=50 name="name">
								</td>
								<td>班级年级 ：</td>
								<td>
								<input class=textbox id="grade"
														style="width: 180px" maxlength=50 name="grade">
								</td>
							</tr>
							
							<tr>
								
								<td>班级专业 ：</td>
								<td>
								<input class=textbox id="major"
														style="width: 180px" maxlength=50 name="major">
								</td>
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
