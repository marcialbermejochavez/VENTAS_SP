package com.tura.ventas.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodigoUsuario")
	private Integer codigoUsuario;
	@NotBlank
	@Column(name = "nombres", nullable = false, length = 100)
	private String nombres;
	@NotBlank
	@Column(name = "apellidos", nullable = false, length = 100)
	private String apellidos;
	@NotBlank
	@Email
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	@NotBlank
	@Column(name = "telefono", nullable = false, length = 20)
	private String telefono;
	@NotBlank
	@Column(name = "usuario", nullable = false, unique = true, length = 100)
	private String usuario;
	@Column(name = "pass", nullable = false, length = 100)
	private String pass;
	@Column(name = "rol", nullable = false, length = 20)
	private String rol = "OPERADOR";

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer v) {
		codigoUsuario = v;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String v) {
		nombres = v;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String v) {
		apellidos = v;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String v) {
		email = v;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String v) {
		telefono = v;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String v) {
		usuario = v;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String v) {
		pass = v;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String v) {
		rol = v;
	}
}
