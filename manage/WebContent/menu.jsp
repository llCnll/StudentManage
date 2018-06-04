<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD id=Head1>
<TITLE>导航</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<style type=text/css>
body {
	padding-right: 0px;
	padding-left: 0px;
	padding-bottom: 0px;
	margin: 0px;
	padding-top: 0px;
	background-color: #2a8dc8
}

body {
	font-size: 11px;
	color: #003366;
	font-family: verdana
}

td {
	font-size: 11px;
	color: #003366;
	font-family: verdana
}

div {
	font-size: 11px;
	color: #003366;
	font-family: verdana
}

p {
	font-size: 11px;
	color: #003366;
	font-family: verdana
}

.mainMenu {
	font-weight: bold;
	font-size: 14px;
	cursor: pointer;
	color: #000000
}

a.style2:link {
	padding-left: 4px;
	color: #0055bb;
	text-decoration: none
}

a.style2:visited {
	padding-left: 4px;
	color: #0055bb;
	text-decoration: none
}

a.style2:hover {
	padding-left: 4px;
	color: #ff0000;
	text-decoration: none
}

a.active {
	padding-left: 4px;
	color: #ff0000;
	text-decoration: none
}

.span {
	color: #ff0000;
}
</STYLE>

<SCRIPT language=javascript>
	function MenuDisplay(obj_id) {
		for (var i = 1; i <= 9; i++) {
			var obj = document.getElementById('table_' + i);
			if(obj){
				document.getElementById('table_' + i).style.display = 'none';
				document.getElementById('table_' + i + 'Span').innerText = '＋';
			}
			
		}
		var obj = document.getElementById(obj_id);
		if(obj){
			if (obj.style.display == 'none') {
				obj.style.display = 'block';
				document.getElementById(obj_id + 'Span').innerText = '－';
			} else {
				obj.style.display = 'none';
				document.getElementById(obj_id + 'Span').innerText = '＋';
			}
		}
		
	}
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<form id=form1 name=form1 action=YHMenu.aspx method=post>
		<table cellSpacing=0 cellPadding=0 width=210 align=center border=0>
			<tbody>
				<tr>
					<td width=15><img src="images/new_005.jpg" border=0></td>
					<td align=middle width=180 background=images/new_006.jpg
						height=35><b>学分绩点管理系统 －功能菜单</b></td>
					<td width=15><img src="images/new_007.jpg" border=0></td>
				</tr>
			</tbody>
		</table>
		<table cellSpacing=0 cellPadding=0 width=210 align=center border=0>
			<tbody>
				<tr>
					<td width=15 background=images/new_008.jpg></td>
					<td vAlign=top width=180 bgColor=#ffffff>
						<table cellSpacing=0 cellPadding=3 width=165 align=center border=0>
							<tbody>
								<tr>
									<td class=mainMenu onClick="MenuDisplay('table_1');"><span
										class=span id=table_1Span>＋</span> 用户管理</td>
								</tr>
								<tr>
									<td>
										<table id=table_1 style="display: none" cellSpacing=0
											cellPadding=2 width=155 align=center border=0>
											<tbody>
												<c:if test="${student.roleId == 1 }">
													<tr>
														<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/customer/add.jsp" 
															target=main>－ 新增用户</a></td>
													</tr>
												</c:if>
												<tr> 
													<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/customer/list.jsp"
														target=main>－ 用户列表</a></td>
												</tr>
												<tr> 
													<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/customer/edit.jsp?id=${student.id}"
														target=main>－ 修改个人信息</a></td>
												</tr>
												
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td background=images/new_027.jpg height=1></td>
								</tr>
								<tr>
									<td class=mainMenu onClick="MenuDisplay('table_2');"><span
										class=span id=table_2Span>＋</span> 课程管理</td>
								</tr>
								<tr>
									<td>
										<table id=table_2 style="DISPLAY: none" cellSpacing=0
											cellPadding=2 width=155 align=center border=0>
											<tbody>
												<c:if test="${student.roleId == 1 }">
													<tr>
														<td class=menuSmall><a class=style2 href="${pageContext.request.contextPath}/jsp/course/add.jsp"
															target=main>－ 新增课程</a></td>
													</tr>
												</c:if>
												<tr>
													<td class=menuSmall><a class=style2 href="${pageContext.request.contextPath}/jsp/course/list.jsp"
														target=main>－课程列表</a></td>
												</tr>
												<tr>
													<td class=menuSmall><a class=style2 href="${pageContext.request.contextPath}/jsp/selection/list.jsp"
														target=main>－选课查询</a></td>
												</tr>
												
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td background=images/new_027.jpg height=1></td>
								</tr>
								<tr>
									<td class=mainMenu onClick="MenuDisplay('table_3');"><SPAN
										class=span id=table_3Span>＋</SPAN> 成绩管理</td>
								</tr>
								<tr>
									<td>
										<table id=table_3 style="DISPLAY: none" cellSpacing=0
											cellPadding=2 width=155 align=center border=0>
											<tbody>
												<tr>
													<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/score/add.jsp"
														target=main>－成绩批量录入</A></td>
												</tr>
												<tr>
													<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/score/list.jsp"
														target=main>－成绩查询</A></td>
												</tr>
												<tr>
													<td class=menuSmall><A class=style2 href="#"
														target=main>－绩点查询</A></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td background=images/new_027.jpg height=1></td>
								</tr>
								<tr>
									<td class=mainMenu onClick="MenuDisplay('table_4');"><SPAN
										class=span id=table_4Span>＋</SPAN>系统管理</td>
								</tr>
								<tr>
									<td>
										<table id=table_4 style="DISPLAY: none" cellSpacing=0
											cellPadding=2 width=155 align=center border=0>
											<tbody>
												<tr>
													<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/system/add.jsp"
														target=main>－新增班级</A></td>
												</tr>
												<tr>
													<td class=menuSmall><A class=style2 href="${pageContext.request.contextPath}/jsp/system/list.jsp"
														target=main>－班级列表</A></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
					<td width=15 background=images/new_009.jpg></td>
				</tr>
			</tbody>
		</table>
		<table cellSpacing=0 cellPadding=0 width=210 align=center border=0>
			<tbody>
				<tr>
					<td width=15><img src="images/new_010.jpg" border=0></td>
					<td align=middle width=180 background=images/new_011.jpg
						height=15></td>
					<td width=15><img src="images/new_012.jpg" border=0></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
