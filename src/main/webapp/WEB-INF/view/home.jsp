<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>TaskManagementHome</title>
<link type="text/css" rel="stylesheet" href="resources/css/style_main.css"/>
<%@ page isELIgnored="false"%>
</head>
<body>
<div class="assign_view">
<span class="successMsg">${rowsCount}</span>
<button type="button" class="assign_task" onclick="location.href='assigntask'">Assign tasks</button>
<button type="button" class="task_view" onclick="location.href='taskview'">Task view</button>
</div>
</body>
</html>
