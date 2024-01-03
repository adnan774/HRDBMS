import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Table } from 'react-bootstrap';
import UpdateDepartmentModal from './UpdateDepartmentModal';
import AddDepartmentModal from './AddDepartmentModal';



function Departments() {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [showAddModal, setShowAddModal] = useState(false);

  const fetchDepartments = () => {
    axios.get('http://localhost:8082/EPAssignment/api/departments')
      .then(response => {
        setDepartments(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('There was an error fetching the departments:', error);
        setError(error);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchDepartments();
  }, []);
  
  const handleModalClose = () => {
    setShowModal(false);
    setSelectedDepartment(null);
  };

  const handleUpdate = (department) => {
    setSelectedDepartment(department);
    setShowModal(true);
  };

  const handleDepartmentUpdate = (updatedDepartment) => {
    console.log('Updated Department:', updatedDepartment);
    
    axios.put(`http://localhost:8082/EPAssignment/api/departments`, updatedDepartment)
    .then(response => {
      fetchDepartments(); // Refresh department data
      setShowModal(false); // Close the modal
      console.log('Department updated successfully:', response.data);
    })
    .catch(error => {
      console.error('Error updating the department:', error);
    });
  };

  const handleAddClick = () => {
    console.log("Add department button clicked");
    setShowAddModal(true);
  };
  
  const handleAddModalClose = () => {
    setShowAddModal(false);
  };

  const handleAddDepartment = (newDepartment) => {
    // Logic to add the new department to the list
    // This might involve fetching the updated list or appending the new department to the existing list
    fetchDepartments();
  };

  const handleDelete = (departmentId) => {
    if (window.confirm('Are you sure you want to delete this department?')) {
      axios.delete(`http://localhost:8082/EPAssignment/api/departments?department_id=${departmentId}`)
        .then(response => {
          console.log('Department deleted successfully');
          fetchDepartments();
        })
        .catch(error => {
          console.error('Error deleting the department:', error);
        });
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <>
    <h1 className="text-center text-primary" >Departments</h1>
      <Button variant="info" onClick={handleAddClick}>+ Insert Department</Button>

      <Table striped bordered hover className="mt-4">
        <thead>
          <tr>
            <th>Department_id</th>
            <th>department_name</th>
            <th>location</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {departments.map(department => (
            <tr key={department.department_id}>
              <td>{department.department_id}</td>
              <td>{department.department_name}</td>
              <td>{department.location}</td>
              <td><Button variant="info" onClick={() => handleUpdate(department)}>Update</Button></td>
              <td><Button variant="danger" onClick={() => handleDelete(department.department_id)}>Delete</Button></td>
            </tr>
          ))}
        </tbody>
      </Table>
	  <UpdateDepartmentModal 
        show={showModal} 
        department={selectedDepartment} 
        onClose={handleModalClose} 
        onUpdate={handleDepartmentUpdate} 
      />
      <AddDepartmentModal
        show={showAddModal}
        onClose={handleAddModalClose}
        onAdd={handleAddDepartment}
      />
    </>
  );
}

export default Departments;
