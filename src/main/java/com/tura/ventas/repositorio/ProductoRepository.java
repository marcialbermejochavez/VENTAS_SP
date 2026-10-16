package com.tura.ventas.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.tura.ventas.modelo.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	@Override @EntityGraph(attributePaths={"categoria"}) List<Producto> findAll();
	@Override @EntityGraph(attributePaths={"categoria"}) java.util.Optional<Producto> findById(Integer id);
	@EntityGraph(attributePaths={"categoria"})
	@Query("select p from Producto p where lower(p.nombre) like lower(concat('%',:q,'%'))")
	List<Producto> buscar(@Param("q") String q);

	@Query("select p from Producto p where p.stockActual <= p.stockMinimo")
	List<Producto> bajoStock();
}