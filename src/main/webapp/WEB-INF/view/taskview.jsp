<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task view</title>
<link type="text/css" rel="stylesheet" href="resources/css/style_main.css"/>
</head>
<body>
<table class="assigntask_table">
	<tr>
		<td><label class="task_heading">View Tasks</label><br><br></td>
	</tr>
	<tr>
		<td><span class="filter_project">Filter by Project</span><br><br><br><br></td>
		<td>
			<select id="taskViewProjectName" name="projectName">
					<option>--Select Project--</option>
					<option value="ALL">All Projects</option>
				<c:forEach var="project" items="${projects}">
					<option value="${project.projectName}">${project.projectName}</option>
				</c:forEach>
			</select><br><br><br><br>
		</td>
	</tr>
	<tr>
		<td>
			<span class="taskProjectName" id="taskProjectName"></span><br><br>
		</td>
	</tr>
	<tr>
		<td>
			<span id="taskDescription"></span>
		</td>
	</tr>
	<tr>
		<td>
			<span id="taskStartDate"></span>
		</td>
	</tr>
	<tr>
		<td>
			<span id="taskEndDate"></span>
		</td>
	</tr>
	<tr>
		<td>
			<div class="employeeDetails" id="employeeDetails">
				<table id="id_name" border='1' style="display: none;">
					<tr><th>MID</th>
					<th>Employee Name</th>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div id="allData"></div>
		</td>
	</tr>
</table>
<script type='text/javascript' src='resources/js/jquery-3.2.1.min.js'></script>
<script type='text/javascript' src='resources/js/jquery.maskedinput.js'></script>
<script type='text/javascript' src='resources/js/script.js'></script>
</body>
</html>