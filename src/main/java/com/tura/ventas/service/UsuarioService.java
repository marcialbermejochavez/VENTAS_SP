package com.tura.ventas.service;

import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tura.ventas.modelo.Usuario;
import com.tura.ventas.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository r;
	private final PasswordEncoder pe;

	public UsuarioService(UsuarioRepository r, PasswordEncoder pe) {
		this.r = r;
		this.pe = pe;
	}

	public List<Usuario> listar() {
		return r.findAll();
	}

	public Usuario obtener(Integer id) {
		return r.findById(id).orElseThrow();
	}

	public Usuario guardar(Usuario u, String nuevaPass) {
		if (u.getCodigoUsuario() != null) {
			Usuario old = obtener(u.getCodigoUsuario());
			if (nuevaPass == null || nuevaPass.isBlank())
				u.setPass(old.getPass());
			else
				u.setPass(pe.encode(nuevaPass));
		} else {
			u.setPass(pe.encode(nuevaPass));
		}
		if (u.getRol() == null || u.getRol().isBlank())
			u.setRol("OPERADOR");
		return r.save(u);
	}

	public void eliminar(Integer id) {
		r.deleteById(id);
	}
}
