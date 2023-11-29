<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Salaries</title>
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
    <h1 class="text-center text-primary">Salaries Table</h1>
     <div class="d-flex flex-column align-items-center">
    	<button type="button" class="nav-link  btn btn-info" data-bs-toggle="modal" data-bs-target="#insertSalaryModal">
  			+ Insert Salary
		</button>
	</div>
</div>

<div class="modal fade" id="insertSalaryModal" tabindex="-1" aria-labelledby="insertSalaryModallabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="insertSalaryModallabel">Insert Salary</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
</div>

   <div class="modal-body">
	<fieldset>
		<form method="POST" action="./salary">
			<input type="hidden" name="salary_id">
			<label for="job_title" class="form-label">Job Title:</label>
			<input type="text" name="job_title" placeholder="Enter job title">
			<label for="Salary" class="form-label">Salary:</label>
			<input type="text" name="salary" placeholder="Enter salary">	
			<input  class="btn btn-primary b1" type="submit" value="Insert">
		</form> 
	</fieldset>
</div>
		</div>
	</div>
</div>


</body>
</html>