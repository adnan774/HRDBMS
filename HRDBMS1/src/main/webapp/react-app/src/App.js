import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Employees from './Employees';
import Salaries from './Salaries';
import NavBar from './NavBar'; 

function App() {
  return (
    <Router>
      <NavBar />
      <Routes>
        <Route path="/employees" element={<Employees />} />
        <Route path="/salaries" element={<Salaries />} />
        
        {/* Add more routes as needed */}
      </Routes>
    </Router>
  );
}

export default App;
