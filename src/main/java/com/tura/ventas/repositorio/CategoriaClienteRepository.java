package com.tura.ventas.repositorio;
import org.springframework.data.jpa.repository.JpaRepository; import com.tura.ventas.modelo.CategoriaCliente;
public interface CategoriaClienteRepository extends JpaRepository<CategoriaCliente,Integer> { boolean existsByNombreIgnoreCase(String nombre); }
