<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>添加客户</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/bootstrap.min.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/xlsx.full.min.js"></script>
<script type="text/javascript">

	var wb;//��ȡ��ɵ�����
	var rABS = false; //�Ƿ��ļ���ȡΪ�������ַ���
	var json = '';
	var jsonStr = '';
	function importf(obj) {//����
	    if(!obj.files) {
	        return;
	    }
	    var f = obj.files[0];
	    var reader = new FileReader();
	    reader.onload = function(e) {
	        var data = e.target.result;
	        if(rABS) {
	            wb = XLSX.read(btoa(fixdata(data)), {//�ֶ�ת��
	                type: 'base64'
	            });
	        } else {
	            wb = XLSX.read(data, {
	                type: 'binary'
	            });
	        }
			console.log(wb);
	        //wb.SheetNames[0]�ǻ�ȡSheets�е�һ��Sheet������
	        //wb.Sheets[Sheet��]��ȡ��һ��Sheet������
	       // document.getElementById("demo").innerHTML= JSON.stringify( XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]) );
	    	json = JSON.stringify( XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]) );
	    	jsonStr = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
	    	tableSt(jsonStr);
			//alert((XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]))[0]);
	    };
	    if(rABS) {
	        reader.readAsArrayBuffer(f);
	    } else {
	        reader.readAsBinaryString(f);
	    }
	}
	
	function fixdata(data) { //�ļ���תBinaryString
	    var o = "",
	        l = 0,
	        w = 10240;
	    for(; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
	    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
	    return o;
	}
	
	function tableSt(json){
		var content = ''
		if(json == ''){
			content = "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'><td colspan='5'><strong>无数据!</strong><td><tr>";
			$('#tbodyAdd').html($('#tbodyAdd').html()+content);
		}else{
			for(var i = 0 ; i < json.length; ++i){
		    		content += "<TR style='FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none'>"
						+ "<TD><input name='id' style=' text-align:center' value='"+json[i].学号+"' size='8' readonly></TD>"
						+ "<TD><input name='name' style=' text-align:center' value='"+json[i].姓名+"' size='6' readonly></TD>"
						+ "<TD><input name='pwd' style=' text-align:center' value='"+(typeof(json[i].密码)== "undefined" ?"123456":json[i].密码)+"' size='6' readonly></TD>"
						+ "<TD><input name='classesName' style=' text-align:center' value='"+json[i].班级+"' size='8' readonly></TD>"
						+ "<TD><input name='roleId' style=' text-align:center' value='"+json[i].等级+"' size='8' readonly></TD>"
						/* + "<TD>"
						+"<a href='${pageContext.request.contextPath}/jsp/customer/edit.jsp?id="+pageBean.list[i].id+"'>修改</a>"
						+"&nbsp;&nbsp;"
						+"<a href='${pageContext.request.contextPath}/student?id="+pageBean.list[i].id+"&method=del'>删除</a>"
						+"</TD>" */
					+"</TR>";
			}
			$('#tbodyAdd').html($('#tbodyAdd').html()+content);
		}
	
	}

	$(function(){
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
		
		$('#addBatchButton').click(function(){
	    	$.ajax({
	    		url:"${pageContext.request.contextPath }/student",
	    		type:"post",
	    		data:$('#addBatchForm').serialize(),
	    		success:function(ids){
					alert("添加成功!");
	    		},
				error:function(){
					alert("请求失败");
				},
	    		datatype:"json"
	    	});
	    	
	    });
		
	});
    
</script>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY style="BACKGROUND-COLOR: #2a8dc8;">
	<FORM id=form1 name=form1
		action="${pageContext.request.contextPath }/student"
		method=post>
		<input type="hidden" name="method" value="add"/>
	
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
								<TD class=manageHead>当前位置：用户管理 &gt; 添加用户</TD>
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
														style="width: 180px" maxlength=50 name="id" value="${param.id }">
								</td>
								<td>用户名称 ：</td>
								<td>
								<input class=textbox id="name"
														style="width: 180px" maxlength=50 name="name" value="${param.name }">
								</td>
							</tr>
							
							<tr>
								
								<td>用户密码 ：</td>
								<td>
								<input class=textbox id="pwd"
														style="width: 180px" maxlength=50 name="pwd" value="${param.pwd }">
								</td>
								<td>确认密码：</td>
								<td>
								<input class=textbox id="repwd"
														style="width: 180px" maxlength=50 value="${param.pwd }">
								</td>
							</tr>
							
							<tr>
								
								
								<td>用户班级 ：</td>
								<td>
								<select class=textbox id="classes"
														style="width: 180px; height: 24px;" name="classesId" >
									<option value="-1">加载中...</option>
								 </select>
								</td>
								<td>用户权限 ：</td>
								<td>
								<select class=textbox id="st_roleId"
														style="width: 180px; height: 24px;" name="roleId">
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
								<td rowspan=2>
								<input class=button id=addBactch type=button value="批量添加 " data-toggle="modal" data-target="#myModal">
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
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						批量添加学生信息
					</h4>
				</div>
				<form id="addBatchForm">
					<div class="modal-body" style="text-align: center;">
						<input type="file"onchange="importf(this)" />
						<div id="demo">
						 	<input type="hidden" name="method" value="addBactch">
							<TABLE id=grid
								style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
								cellSpacing=1 cellPadding=2 rules=all border=0>
								<TBODY id="tbodyAdd">
									<TR
										style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
										<TD>用户ID</TD>
										<TD>用户姓名</TD>
										<TD>用户密码</TD>
										<TD>用户班级</TD>
										<TD>用户权限</TD>
									</TR>
								</TBODY>
							</TABLE>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="addBatchButton">
							提交更改
						</button>	
					</div>
				</form>
			</div>
		</div>
	</div>
</BODY>
</HTML>
