function ContactUs() {
  return (
    <div className="container">
      <h1>¿Tienes alguna duda o sugerencia?</h1>
      <form
        action="https://formsubmit.co/david.ruano.ruiz@gmail.com"
        method="POST"
      >
        <label>Nombre:</label>
        <input type="text" id="nombre" name="nombre" />

        <label>Email:</label>
        <input type="email" id="email" name="email" />

        <label>Mensaje:</label>
        <textarea id="mensaje" name="mensaje"></textarea>

        <input id="enviar" type="submit" value="Enviar" />
      </form>
    </div>
  );
}
export default ContactUs;
