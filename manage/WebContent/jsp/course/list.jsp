<%@page import="dao.impl.StudentDaoImpl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>课程列表</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<SCRIPT language=javascript>

	$(function (){
		
		checkId();
		
		<%
			Logger logger = Logger.getLogger(this.getClass());
			domain.Student selectStudent = (domain.Student)request.getAttribute("selectStudent");
			if(selectStudent == null){
				String stid = request.getParameter("stid");
				if(stid == null)
					selectStudent = (domain.Student)request.getSession().getAttribute("student");
				else{
					logger.info("---前台查询中---\n");
					selectStudent = new StudentDaoImpl().studentEdit(stid);
					logger.info("---前台查询结束---\n\n");
				}
			}
			request.setAttribute("selectStudent", selectStudent);
		%>
		
		$('#currentStudent').text('${selectStudent.name}');
		
		//选课列表
		$.ajax({
			url:"${pageContext.request.contextPath}/course",
			async:true,
			type:"POST",
			data:{"method":"list","cid":"${param.cid}", "name":"${param.name}", "page":"${param.page}", "currentCount":"${param.currentCount}"},
			success:function(pageBean){
				var content = "";
				var selected = "";
				for(var i = 0; pageBean.list!=null && i < pageBean.list.length; ++i){
					content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
								+ "<TD style=\"width: 76px;\"><input type=\"checkbox\" name=\"select\" value=\""+pageBean.list[i].id+"\"/></TD>"
								+ "<TD>"+pageBean.list[i].id+"</TD>"
								+ "<TD>"+pageBean.list[i].name+"</TD>"
								+ "<TD>"+pageBean.list[i].credithour+"</TD>"
								+ "<TD>"+pageBean.list[i].classhour+"</TD>"
								+ "<TD>"+pageBean.list[i].practicehour+"</TD>"
								+ "<TD>"+pageBean.list[i].remark+"</TD>"
								+ "<TD>"
								/* +"<a href='${pageContext.request.contextPath}/jsp/customer/edit.jsp?id="+pageBean.list[i].id+"'>修改</a>" */
								+"${selectStudent.roleId==1?"<a href=\'myRoleEditAddress\'>修改</a>":"judgeCourse"}"
								+"&nbsp;&nbsp;"
								/* +"<a href='${pageContext.request.contextPath}/student?id="+pageBean.list[i].id+"&method=del'>删除</a>" */
								+"${selectStudent.roleId==1?"<a href=\'myRoleDelAddress\'>删除</a>":""}"
								+"</TD>"
							+"</TR>";
							var courses = "${selectStudent.courses}";
							if(courses.search("id="+pageBean.list[i].id+",")>0){//存在
								content = content.replace('judgeCourse',"<a href='javascript:delSelectCourse(${selectStudent.id},"+pageBean.list[i].id+");'>退选</a>");
								//selected += pageBean.list[i].name+", ";
							}else{
								content = content.replace('judgeCourse',"<a href='javascript:addSelectCourse(${selectStudent.id},"+pageBean.list[i].id+");'>选课</a>");
							}
						///* <a onclick=\'roleDel()\'>删除</a> */
						content = content.replace('myRoleEditAddress',"${pageContext.request.contextPath}/jsp/course/edit.jsp?id="+pageBean.list[i].id);
						content = content.replace('myRoleDelAddress',"${pageContext.request.contextPath}/course?id="+pageBean.list[i].id+"&method=del");
				}
				//alert(courses);
				//alert((courses.split("name=")).length);//0不算从1, 开始, 直到等于length-1
				if(pageBean.list!=null){
					var arr = courses.split("name=");
					for(var i = 1; i < arr.length; ++i){
						selected += arr[i].split(",")[0]+", ";
					}
				}
				$('#selectedCourses').text(selected);
				$('#grid').html($('#grid').html()+content);
				$("#totalCount").text(pageBean.totalCount);
				$("#totalPage").text(pageBean.totalPage);
				$("#currentCount").val(pageBean.currentCount);
				$("#currentPage_pre").attr("href", "javascript:to_page("+(pageBean.currentPage-1)+")");
				$("#currentPage").text(pageBean.currentPage);
				$("#currentPage_aft").attr("href", "javascript:to_page("+(pageBean.currentPage+1)+")");
				$("#firstPage").attr("href", "javascript:to_page(1)");
				$("#lastPage").attr("href", "javascript:to_page("+(pageBean.totalPage)+")");
				
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
						url:"${pageContext.request.contextPath}/course",
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
		//id 和 (姓名和班级)
		function checkId(){
			var id = $("#id").val();
			if(id.length != 0){
				$("#name").val('');
				$("#name").attr("disabled", true);
			}else{
				$("#name").attr("disabled", false);
			}
		}
		$("#id").blur(function (){
			checkId();
		});
		$('#currentCount').change(function(){
			document.courseForm.submit();
		});
	});
	function addSelectCourse(stid, cid){
		$.ajax({
			url:"${pageContext.request.contextPath}/student",
			data:{"method":"addSelectCourse", "stid":stid, "cid":cid},
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
	};
	function delSelectCourse(stid, cid){
		$.ajax({
			url:"${pageContext.request.contextPath}/student",
			data:{"method":"delSelectCourse", "stid":stid, "cid":cid},
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
	};
	
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
		document.courseForm.submit();
	}
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="courseForm" name="courseForm"
		action="${pageContext.request.contextPath }/jsp/course/list.jsp"
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
								<TD class=manageHead>当前位置：课程管理 &gt; 课程列表</TD>
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
													
													<TD>课程ID：</TD>
													<TD><INPUT class=textbox id=id
														style="WIDTH: 80px" maxLength=50 name="cid" value="${param.cid}"></TD>
													<TD>课程名：</TD>
													<TD><INPUT class=textbox id=name
														style="WIDTH: 80px" maxLength=50 name="name" value="${param.name}"></TD>
													<TD><INPUT class=button id=sButton2 type=submit
														value=" 筛选 " name=sButton2><span style="height: 18px;color: red;">${error }${message }</span></TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<tr>
									<td>
										当前用户: <span id="currentStudent"></span>, <input type="hidden" name="stid" value="${selectStudent.id }">已选课程:<span id="selectedCourses"></span>
									</td>
								</tr>
							    
								<TR>
									<TD>
										<TABLE id=grid
											style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
											cellSpacing=1 cellPadding=2 rules=all border=0>
											<TBODY id="columnTbody">
												<TR
													style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
													<TD style="width:76px">
													<c:if test="${selectStudent.roleId==1 }">
														<input type="button" id="delBnt" value="批量删除">
													</c:if>
														<input type="checkbox" id="checkAll" name="checkAll">
													</TD>
													<TD>课程ID</TD>
													<TD>课程名</TD>
													<TD>学分</TD>
													<TD>讲授学时</TD>
													<TD>实验学时</TD>
													<TD>备注</TD>
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
												[<A id="firstPage" href="">首页</A>]
												[<A id="currentPage_pre" href="javascript:to_page(${currentPage-1})">前一页</A>]
												<B id="currentPage"></B><!-- 初始化域id=page一致, 所以当前页数传page的值 -->
												[<A id="currentPage_aft"href="javascript:to_page(${currentPage+1})">后一页</A>]
												[<A id="lastPage" href="">尾页</A>] 
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