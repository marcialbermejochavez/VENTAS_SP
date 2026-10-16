package com.tura.ventas.controlador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tura.ventas.modelo.*;
import com.tura.ventas.service.*;

@RestController
@RequestMapping("/api")
public class ApiController {
	private final ClienteService cs;
	private final ProductoService ps;
	private final VentaService vs;

	public ApiController(ClienteService c, ProductoService p, VentaService v) {
		cs = c;
		ps = p;
		vs = v;
	}

	record Dashboard(long clientes, long productos, long bajoStock, long ventas) {
	}

	record VentaReq(Integer codigoCliente, Integer codigoProducto, int cantidad, LocalDate fecha) {
	}

	record VentaDto(Integer codigoVenta, Integer codigoCliente, String cliente, Integer codigoProducto, String producto,
			int cantidad, BigDecimal precio, LocalDate fecha, BigDecimal total) {
	}

	@GetMapping("/dashboard")
	Dashboard dashboard() {
		return new Dashboard(cs.contar(), ps.contar(), ps.contarBajoStock(), vs.contar());
	}

	@GetMapping("/clientes")
	List<Cliente> clientes(@RequestParam(required = false) String q) {
		return cs.listar(q);
	}

	@PostMapping("/clientes")
	Cliente cliente(@RequestBody Cliente c) {
		return cs.guardar(c);
	}

	@DeleteMapping("/clientes/{id}")
	void delCliente(@PathVariable Integer id) {
		cs.eliminar(id);
	}

	@GetMapping("/productos")
	List<Producto> productos(@RequestParam(required = false) String q) {
		return ps.listar(q);
	}

	@PostMapping("/productos")
	Producto producto(@RequestBody Producto p) {
		return ps.guardar(p);
	}

	@DeleteMapping("/productos/{id}")
	void delProducto(@PathVariable Integer id) {
		ps.eliminar(id);
	}

	@GetMapping("/ventas")
	List<VentaDto> ventas() {
		return vs.listar().stream().map(this::dto).toList();
	}

	@PostMapping("/ventas")
	VentaDto venta(@RequestBody VentaReq r) {
		return dto(vs.registrar(r.codigoCliente(), r.codigoProducto(), r.cantidad(), r.fecha()));
	}

	@DeleteMapping("/ventas/{id}")
	void delVenta(@PathVariable Integer id) {
		vs.eliminar(id);
	}

	private VentaDto dto(Venta v) {
		return new VentaDto(v.getCodigoVenta(), v.getCliente().getCodigoCliente(), v.getCliente().getNombreCompleto(),
				v.getProducto().getCodigoProducto(), v.getProducto().getNombre(), v.getCantidad(), v.getPrecio(),
				v.getFecha(), v.getTotal());
	}
}
