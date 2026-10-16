package com.tura.ventas.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.tura.ventas.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	@Override @EntityGraph(attributePaths={"categoria"}) List<Cliente> findAll();
	@Override @EntityGraph(attributePaths={"categoria"}) java.util.Optional<Cliente> findById(Integer id);
	@EntityGraph(attributePaths={"categoria"})
	@Query("select c from Cliente c where lower(c.nombres) like lower(concat('%',:q,'%')) or lower(c.apellidos) like lower(concat('%',:q,'%')) or c.dni like concat('%',:q,'%')")
	List<Cliente> buscar(@Param("q") String q);
}