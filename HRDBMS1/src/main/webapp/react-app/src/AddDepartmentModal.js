import React, { useState } from 'react';
import axios from 'axios';
import { Modal, Button, Form } from 'react-bootstrap';

function AddDepartmentModal({ show, onClose, onAdd }) {
    const initialFormData = {
        department_name: '',
        location: ''
    };

    const [formData, setFormData] = useState(initialFormData);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8082/EPAssignment/api/departments', formData)
            .then(response => {
                onAdd(response.data);
                onClose();
                setFormData(initialFormData); // Reset form data after submit
            })
            .catch(error => console.error('Error adding salary:', error));
    };

    if (!show) return null;

    return (
        <Modal show={show} onHide={onClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add New Department</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Department Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="department_name"
                            value={formData.department_name}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Location</Form.Label>
                        <Form.Control
                            type="text"
                            name="location"
                            value={formData.location}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onClose}>Close</Button>
                <Button variant="primary" onClick={handleSubmit}>Add Department</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AddDepartmentModal;
