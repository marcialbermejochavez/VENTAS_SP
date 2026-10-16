package com.tura.ventas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
	@GetMapping({ "/", "/login" })
	public String login() {
		return "login";
	}

	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}
}