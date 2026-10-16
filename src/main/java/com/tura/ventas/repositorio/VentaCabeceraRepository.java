package com.tura.ventas.repositorio;
import java.util.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import com.tura.ventas.modelo.VentaCabecera;
public interface VentaCabeceraRepository extends JpaRepository<VentaCabecera,Integer> {
 @EntityGraph(attributePaths={"cliente","detalles","detalles.producto"}) @Query("select distinct c from VentaCabecera c order by c.idventac desc") List<VentaCabecera> listarCompleto();
 @EntityGraph(attributePaths={"cliente","detalles","detalles.producto"}) @Query("select distinct c from VentaCabecera c where c.idventac=:id") Optional<VentaCabecera> obtenerCompleto(@Param("id") Integer id);
}
