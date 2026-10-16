package com.tura.ventas.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Ventas")
public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodigoVenta")
	private Integer codigoVenta;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CodigoCliente", nullable = false)
	private Cliente cliente;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CodigoProducto", nullable = false)
	private Producto producto;
	@Min(1)
	@Column(name = "Cantidad", nullable = false)
	private int cantidad;
	@NotNull
	@Column(name = "Precio", nullable = false, precision = 10, scale = 2)
	private BigDecimal precio;
	@NotNull
	@Column(name = "Fecha", nullable = false)
	private LocalDate fecha;

	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="Idventac") private VentaCabecera cabecera;
	public VentaCabecera getCabecera(){return cabecera;} public void setCabecera(VentaCabecera v){cabecera=v;}
	public Integer getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(Integer v) {
		codigoVenta = v;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente v) {
		cliente = v;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto v) {
		producto = v;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int v) {
		cantidad = v;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal v) {
		precio = v;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate v) {
		fecha = v;
	}

	@Transient
	public BigDecimal getTotal() {
		return precio == null ? BigDecimal.ZERO : precio.multiply(BigDecimal.valueOf(cantidad));
	}
}
