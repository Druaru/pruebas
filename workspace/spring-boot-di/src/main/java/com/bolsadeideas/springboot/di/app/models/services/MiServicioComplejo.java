package com.bolsadeideas.springboot.di.app.models.services;


//@Component("miServicioComplejo")
public class MiServicioComplejo implements IServicio {

	@Override
	public String operacion() {
		
		return "Ejecutando un proceso importante complicado";
	}
	
	
	
}
