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


<div class="container mt-4">
    <h1 class="text-center text-primary">Employees Table</h1>
    <div class="d-flex flex-column align-items-center">
    <button type="button" class="nav-link  btn btn-info" data-bs-toggle="modal" data-bs-target="#insertEmployeeModal">
  			+ Insert Employee
		</button>
		</div>
</div>


<div class="modal fade" id="insertEmployeeModal" tabindex="-1" aria-labelledby="insertEmployeeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="insertEmployeeModalLabel">Insert Employees</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      		</div>
      		<div class="modal-body">
			<fieldset>
				<form method="POST" action="./employee">
				
					<div class="col">
						<input type="hidden" name="emp_id">
						</div>
					<div class="row">
						<div class="col-md-6">
							<label for="first_name" class="form-label">First Name:</label>
							<input class="form-control form-control-sm" type="text" name="first_name" placeholder="Enter first name">
						</div>
						<div class="col-md-6">
							<label for="last_name" class="form-label">Last Name:</label>
							<input class="form-control form-control-sm" type="text" name="last_name" placeholder="Enter last name">
						</div>
						<div class="col-md-6">
							<label for="email" class="form-label">Email:</label>	
							<input class="form-control form-control-sm" type="text" name="email" placeholder="Enter email">
						</div>
						<div class="col-md-6">
							<label for="date_of_birth" class="form-label">Date of Birth:</label>
							<input class="form-control form-control-sm" type="text" name="date_of_birth" placeholder="Enter date of birth">
						</div>	
						<div class="col-md-6">
							<label for="phone_number" class="form-label">Phone Number:</label>
							<input class="form-control form-control-sm" type="text" name="phone_number" placeholder="Enter phone number">
						</div>
						<div class="col-md-6">
							<label for="hire_date" class="form-label">Hire Date:</label>
							<input class="form-control form-control-sm" type="text" name="hire_date" placeholder="Enter hire date">
						</div>
						<div class="col-md-6">
							<label for="address" class="form-label">Address:</label>
							<input class="form-control form-control-sm" type="text" name="address" placeholder="Enter address">
						</div>
						<div class="col-md-6">
							<label for="city" class="form-label">City:</label>
							<input class="form-control form-control-sm" type="text" name="city" placeholder="Enter city">
						</div>
						<div class="col-md-6">
							<label for="town" class="form-label">Town:</label>
							<input class="form-control form-control-sm" type="text" name="town" placeholder="Enter town">
						</div>
						<div class="col-md-6">
							<label for="post_code" class="form-label">Postcode:</label>
							<input class="form-control form-control-sm" type="text" name="post_code" placeholder="Enter postcode">
						</div>
			
						<div class="col-md-6">
    				    	<label class="department" for="autoSizingSelect department_name">Department</label>
    						<select class="form-select" name="department_name" id="autoSizingSelect department_name">
    							<c:forEach items="${departmentNames}" var="dn">
      								<option value="${dn}">${dn}</option>
      							</c:forEach>
    						</select>
  						</div>
  				
  						<div class="col-md-6">		
							<label class="job_title" for="autoSizingSelect job_title" class="form-label">Salary & Job Title</label>
							<select class="form-select" name="job_title" id="autoSizingSelect job_title">
    							<c:forEach items="${jobTitles}" var="jt">
        							<option value="${jt}">${jt}</option>
    							</c:forEach>
							</select>
						</div>
						<p>
						<div class="col-md-6">
							<input class="btn btn-primary b1" type="submit" value="Insert">
						</div>
						</div>
					</form> 
				</fieldset>
			</div>
		</div>
	</div>
</div>
<div class="table-container">
	<table id="employees" border='1' class="table table-bordered border-primary mt-4 mx-auto w-auto">
		<tr class="table-primary">
			<th scope='col'>emp_id</th>
			<th scope='col'>First Name</th>
			<th scope='col'>Last Name</th>
			<th scope='col'>Email</th>
			<th scope='col'>DOB</th>
			<th scope='col'>Phone Number</th>
			<th scope='col'>hire date</th>
			<th scope='col'>address</th>
			<th scope='col'>city</th>
			<th scope='col'>town</th>
			<th scope='col'>post_code</th>
			<th scope='col'>department name</th>
			<th scope='col'>job title</th>
			<th scope='col'>salary</th>
			<th scope='col'>Update</th>
			<th scope='col'>Delete</th>
		</tr>
		<c:forEach items="${employees}" var="e">
		<tr class='table-secondary'>
			<td >${e.emp_id}</td> 
			<td>${e.first_name}</td>
			<td>${e.last_name}</td>
			<td>${e.email}</td>
			<td>${e.dob}</td>
			<td>${e.phone_number}</td>
			<td>${e.hire_date}</td>
			<td>${e.address}</td>
			<td>${e.city}</td>
			<td>${e.town}</td>
			<td>${e.post_code}</td>
			<td>${e.getDepartments().getDepartment_name()}</td>
			<td>${e.getSalaries().getJob_title()}</td>
			<td>${e.getSalaries().getSalary()}</td>
			
			<td><a class="btn btn-info" href="./UpdateController?emp_id=${e.emp_id}">Update</a></td>
			<td><a class="btn btn-danger" href="./DeleteController?emp_id=${e.emp_id}">Delete</a></td>
		</tr>
		</c:forEach>
	</table>
	<br>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>