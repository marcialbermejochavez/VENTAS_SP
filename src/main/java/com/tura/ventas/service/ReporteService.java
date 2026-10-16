package com.tura.ventas.service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.tura.ventas.modelo.*;

@Service
public class ReporteService {
	private byte[] pdf(String jrxml, Collection<?> data, Map<String, Object> p) throws JRException, IOException {
		try (InputStream in = getClass().getResourceAsStream("/reports/" + jrxml)) {
			if (in == null)
				throw new FileNotFoundException(jrxml);
			JasperReport r = JasperCompileManager.compileReport(in);
			JasperPrint jp = JasperFillManager.fillReport(r, p, new JRBeanCollectionDataSource(data));
			return JasperExportManager.exportReportToPdf(jp);
		}
	}

	public byte[] productos(List<Producto> x) throws Exception {
		return pdf("productos.jrxml", x, Map.of("TITULO", "Catálogo de Productos"));
	}

	public byte[] ventas(List<Venta> x, LocalDate d, LocalDate h) throws Exception {
		return pdf("ventas.jrxml", x,
				Map.of("TITULO", "Ventas por rango de fechas", "DESDE", d.toString(), "HASTA", h.toString()));
	}

	public byte[] resumen(List<Object[]> x) throws Exception {
		List<ResumenProducto> d = x.stream()
				.map(a -> new ResumenProducto((String) a[0], ((Number) a[1]).longValue(), (java.math.BigDecimal) a[2]))
				.toList();
		return pdf("resumen.jrxml", d, Map.of("TITULO", "Resumen de Ventas por Producto"));
	}

	public record ResumenProducto(String producto, long cantidad, java.math.BigDecimal total) {
	}
}
