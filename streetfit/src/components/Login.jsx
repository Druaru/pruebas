export default function Login(){
    return (
        <div className="container">
            <h1>Inicia sesión</h1>
            <form action="">
                <label>Usuario: </label>
                <input type="text" />

                <label>Contraseña: </label>
                <input type="password" />
            </form>
        </div>
    );
}