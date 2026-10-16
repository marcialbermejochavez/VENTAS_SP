package com.tura.ventas.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String general(Exception e, Model m) {
		m.addAttribute("mensaje", e.getMessage());
		return "error";
	}
}