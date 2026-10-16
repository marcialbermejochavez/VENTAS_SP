package com.tura.ventas.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.tura.ventas.modelo.Producto;
import com.tura.ventas.repositorio.ProductoRepository;

@Service
public class ProductoService {
	private final ProductoRepository r;

	public ProductoService(ProductoRepository r) {
		this.r = r;
	}

	public List<Producto> listar(String q) {
		return q == null || q.isBlank() ? r.findAll() : r.buscar(q.trim());
	}

	public Producto obtener(Integer id) {
		return r.findById(id).orElseThrow();
	}

	public Producto guardar(Producto p) {
		return r.save(p);
	}

	public void eliminar(Integer id) {
		r.deleteById(id);
	}

	public long contar() {
		return r.count();
	}

	public long contarBajoStock() {
		return r.bajoStock().size();
	}
}