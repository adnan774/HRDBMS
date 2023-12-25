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
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default Employees;
