package com.bolsadeideas.springboot.di.app.models.services;


//@Component("miServicioSimple")
//@Primary
public class MiServicio implements IServicio {

	@Override
	public String operacion() {
		
		return "Ejecutando un proceso importante simple";
	}
	
	
	
}
