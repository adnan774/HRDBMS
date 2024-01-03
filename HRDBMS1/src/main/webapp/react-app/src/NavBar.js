import React from 'react';
import { Link } from 'react-router-dom';
import SearchComponent from './SearchComponent';


function NavBar({ onSearch }) {
  return (
    <nav className="navbar navbar-expand-lg bg-primary">
      <div className="container-fluid">
        <Link className="navbar-brand text-white" to="/employees">HRDBMS</Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" 
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
                aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item dropdown">
              <Link className="nav-link dropdown-toggle text-white" to="#" role="button" 
                 data-bs-toggle="dropdown" aria-expanded="false">
                Tables
              </Link>
              <ul className="dropdown-menu">
                <li><Link className="dropdown-item" to="/employees">Employees</Link></li>
                <li><Link className="dropdown-item" to="/salaries">Salaries</Link></li>
                <li><Link className="dropdown-item" to="/departments">Departments</Link></li>
              </ul>
            </li>
          </ul>

          <SearchComponent onSearch={onSearch} />

          <Link className="btn btn-secondary" to="/logout">Logout</Link>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;