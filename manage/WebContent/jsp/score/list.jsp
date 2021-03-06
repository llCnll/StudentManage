﻿  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>成绩查询</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<SCRIPT language=javascript>

	$(function (){
		
		checkId();
		
		var classes = '${param.classes}';
		var courses = '${param.courses}';
		var roleId = '${student.roleId}';
		//学生列表
		$.ajax({
			url:"${pageContext.request.contextPath}/student",
			async:true,
			type:"POST",
			data:{"method":"selectCourseList"<c:if test="${student.roleId==1 }">,"id":"${param.id}", "name":"${param.name}", "classes":"${param.classes}", "courses":"${param.courses}", "page":"${param.page}", "currentCount":"${param.currentCount}"</c:if><c:if test="${student.roleId!=1 }">,"id":"${student.id }"</c:if>},
			success:function(pageBean){
				var content = "";
				var stid = ${student.id};
				//pageBean.list.length;
				for(var i = 0; pageBean.list!=null && i < pageBean.list.length; ++i){
					if(${student.roleId == 1}){
						content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
									+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+"style=\"width: 76px;\"><input type=\"checkbox\" name=\"select\" value=\""+pageBean.list[i].id+"\"/></TD>"
									+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+">"+pageBean.list[i].id+"</TD>"
									+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+">"+pageBean.list[i].name+"</TD>"
									+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+">"+pageBean.list[i].classes.name+"</TD>";
									/* 选课情况 */
									if(pageBean.list[i].courses == undefined){
										content  += "<td>未选课</td>";
										content += "<TD>--</TD><TD>--</TD>";
										if(roleId == 1)
											content +=	"<TD>未选课";
									}else{
										content += "<TD>"+pageBean.list[i].courses[0].name+"</TD>";
										if(pageBean.list[i].courses[0].score != null){
											content += "<TD>"+(pageBean.list[i].courses[0].score.score1==null?"--":pageBean.list[i].courses[0].score.score1)+"</TD>";
											content += "<TD>"+(pageBean.list[i].courses[0].score.score2==null?"--":pageBean.list[i].courses[0].score.score2)+"</TD>";
											if(roleId == 1)
												content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[0].id+"', '"+pageBean.list[i].courses[0].name+"')\">修改</a>"
										}else{
											content += "<TD>--</TD><TD>--</TD>";
											if(roleId == 1)
											content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[0].id+"', '"+pageBean.list[i].courses[0].name+"')\">录入</a>"
										}
									}
								+"</TR>";
						if(pageBean.list[i].courses != undefined){
							for(var j = 1; j < pageBean.list[i].courses.length; ++j){
								content += "<Tr style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
										+ "<td>"+pageBean.list[i].courses[j].name+"</td>";
										if(pageBean.list[i].courses[j].score != null){
											content += "<TD>"+(pageBean.list[i].courses[j].score.score1==null?"--":pageBean.list[i].courses[j].score.score1)+"</TD>"
													+  "<TD>"+(pageBean.list[i].courses[j].score.score2==null?"--":pageBean.list[i].courses[j].score.score2)+"</TD>"
											if(roleId == 1)
												content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[j].id+"', '"+pageBean.list[i].courses[j].name+"')\">修改</a>"
										}else{
											content += "<TD>--</TD><TD>--</TD>";
											if(roleId == 1)
												content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[j].id+"', '"+pageBean.list[i].courses[j].name+"')\">录入</a>"
										}
								content += "</Tr>";
							}
						}
					}
					else{
						if(pageBean.list[i].id == stid){
							content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
								+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+"style=\"width: 76px;\"><input type=\"checkbox\" name=\"select\" value=\""+pageBean.list[i].id+"\"/></TD>"
								+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+">"+pageBean.list[i].id+"</TD>"
								+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+">"+pageBean.list[i].name+"</TD>"
								+ "<TD "+(pageBean.list[i].courses == undefined ? ("rowspan=1"):("rowspan="+pageBean.list[i].courses.length))+">"+pageBean.list[i].classes.name+"</TD>";
								/* 选课情况 */
								if(pageBean.list[i].courses == undefined){
									content  += "<td>未选课</td>";
									content += "<TD>--</TD><TD>--</TD>";
									if(roleId == 1)
										content +=	"<TD>未选课";
								}else{
									content += "<TD>"+pageBean.list[i].courses[0].name+"</TD>";
									if(pageBean.list[i].courses[0].score != null){
										content += "<TD>"+(pageBean.list[i].courses[0].score.score1==null?"--":pageBean.list[i].courses[0].score.score1)+"</TD>";
										content += "<TD>"+(pageBean.list[i].courses[0].score.score2==null?"--":pageBean.list[i].courses[0].score.score2)+"</TD>";
										if(roleId == 1)
											content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[0].id+"', '"+pageBean.list[i].courses[0].name+"')\">修改</a>"
									}else{
										content += "<TD>--</TD><TD>--</TD>";
										if(roleId == 1)
										content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[0].id+"', '"+pageBean.list[i].courses[0].name+"')\">录入</a>"
									}
								}
							+"</TR>";
					if(pageBean.list[i].courses != undefined){
						for(var j = 1; j < pageBean.list[i].courses.length; ++j){
							content += "<Tr style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
									+ "<td>"+pageBean.list[i].courses[j].name+"</td>";
									if(pageBean.list[i].courses[j].score != null){
										content += "<TD>"+(pageBean.list[i].courses[j].score.score1==null?"--":pageBean.list[i].courses[j].score.score1)+"</TD>"
												+  "<TD>"+(pageBean.list[i].courses[j].score.score2==null?"--":pageBean.list[i].courses[j].score.score2)+"</TD>"
										if(roleId == 1)
											content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[j].id+"', '"+pageBean.list[i].courses[j].name+"')\">修改</a>"
									}else{
										content += "<TD>--</TD><TD>--</TD>";
										if(roleId == 1)
											content +=	"<TD><a href='javascript:;' onclick=\"update('"+pageBean.list[i].id+"', '"+pageBean.list[i].courses[j].id+"', '"+pageBean.list[i].courses[j].name+"')\">录入</a>"
									}
							content += "</Tr>";
						}
					}
						}
					}
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
		//课程下拉框
		$.ajax({
			url:"${pageContext.request.contextPath}/course",
			data:{"method":"select"},
			async:true,
			type:"POST",
			success:function(list){
				var content = "<option value='-1'>--------请选择--------</option>";
				for(var i = 0; i < list.length; ++i){
					if(courses == list[i].id){
						content += "<option value='"+list[i].id+"' selected>"+list[i].name+"</option>";
					}else{
						content += "<option value='"+list[i].id+"'>"+list[i].name+"</option>";
					}
				}
				$("#courses").text('');
				$('#courses').html($('#courses').html()+content);
			},
			error:function(){
				alert("课程列表请求失败");
			},
			dataType:"json"
		});
		//id 和 (姓名和班级)
		function checkId(){
			var id = $("#id").val();
			if(id.length != 0){
				$("#name").val('');
				$("#name").attr("disabled", true);
				$("#classes").val("-1");
				$("#classes").attr("disabled", true);
				$("#courses").val("-1");
				$("#courses").attr("disabled", true);
			}else{
				$("#name").attr("disabled", false);
				$("#classes").attr("disabled", false);
				$("#courses").attr("disabled", false);
			}
		}
		$("#id").blur(function (){
			checkId();
		});
		$('#currentCount').change(function(){
			document.scoreForm.submit();
		});
	});
	
	function to_page(page){

		judgePage(page);
		document.scoreForm.submit();
	}
	
	function update(id, cid, cname){
		//console.log("id: "+id+" cid: "+cid+" cname: "+cname);
		//window.location.href = "${pageContext.request.contextPath}/student?method=returnSt&id="+id+"&goto=score";
		cname = encodeURI(cname).replace(/\+/g,'%2B');
		window.location.href = "${pageContext.request.contextPath}/jsp/score/edit.jsp?stid="+id+"&cid="+cid+"&cname="+cname;
	}
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="scoreForm" name="scoreForm"
		action="${pageContext.request.contextPath }/jsp/score/list.jsp"
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
								<TD class=manageHead>当前位置：成绩管理 &gt; 成绩查询</TD>
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
														<TD>课程名：</TD>
														<TD>
														   <select name="courses" id="courses">
														       <option value="-1">加载中...</option>
														   </select>
														</TD>
														<TD><INPUT class=button id=sButton2 type=submit
															value=" 筛选 " name=sButton2><span style="height: 18px;color: red;">${error }${message }</span></TD>
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
													<TD>课程</TD>
													<TD>一考</TD>
													<TD>二考</TD>
													<c:if test="${student.roleId == 1 }">
														<TD>操作</TD>
													</c:if>
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
