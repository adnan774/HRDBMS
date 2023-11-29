<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Employees</title>
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
</div>	
	<div class="container">
        <form method="POST" action="./UpdateController">
            <input type="hidden" name="emp_id" value="${employee.emp_id}">
            <div class="form-group">
                <label for="first_name">Enter First Name:</label>
                <input type="text" class="form-control" id="first_name" name="first_name" placeholder="Enter first name" value="${employee.first_name}">
            </div>
            <div class="form-group">
                <label for="last_name">Enter Last Name:</label>
                <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Enter last name" value="${employee.last_name}">
            </div>
            <div class="form-group">
                <label for="email">Enter Email:</label>
                <input type="text" class="form-control" id="email" name="email" placeholder="Enter email" value="${employee.email}">
            </div>
            <div class="form-group">
                <label for="date_of_birth">Enter Date of Birth:</label>
                <input type="date" class="form-control" id="date_of_birth" name="date_of_birth" placeholder="Enter date of birth" value="${employee.dob}">
            </div>
            <div class="form-group">
                <label for="phone_number">Enter Phone Number:</label>
                <input type="text" class="form-control" id="phone_number" name="phone_number" placeholder="Enter phone number" value="${employee.phone_number}">
            </div>
            <div class="form-group">
                <label for="hire_date">Enter Hire Date:</label>
                <input type="date" class="form-control" id="hire_date" name="hire_date" placeholder="Enter hire date" value="${employee.hire_date}">
            </div>
            <div class="form-group">
                <label for="address">Enter Address:</label>
                <input type="text" class="form-control" id="address" name="address" placeholder="Enter address" value="${employee.address}">
            </div>
            <div class="form-group">
                <label for="city">Enter City:</label>
                <input type="text" class="form-control" id="city" name="city" placeholder="Enter city" value="${employee.city}">
            </div>
            <div class="form-group">
                <label for="town">Enter Town:</label>
	
					<input type="text" class="form-control" id="town" name="town" placeholder="Enter town" value="${employee.town}">
        </div>
        <div class="form-group">
            <label for="post_code">Enter Postcode:</label>
            <input type="text" class="form-control" id="post_code" name="post_code" placeholder="Enter postcode" value="${employee.post_code}">
        </div>
        <div class="form-group">
            <label for="department_name">Select Department:</label>
            <select class="form-control" name="department_name" id="department_name">
                <c:forEach items="${departmentNames}" var="dn">
                    <option value="${dn}" ${dn == employee.getDepartments().getDepartment_name() ? 'selected' : ''}>${dn}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="job_title">Select Job Title & Salary:</label>
            <select class="form-control" name="job_title" id="job_title">
                <c:forEach items="${jobTitles}" var="jt">
                    <option value="${jt}" ${jt == employee.getSalaries().getJob_title() ? 'selected' : ''}>${jt}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
		
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>			
</body>
</html>