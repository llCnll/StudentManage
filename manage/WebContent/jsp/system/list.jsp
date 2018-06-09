  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>客户列表</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<SCRIPT language=javascript>

	$(function (){
		var grade = '<%=request.getParameter("grade")%>';
		var major = '<%=request.getParameter("major")%>';
		//班级列表
		$.ajax({
			url:"${pageContext.request.contextPath}/classes",
			async:true,
			type:"POST",
			data:{"method":"list","name":"${param.name}", "grade":"${param.grade}", "major":"${param.major}", "page":"${param.page}", "currentCount":"${param.currentCount}"},
			success:function(pageBean){
				var content = "";
				for(var i = 0; i < pageBean.list.length; ++i){
					content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
								+ "<TD style=\"width: 76px;\"><input type=\"checkbox\" name=\"select\" value=\""+pageBean.list[i].id+"\"/></TD>"
								+ "<TD>"+pageBean.list[i].id+"</TD>"
								+ "<TD>"+pageBean.list[i].name+"</TD>"
								+ "<TD>"+pageBean.list[i].grade+"</TD>"
								+ "<TD>"+pageBean.list[i].major+"</TD>"
								+ "<TD>"
								/* +"<a href='${pageContext.request.contextPath}/jsp/customer/edit.jsp?id="+pageBean.list[i].id+"'>修改</a>" */
								+"${student.roleId==1?"<a href=\'myRoleEditAddress\'>修改</a>":"<a onclick=\'roleEdit()\'>修改</a>"}"
								+"&nbsp;&nbsp;"
								/* +"<a href='${pageContext.request.contextPath}/student?id="+pageBean.list[i].id+"&method=del'>删除</a>" */
								+"${student.roleId==1?"<a href=\'myRoleDelAddress\'>删除</a>":"<a onclick=\'roleDel()\'>删除</a>"}"
								+"</TD>"
							+"</TR>";
						
						content = content.replace('thisRowId',pageBean.list[i].id);
						content = content.replace('myRoleEditAddress',"${pageContext.request.contextPath}/jsp/system/edit.jsp?id="+pageBean.list[i].id);/* 待修改 */
						content = content.replace('myRoleDelAddress',"${pageContext.request.contextPath}/classes?id="+pageBean.list[i].id+"&method=del");
				}
				$('#grid').html($('#grid').html()+content);
				$("#totalCount").text(pageBean.totalCount);
				$("#totalPage").text(pageBean.totalPage);
				$("#currentCount").val(pageBean.currentCount);
				$("#currentPage_pre").attr("href", "javascript:to_page("+(pageBean.currentPage-1)+")");
				$("#currentPage").text(pageBean.currentPage);
				$("#currentPage_aft").attr("href", "javascript:to_page("+(pageBean.currentPage+1)+")");
				
				$("#checkAll").click(function() {
			         $('input[name="select"]').prop("checked",this.checked); 
			     });
			     var $subBox = $("input[name='select']");
			     $subBox.click(function(){
			    	 $("#checkAll").prop("checked" , $subBox.length == $subBox.filter(":checked").length ? true :false);
			     });
			     $('#delBnt').click(function(){
			 		var ids ="";
			 		$("input[name='select']:checkbox:checked").each(function(){ 
			 			ids+=$(this).val()+",";
			 		});
			 		
			 		$.ajax({
						url:"${pageContext.request.contextPath}/classes",
						data:{"method":"delBactch", "ids":ids},
						async:true,
						type:"POST",
						success:function(message){
							alert(message.message);
							window.location.reload();
						},
						error:function(){
							alert("删除请求失败");
							window.location.reload();
						},
						dataType:"json"
					});
			 		
			 	});
			},
			error:function(){
				alert("学生列表请求失败");
			},
			dataType:"json"
		});
		//班级年级下拉框
		$.ajax({
			url:"${pageContext.request.contextPath}/classes",
			data:{"method":"gradeSelect"},
			async:true,
			type:"POST",
			success:function(list){
				var content = "<option value='-1'>--------请选择--------</option>";
				for(var i = 0; i < list.length; ++i){
					if(grade == list[i]){
						content += "<option value='"+list[i]+"' selected>"+list[i]+"</option>";
					}else{
						content += "<option value='"+list[i]+"'>"+list[i]+"</option>";
					}
				}
				$("#grade").text('');
				$('#grade').html($('#grade').html()+content);
			},
			error:function(){
				alert("班级年级请求失败");
			},
			dataType:"json"
		});
		//班级专业下拉框
		$.ajax({
			url:"${pageContext.request.contextPath}/classes",
			data:{"method":"majorSelect"},
			async:true,
			type:"POST",
			success:function(list){
				var content = "<option value='-1'>--------请选择--------</option>";
				for(var i = 0; i < list.length; ++i){
					if(major == list[i]){
						content += "<option value='"+list[i]+"' selected>"+list[i]+"</option>";
					}else{
						content += "<option value='"+list[i]+"'>"+list[i]+"</option>";
					}
				}
				$("#major").text('');
				$('#major').html($('#major').html()+content);
			},
			error:function(){
				alert("班级专业请求失败");
			},
			dataType:"json"
		});
		//name 和 (garde和major)
		function checkId(){
			var name = $("#name").val();
			if(name.length != 0){
				$("#grade").val('-1');
				$("#grade").attr("disabled", true);
				$("#major").val("-1");
				$("#major").attr("disabled", true);
			}else{
				$("#grade").attr("disabled", false);
				$("#major").attr("disabled", false);
			}
		}
		$("#name").blur(function (){
			checkId();
		});
	});
	
	
	function to_page(page){

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
		document.classesForm.submit();
	}
	function roleDel(){
		alert("删除权限不足!");
	}
	function roleEdit(id, obj){
		alert("修改权限不足!");
	}
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="classesForm" name="classesForm"
		action="${pageContext.request.contextPath }/jsp/system/list.jsp"
		method=post>
		
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg"
						border=0></TD>
					<TD width="100%" background="${pageContext.request.contextPath }/images/new_020.jpg"
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
								<TD class=manageHead>当前位置：系统管理 &gt; 班级列表</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<TABLE borderColor=#cccccc cellSpacing=0 cellPadding=0
							width="100%" align=center border=0>
							<TBODY>
								<TR>
									<TD height=25>
										<TABLE cellSpacing=0 cellPadding=2 border=0>
											<TBODY>
												<TR>
													<TD>班级名称：</TD>
													<TD><INPUT class=textbox id=name
														style="WIDTH: 80px" maxLength=50 name="name" value="${param.name}"></TD>
													<TD>班级年级：</TD>
													<TD>
													   <select name="grade" id="grade">
													       <option value="-1">加载中...</option>
													   </select>
													</TD>
													<TD>班级专业：</TD>
													<TD>
													   <select name="major" id="major">
													       <option value="-1">加载中...</option>
													   </select>
													</TD>
													<TD><INPUT class=button id=sButton2 type=submit
														value=" 筛选 " name=sButton2><span style="height: 18px;color: red;">${error }${message }</span></TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							    
								<TR>
									<TD>
										<TABLE id=grid
											style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
											cellSpacing=1 cellPadding=2 rules=all border=0>
											<TBODY id="columnTbody">
												<TR
													style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
													<TD style="width:76px">
													<c:if test="${student.roleId==1 }">
														<input type="button" id="delBnt" value="批量删除">
													</c:if>
														<input type="checkbox" id="checkAll" name="checkAll">
													</TD>
													<TD>班级ID</TD>
													<TD>班级名称</TD>
													<TD>班级年级</TD>
													<TD>班级专业</TD>
													<TD>操作</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								
								<TR>
									<TD><SPAN id=pagelink>
											<div style="LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right">
												共[<B id="totalCount"></B>]条记录,[<B id="totalPage"></B>]页,每页显示
												<select id="currentCount" name="currentCount">
													<option value="5">5</option>
													<option value="10">10</option>
												</select>
												条
												
												[<A id="currentPage_pre" href="javascript:to_page(${currentPage-1})">前一页</A>]
												<B id="currentPage"></B><!-- 初始化域id=page一致, 所以当前页数传page的值 -->
												[<A id="currentPage_aft"href="javascript:to_page(${currentPage+1})">后一页</A>] 
												到
												<input type="text" size="3" id="page" name="page" />
												页
												
												<input type="button" value="Go" onclick="to_page()"/>
											</div>
									</SPAN></TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg"><IMG
						src="${pageContext.request.contextPath }/images/new_023.jpg" border=0></TD>
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
