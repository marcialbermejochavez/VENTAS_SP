package com.tura.ventas.controlador;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*; import org.springframework.web.servlet.mvc.support.RedirectAttributes; import com.tura.ventas.modelo.*; import com.tura.ventas.service.*;
@Controller public class CategoriaController {
 private final CategoriaProductoService cps; private final CategoriaClienteService ccs;
 public CategoriaController(CategoriaProductoService cps,CategoriaClienteService ccs){this.cps=cps;this.ccs=ccs;}
 @GetMapping("/categorias/productos") String productos(@RequestParam(required=false) Integer editar,Model m){m.addAttribute("lista",cps.listar());m.addAttribute("categoria",editar==null?new CategoriaProducto():cps.obtener(editar));return "categorias-productos";}
 @PostMapping("/categorias/productos/guardar") String guardarProducto(CategoriaProducto c,RedirectAttributes ra){try{cps.guardar(c);ra.addFlashAttribute("mensaje","Categoría guardada.");}catch(Exception e){ra.addFlashAttribute("error",e.getMessage());}return "redirect:/categorias/productos";}
 @PostMapping("/categorias/productos/eliminar/{id}") String eliminarProducto(@PathVariable Integer id,RedirectAttributes ra){try{cps.eliminar(id);ra.addFlashAttribute("mensaje","Categoría eliminada.");}catch(Exception e){ra.addFlashAttribute("error","No se puede eliminar: puede estar asignada a productos.");}return "redirect:/categorias/productos";}
 @GetMapping("/categorias/clientes") String clientes(@RequestParam(required=false) Integer editar,Model m){m.addAttribute("lista",ccs.listar());m.addAttribute("categoria",editar==null?new CategoriaCliente():ccs.obtener(editar));return "categorias-clientes";}
 @PostMapping("/categorias/clientes/guardar") String guardarCliente(CategoriaCliente c,RedirectAttributes ra){try{ccs.guardar(c);ra.addFlashAttribute("mensaje","Categoría guardada.");}catch(Exception e){ra.addFlashAttribute("error",e.getMessage());}return "redirect:/categorias/clientes";}
 @PostMapping("/categorias/clientes/eliminar/{id}") String eliminarCliente(@PathVariable Integer id,RedirectAttributes ra){try{ccs.eliminar(id);ra.addFlashAttribute("mensaje","Categoría eliminada.");}catch(Exception e){ra.addFlashAttribute("error","No se puede eliminar: puede estar asignada a clientes.");}return "redirect:/categorias/clientes";}
}
