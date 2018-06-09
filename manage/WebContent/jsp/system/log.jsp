  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>日志查询</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<SCRIPT language=javascript>

	$(function (){
		$('#loglevel').val('${param.loglevel}');
		//日志列表
		$.ajax({
			url:"${pageContext.request.contextPath}/log",
			async:true,
			type:"POST",
			data:{"method":"list","stid":"${param.stid}", "loglevel":"${param.loglevel}", "createtime":"${param.createtime}", "page":"${param.page}", "currentCount":"${param.currentCount}"},
			success:function(pageBean){
				var content = "";
				for(var i = 0; pageBean.list!=null && i < pageBean.list.length; ++i){
					content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
								+ "<TD style=\"width: 76px;\"><input type=\"checkbox\" name=\"select\" value=\""+pageBean.list[i].id+"\"/></TD>"
								+ "<TD>"+pageBean.list[i].createtime+"</TD>"
								+ "<TD>"+pageBean.list[i].student.id+"</TD>"
								+ "<TD>"+pageBean.list[i].loglevel+"</TD>"
								+ "<TD>"+pageBean.list[i].msg+"</TD>"
							+"</TR>";
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
			},
			error:function(){
				alert("日志加载失败");
			},
			dataType:"json"
		});
		$('#currentCount').change(function(){
			document.logForm.submit();
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
		document.logForm.submit();
	}
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="logForm" name="logForm"
		action="${pageContext.request.contextPath }/jsp/system/log.jsp"
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
								<TD class=manageHead>当前位置：系统管理 &gt; 日志查询</TD>
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
													<TD>用户ID：</TD>
													<TD><INPUT class=textbox id=stid
														style="WIDTH: 80px" maxLength=50 name="stid" value="${param.stid}"></TD>
													<TD>时间：</TD>
													<TD><INPUT class=textbox id=createtime
														style="WIDTH: 80px" maxLength=50 name="createtime" value="${param.createtime}"></TD>
													<TD>日志等级：</TD>
													<TD>
													   <select name="loglevel" id="loglevel">
													       <option value='-1'>--------请选择--------</option>
													       <option value='INFO'>INFO</option>
													       <option value='ERROR'>ERROR</option>
													   </select>
													</TD>
													<TD><INPUT class=button id=sButton2 type=submit
														value=" 筛选 " name=sButton2><span style="height: 18px;color: red;">${error }</span></TD>
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
													<%-- <c:if test="${student.roleId==1 }">
														<input type="button" id="delBnt" value="批量删除">
													</c:if> --%>
														<input type="checkbox" id="checkAll" name="checkAll">
													</TD>
													<TD>时间</TD>
													<TD>用户ID</TD>
													<TD>日志等级</TD>
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
													<option value="20">20</option>
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
