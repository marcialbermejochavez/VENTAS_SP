# Cambios implementados en Proyecto TURA - Ventas

## CRUD nuevos
- Categorías de productos (`CategProduc`): listar, crear, editar y eliminar.
- Categorías de clientes (`CatClie`): listar, crear, editar y eliminar.
- Los formularios de Productos y Clientes permiten asignar su categoría.
- Clientes incorpora RUC y correo.

## Módulo de ventas
- Cabecera almacenada en `Ventascab`: número, cliente, RUC, moneda, subtotal afecto, total no afecto, IGV y total.
- Detalle almacenado en la tabla `Ventas` existente, agregando `Idventac` como relación opcional a `Ventascab`.
- Una venta puede contener varios productos.
- El precio se toma del catálogo y se valida el stock antes de descontarlo.
- Al eliminar una cabecera, se eliminan sus líneas y se devuelve el stock de cada línea.
- Se conserva compatibilidad con el registro antiguo de una sola línea utilizado por el API existente.

## Base de datos
Ejecutar `BD_URA.sql` en MySQL Workbench. El script usa `CREATE DATABASE IF NOT EXISTS`, `CREATE TABLE IF NOT EXISTS` y comprobaciones de columnas/relaciones; no contiene `DROP DATABASE` y no elimina tablas ni datos existentes.

Los registros de venta antiguos se enlazan a una cabecera `LEGACY-<CodigoVenta>` para que aparezcan en el nuevo listado. El esquema antiguo no guardaba desglose de IGV, por lo que esas cabeceras históricas quedan con IGV 0 y su importe original como subtotal/total.

## Supuestos funcionales adoptados
- Los detalles nuevos se consideran 100 % afectos al IGV, porque no se solicitó un campo de afectación por producto.
- Se aplica IGV del 18 % sobre la suma de precio por cantidad y se suma al subtotal. Si los precios del catálogo ya incluyen IGV, la fórmula debe ajustarse.
- `Moneda=1` representa PEN (soles) y `Moneda=2` representa USD (dólares), según el formulario.
- La fecha de venta se guarda en cada línea de detalle, porque la estructura solicitada para `Ventascab` no incluye un campo Fecha.

## Ejecución
1. Respaldar la base de datos.
2. Ejecutar `BD_URA.sql`.
3. Confirmar que `application.properties` apunta a `sistemaventasdb`.
4. Compilar y ejecutar el proyecto con Java 21 y Maven.
5. Ingresar al sistema y probar primero categorías, luego asignarlas a productos/clientes y registrar una venta con dos o más líneas.

No fue posible ejecutar una compilación Maven en el entorno de preparación: el ZIP no incluye los archivos de configuración del Maven Wrapper y el comando `mvn` no está instalado en este entorno. Por ello, validar la compilación y pruebas de integración al abrir el proyecto en el entorno de desarrollo.
