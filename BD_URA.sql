-- Inicialización/migración segura de sistemaventasdb.
-- No elimina la base de datos, tablas ni datos existentes.
CREATE DATABASE IF NOT EXISTS sistemaventasdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sistemaventasdb;

CREATE TABLE IF NOT EXISTS Usuarios (
 CodigoUsuario INT AUTO_INCREMENT PRIMARY KEY, nombres VARCHAR(100) NOT NULL, apellidos VARCHAR(100) NOT NULL,
 email VARCHAR(100) NOT NULL, telefono VARCHAR(20) NOT NULL, usuario VARCHAR(100) NOT NULL UNIQUE,
 pass VARCHAR(100) NOT NULL, rol VARCHAR(20) NOT NULL DEFAULT 'OPERADOR'
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Clientes (
 CodigoCliente INT AUTO_INCREMENT PRIMARY KEY, Nombres VARCHAR(100) NOT NULL, Apellidos VARCHAR(100) NOT NULL,
 Direccion VARCHAR(200), Telefono VARCHAR(20), DNI VARCHAR(8) NOT NULL
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Productos (
 CodigoProducto INT AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(100) NOT NULL, Precio DECIMAL(10,2) NOT NULL,
 StockActual INT NOT NULL, StockMinimo INT NOT NULL, StockMaximo INT NOT NULL
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Ventas (
 CodigoVenta INT AUTO_INCREMENT PRIMARY KEY, CodigoCliente INT NOT NULL, CodigoProducto INT NOT NULL,
 Cantidad INT NOT NULL, Precio DECIMAL(10,2) NOT NULL, Fecha DATE NOT NULL,
 CONSTRAINT FK_Venta_Cliente FOREIGN KEY(CodigoCliente) REFERENCES Clientes(CodigoCliente),
 CONSTRAINT FK_Venta_Producto FOREIGN KEY(CodigoProducto) REFERENCES Productos(CodigoProducto)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS CategProduc (
 Idcatpr INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS CatClie (
 Idcatclie INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS Ventascab (
 Idventac INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Numero VARCHAR(50) NOT NULL, Ruc VARCHAR(20) NULL,
 IdCliente INT NOT NULL, Moneda INT NOT NULL DEFAULT 1, Stotafec DECIMAL(18,5) NOT NULL DEFAULT 0,
 Totnoafec DECIMAL(18,5) NOT NULL DEFAULT 0, Igv DECIMAL(18,5) NOT NULL DEFAULT 0,
 Totalv DECIMAL(18,5) NOT NULL DEFAULT 0, KEY IX_Ventascab_IdCliente (IdCliente),
 CONSTRAINT FK_Ventascab_Cliente FOREIGN KEY(IdCliente) REFERENCES Clientes(CodigoCliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Añadir columnas solo cuando aún no existan (compatible con ejecuciones repetidas).
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Productos ADD COLUMN Idcatproductos INT NULL', 'SELECT 1') FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='Productos' AND COLUMN_NAME='Idcatproductos'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Clientes ADD COLUMN Ruc VARCHAR(20) NULL', 'SELECT 1') FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='Clientes' AND COLUMN_NAME='Ruc'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Clientes ADD COLUMN Correo VARCHAR(30) NULL', 'SELECT 1') FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='Clientes' AND COLUMN_NAME='Correo'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Clientes ADD COLUMN Idcategoria INT NULL', 'SELECT 1') FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='Clientes' AND COLUMN_NAME='Idcategoria'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Ventas ADD COLUMN Idventac INT NULL', 'SELECT 1') FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='Ventas' AND COLUMN_NAME='Idventac'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- Claves foráneas: crear solo si todavía no existen.
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Productos ADD CONSTRAINT FK_Productos_CategProduc FOREIGN KEY (Idcatproductos) REFERENCES CategProduc(Idcatpr)', 'SELECT 1') FROM information_schema.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='FK_Productos_CategProduc'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Clientes ADD CONSTRAINT FK_Clientes_CatClie FOREIGN KEY (Idcategoria) REFERENCES CatClie(Idcatclie)', 'SELECT 1') FROM information_schema.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='FK_Clientes_CatClie'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
SET @ddl = (SELECT IF(COUNT(*)=0, 'ALTER TABLE Ventas ADD CONSTRAINT FK_Ventas_Ventascab FOREIGN KEY (Idventac) REFERENCES Ventascab(Idventac)', 'SELECT 1') FROM information_schema.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='FK_Ventas_Ventascab'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- Migrar ventas históricas a cabecera/detalle sin borrar los detalles existentes.
-- Como el esquema antiguo no almacenaba desglose de IGV, las cabeceras históricas usan IGV=0.
INSERT INTO Ventascab (Numero, Ruc, IdCliente, Moneda, Stotafec, Totnoafec, Igv, Totalv)
SELECT CONCAT('LEGACY-', v.CodigoVenta), c.Ruc, v.CodigoCliente, 1,
       CAST(v.Precio * v.Cantidad AS DECIMAL(18,5)), 0, 0,
       CAST(v.Precio * v.Cantidad AS DECIMAL(18,5))
FROM Ventas v LEFT JOIN Clientes c ON c.CodigoCliente=v.CodigoCliente
LEFT JOIN Ventascab vc ON vc.Numero=CONCAT('LEGACY-', v.CodigoVenta)
WHERE v.Idventac IS NULL AND vc.Idventac IS NULL;
UPDATE Ventas v JOIN Ventascab vc ON vc.Numero=CONCAT('LEGACY-', v.CodigoVenta)
SET v.Idventac=vc.Idventac WHERE v.Idventac IS NULL;

-- No se insertan usuarios/clientes/productos de demostración automáticamente para evitar duplicados.
