import { Link } from "react-router-dom";
import logo from "../assets/img/logotipo.png";

function MainNavigation() {
  return (
    <header>
      <div className="headerPers">
        <img src={logo} alt="logotipo streetfit" />
        <h1>No esperes al lunes...</h1>
      </div>
      <nav className="navPer">
        <ul>
          <li>
            <Link to="/">Inicio</Link>
          </li>
          <li>
            <Link to="/about">¿Quién somos?</Link>
          </li>
          <li>
            <Link to="/galery">Galería</Link>
          </li>
          <li>
            <Link to="/contactus">Contacto</Link>
          </li>
          <li className="dropdown">
            <span>Acceso</span>
            <ul className="dropdown-content">
              <li>
                <Link to="/login">Iniciar sesión</Link>
              </li>
              <li>
                <Link to="/register">Registrarse</Link>
              </li>
            </ul>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;
