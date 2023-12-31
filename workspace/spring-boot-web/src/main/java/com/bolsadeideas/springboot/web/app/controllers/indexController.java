package com.bolsadeideas.springboot.web.app.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.web.app.models.Usuario;

@Controller
@RequestMapping("/app")
public class indexController {

	@Value("${texto.indexcontroller.index.titulo}")
	private String textoIndex;
	@Value("${texto.indexcontroller.perfil.titulo}")
	private String textoPerfil;
	@Value("${texto.indexcontroller.listar.titulo}")
	private String textoListar;

	@GetMapping(value = { "/index", "", "/home", "/" })
	public String index(Model model) {
		model.addAttribute("titulo", textoIndex);
		return "index";
	}

	@GetMapping("/perfil")
	public String perfil(Model model) {

		Usuario usuario = new Usuario();
		usuario.setNombre("David");
		usuario.setApellido("Ruano Ruiz");
		usuario.setEmail("david.ruano.ruiz@gmail.com");

		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", textoPerfil .concat(usuario.getNombre()));

		return "perfil";
	}

	@GetMapping("/listar")
	public String listar(Model model) {

		model.addAttribute("titulo", textoListar);

		return "/listar";
	}

	@ModelAttribute("usuarios")
	public List<Usuario> poblarUsuarios() {
		List<Usuario> usuarios = Arrays.asList(new Usuario("David", "Ruano", "davidruanoruiz@gmail.com"),
				new Usuario("David", "Ruano", "davidruanoruiz@gmail.com"),
				new Usuario("Ian", "Ruano", "IanRuano@gmail.com"),
				new Usuario("Tornado", "Letal", "Tormentafilomena@gmail.com"));
		return usuarios;
	}
}
