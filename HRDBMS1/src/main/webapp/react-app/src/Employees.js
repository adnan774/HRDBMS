import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Employees() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/HRDBMS1/api/employee') 
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
          <th>Salary & Job Title</th>
        </tr>
      </thead>
      <tbody>
        {employees.map(employee => (
          <tr key={employee.id}>
            <td>{employee.firstName}</td>
            <td>{employee.lastName}</td>
            <td>{employee.email}</td>
           	<td>{employee.dob}</td>
           	<td>{employee.phoneNumber}</td>
           	<td>{employee.hireDate}</td>
           	<td>{employee.address}</td>
           	<td>{employee.city}</td>
           	<td>{employee.town}</td>
           	<td>{employee.postcode}</td>
           	<td>{employee.department}</td>
           	<td>{employee.jobTitles}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default Employees;
