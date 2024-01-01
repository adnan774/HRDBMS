import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Modal, Button, Form } from 'react-bootstrap';

function AddEmployeeModal({ show, onClose, onAdd }) {
	console.log("Modal Rendered, show is: ", show);
  const initialFormData = {
        first_name: '',
        last_name: '',
        email: '',
        date_of_birth: '',
        phone_number: '',
        hire_date: '',
        address: '',
        city: '',
        town: '',
        post_code: '',
        department_id: '',
        salary_id: '',
    };
  const [departments, setDepartments] = useState([]);
  const [salaries, setSalaries] = useState([]);
  const [formData, setFormData] = useState(initialFormData);
  
  const modalClass = show ? "modal fade show d-block" : "modal fade";

  useEffect(() => {
    if (show) {
	setFormData(initialFormData); // Reset the form data when modal is opened
	
      axios.get('http://localhost:8082/EPAssignment/api/departments')
        .then(response => setDepartments(response.data))
        .catch(error => console.error('Failed to load departments:', error));

      axios.get('http://localhost:8082/EPAssignment/api/salaries')
        .then(response => setSalaries(response.data))
        .catch(error => console.error('Failed to load salaries:', error));
    }
  }, [show]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  
  const handleClose = () => {
        onClose();
        setFormData(initialFormData); // Reset form data when modal is closed
    };

  const handleSubmit = (e) => {
  e.preventDefault();
  const department = departments.find(dept => dept.department_id === parseInt(formData.department_id));
  const salary = salaries.find(sal => sal.salary_id === parseInt(formData.salary_id));

  const newEmployeeData = {
    ...formData,
    departments: {
      department_id: department ? department.department_id : null,
      department_name: department ? department.department_name : ''
    },
    salaries: {
      salary_id: salary ? salary.salary_id : null,
      job_title: salary ? salary.job_title : ''
    }
  };

    axios.post('http://localhost:8082/EPAssignment/api/employee', newEmployeeData)
      .then(response => {
        onAdd(response.data);
        onClose();
      })
      .catch(error => console.error('Error adding employee:', error));
  };

  if (!show) return null;

  return (
     <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Add New Employee</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
            <Form.Label>First Name</Form.Label>
            <Form.Control 
              type="text" 
              name="first_name"
              value={formData.first_name} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Last Name</Form.Label>
            <Form.Control 
              type="text" 
              name="last_name"
              value={formData.last_name} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control 
              type="text" 
              name="email"
              value={formData.email} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Date of Birth</Form.Label>
            <Form.Control 
              type="text" 
              name="date_of_birth"
              value={formData.date_of_birth} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          
          <Form.Group className="mb-3">
            <Form.Label>Phone Number</Form.Label>
            <Form.Control 
              type="text" 
              name="phone_number"
              value={formData.phone_number} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Hire Date</Form.Label>
            <Form.Control 
              type="text" 
              name="hire_date"
              value={formData.hire_date} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Address</Form.Label>
            <Form.Control 
              type="text" 
              name="address"
              value={formData.address} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>City</Form.Label>
            <Form.Control 
              type="text" 
              name="city"
              value={formData.city} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Town</Form.Label>
            <Form.Control 
              type="text" 
              name="town"
              value={formData.town} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
            <Form.Label>Postcode</Form.Label>
            <Form.Control 
              type="text" 
              name="post_code"
              value={formData.post_code} 
              onChange={handleChange} 
            />
          </Form.Group>
          
          <Form.Group className="mb-3">
  			<Form.Label htmlFor="department_name">Department:</Form.Label>
  			<Form.Control 
    		  as="select"
    		  id="department_id" 
    		  name="department_id" 
    		  value={formData.department_id || ''} 
    		  onChange={handleChange}
  			>
    		{departments.map((department) => (
      			<option key={department.department_id} value={department.department_id}>
        		 {department.department_name}
      			</option>
    		))}
  			</Form.Control>
		  </Form.Group>
          
          <Form.Group className="mb-3">
  			<Form.Label htmlFor="job_title">Job Title:</Form.Label>
  			<Form.Control 
    		  as="select"
    		  id="salary_id" 
    		  name="salary_id" 
    		  value={formData.salary_id || ''} 
    		  onChange={handleChange}
  			>
    		{salaries.map((salary) => (
      			<option key={salary.salary_id} value={salary.salary_id}>
        		 {salary.job_title}
      			</option>
    		))}
  			</Form.Control>
		  </Form.Group>
          
          </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Add Employee
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddEmployeeModal;
