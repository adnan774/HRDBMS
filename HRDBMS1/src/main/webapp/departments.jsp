<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Departments</title>
<!-- CSS -->
<link rel="stylesheet"
	href="./css/styles.css"
    type="text/css"/>
<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" 
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous"/>
</head>
<body>
<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg bg-primary">

<div class="container-fluid">
    <a class="navbar-brand text-white" href="./employee">
      HRDBMS
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Tables
          </a>
          <ul class="dropdown-menu">
          	<li><a class="dropdown-item" href="./employee">Employees</a></li>
            <li><a class="dropdown-item" href="./salary">Salaries</a></li>
            <li><a class="dropdown-item" href="./department">Departments</a></li>
          </ul>
        </li>

        
        </ul>
		<form method="GET" class="d-flex" action="./search">
			<input class="form-control me-2" type="search" name="search" placeholder="Search" aria-label="Search">
			 <button class="btn btn-primary" type="submit">Search</button>
    		<br/>	
    	</form>
    	<a class="btn btn-secondary" href="./logout">Logout</a>
	</div>
</div>
</nav>

<div class="container mt-4">
    <h1 class="text-center text-primary">Departments Table</h1>
    <div class="d-flex flex-column align-items-center">
    <button type="button" class="nav-link  btn btn-info" data-bs-toggle="modal" data-bs-target="#insertDepartmentModal">
  			+ Insert Department
		</button>
		</div>
</div>

<div class="modal fade" id="insertDepartmentModal" tabindex="-1" aria-labelledby="insertDepartmentModallabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="insertDepartmentModallabel">Insert Department</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      		</div>
      		<div class="modal-body">
	<fieldset>
		<form method="POST" action="./department">
			<input type="hidden" name="department_id">
			<label for="department_name" class="form-label">Department Name:</label>
			<input type="text" name="department_name" placeholder="Enter Department Name">
			<label for="location" class="form-label">Location:</label>
			<input type="text" name="location" placeholder="Enter Location">	
			<input  class="btn btn-primary b1" type="submit" value="Insert">
		</form> 
	</fieldset>
	</div>
		</div>
	</div>
</div>

<div class="table-container">
<table id="departments" border='1' class="table table-bordered border-primary mt-4 mx-auto w-auto">
	<tr class="table-primary">
		<th>Department ID</th>
		<th>Department Name</th>
		<th>Location</th>
		<th>Update</th>
		<th>Delete</th>
	</tr>
	<c:forEach items="${departments}" var="d">
	<tr class='table-secondary'>
		<td>${d.department_id}</td>
		<td>${d.department_name}</td>
		<td>${d.location}</td>
		<td><a class="btn btn-info" href="./UpdateController?department_id=${d.department_id}">Update</a></td>
		<td><a class="btn btn-danger" href="./DeleteController?department_id=${d.department_id}">Delete</a></td>
	</tr>
	</c:forEach>
</table>
	<br>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>	
</body>
</html>