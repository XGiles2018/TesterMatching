<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
	.selected{
		background-color:gray;
	}
</style>
<script>
$(document).ready(function(){
	console.log('Page loaded');
	$("#deviceTable tr").click(function(){
		if(this.className == 'selected'){
			$(this).removeClass('selected');
		}else{  
			$(this).addClass('selected');  
		}});
	
	$("#countryTable tr").click(function(){
		if(this.className == 'selected'){
			$(this).removeClass('selected');
		}else{  
			$(this).addClass('selected');  
		}});
});

function getCriteriaResults(){
	var devicesSelected = [];
	var countriesSelected = [];
	
	
	$('#deviceTable tr').each(function(){
		if(this.className == 'selected'){
			devicesSelected.push($(this).find('td').text());
		}
	});
	
	$('#countryTable tr').each(function(){
		if(this.className == 'selected'){
			countriesSelected.push($(this).find('td').text());
		}
	});
	
	console.log('Devices: ' + devicesSelected);
	console.log('Countries: ' + countriesSelected);
	
	$.ajax({
			url:'getCriteriaResults.html',
			type:'POST',
			data:{
				devices: JSON.stringify(devicesSelected),
				countries: JSON.stringify(countriesSelected)
			}}).success(function(data){
				console.log(data);
				alert(data);
			});
}

</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Applause Interview Challenge</title>
</head>
<body>
	<h1>Applause Interview Challenge</h1>
	<hr>
	<p>Select criteria entries by clicking rows in the table. Unselect the entries by clicking again.
	Press the submit button to receive results of criteria sorted by Experience!</p>
	<table class="table">
	<thead> 
		<tr><th>Devices</th><th>Countries</th></tr>
	</thead>
	<tr><td>
		<table id="deviceTable">
				<c:forEach items="${devices}" var="device">
					<tr>
						<td>${device}</td>
					</tr>
				</c:forEach>
		</table>
		</td>
		<td>
			<table id="countryTable">
				<c:forEach items="${countries}" var="country">
					<tr>
						<td>${country}</td>
					</tr>
				</c:forEach>
			</table>	
		</td></tr>
	</table>
	<button onClick="getCriteriaResults()">Submit</button>
	

</body>
</html>