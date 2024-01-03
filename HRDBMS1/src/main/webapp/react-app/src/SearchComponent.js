import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Table } from 'react-bootstrap';


function SearchComponent({ onSearch }) {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent the default form submit action
    onSearch(searchTerm); // Call the passed onSearch function with the searchTerm
  };

  return (
    <form onSubmit={handleSubmit} className="d-flex">
      <input
        className="form-control me-2"
        type="search"
        name="search"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        placeholder="Search employees"
        aria-label="Search"
      />
      <button className="btn btn-primary" type="submit">Search</button>
    </form>
  );
}


export default SearchComponent;
