import React, { useState } from 'react';
import axios from 'axios';
import { Modal, Button, Form } from 'react-bootstrap';

function AddSalaryModal({ show, onClose, onAdd }) {
    const initialFormData = {
        job_title: '',
        salary: ''
    };

    const [formData, setFormData] = useState(initialFormData);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8082/EPAssignment/api/salaries', formData)
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
                <Modal.Title>Add New Salary</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Job Title</Form.Label>
                        <Form.Control
                            type="text"
                            name="job_title"
                            value={formData.job_title}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Salary</Form.Label>
                        <Form.Control
                            type="number"
                            name="salary"
                            value={formData.salary}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onClose}>Close</Button>
                <Button variant="primary" onClick={handleSubmit}>Add Salary</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AddSalaryModal;
