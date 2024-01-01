import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button } from 'react-bootstrap';
import UpdateEmployeeModal from './UpdateEmployeeModel';
import AddEmployeeModal from './AddEmployeeModal';



function Employees() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedEmployee, setSelectedEmployee] = useState(null); // Selected employee for update
  const [showModal, setShowModal] = useState(false); // State to control modal visibility
  const [showAddModal, setShowAddModal] = useState(false);

  const fetchEmployees = () => {
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
};

useEffect(() => {
  fetchEmployees();
}, []);
  
  const handleUpdate = (employee) => {
    setSelectedEmployee(employee); // Set selected employee
    setShowModal(true); // Show the modal
  };

  const handleModalClose = () => {
    setShowModal(false); // Close the modal
    setSelectedEmployee(null); // Reset selected employee
  };

  const handleEmployeeUpdate = (updatedEmployee) => {
	console.log("Updated Employee:", updatedEmployee);

  axios.put(`http://localhost:8082/EPAssignment/api/employee`, updatedEmployee)
    .then(response => {
      fetchEmployees(); // Refresh employee data
      setShowModal(false); // Close the modal
      console.log('Employee updated successfully:', response.data);
    })
    .catch(error => {
      console.error('Error updating the employee:', error);
    });
};

const handleAddClick = () => {
  console.log("Add Employee button clicked");
  setShowAddModal(true);
};


const handleAddModalClose = () => {
    setShowAddModal(false);
  };

  const handleAddEmployee = (newEmployee) => {
    // Logic to add the new employee to the list
    // This might involve fetching the updated list or appending the new employee to the existing list
    fetchEmployees();
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
	<>
      <Button 
        type="button" 
        variant="primary"
        onClick={handleAddClick}
      >
        Add Employee
      </Button>
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
			  <Button variant="info" onClick={() => handleUpdate(employee)}>Update</Button>
              <Button variant="danger" onClick={() => handleDelete(employee.emp_id)}>Delete</Button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
    <UpdateEmployeeModal 
        show={showModal} 
        employee={selectedEmployee} 
        onClose={handleModalClose}
        onUpdate={handleEmployeeUpdate} 
      />
      <AddEmployeeModal
        show={showAddModal}
        onClose={handleAddModalClose}
        onAdd={handleAddEmployee}
      />
    </>
  );
}

export default Employees;
