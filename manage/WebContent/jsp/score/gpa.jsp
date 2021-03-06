  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>绩点查询</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<SCRIPT language=javascript>

	$(function (){
		
		checkId();
		
		var classes = <%=request.getParameter("classes")%>;
		//学生列表
		$.ajax({
			url:"${pageContext.request.contextPath}/score",
			async:true,
			type:"POST",
			data:{"method":"getGpa"<c:if test="${student.roleId==1 }">,"id":"${param.id}", "name":"${param.name}", "classes":"${param.classes}", "page":"${param.page}", "currentCount":"${param.currentCount}"</c:if><c:if test="${student.roleId!=1 }">,"id":"${student.id }"</c:if>},
			success:function(pageBean){
				var content = "";
				for(var i = 0; pageBean.list!=null && i < pageBean.list.length; ++i){
					content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
								+ "<TD style=\"width: 76px;\"><input type=\"checkbox\" name=\"select\" value=\""+pageBean.list[i].id+"\"/></TD>"
								+ "<TD>"+pageBean.list[i].id+"</TD>"
								+ "<TD>"+pageBean.list[i].name+"</TD>"
								+ "<TD>"+pageBean.list[i].classes.name+"</TD>"
								+ "<TD>"+(pageBean.list[i].gpa==null?"未选课":pageBean.list[i].gpa)+"</TD>"
							+"</TR>";
				}
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
			},
			error:function(){
				alert("学生列表请求失败");
			},
			dataType:"json"
		});
		//班级下拉框
		getClassesSelect('${pageContext.request.contextPath}', '${param.classes}');
		//id 和 (姓名和班级)
		function checkId(){
			var id = $("#id").val();
			if(id.length != 0){
				$("#name").val('');
				$("#name").attr("disabled", true);
				$("#classes").val("-1");
				$("#classes").attr("disabled", true);
			}else{
				$("#name").attr("disabled", false);
				$("#classes").attr("disabled", false);
			}
		}
		$("#id").blur(function (){
			checkId();
		});
		$('#currentCount').change(function(){
			document.gpaForm.submit();
		});
		
		$('#export').click(function(){
			//学生列表
			/* $.ajax({
				url:"${pageContext.request.contextPath}/score",
				async:true,
				type:"POST",
				data:{"method":"exportGpa","id":"${param.id}", "name":"${param.name}", "classes":"${param.classes}", "page":"${param.page}", "currentCount":"${param.currentCount}"},
				success:function(){
				 	
				},
				error:function(){
					alert("学生列表请求失败");
				},
				dataType:"json"
			}); */
			window.location.href = "${pageContext.request.contextPath}/score?method=exportGpa&id=${param.id}&name=${param.name}&classes=${param.classes}";
		});
		
	});
	
	function to_page(page){

		judgePage(page);
		document.gpaForm.submit();
	}
	
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="gpaForm" name="gpaForm"
		action="${pageContext.request.contextPath }/jsp/score/gpa.jsp"
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
								<TD class=manageHead>当前位置：成绩管理 &gt; 绩点查询</TD>
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
													<c:if test="${student.roleId == 1 }">
														<TD>用户学号：</TD>
														<TD><INPUT class=textbox id=id
															style="WIDTH: 80px" maxLength=50 name="id" value="${param.id}"></TD>
														<TD>用户姓名：</TD>
														<TD><INPUT class=textbox id=name
															style="WIDTH: 80px" maxLength=50 name="name" value="${param.name}"></TD>
														<TD>班级：</TD>
														<TD>
														   <select name="classes" id="classes">
														       <option value="-1">加载中...</option>
														   </select>
														</TD>
														<TD><INPUT class=button id=sButton2 type=submit
															value=" 筛选 " name=sButton2>
															&nbsp;&nbsp;
															<c:if test="${student.roleId==1 }">
																<INPUT class=button id=export type=button
																value=" 打印当前条件下的学生 " name=sButton2>
															</c:if>
															<span style="height: 18px;color: red;">${error }${message }</span></TD>
													</c:if>
													<c:if test="${student.roleId != 1 }">
														<input id=id name="id" value="${student.id }" type="hidden">
													</c:if>
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
														<input type="checkbox" id="checkAll" name="checkAll">
													</TD>
													<TD>用户ID</TD>
													<TD>用户姓名</TD>
													<TD>用户班级</TD>
													<TD>绩点</TD>
												</TR>

											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<c:if test="${student.roleId == 1 }">
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
								</c:if>
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
