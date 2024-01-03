import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Employees from './Employees';
import Salaries from './Salaries';
import Departments from './Departments';
import NavBar from './NavBar'; 

function App() {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearch = (newSearchTerm) => {
    setSearchTerm(newSearchTerm); // Update the searchTerm state
  };

  return (
    <Router>
      <NavBar onSearch={handleSearch} />
      <Routes>
        <Route path="/employees" element={<Employees searchTerm={searchTerm} />} />
        <Route path="/salaries" element={<Salaries />} />
        <Route path="/departments" element={<Departments />} />
      </Routes>
    </Router>
  );
}

export default App;