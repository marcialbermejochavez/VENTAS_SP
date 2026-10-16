package com.tura.ventas.modelo;
import java.math.BigDecimal; import java.util.*; import jakarta.persistence.*;
@Entity @Table(name="Ventascab")
public class VentaCabecera {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="Idventac") private Integer idventac;
 @Column(name="Numero",length=50,nullable=false) private String numero;
 @Column(name="Ruc",length=20) private String ruc;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="IdCliente",referencedColumnName="CodigoCliente",nullable=false) private Cliente cliente;
 @Column(name="Moneda",nullable=false) private Integer moneda=1;
 @Column(name="Stotafec",precision=18,scale=5,nullable=false) private BigDecimal subtotalAfecto=BigDecimal.ZERO;
 @Column(name="Totnoafec",precision=18,scale=5,nullable=false) private BigDecimal totalNoAfecto=BigDecimal.ZERO;
 @Column(name="Igv",precision=18,scale=5,nullable=false) private BigDecimal igv=BigDecimal.ZERO;
 @Column(name="Totalv",precision=18,scale=5,nullable=false) private BigDecimal total=BigDecimal.ZERO;
 @OneToMany(mappedBy="cabecera",cascade=CascadeType.ALL,orphanRemoval=true) private List<Venta> detalles=new ArrayList<>();
 @Transient private java.time.LocalDate fecha;
 public Integer getIdventac(){return idventac;} public void setIdventac(Integer v){idventac=v;}
 public String getNumero(){return numero;} public void setNumero(String v){numero=v;}
 public String getRuc(){return ruc;} public void setRuc(String v){ruc=v;}
 public Cliente getCliente(){return cliente;} public void setCliente(Cliente v){cliente=v;}
 public Integer getMoneda(){return moneda;} public void setMoneda(Integer v){moneda=v;}
 public BigDecimal getSubtotalAfecto(){return subtotalAfecto;} public void setSubtotalAfecto(BigDecimal v){subtotalAfecto=v;}
 public BigDecimal getTotalNoAfecto(){return totalNoAfecto;} public void setTotalNoAfecto(BigDecimal v){totalNoAfecto=v;}
 public BigDecimal getIgv(){return igv;} public void setIgv(BigDecimal v){igv=v;}
 public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
 public List<Venta> getDetalles(){return detalles;} public void setDetalles(List<Venta> v){detalles=v;}
 public java.time.LocalDate getFecha(){return fecha;} public void setFecha(java.time.LocalDate v){fecha=v;}
}
