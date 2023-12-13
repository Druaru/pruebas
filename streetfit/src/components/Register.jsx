export default function Register(){
    return (
        <div className="container">
            <h1>Registrate</h1>
            <form action="">
                <label>Nombre de usuario: </label>
                <input type="text" />

                <label>Correo Electrónico </label>
                <input type="email" />

                <label>Contraseña: </label>
                <input type="password" />

                <label>Repita la contraseña: </label>
                <input type="password" />
            </form>
        </div>
    );
}