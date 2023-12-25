import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Employees() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8082/EPAssignment/api/employee') 
      .then(response => {
        setEmployees(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('There was an error fetching the employees:', error);
        setError(error);
        setLoading(false);
      });
  }, []);
  
  const handleUpdate = (empId) => {
    console.log('Update employee with ID:', empId);
    // update logic here
  };

  const handleDelete = (empId) => {
    if (window.confirm('Are you sure you want to delete this employee?')) {
        axios.delete(`http://localhost:8082/EPAssignment/api/employee?emp_id=${empId}`)
            .then(response => {  
                console.log('Employee deleted successfully:', response.data);
                setEmployees(employees.filter(employee => employee.emp_id !== empId));
            })
            .catch(error => {
                console.error('Error deleting the employee:', error);
            });
    }
 };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <table>
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Email</th>
          <th>Date of Birth</th>
          <th>Phone Number</th>
          <th>Hire Date</th>
          <th>Address</th>
          <th>City</th>
          <th>Town</th>
          <th>Postcode</th>
          <th>Department</th>
          <th>Job Title</th>
          <th>Salary</th>
          <th>Update</th>
          <th>Delete</th>
        </tr>
      </thead>
      <tbody>
        {employees.map(employee => (
          <tr key={employee.id}>
            <td>{employee.first_name}</td>
            <td>{employee.last_name}</td>
            <td>{employee.email}</td>
           	<td>{employee.date_of_birth}</td>
           	<td>{employee.phone_number}</td>
           	<td>{employee.hire_date}</td>
           	<td>{employee.address}</td>
           	<td>{employee.city}</td>
           	<td>{employee.town}</td>
           	<td>{employee.post_code}</td>
           	<td>{employee.departments?.department_name}</td>
            <td>{`${employee.salaries?.job_title}`}</td>
            <td>{`${employee.salaries?.salary}`}</td>
            
            <td>
              <button onClick={() => handleUpdate(employee.emp_id)}>Update</button>
              <button onClick={() => handleDelete(employee.emp_id)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default Employees;
