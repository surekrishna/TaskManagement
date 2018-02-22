$(function(){
	$("#startDate,#endDate").mask("99-99-9999",{"placeholder": "DD-MM-YYYY"});
});
$(document).ready(function() {
	$('#projectName').change(function() {
		if("" == $("#projectName").val()){
			$('#employeeList').empty();
		}
		$.getJSON("loadEmployees/"+$("#projectName").val(), function(data){
			$('#employeeList').empty();
		    $.each(data, function (index, taskManagement) {
		        $('#employeeList').append("<option value='" + taskManagement.employeeId + "'>" + taskManagement.employeeName +" "+ taskManagement.employeeId +"</option>");
		    });
		});
	});
	
	$('#taskViewProjectName').change(function() {
		if($("#taskViewProjectName").val() == "--Select Project--"){
			$('#allData').empty();
			$("#taskProjectName").empty();
			$("#taskDescription").empty();
			$("#taskStartDate").empty();
			$("#taskEndDate").empty();
		}
		
		try {
			var table = document.getElementById("id_name");
			var rowCount = table.rows.length;
			if(rowCount != 1){
				for(var i=1; i<rowCount; i++) {
					var row = table.rows[i];
					table.deleteRow(i);
					rowCount--;
					i--;
				}
			}
			}catch(e) {
				console.log(e);
			}
		
		var singleStatus = 0;
		var table = document.getElementById("id_name");
        var row = null;
        var cell1 = null;
        var cell2 = null;
        
        var projectsList = [];
        var taskManagementList = new Array();
        
        if($("#taskViewProjectName").val() == "ALL"){
        	$.getJSON("loadProjectsAndEmployees/"+$("#taskViewProjectName").val(), function(data){
    			$('#taskProjectName,#taskDescription,#taskStartDate,#taskEndDate').empty();
    			var projectData = [];
    			var status = true;
    			for(var i = 0 ; i < data.length ; i ++){
    				for(var j = 0 ; j < data[i].employeeSet.length ; j++){
    					if(status == true){
        					projectData.push("<table><tr><td><b>Project: </b></td><td><b>"+data[i].projectName+"</b></td></tr><tr><td>Task Description: </td><td>"+data[i].employeeSet[j].description+"</td></tr><tr><td>Task Start Date: </td><td>"+data[i].employeeSet[j].startDate+"</td></tr><tr><td>Task End Date: </td><td>"+data[i].employeeSet[j].endDate+"</td></tr><table border='1'><tr><th>MID</th><th>Employee Name</th><tr><td>"+data[i].employeeSet[j].employeeId+"</td><td>"+data[i].employeeSet[j].employeeName+"</td></tr>");
        					status = false;
        				}else{
        					projectData.push("<table border='1'><tr><td>"+data[i].employeeSet[j].employeeId+"</td><td>"+data[i].employeeSet[j].employeeName+"</td></tr></table>");
        				}projectData.push("</table></table>");
    				}status = true;
    		    	projectData.push("<br>");
		    	}
		    	$("#allData").append(projectData);
    		});
        	
        }else if($("#taskViewProjectName").val() != "--Select Project--"){
        	$.getJSON("loadProjectsAndEmployees/"+$("#taskViewProjectName").val(), function(data){
    			$('#taskProjectName,#taskDescription,#taskStartDate,#taskEndDate').empty();
    		    $.each(data, function (index, projects) {
    		    	//alert(JSON.stringify(projects.employeeSet.length))
    		    	if(projects.employeeSet.length > 0){
    		    		if($("#taskViewProjectName").val() != "ALL"){
        		    		singleStatus++;	
        		    	}
        		    	if(singleStatus == 1){
        		    		$('#taskProjectName').append("Project:"+$("#taskViewProjectName").val());
        		    		$('#taskDescription').append("Task Description:"+projects.employeeSet[0].description);
        		    		$('#taskStartDate').append("Task Start Date:"+projects.employeeSet[0].startDate);
        		    		$('#taskEndDate').append("Task End Date:"+projects.employeeSet[0].endDate);
        		    		$("#id_name").show();
        		    		$("#allData").empty();
        		    	}
        		    	for(var i = 0; i< projects.employeeSet.length;i++){
        		    		row = table.insertRow(i+1);
        		    		cell1 = row.insertCell(0);
        		    		cell2 = row.insertCell(1);
        		    		cell1.innerHTML = projects.employeeSet[i].employeeId;
        		    		cell2.innerHTML = projects.employeeSet[i].employeeName;
        		        }
    		    	}
    		    });
    		});
        }
        $("#id_name").hide();
	});
});

