package com.tura.ventas.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodigoCliente")
	private Integer codigoCliente;
	@NotBlank
	@Size(max = 100)
	@Column(name = "Nombres", nullable = false, length = 100)
	private String nombres;
	@NotBlank
	@Size(max = 100)
	@Column(name = "Apellidos", nullable = false, length = 100)
	private String apellidos;
	@Size(max = 200)
	@Column(name = "Direccion", length = 200)
	private String direccion;
	@Size(max = 20)
	@Column(name = "Telefono", length = 20)
	private String telefono;
	@NotBlank
	@Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
	@Column(name = "DNI", nullable = false, length = 8)
	private String dni;

	@Size(max=20) @Column(name="Ruc", length=20) private String ruc;
	@Email @Size(max=30) @Column(name="Correo", length=30) private String correo;
	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="Idcategoria") private CategoriaCliente categoria;
	public String getRuc(){return ruc;} public void setRuc(String v){ruc=v;}
	public String getCorreo(){return correo;} public void setCorreo(String v){correo=v;}
	public CategoriaCliente getCategoria(){return categoria;} public void setCategoria(CategoriaCliente v){categoria=v;}
	public Integer getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Integer v) {
		codigoCliente = v;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String v) {
		direccion = v;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String v) {
		telefono = v;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String v) {
		dni = v;
	}

	@Transient
	public String getNombreCompleto() {
		return nombres + " " + apellidos;
	}
}
