import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Table } from 'react-bootstrap';
import UpdateSalaryModal from './UpdateSalaryModal';
import AddSalaryModal from './AddSalaryModal';



function Salaries() {
  const [salaries, setSalaries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [selectedSalary, setSelectedSalary] = useState(null);
  const [showAddModal, setShowAddModal] = useState(false);

  const fetchSalaries = () => {
    axios.get('http://localhost:8082/EPAssignment/api/salaries')
      .then(response => {
        setSalaries(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('There was an error fetching the salaries:', error);
        setError(error);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchSalaries();
  }, []);
  
  const handleModalClose = () => {
    setShowModal(false);
    setSelectedSalary(null);
  };

  const handleUpdate = (salary) => {
    setSelectedSalary(salary);
    setShowModal(true);
  };

  const handleSalaryUpdate = (updatedSalary) => {
    console.log('Updated Salary:', updatedSalary);
    
    axios.put(`http://localhost:8082/EPAssignment/api/salaries`, updatedSalary)
    .then(response => {
      fetchSalaries(); // Refresh salary data
      setShowModal(false); // Close the modal
      console.log('Salary updated successfully:', response.data);
    })
    .catch(error => {
      console.error('Error updating the Salary:', error);
    });
  };

  const handleAddClick = () => {
    console.log("Add Salary button clicked");
    setShowAddModal(true);
  };
  
  const handleAddModalClose = () => {
    setShowAddModal(false);
  };

  const handleAddSalary = (newSalary) => {
    // Logic to add the new salary to the list
    // This might involve fetching the updated list or appending the new salary to the existing list
    fetchSalaries();
  };

  const handleDelete = (salaryId) => {
    if (window.confirm('Are you sure you want to delete this salary?')) {
      axios.delete(`http://localhost:8082/EPAssignment/api/salaries?salary_id=${salaryId}`)
        .then(response => {
          console.log('Salary deleted successfully');
          fetchSalaries();
        })
        .catch(error => {
          console.error('Error deleting the salary:', error);
        });
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <>
    <h1 className="text-center text-primary" >Salaries</h1>
      <Button variant="info" onClick={handleAddClick}>+ Insert Salary</Button>

      <Table striped bordered hover className="mt-4">
        <thead>
          <tr>
            <th>Salary_id</th>
            <th>Job_title</th>
            <th>Salary</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {salaries.map(salary => (
            <tr key={salary.salary_id}>
              <td>{salary.salary_id}</td>
              <td>{salary.job_title}</td>
              <td>{salary.salary}</td>
              <td><Button variant="info" onClick={() => handleUpdate(salary)}>Update</Button></td>
              <td><Button variant="danger" onClick={() => handleDelete(salary.salary_id)}>Delete</Button></td>
            </tr>
          ))}
        </tbody>
      </Table>
	  <UpdateSalaryModal 
        show={showModal} 
        salary={selectedSalary} 
        onClose={handleModalClose} 
        onUpdate={handleSalaryUpdate} 
      />
      <AddSalaryModal
        show={showAddModal}
        onClose={handleAddModalClose}
        onAdd={handleAddSalary}
      />
    </>
  );
}

export default Salaries;
