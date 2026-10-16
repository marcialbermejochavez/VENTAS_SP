package com.tura.ventas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tura.ventas.modelo.Cliente;
import com.tura.ventas.service.ClienteService;

@Controller
public class ClienteController {
	private final ClienteService s;
	private final com.tura.ventas.service.CategoriaClienteService categorias;

	public ClienteController(ClienteService s, com.tura.ventas.service.CategoriaClienteService categorias) {
		this.s = s; this.categorias = categorias;
	}

	@GetMapping("/clientes")
	String listar(@RequestParam(required = false) String q, @RequestParam(required = false) Integer editar, Model m) {
		m.addAttribute("listaClientes", s.listar(q));
		m.addAttribute("categorias", categorias.listar());
		m.addAttribute("q", q);
		m.addAttribute("cliente", editar == null ? new Cliente() : s.obtener(editar));
		return "clientes";
	}

	@PostMapping("/clientes/guardar")
	String guardar(Cliente c, @RequestParam(required=false) Integer categoriaId,
			@RequestParam(required=false) String ruc, @RequestParam(required=false) String correo) {
		c.setCategoria(categoriaId == null ? null : categorias.obtener(categoriaId));
		c.setRuc(ruc); c.setCorreo(correo);
		s.guardar(c);
		return "redirect:/clientes";
	}

	@PostMapping("/clientes/eliminar/{id}")
	String eliminar(@PathVariable Integer id) {
		s.eliminar(id);
		return "redirect:/clientes";
	}
}
