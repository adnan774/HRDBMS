<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Departments</title>
<!-- CSS -->
<link rel="stylesheet"
	href="./css/styles.css"
    type="text/css"/>
<!-- Add Bootstrap CSS -->
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
            <li><a class="dropdown-item" href="./salary">SALARIES</a></li>
            <li><a class="dropdown-item" href="./department">DEPARTMENTS</a></li>
          </ul>
        </li>
       
        </ul>
		
	</div>
</div>
</nav>

<div class="container mt-4">
    <h1 class="text-center text-primary">Update Form</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form method="POST" action="./UpdateController">
                <input type="hidden" name="department_id" value="${department.department_id}">
                
                <div class="mb-3">
                    <label for="department_name" class="form-label">Enter Department Name:</label>
                    <input type="text" class="form-control" id="department_name" name="department_name" placeholder="Enter department name" value="${department.department_name}">
                </div>
                
                <div class="mb-3">
                    <label for="location" class="form-label">Enter Location:</label>
                    <input type="text" class="form-control" id="location" name="location" placeholder="Enter location" value="${department.location}">
                </div>
                
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>		
</body>
</html>