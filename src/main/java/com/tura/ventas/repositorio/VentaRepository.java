package com.tura.ventas.repositorio;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.tura.ventas.modelo.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
	@Query("select v from Venta v where v.cabecera.idventac=:id order by v.codigoVenta")
	@EntityGraph(attributePaths={"cliente","producto","cabecera"}) List<Venta> porCabecera(@Param("id") Integer id);
	@EntityGraph(attributePaths = { "cliente", "producto" })
	@Query("select v from Venta v order by v.fecha desc,v.codigoVenta desc")
	List<Venta> listarDetalle();

	@EntityGraph(attributePaths = { "cliente", "producto" })
	@Query("select v from Venta v where v.fecha between :desde and :hasta order by v.fecha desc")
	List<Venta> porFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

	@EntityGraph(attributePaths = { "cliente", "producto" })
	@Query("select v from Venta v where v.cliente.codigoCliente=:cliente order by v.fecha desc")
	List<Venta> porCliente(@Param("cliente") Integer cliente);

	@EntityGraph(attributePaths = { "cliente", "producto" })
	@Query("select v from Venta v where v.fecha between :desde and :hasta and (:cliente is null or v.cliente.codigoCliente=:cliente) order by v.fecha desc")
	List<Venta> filtrar(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta,
			@Param("cliente") Integer cliente);

	@Query("select v.producto.nombre,sum(v.cantidad),sum(v.precio*v.cantidad) from Venta v group by v.producto.codigoProducto,v.producto.nombre order by sum(v.precio*v.cantidad) desc")
	List<Object[]> resumenPorProducto();
}
