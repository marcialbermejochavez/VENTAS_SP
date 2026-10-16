package com.tura.ventas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.tura.ventas.repositorio.UsuarioRepository;

@Configuration
public class PasswordMigrationConfig {
	@Bean
	CommandLineRunner migrarPasswords(UsuarioRepository r, PasswordEncoder pe) {
		return args -> {
			for (var u : r.findAll()) {
				if (u.getPass() != null && !u.getPass().startsWith("$2")) {
					u.setPass(pe.encode(u.getPass()));
					r.save(u);
				}
			}
		};
	}
}
