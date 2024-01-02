import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Table, Modal, Form } from 'react-bootstrap';

function Salaries() {
  const [salaries, setSalaries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [selectedSalary, setSelectedSalary] = useState(null);

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

  const handleAddClick = () => {
    console.log("Add Salary button clicked");
    setSelectedSalary(null);
    setShowModal(true);
  };

  const handleModalClose = () => {
    setShowModal(false);
    setSelectedSalary(null);
  };

  const handleUpdate = (salary) => {
    setSelectedSalary(salary);
    setShowModal(true);
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

      {/* Modal for Add/Update Salary */}
      <Modal show={showModal} onHide={handleModalClose}>
        <Modal.Header closeButton>
          <Modal.Title>{selectedSalary ? 'Update' : 'Add'} Salary</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {/* Form for adding/updating a salary */}
          {/* Implement form here */}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleModalClose}>Close</Button>
          <Button variant="primary">Save Changes</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Salaries;
