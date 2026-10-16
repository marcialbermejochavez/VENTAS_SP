package com.tura.ventas.modelo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="CatClie")
public class CategoriaCliente {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="Idcatclie") private Integer idcatclie;
 @NotBlank @Size(max=50) @Column(name="Nombre",nullable=false,length=50) private String nombre;
 public Integer getIdcatclie(){return idcatclie;} public void setIdcatclie(Integer v){idcatclie=v;}
 public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
}
