package com.tura.ventas.controlador;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tura.ventas.service.*;

@Controller
@RequestMapping("/reportes")
public class ReporteController {
	private final ReporteService r;
	private final ProductoService p;
	private final VentaService v;

	public ReporteController(ReporteService r, ProductoService p, VentaService v) {
		this.r = r;
		this.p = p;
		this.v = v;
	}

	private ResponseEntity<byte[]> out(byte[] b, String n) {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + n)
				.contentType(MediaType.APPLICATION_PDF).body(b);

	}

	@GetMapping("/productos.pdf")
	ResponseEntity<byte[]> productos() throws Exception {
		return out(r.productos(p.listar(null)), "productos.pdf");
	}

	@GetMapping("/ventas.pdf")
	ResponseEntity<byte[]> ventas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) throws Exception {
		return out(r.ventas(v.filtrar(desde, hasta, null), desde, hasta), "ventas.pdf");
	}

	@GetMapping("/resumen.pdf")
	ResponseEntity<byte[]> resumen() throws Exception {
		return out(r.resumen(v.resumen()), "resumen.pdf");
	}
}
