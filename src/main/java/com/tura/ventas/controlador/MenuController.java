package com.tura.ventas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.tura.ventas.service.*;

@Controller
public class MenuController {
	private final ClienteService c;
	private final ProductoService p;
	private final VentaService v;

	public MenuController(ClienteService c, ProductoService p, VentaService v) {
		this.c = c;
		this.p = p;
		this.v = v;
	}

	@GetMapping("/dashboard")
	String dash(Model m) {
		m.addAttribute("clientes", c.contar());
		m.addAttribute("productos", p.contar());
		m.addAttribute("bajoStock", p.contarBajoStock());
		m.addAttribute("ventas", v.contar());
		return "dashboard";
	}
}
