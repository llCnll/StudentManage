<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>添加客户</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$.ajax({
			url:"${pageContext.request.contextPath}/student",
			async:true,
			data:{"method":"edit","id":"${param.stid}"},
			type:"post",
			success:function(student){
				$("#id").val(student.id);
				$("#name").val(student.name);
				$("#name").val(student.name);
				$("#classesName").val(student.classes.name);
				$("#classesId").val(student.classes.id);
				
				$("#courseName").val("${param.cname}");
				$("#cid").val("${param.cid}");
				/* for(var course in student["courses"]){

					console.log(course);   //键

					//consolelog(json[key])  //值

					} */
				 for(var i=0;i<student.courses.length;++i){
				        if(student.courses[i]["id"] == '${param.cid}'){
				        	if(student.courses[i].score != null){
					        	var score1 =student.courses[i].score.score1;
					        	$("#score1").val(score1);
					        	if(score1 < 60){
					        		var score2 = student.courses[i].score.score2;
					        		if(score2 != null){
					        			$("#score2").val(score2);
					        			if(score2 < 60){
					        				var score3 = student.courses[i].score.score3;
					        				if(score3 != null){
					        					$("#score3").val(score3);
					        				}
					        			}else{
					        				//禁用score3
					        				$("#score3").prop("disabled", "true");
					        			}
					        		}
					        	}else{
					        		//禁用score2 score3
					        		$("#score2").prop("disabled", "true");
					        		$("#score3").prop("disabled", "true");
					        	}
					        }
				        	break;
				        }
				}
				//$("#classes").val(student.classes.id);
				//$("#roleId").val(student.roleId);
			},
			error:function(){
				alert("请求失败");
			},
			dataType:"json"
		});
		
		/* $.ajax({
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
		}); */
		
		$("#score1").blur(function (){
			var score = $("#score1").val();
			//alert(score < 60);
			if(score < 60){
				$("#score2").removeAttr("disabled");
        	}else{
        		//禁用score2 score3
				$("#score2").val('');
				$("#score3").val('');
        		$("#score2").prop("disabled", "true");
        		$("#score3").prop("disabled", "true");
        	}
		});
		$("#score2").blur(function (){
			var score = $("#score2").val();
			//alert(score < 60);
			if(score < 60){
				$("#score3").removeAttr("disabled");
        	}else{
        		//禁用score2 score3
				$("#score3").val('');
        		$("#score3").prop("disabled", "true");
        	}
		});
		
	})
	
</script>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id=form1 name=form1
		action="${pageContext.request.contextPath }/score"
		method=post>
		<input type="hidden" name="method" value="saveScore">
		<input type="hidden" name="semester" value="1">
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
					<TD width=15 background="${pageContext.request.contextPath }/images/new_022.jpg"><IMG
						src="${pageContext.request.contextPath }/images/new_022.jpg" border=0></TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD class=manageHead>当前位置：成绩管理 &gt; 成绩录入</TD>
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
														style="width: 180px" maxlength=50 name="name" readonly>
								</td>
							</tr>
							
							<tr>
								<td>用户班级 ：</td>
								<td>
								<!-- <select class=textbox id="classes"
														style="width: 180px; height: 24px;" name="classesId" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
									<option value="-1">加载中...</option>
								</select> -->
								<input class=textbox id="classesName"
														style="width: 180px" maxlength=50 readonly>
								<input type="hidden" name="classesId">
								</td>
								<td>当前课程 ：</td>
								<td>
									<input class=textbox id="courseName"
														style="width: 180px" maxlength=50 readonly>
									<input type="hidden" id="cid" name="cid">
								</td>
							</tr>
							<tr>
								<td>一考 ：</td>
								<td>
									<input class=textbox id="score1"
														style="width: 180px" maxlength=50 name="score1">
								</td>
								<td>二考 ：</td>
								<td>
									<input class=textbox id="score2"
														style="width: 180px" maxlength=50 name="score2">
								</td>
							</tr>
							<tr>
								<td>三考 ：</td>
								<td>
									<input class=textbox id="score3"
														style="width: 180px" maxlength=50 name="score3">
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
