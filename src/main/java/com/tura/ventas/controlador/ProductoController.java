package com.tura.ventas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tura.ventas.modelo.Producto;
import com.tura.ventas.service.ProductoService;

@Controller
public class ProductoController {
	private final ProductoService s;
	private final com.tura.ventas.service.CategoriaProductoService categorias;

	public ProductoController(ProductoService s, com.tura.ventas.service.CategoriaProductoService categorias) {
		this.s = s; this.categorias = categorias;
	}

	@GetMapping("/productos")
	String listar(@RequestParam(required = false) String q, @RequestParam(required = false) Integer editar, Model m) {
		m.addAttribute("listaProductos", s.listar(q));
		m.addAttribute("categorias", categorias.listar());
		m.addAttribute("q", q);
		m.addAttribute("producto", editar == null ? new Producto() : s.obtener(editar));
		return "productos";
	}

	@GetMapping("/catalogo")
	String catalogo(Model m) {
		m.addAttribute("listaProductos", s.listar(null));
		return "catalogo";
	}

	@PostMapping("/productos/guardar")
	String guardar(Producto p, @RequestParam(required=false) Integer categoriaId) {
		p.setCategoria(categoriaId == null ? null : categorias.obtener(categoriaId));
		s.guardar(p);
		return "redirect:/productos";
	}

	@PostMapping("/productos/eliminar/{id}")
	String eliminar(@PathVariable Integer id) {
		s.eliminar(id);
		return "redirect:/productos";
	}
}
