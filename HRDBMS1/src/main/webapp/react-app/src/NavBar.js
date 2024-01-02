import React from 'react';
import { Link } from 'react-router-dom';

function NavBar() {
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
          <form method="GET" className="d-flex" action="./search">
            <input className="form-control me-2" type="search" name="search" placeholder="Search" aria-label="Search" />
            <button className="btn btn-primary" type="submit">Search</button>
          </form>
          <a className="btn btn-secondary" href="./logout">Logout</a>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;

	
	
	
    /*<nav className="navbar navbar-expand-lg bg-primary">
      <div className="container-fluid">
        <a className="navbar-brand text-white" href="./employee">HRDBMS</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" 
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
                aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle text-white" href="#" role="button" 
                 data-bs-toggle="dropdown" aria-expanded="false">
                Tables
              </a>
              <ul className="dropdown-menu">
                <li><a className="dropdown-item" href="./employee">Employees</a></li>
                <li><a className="dropdown-item" href="./salary">Salaries</a></li>
                <li><a className="dropdown-item" href="./department">Departments</a></li>
              </ul>
            </li>
          </ul>
          <form method="GET" className="d-flex" action="./search">
            <input className="form-control me-2" type="search" name="search" placeholder="Search" aria-label="Search" />
            <button className="btn btn-primary" type="submit">Search</button>
          </form>
          <a className="btn btn-secondary" href="./logout">Logout</a>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;*/
