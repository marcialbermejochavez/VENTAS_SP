# Proyecto TURA - Spring Boot

Migración académica del proyecto Java Web original a Spring Boot + Jakarta + JPA/Hibernate + JPQL + Thymeleaf + Spring Security + JasperReports.

## Requisitos
- JDK 21
- MySQL 8.x
- Spring Tools for Eclipse / STS

## Preparación
1. Ejecutar `BD_URA.sql` en MySQL.
2. Por defecto la app usa `root/12345`. Si su clave es distinta, defina `DB_PASSWORD` o cambie `application.properties`.
3. En STS: File > Import > Maven > Existing Maven Projects y seleccione la carpeta `ventas`.
4. Maven > Update Project.
5. Run As > Spring Boot App.
6. Abrir http://localhost:8080/login

Usuario inicial: `tito` / `123456`. En el primer arranque la contraseña de prueba se migra automáticamente a BCrypt.

## Funciones
CRUD clientes, productos y usuarios; autenticación/roles; catálogo; ventas con validación y descuento/devolución transaccional de stock; consultas JPQL; indicadores; PDFs JasperReports.
