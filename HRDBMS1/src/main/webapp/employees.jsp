<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employees</title>
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
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" 
    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
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



</body>
</html>