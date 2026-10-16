package com.tura.ventas.modelo;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Productos")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodigoProducto")
	private Integer codigoProducto;
	@NotBlank
	@Size(max = 100)
	@Column(name = "Nombre", nullable = false, length = 100)
	private String nombre;
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	@Digits(integer = 8, fraction = 2)
	@Column(name = "Precio", nullable = false, precision = 10, scale = 2)
	private BigDecimal precio;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Idcatproductos")
	private CategoriaProducto categoria;

	public CategoriaProducto getCategoria() { return categoria; }
	public void setCategoria(CategoriaProducto v) { categoria = v; }

	@Min(0)
	@Column(name = "StockActual", nullable = false)
	private int stockActual;
	@Min(0)
	@Column(name = "StockMinimo", nullable = false)
	private int stockMinimo;
	@Min(0)
	@Column(name = "StockMaximo", nullable = false)
	private int stockMaximo;

	public Integer getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(Integer v) {
		codigoProducto = v;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String v) {
		nombre = v;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal v) {
		precio = v;
	}

	public int getStockActual() {
		return stockActual;
	}

	public void setStockActual(int v) {
		stockActual = v;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int v) {
		stockMinimo = v;
	}

	public int getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(int v) {
		stockMaximo = v;
	}
}
