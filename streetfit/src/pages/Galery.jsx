import imagenes from "../assets/util/images.js";

const Galery = () => {
  return (
    <div className="container">
      <div className="galeria-container">
        <h1>GALERIA DE FOTOS</h1>
        <div className="imagenes-container">
          {imagenes.map((imagen, index) => {
            const rutaImagen = `/src/assets/img/${imagen}`;
            console.log(rutaImagen);

            return (
              <img
                key={index}
                src={rutaImagen}
                alt={`Imagen ${index + 1}`}
                className="imagen-galeria"
              />
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default Galery;
