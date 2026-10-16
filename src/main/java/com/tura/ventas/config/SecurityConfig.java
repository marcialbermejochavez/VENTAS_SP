package com.tura.ventas.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.tura.ventas.repositorio.UsuarioRepository;

@Configuration
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(UsuarioRepository r) {
		return username -> {
			var u = r.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
			return User.withUsername(u.getUsuario()).password(u.getPass()).roles(u.getRol()).build();
		};
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity h) throws Exception {
		h.authorizeHttpRequests(a -> a.requestMatchers("/login", "/css/**", "/images/**").permitAll()
				.requestMatchers("/usuarios/**").hasRole("ADMIN").anyRequest().authenticated()).httpBasic(b -> {
				})
				.formLogin(f -> f.loginPage("/login").loginProcessingUrl("/login").usernameParameter("usuario")
						.passwordParameter("contrasena").defaultSuccessUrl("/menu", true).failureUrl("/login?error=1")
						.permitAll())
				.logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout=1").permitAll())
				.csrf(c -> c.ignoringRequestMatchers("/api/**"));
		return h.build();
	}
}
