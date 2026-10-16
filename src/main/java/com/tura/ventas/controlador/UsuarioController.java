package com.tura.ventas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tura.ventas.modelo.Usuario;
import com.tura.ventas.service.UsuarioService;

@Controller
public class UsuarioController {
	private final UsuarioService s;

	public UsuarioController(UsuarioService s) {
		this.s = s;
	}

	@GetMapping("/usuarios")
	String listar(@RequestParam(required = false) Integer editar, Model m) {
		m.addAttribute("listaUsuarios", s.listar());
		m.addAttribute("usuarioForm", editar == null ? new Usuario() : s.obtener(editar));
		return "usuarios";
	}

	@PostMapping("/usuarios/guardar")
	String guardar(Usuario u, @RequestParam(required = false) String nuevaPass) {
		s.guardar(u, nuevaPass);
		return "redirect:/usuarios";
	}

	@PostMapping("/usuarios/eliminar/{id}")
	String eliminar(@PathVariable Integer id) {
		s.eliminar(id);
		return "redirect:/usuarios";
	}
}
