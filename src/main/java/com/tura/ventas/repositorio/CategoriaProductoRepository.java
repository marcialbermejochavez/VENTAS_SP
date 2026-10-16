package com.tura.ventas.repositorio;
import org.springframework.data.jpa.repository.JpaRepository; import com.tura.ventas.modelo.CategoriaProducto;
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto,Integer> { boolean existsByNombreIgnoreCase(String nombre); }
