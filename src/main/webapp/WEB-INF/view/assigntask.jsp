<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign Tasks</title>
<link type="text/css" rel="stylesheet" href="resources/css/style_main.css"/>
</head>
<body>
<form:form action="assigntask" commandName="taskAssigned" method="post">
	<table class="assigntask_table">
		<tr>
			<td><label class="assign_heading">Assign Tasks</label><br><br></td>
		</tr>
		<tr>
			<td>Project<span class="star">*</span></td>
			<td>
				<form:select path="projectName" name="projectName" id="projectName">
						<form:option value="">--Select Project--</form:option>
					<c:forEach var="project" items="${projects}">
						<form:option value="${project.projectId}">${project.projectName}</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><form:errors path="projectName" id="projectNameError"/></td>
		</tr>
		<tr>
			<td>Description<span class="star">*</span></td>
			<td><form:input path="description" id="description"/></td>
			<td><form:errors path="description" id="descriptionError"/></td>
		</tr>
		<tr>
			<td>Start Date of Task[dd-mm-yyyy]<span class="star">*</span></td>
			<td><form:input path="startDate" placeholder="DD-MM-YYYY" id="startDate"/></td>
			<td><form:errors path="startDate" id="startDateError"/></td>
		</tr>
		<tr>
			<td>Due Date of Task[dd-mm-yyyy]<span class="star">*</span></td>
			<td><form:input path="endDate" placeholder="DD-MM-YYYY" id="endDate"/></td>
			<td><form:errors path="endDate" id="endDateError"/></td>
		</tr>
		<tr>
			<td>Who Should do this?<span class="star">*</span></td>
			<td>
				<form:select path="employeeList" id="employeeList" name="employeeList" multiple="true">
				</form:select>
			</td>
			<td><form:errors path="employeeList" id="employeeListError"/></td>
		</tr>
		<tr>
			<td><button type="submit" id="addTask">Add a task</button></td>
			<td><button type="button" id="cancel" onclick="location.href='/TaskManagement'">Cancel</button></td>
		</tr>
	</table>
</form:form>
<script type='text/javascript' src='resources/js/jquery-3.2.1.min.js'></script>
<script type='text/javascript' src='resources/js/jquery.maskedinput.js'></script>
<script type='text/javascript' src='resources/js/script.js'></script>
<script type="text/javascript">
	var status = ${projectStatus};
	if(status){
		$.getJSON("loadEmployees/"+$("#projectName").val(), function(data){
			$('#employeeList').empty();
		    $.each(data, function (index, taskManagement) {
		        $('#employeeList').append("<option value='" + taskManagement.employeeId + "'>" + taskManagement.employeeName +" "+ taskManagement.employeeId +"</option>");
		    });
		});
	} 
</script>
</body>
</html>