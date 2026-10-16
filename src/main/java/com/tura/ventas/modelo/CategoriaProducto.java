package com.tura.ventas.modelo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="CategProduc")
public class CategoriaProducto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="Idcatpr") private Integer idcatpr;
 @NotBlank @Size(max=50) @Column(name="Nombre",nullable=false,length=50) private String nombre;
 public Integer getIdcatpr(){return idcatpr;} public void setIdcatpr(Integer v){idcatpr=v;}
 public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
}
