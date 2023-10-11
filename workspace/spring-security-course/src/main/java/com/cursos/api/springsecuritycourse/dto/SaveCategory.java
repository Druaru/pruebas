package com.cursos.api.springsecuritycourse.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class SaveCategory implements Serializable {
	
	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
