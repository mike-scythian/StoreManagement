import { Outlet, Link } from "react-router-dom";

const LayoutAdmin = () => {
  return (
    <>
      <nav className="navbar navbar-light bg-light d-flex justify-content-center">
        <ul className="nav nav-pills nav-fill">
          <li className="nav-item">
            <Link 
              to="/products"
              className="nav-link">Products</Link>
          </li>
          <li className="nav-item">
            <Link 
              to="/stores"
              className="nav-link">Stores</Link>
          </li>
          <li className="nav-item">
            <Link 
            to="/users"
            className="nav-link">Users</Link>
          </li>
          <li className="nav-item">
            <Link 
              to="/orders"
              className="nav-link">Orders</Link>
          </li>
          <li className="nav-item">
            <Link 
              to="/summary"
              className="nav-link">Summary</Link>
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default LayoutAdmin;