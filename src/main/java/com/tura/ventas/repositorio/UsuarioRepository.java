package com.tura.ventas.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tura.ventas.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByUsuario(String usuario);

	boolean existsByUsuario(String usuario);
}