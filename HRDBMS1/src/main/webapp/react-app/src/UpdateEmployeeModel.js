import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Modal, Button, Form } from 'react-bootstrap';

function UpdateEmployeeModal({ show, employee, onClose, onUpdate }) {
  // Local state to manage form inputs
  const [formData, setFormData] = useState({ ...employee });
  const [departments, setDepartments] = useState([]); 
  const [salaries, setSalaries] = useState([]);

  // Fetch departments and salaries data from  API
  useEffect(() => {
    if (show) {
      console.log("Employee prop:", employee);
      setFormData({
      ...employee,
      department_id: employee.departments?.department_id,
      salary_id: employee.salaries?.salary_id
    });

      axios.get('http://localhost:8082/EPAssignment/api/departments')
        .then(response => setDepartments(response.data))
        .catch(error => console.error('Failed to load departments:', error));

      axios.get('http://localhost:8082/EPAssignment/api/salaries')
        .then(response => setSalaries(response.data))
        .catch(error => console.error('Failed to load salaries:', error));
    }
  }, [employee, show]);


  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value,
    }));
  };
  
  const handleSelectChange = (e) => {
    // Same as handleChange, but for select inputs
    handleChange(e);
  };

  const handleSubmit = (e) => {
  e.preventDefault();

const department = departments.find(dept => dept.department_id === parseInt(formData.department_id));
const salary = salaries.find(sal => sal.salary_id === parseInt(formData.salary_id));

  
  if (!department || !salary) {
   console.error('Department or salary not found');
   return; 
  }

  const updatedEmployee = {
  ...formData,
  departments: {
    ...formData.departments,
    department_name: department.department_name
  },
  salaries: {
    ...formData.salaries,
    job_title: salary.job_title
  }
};



  onUpdate(updatedEmployee);
  setFormData({}); // Reset form data after submit
};



  if (!show) return null;

  return (
	<Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Update Employee</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>First Name</Form.Label>
            <Form.Control 
              type="text" 
              name="first_name"
              value={formData.first_name || ''} 
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
            <Form.Label>Department</Form.Label>
            <Form.Control 
              as="select" 
              name="department_id" 
              value={formData.department_id || ''} 
              onChange={handleChange}
            >
              {departments.map(department => (
                <option key={department.department_id} value={department.department_id}>
                  {department.department_name}
                </option>
              ))}
            </Form.Control>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Job Title</Form.Label>
            <Form.Control 
              as="select" 
              name="salary_id" 
              value={formData.salary_id || ''} 
              onChange={handleChange}
            >
              {salaries.map(salary => (
                <option key={salary.salary_id} value={salary.salary_id}>
                  {salary.job_title}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>Close</Button>
        <Button variant="primary" type="submit" onClick={handleSubmit}>Update</Button>
      </Modal.Footer>
    </Modal>
    /*<div className="modal">
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title">Update Employee</h5>
          <button type="button" className="close" onClick={onClose}>&times;</button>
        </div>
        <div className="modal-body">
          <form onSubmit={handleSubmit}>

            <div className="form-group">
              <label htmlFor="first_name">First Name:</label>
              <input
                type="text"
                className="form-control"
                id="first_name"
                name="first_name"
                value={formData.first_name || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="last_name">Last Name:</label>
              <input
                type="text"
                className="form-control"
                id="last_name"
                name="last_name"
                value={formData.last_name || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                type="text"
                className="form-control"
                id="email"
                name="email"
                value={formData.email || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="date_of_birth">Date of Birth:</label>
              <input
                type="text"
                className="form-control"
                id="date_of_birth"
                name="date_of_birth"
                value={formData.date_of_birth || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="phone_number">Phone Number:</label>
              <input
                type="text"
                className="form-control"
                id="phone_number"
                name="phone_number"
                value={formData.phone_number || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="hire_date">Hire Date:</label>
              <input
                type="text"
                className="form-control"
                id="hire_date"
                name="hire_date"
                value={formData.hire_date || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="address">Address:</label>
              <input
                type="text"
                className="form-control"
                id="address"
                name="address"
                value={formData.address || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="city">City:</label>
              <input
                type="text"
                className="form-control"
                id="city"
                name="city"
                value={formData.city || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="town">Town:</label>
              <input
                type="text"
                className="form-control"
                id="town"
                name="town"
                value={formData.town || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="post_code">Postcode:</label>
              <input
                type="text"
                className="form-control"
                id="post_code"
                name="post_code"
                value={formData.post_code || ''}
                onChange={handleChange}
              />
            </div>
            
            <div className="form-group">
            <label htmlFor="department_name">Department:</label>
  			<select
  				className="form-control"
  				name="department_id"
  				id="department_id"
  				value={formData.department_id || ''}
  				onChange={handleChange}
			>
  				{departments.map((department) => (
    				<option key={department.department_id} value={department.department_id}>
      					{department.department_name}
   					</option>
  					))}
			</select>

			</div>

			<div className="form-group">
  			<label htmlFor="job_title">Job Title:</label>
  			<select
  				className="form-control"
  				name="salary_id"
  				id="salary_id"
  				value={formData.salary_id || ''}
  				onChange={handleChange}
			>
    			{salaries.map((salary) => (
      				<option key={salary.salary_id} value={salary.salary_id}>
        				{salary.job_title}
      				</option>
    			))}
  			</select>
			</div>


            <div className="form-group">
              <button type="submit" className="btn btn-primary">Update</button>
            </div>
          </form>
        </div>
      </div>
    </div>*/
  );
}

export default UpdateEmployeeModal;
