package com.tura.ventas.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.tura.ventas.modelo.Cliente;
import com.tura.ventas.repositorio.ClienteRepository;

@Service
public class ClienteService {
	private final ClienteRepository r;

	public ClienteService(ClienteRepository r) {
		this.r = r;
	}

	public List<Cliente> listar(String q) {
		return q == null || q.isBlank() ? r.findAll() : r.buscar(q.trim());
	}

	public Cliente obtener(Integer id) {
		return r.findById(id).orElseThrow();
	}

	public Cliente guardar(Cliente c) {
		return r.save(c);
	}

	public void eliminar(Integer id) {
		r.deleteById(id);
	}

	public long contar() {
		return r.count();
	}
}