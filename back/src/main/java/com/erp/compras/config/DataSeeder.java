package com.erp.compras.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.erp.compras.model.*;
import com.erp.compras.repository.*;

@Configuration
public class DataSeeder {

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase(
            IProveedorRepository proveedorRepo,
            IProductoRepository productoRepo,
            IProveedorProductoRepository proveedorProductoRepo,
            IOrdenCompraRepository ordenCompraRepo,
            IRecepcionRepository recepcionRepo) {

        return args -> {
            // Solo cargar si la base está vacía
            if (proveedorRepo.count() > 0) {
                System.out.println(">>> Base de datos ya tiene datos, saltando seeder...");
                return;
            }

            System.out.println(">>> Cargando datos de prueba...");

            // ==================== PROVEEDORES ====================
            Proveedor prov1 = new Proveedor();
            prov1.setCodigo("PROV-001");
            prov1.setNombre("Distribuidora Industrial del Norte S.A.");
            prov1.setRfc("DIN850101ABC");
            prov1.setContacto("Carlos Mendoza");
            prov1.setEmail("ventas@dinorte.com");
            prov1.setTelefono("+52 81 1234 5678");
            prov1.setDireccion("Av. Industrial 1250");
            prov1.setCiudad("Monterrey");
            prov1.setEstado("Nuevo León");
            prov1.setActivo(true);
            prov1.setCategoria("Materiales");
            prov1.setRating(new BigDecimal("4.8"));
            prov1.setReviewCount(245);
            prov1.setVerified(true);

            Proveedor prov2 = new Proveedor();
            prov2.setCodigo("PROV-002");
            prov2.setNombre("Tecnología y Equipos SA de CV");
            prov2.setRfc("TEQ900215XYZ");
            prov2.setContacto("María García");
            prov2.setEmail("contacto@tecnoequipos.mx");
            prov2.setTelefono("+52 55 9876 5432");
            prov2.setDireccion("Calle Reforma 456");
            prov2.setCiudad("Ciudad de México");
            prov2.setEstado("CDMX");
            prov2.setActivo(true);
            prov2.setCategoria("Equipos");
            prov2.setRating(new BigDecimal("4.7"));
            prov2.setReviewCount(156);
            prov2.setVerified(true);

            Proveedor prov3 = new Proveedor();
            prov3.setCodigo("PROV-003");
            prov3.setNombre("Suministros Oficina Express");
            prov3.setRfc("SOE880530QWE");
            prov3.setContacto("Roberto Sánchez");
            prov3.setEmail("pedidos@oficinaexpress.com");
            prov3.setTelefono("+52 33 2468 1357");
            prov3.setDireccion("Blvd. Empresarial 789");
            prov3.setCiudad("Guadalajara");
            prov3.setEstado("Jalisco");
            prov3.setActivo(true);
            prov3.setCategoria("Oficina");
            prov3.setRating(new BigDecimal("4.4"));
            prov3.setReviewCount(567);
            prov3.setVerified(true);

            Proveedor prov4 = new Proveedor();
            prov4.setCodigo("PROV-004");
            prov4.setNombre("Químicos Industriales del Bajío");
            prov4.setRfc("QIB750820RTY");
            prov4.setContacto("Ana López");
            prov4.setEmail("ventas@quimicosbajio.com");
            prov4.setTelefono("+52 477 123 4567");
            prov4.setDireccion("Parque Industrial Norte 234");
            prov4.setCiudad("León");
            prov4.setEstado("Guanajuato");
            prov4.setActivo(true);
            prov4.setCategoria("Químicos");
            prov4.setRating(new BigDecimal("4.4"));
            prov4.setReviewCount(156);
            prov4.setVerified(true);

            Proveedor prov5 = new Proveedor();
            prov5.setCodigo("PROV-005");
            prov5.setNombre("Logística y Empaques Modernos");
            prov5.setRfc("LEM920415UIO");
            prov5.setContacto("Fernando Torres");
            prov5.setEmail("info@empaques-modernos.mx");
            prov5.setTelefono("+52 222 987 6543");
            prov5.setDireccion("Zona Industrial Sur 567");
            prov5.setCiudad("Puebla");
            prov5.setEstado("Puebla");
            prov5.setActivo(false);
            prov5.setCategoria("Empaques");
            prov5.setRating(new BigDecimal("4.2"));
            prov5.setReviewCount(89);
            prov5.setVerified(false);

            // Proveedores adicionales para SupplierQuotes
            Proveedor prov6 = new Proveedor();
            prov6.setCodigo("PROV-006");
            prov6.setNombre("Tornillos y Fijaciones Express");
            prov6.setRfc("TFE950101ABC");
            prov6.setContacto("Juan Pérez");
            prov6.setEmail("ventas@tornillos-express.com");
            prov6.setTelefono("+52 33 1234 5678");
            prov6.setDireccion("Av. Ferretería 100");
            prov6.setCiudad("Guadalajara");
            prov6.setEstado("Jalisco");
            prov6.setActivo(true);
            prov6.setCategoria("Ferretería");
            prov6.setRating(new BigDecimal("4.9"));
            prov6.setReviewCount(189);
            prov6.setVerified(true);

            Proveedor prov7 = new Proveedor();
            prov7.setCodigo("PROV-007");
            prov7.setNombre("Distribuidora de Materiales CDMX");
            prov7.setRfc("DMC880101XYZ");
            prov7.setContacto("Laura Martínez");
            prov7.setEmail("ventas@materiales-cdmx.com");
            prov7.setTelefono("+52 55 5555 1234");
            prov7.setDireccion("Av. Central 500");
            prov7.setCiudad("Ciudad de México");
            prov7.setEstado("CDMX");
            prov7.setActivo(true);
            prov7.setCategoria("Materiales");
            prov7.setRating(new BigDecimal("4.5"));
            prov7.setReviewCount(312);
            prov7.setVerified(true);

            Proveedor prov8 = new Proveedor();
            prov8.setCodigo("PROV-008");
            prov8.setNombre("CompuMayor México");
            prov8.setRfc("CMM900215QWE");
            prov8.setContacto("Pedro Ramírez");
            prov8.setEmail("ventas@compumayor.mx");
            prov8.setTelefono("+52 81 8888 9999");
            prov8.setDireccion("Plaza Tech 200");
            prov8.setCiudad("Monterrey");
            prov8.setEstado("Nuevo León");
            prov8.setActivo(true);
            prov8.setCategoria("Tecnología");
            prov8.setRating(new BigDecimal("4.9"));
            prov8.setReviewCount(423);
            prov8.setVerified(true);

            // Más proveedores de tecnología
            Proveedor prov9 = new Proveedor();
            prov9.setCodigo("PROV-009");
            prov9.setNombre("Soluciones IT Empresarial");
            prov9.setRfc("SIE950101ABC");
            prov9.setContacto("Ricardo Flores");
            prov9.setEmail("ventas@solucionesit.mx");
            prov9.setTelefono("+52 442 123 4567");
            prov9.setDireccion("Parque Tecnológico 500");
            prov9.setCiudad("Querétaro");
            prov9.setEstado("Querétaro");
            prov9.setActivo(true);
            prov9.setCategoria("Tecnología");
            prov9.setRating(new BigDecimal("4.6"));
            prov9.setReviewCount(89);
            prov9.setVerified(true);

            Proveedor prov10 = new Proveedor();
            prov10.setCodigo("PROV-010");
            prov10.setNombre("Laptops Corporativas MX");
            prov10.setRfc("LCM880101XYZ");
            prov10.setContacto("Sandra Vega");
            prov10.setEmail("ventas@laptopscorp.mx");
            prov10.setTelefono("+52 55 4444 5555");
            prov10.setDireccion("Av. Insurgentes Sur 1500");
            prov10.setCiudad("Ciudad de México");
            prov10.setEstado("CDMX");
            prov10.setActivo(true);
            prov10.setCategoria("Tecnología");
            prov10.setRating(new BigDecimal("4.8"));
            prov10.setReviewCount(312);
            prov10.setVerified(true);

            Proveedor prov11 = new Proveedor();
            prov11.setCodigo("PROV-011");
            prov11.setNombre("TechStore Empresas");
            prov11.setRfc("TSE920415QWE");
            prov11.setContacto("Miguel Ángel Torres");
            prov11.setEmail("empresas@techstore.com.mx");
            prov11.setTelefono("+52 33 3333 4444");
            prov11.setDireccion("Plaza Corporativa 200");
            prov11.setCiudad("Guadalajara");
            prov11.setEstado("Jalisco");
            prov11.setActivo(true);
            prov11.setCategoria("Tecnología");
            prov11.setRating(new BigDecimal("4.5"));
            prov11.setReviewCount(198);
            prov11.setVerified(true);

            Proveedor prov12 = new Proveedor();
            prov12.setCodigo("PROV-012");
            prov12.setNombre("Digital Business Solutions");
            prov12.setRfc("DBS900101RTY");
            prov12.setContacto("Carolina Mendez");
            prov12.setEmail("ventas@digitalbusiness.mx");
            prov12.setTelefono("+52 81 2222 3333");
            prov12.setDireccion("Torre Empresarial 1800");
            prov12.setCiudad("Monterrey");
            prov12.setEstado("Nuevo León");
            prov12.setActivo(true);
            prov12.setCategoria("Tecnología");
            prov12.setRating(new BigDecimal("4.7"));
            prov12.setReviewCount(267);
            prov12.setVerified(true);

            // Más proveedores de papelería/oficina
            Proveedor prov13 = new Proveedor();
            prov13.setCodigo("PROV-013");
            prov13.setNombre("Papelería Corporativa SA");
            prov13.setRfc("PCS850101ABC");
            prov13.setContacto("Luis Hernández");
            prov13.setEmail("ventas@papeleriacorp.com");
            prov13.setTelefono("+52 55 6666 7777");
            prov13.setDireccion("Av. Universidad 300");
            prov13.setCiudad("Ciudad de México");
            prov13.setEstado("CDMX");
            prov13.setActivo(true);
            prov13.setCategoria("Oficina");
            prov13.setRating(new BigDecimal("4.8"));
            prov13.setReviewCount(456);
            prov13.setVerified(true);

            Proveedor prov14 = new Proveedor();
            prov14.setCodigo("PROV-014");
            prov14.setNombre("Mayoreo Oficina Plus");
            prov14.setRfc("MOP900215XYZ");
            prov14.setContacto("Patricia Ruiz");
            prov14.setEmail("ventas@mayoreooficina.mx");
            prov14.setTelefono("+52 222 888 9999");
            prov14.setDireccion("Centro Comercial 150");
            prov14.setCiudad("Puebla");
            prov14.setEstado("Puebla");
            prov14.setActivo(true);
            prov14.setCategoria("Oficina");
            prov14.setRating(new BigDecimal("4.3"));
            prov14.setReviewCount(178);
            prov14.setVerified(false);

            Proveedor prov15 = new Proveedor();
            prov15.setCodigo("PROV-015");
            prov15.setNombre("Office Depot Empresarial");
            prov15.setRfc("ODE880530QWE");
            prov15.setContacto("Fernando García");
            prov15.setEmail("empresarial@officedepot.mx");
            prov15.setTelefono("+52 55 1111 2222");
            prov15.setDireccion("Av. Reforma 500");
            prov15.setCiudad("Ciudad de México");
            prov15.setEstado("CDMX");
            prov15.setActivo(true);
            prov15.setCategoria("Oficina");
            prov15.setRating(new BigDecimal("4.6"));
            prov15.setReviewCount(892);
            prov15.setVerified(true);

            Proveedor prov16 = new Proveedor();
            prov16.setCodigo("PROV-016");
            prov16.setNombre("Distribuidora de Papel del Norte");
            prov16.setRfc("DPN750820RTY");
            prov16.setContacto("Jorge Salinas");
            prov16.setEmail("ventas@papelnorte.com");
            prov16.setTelefono("+52 81 5555 6666");
            prov16.setDireccion("Zona Industrial 800");
            prov16.setCiudad("Monterrey");
            prov16.setEstado("Nuevo León");
            prov16.setActivo(true);
            prov16.setCategoria("Oficina");
            prov16.setRating(new BigDecimal("4.4"));
            prov16.setReviewCount(234);
            prov16.setVerified(true);

            Proveedor prov17 = new Proveedor();
            prov17.setCodigo("PROV-017");
            prov17.setNombre("Papelera del Bajío");
            prov17.setRfc("PDB920415UIO");
            prov17.setContacto("Martha López");
            prov17.setEmail("ventas@papelerabajio.mx");
            prov17.setTelefono("+52 477 333 4444");
            prov17.setDireccion("Blvd. Industrial 250");
            prov17.setCiudad("León");
            prov17.setEstado("Guanajuato");
            prov17.setActivo(true);
            prov17.setCategoria("Oficina");
            prov17.setRating(new BigDecimal("4.2"));
            prov17.setReviewCount(145);
            prov17.setVerified(true);

            List<Proveedor> proveedores = proveedorRepo.saveAll(
                Arrays.asList(prov1, prov2, prov3, prov4, prov5, prov6, prov7, prov8,
                              prov9, prov10, prov11, prov12, prov13, prov14, prov15, prov16, prov17));
            System.out.println(">>> " + proveedores.size() + " proveedores creados");

            // ==================== PRODUCTOS ====================
            Producto prod1 = new Producto();
            prod1.setCodigo("MAT-001");
            prod1.setNombre("Acero Inoxidable Calibre 18");
            prod1.setDescripcion("Lámina de acero inoxidable calibre 18, 4x8 pies");
            prod1.setCategoria("Materiales");
            prod1.setUnidad("Pieza");
            prod1.setPrecioUnitario(new BigDecimal("2500.00"));
            prod1.setStock(45);
            prod1.setStockMinimo(20);
            prod1.setProveedor(prov1);

            Producto prod2 = new Producto();
            prod2.setCodigo("MAT-002");
            prod2.setNombre("Tornillería Industrial M8");
            prod2.setDescripcion("Tornillos hexagonales M8x25mm grado 8.8");
            prod2.setCategoria("Ferretería");
            prod2.setUnidad("Caja (100 pzas)");
            prod2.setPrecioUnitario(new BigDecimal("350.00"));
            prod2.setStock(120);
            prod2.setStockMinimo(50);
            prod2.setProveedor(prov1);

            Producto prod3 = new Producto();
            prod3.setCodigo("EQP-001");
            prod3.setNombre("Laptop HP ProBook 450");
            prod3.setDescripcion("Laptop empresarial i5, 16GB RAM, 512GB SSD");
            prod3.setCategoria("Tecnología");
            prod3.setUnidad("Pieza");
            prod3.setPrecioUnitario(new BigDecimal("18500.00"));
            prod3.setStock(8);
            prod3.setStockMinimo(5);
            prod3.setProveedor(prov2);

            Producto prod4 = new Producto();
            prod4.setCodigo("EQP-002");
            prod4.setNombre("Monitor Dell 27 pulgadas");
            prod4.setDescripcion("Monitor LED 27\" Full HD, ajustable en altura");
            prod4.setCategoria("Tecnología");
            prod4.setUnidad("Pieza");
            prod4.setPrecioUnitario(new BigDecimal("5200.00"));
            prod4.setStock(15);
            prod4.setStockMinimo(10);
            prod4.setProveedor(prov2);

            Producto prod5 = new Producto();
            prod5.setCodigo("OFI-001");
            prod5.setNombre("Papel Bond Carta");
            prod5.setDescripcion("Resma de papel bond carta 75g, 500 hojas");
            prod5.setCategoria("Oficina");
            prod5.setUnidad("Resma");
            prod5.setPrecioUnitario(new BigDecimal("95.00"));
            prod5.setStock(200);
            prod5.setStockMinimo(100);
            prod5.setProveedor(prov3);

            Producto prod6 = new Producto();
            prod6.setCodigo("OFI-002");
            prod6.setNombre("Tóner HP 58A");
            prod6.setDescripcion("Cartucho de tóner original HP 58A negro");
            prod6.setCategoria("Oficina");
            prod6.setUnidad("Pieza");
            prod6.setPrecioUnitario(new BigDecimal("1850.00"));
            prod6.setStock(12);
            prod6.setStockMinimo(8);
            prod6.setProveedor(prov3);

            Producto prod7 = new Producto();
            prod7.setCodigo("QUI-001");
            prod7.setNombre("Solvente Industrial");
            prod7.setDescripcion("Solvente multiusos para limpieza industrial, 20L");
            prod7.setCategoria("Químicos");
            prod7.setUnidad("Cubeta");
            prod7.setPrecioUnitario(new BigDecimal("890.00"));
            prod7.setStock(25);
            prod7.setStockMinimo(15);
            prod7.setProveedor(prov4);

            Producto prod8 = new Producto();
            prod8.setCodigo("EMP-001");
            prod8.setNombre("Caja de Cartón 40x30x25");
            prod8.setDescripcion("Caja de cartón corrugado doble, 40x30x25 cm");
            prod8.setCategoria("Empaques");
            prod8.setUnidad("Paquete (25 pzas)");
            prod8.setPrecioUnitario(new BigDecimal("425.00"));
            prod8.setStock(80);
            prod8.setStockMinimo(40);
            prod8.setProveedor(prov5);

            // Productos para prov6 - Tornillos y Fijaciones Express
            Producto prod9 = new Producto();
            prod9.setCodigo("FER-001");
            prod9.setNombre("Tornillos Galvanizados 1/4");
            prod9.setDescripcion("Tornillos galvanizados cabeza hexagonal 1/4 x 1 pulgada");
            prod9.setCategoria("Ferretería");
            prod9.setUnidad("Caja (500 pzas)");
            prod9.setPrecioUnitario(new BigDecimal("280.00"));
            prod9.setStock(200);
            prod9.setStockMinimo(50);
            prod9.setProveedor(prov6);

            // Productos para prov7 - Distribuidora de Materiales CDMX
            Producto prod10 = new Producto();
            prod10.setCodigo("MAT-003");
            prod10.setNombre("Varilla Corrugada 3/8");
            prod10.setDescripcion("Varilla de acero corrugada 3/8 pulgada, 12 metros");
            prod10.setCategoria("Materiales");
            prod10.setUnidad("Pieza");
            prod10.setPrecioUnitario(new BigDecimal("185.00"));
            prod10.setStock(500);
            prod10.setStockMinimo(100);
            prod10.setProveedor(prov7);

            // Productos para prov8 - CompuMayor México
            Producto prod11 = new Producto();
            prod11.setCodigo("TEC-001");
            prod11.setNombre("Mouse Inalámbrico Logitech M720");
            prod11.setDescripcion("Mouse ergonómico inalámbrico, multi-dispositivo, batería 24 meses");
            prod11.setCategoria("Tecnología");
            prod11.setUnidad("Pieza");
            prod11.setPrecioUnitario(new BigDecimal("890.00"));
            prod11.setStock(150);
            prod11.setStockMinimo(30);
            prod11.setProveedor(prov8);

            // Productos para prov9 - Soluciones IT Empresarial
            Producto prod12 = new Producto();
            prod12.setCodigo("TEC-002");
            prod12.setNombre("Docking Station USB-C");
            prod12.setDescripcion("Docking station universal USB-C, 3 monitores, 100W PD");
            prod12.setCategoria("Tecnología");
            prod12.setUnidad("Pieza");
            prod12.setPrecioUnitario(new BigDecimal("3200.00"));
            prod12.setStock(45);
            prod12.setStockMinimo(10);
            prod12.setProveedor(prov9);

            // Productos para prov10 - Laptops Corporativas MX
            Producto prod13 = new Producto();
            prod13.setCodigo("TEC-003");
            prod13.setNombre("Laptop Lenovo ThinkPad E14");
            prod13.setDescripcion("Laptop empresarial i7, 16GB RAM, 512GB SSD, Windows 11 Pro");
            prod13.setCategoria("Tecnología");
            prod13.setUnidad("Pieza");
            prod13.setPrecioUnitario(new BigDecimal("21500.00"));
            prod13.setStock(35);
            prod13.setStockMinimo(10);
            prod13.setProveedor(prov10);

            // Productos para prov11 - TechStore Empresas
            Producto prod14 = new Producto();
            prod14.setCodigo("TEC-004");
            prod14.setNombre("Teclado Mecánico Logitech MX");
            prod14.setDescripcion("Teclado mecánico inalámbrico, retroiluminado, recargable");
            prod14.setCategoria("Tecnología");
            prod14.setUnidad("Pieza");
            prod14.setPrecioUnitario(new BigDecimal("2450.00"));
            prod14.setStock(80);
            prod14.setStockMinimo(20);
            prod14.setProveedor(prov11);

            // Productos para prov12 - Digital Business Solutions
            Producto prod15 = new Producto();
            prod15.setCodigo("TEC-005");
            prod15.setNombre("Webcam Logitech Brio 4K");
            prod15.setDescripcion("Cámara web 4K HDR, micrófono integrado, corrección de luz");
            prod15.setCategoria("Tecnología");
            prod15.setUnidad("Pieza");
            prod15.setPrecioUnitario(new BigDecimal("3800.00"));
            prod15.setStock(60);
            prod15.setStockMinimo(15);
            prod15.setProveedor(prov12);

            // Productos para prov13 - Papelería Corporativa SA
            Producto prod16 = new Producto();
            prod16.setCodigo("OFI-003");
            prod16.setNombre("Carpeta Ejecutiva Piel");
            prod16.setDescripcion("Carpeta ejecutiva de piel sintética con bloc y calculadora");
            prod16.setCategoria("Oficina");
            prod16.setUnidad("Pieza");
            prod16.setPrecioUnitario(new BigDecimal("450.00"));
            prod16.setStock(100);
            prod16.setStockMinimo(25);
            prod16.setProveedor(prov13);

            // Productos para prov14 - Mayoreo Oficina Plus
            Producto prod17 = new Producto();
            prod17.setCodigo("OFI-004");
            prod17.setNombre("Folders Manila Carta");
            prod17.setDescripcion("Paquete de 100 folders tamaño carta, color manila");
            prod17.setCategoria("Oficina");
            prod17.setUnidad("Paquete");
            prod17.setPrecioUnitario(new BigDecimal("120.00"));
            prod17.setStock(300);
            prod17.setStockMinimo(50);
            prod17.setProveedor(prov14);

            // Productos para prov15 - Office Depot Empresarial
            Producto prod18 = new Producto();
            prod18.setCodigo("OFI-005");
            prod18.setNombre("Silla Ejecutiva Ergonómica");
            prod18.setDescripcion("Silla de oficina con soporte lumbar, brazos ajustables");
            prod18.setCategoria("Mobiliario");
            prod18.setUnidad("Pieza");
            prod18.setPrecioUnitario(new BigDecimal("4500.00"));
            prod18.setStock(40);
            prod18.setStockMinimo(10);
            prod18.setProveedor(prov15);

            // Productos para prov16 - Distribuidora de Papel del Norte
            Producto prod19 = new Producto();
            prod19.setCodigo("OFI-006");
            prod19.setNombre("Papel Bond Oficio");
            prod19.setDescripcion("Resma de papel bond tamaño oficio 75g, 500 hojas");
            prod19.setCategoria("Oficina");
            prod19.setUnidad("Resma");
            prod19.setPrecioUnitario(new BigDecimal("115.00"));
            prod19.setStock(400);
            prod19.setStockMinimo(100);
            prod19.setProveedor(prov16);

            // Productos para prov17 - Papelera del Bajío
            Producto prod20 = new Producto();
            prod20.setCodigo("OFI-007");
            prod20.setNombre("Libretas Profesionales Pack");
            prod20.setDescripcion("Pack de 5 libretas profesionales pasta dura, 100 hojas c/u");
            prod20.setCategoria("Oficina");
            prod20.setUnidad("Pack");
            prod20.setPrecioUnitario(new BigDecimal("185.00"));
            prod20.setStock(150);
            prod20.setStockMinimo(30);
            prod20.setProveedor(prov17);

            List<Producto> productos = productoRepo.saveAll(
                Arrays.asList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8,
                              prod9, prod10, prod11, prod12, prod13, prod14, prod15,
                              prod16, prod17, prod18, prod19, prod20));
            System.out.println(">>> " + productos.size() + " productos creados");

            // ==================== SUPPLIER QUOTES (ProveedorProducto) ====================
            // Tornillos M8 - múltiples proveedores
            ProveedorProducto sq1 = createSupplierQuote(prov1, prod2, "3.50", 5000, 1, 2, "local",
                "USD", 100, "150", "2000", "Crédito 30 días,Transferencia,Tarjeta",
                "Mínimo 100 piezas,Garantía de calidad", "price", true, "Monterrey, NL", "4.8", 245, "pieza");

            ProveedorProducto sq2 = createSupplierQuote(prov6, prod2, "4.20", 3200, 2, 3, "web",
                "USD", 50, "0", "500", "Transferencia,Tarjeta,PayPal",
                "Mínimo 50 piezas,Envío gratis +$500", "rating", true, "Guadalajara, JAL", "4.9", 189, "pieza");

            ProveedorProducto sq3 = createSupplierQuote(prov7, prod2, "3.80", 8500, 1, 1, "local",
                "USD", 200, "200", null, "Crédito 15 días,Transferencia",
                "Mínimo 200 piezas,Entrega mismo día CDMX", "delivery", true, "Ciudad de México", "4.5", 312, "pieza");

            // Laptops HP - múltiples proveedores
            ProveedorProducto sq4 = createSupplierQuote(prov2, prod3, "18500", 45, 3, 5, "local",
                "USD", 1, "0", null, "Crédito 30 días,Transferencia,Tarjeta",
                "Garantía 3 años,Soporte técnico incluido", "price", true, "Ciudad de México", "4.7", 156, "pieza");

            ProveedorProducto sq5 = createSupplierQuote(prov8, prod3, "19200", 120, 1, 2, "web",
                "USD", 1, "0", null, "Crédito 45 días,Transferencia,Tarjeta,Leasing",
                "Garantía 3 años,Configuración incluida,Envío express gratis", "rating", true, "Monterrey, NL", "4.9", 423, "pieza");

            // Papel Bond - múltiples proveedores
            ProveedorProducto sq6 = createSupplierQuote(prov3, prod5, "89", 2500, 2, 4, "local",
                "USD", 10, "150", "3000", "Transferencia,Tarjeta",
                "Mínimo 10 resmas", "price", true, "Guadalajara, JAL", "4.4", 567, "resma");

            // Monitor Dell - múltiples proveedores
            ProveedorProducto sq7 = createSupplierQuote(prov2, prod4, "8900", 32, 2, 4, "local",
                "USD", 1, "0", null, "Crédito 30 días,Transferencia,Tarjeta",
                "Garantía 3 años Dell", "price", true, "Ciudad de México", "4.7", 156, "pieza");

            ProveedorProducto sq8 = createSupplierQuote(prov8, prod4, "9200", 58, 1, 2, "web",
                "USD", 1, "0", null, "Crédito 45 días,Transferencia,Tarjeta,Leasing",
                "Garantía 3 años,Calibración incluida", "rating", true, "Monterrey, NL", "4.9", 423, "pieza");

            // Tóner HP
            ProveedorProducto sq9 = createSupplierQuote(prov3, prod6, "1650", 150, 1, 2, "local",
                "USD", 2, "0", "2000", "Transferencia,Tarjeta",
                "Producto original HP,Garantía HP", "price", true, "Guadalajara, JAL", "4.4", 567, "pieza");

            // Químicos
            ProveedorProducto sq10 = createSupplierQuote(prov4, prod7, "650", 200, 2, 4, "local",
                "USD", 5, "300", "5000", "Crédito 30 días,Transferencia",
                "Ficha técnica incluida,Certificación ambiental", "price", true, "León, GTO", "4.4", 156, "cubeta");

            // ==================== MÁS LAPTOPS EMPRESARIALES ====================
            // Laptop HP - más proveedores locales
            ProveedorProducto sq11 = createSupplierQuote(prov9, prod3, "18800", 28, 2, 3, "local",
                "USD", 5, "350", "50000", "Crédito 60 días,Transferencia",
                "Mínimo 5 equipos,Garantía extendida disponible", "delivery", true, "Querétaro, QRO", "4.6", 89, "pieza");

            ProveedorProducto sq12 = createSupplierQuote(prov10, prod3, "18200", 65, 2, 4, "local",
                "USD", 1, "0", null, "Crédito 30 días,Transferencia,Tarjeta,MSI",
                "Garantía 3 años,Soporte en sitio,Configuración incluida", "price", true, "Ciudad de México", "4.8", 312, "pieza");

            ProveedorProducto sq13 = createSupplierQuote(prov11, prod3, "18650", 42, 3, 5, "local",
                "USD", 1, "200", "30000", "Crédito 45 días,Transferencia,Tarjeta",
                "Garantía 3 años,Software preinstalado", null, true, "Guadalajara, JAL", "4.5", 198, "pieza");

            ProveedorProducto sq14 = createSupplierQuote(prov12, prod3, "18400", 95, 1, 2, "local",
                "USD", 1, "0", null, "Crédito 30 días,Transferencia,Tarjeta,Leasing",
                "Garantía 3 años,Entrega express,Mesa de ayuda 24/7", "delivery", true, "Monterrey, NL", "4.7", 267, "pieza");

            // Laptop HP - proveedores web
            ProveedorProducto sq15 = createSupplierQuote(prov2, prod3, "17900", 150, 4, 6, "web",
                "USD", 10, "0", null, "Crédito 60 días,Transferencia,Tarjeta",
                "Mínimo 10 equipos,Precio especial por volumen,Garantía corporativa", "price", true, "Ciudad de México", "4.7", 156, "pieza");

            ProveedorProducto sq16 = createSupplierQuote(prov8, prod3, "19500", 200, 1, 2, "web",
                "USD", 1, "0", null, "Crédito 45 días,Transferencia,Tarjeta,PayPal,Leasing",
                "Garantía 5 años,Configuración remota,Soporte premium", "rating", true, "Monterrey, NL", "4.9", 423, "pieza");

            ProveedorProducto sq17 = createSupplierQuote(prov10, prod3, "18100", 80, 3, 5, "web",
                "USD", 3, "150", "40000", "Transferencia,Tarjeta,MSI",
                "Mínimo 3 equipos,Envío asegurado,Facturación inmediata", null, true, "Ciudad de México", "4.8", 312, "pieza");

            ProveedorProducto sq18 = createSupplierQuote(prov11, prod3, "18950", 55, 2, 3, "web",
                "USD", 1, "0", "25000", "Crédito 30 días,Transferencia,Tarjeta",
                "Garantía 3 años,Devolución 30 días,Envío gratis +$25,000", null, true, "Guadalajara, JAL", "4.5", 198, "pieza");

            // ==================== MÁS PAPEL BOND CARTA ====================
            // Papel Bond - más proveedores locales
            ProveedorProducto sq19 = createSupplierQuote(prov13, prod5, "92", 4500, 1, 2, "local",
                "USD", 5, "0", "1500", "Crédito 30 días,Transferencia,Tarjeta",
                "Mínimo 5 resmas,Envío gratis +$1,500,Certificación FSC", "rating", true, "Ciudad de México", "4.8", 456, "pieza");

            ProveedorProducto sq20 = createSupplierQuote(prov14, prod5, "85", 8000, 1, 1, "local",
                "USD", 20, "100", null, "Transferencia",
                "Mínimo 20 resmas,Entrega mismo día zona centro,Precio mayoreo", "price", false, "Puebla, PUE", "4.3", 178, "resma");

            ProveedorProducto sq21 = createSupplierQuote(prov15, prod5, "98", 3200, 1, 2, "local",
                "USD", 1, "0", "500", "Crédito 15 días,Transferencia,Tarjeta,MSI",
                "Sin mínimo,Envío gratis +$500,Puntos de recompensa", null, true, "Ciudad de México", "4.6", 892, "resma");

            ProveedorProducto sq22 = createSupplierQuote(prov16, prod5, "88", 6000, 2, 3, "local",
                "USD", 10, "150", "3000", "Crédito 30 días,Transferencia",
                "Mínimo 10 resmas,Precio especial norte del país", "price", true, "Monterrey, NL", "4.4", 234, "resma");

            ProveedorProducto sq23 = createSupplierQuote(prov17, prod5, "90", 5500, 2, 4, "local",
                "USD", 15, "200", "4000", "Crédito 45 días,Transferencia",
                "Mínimo 15 resmas,Descuento por volumen", null, true, "León, GTO", "4.2", 145, "resma");

            // Papel Bond - proveedores web
            ProveedorProducto sq24 = createSupplierQuote(prov3, prod5, "94", 10000, 3, 5, "web",
                "USD", 5, "0", "2000", "Transferencia,Tarjeta,PayPal",
                "Mínimo 5 resmas,Envío gratis +$2,000,Tracking en tiempo real", null, true, "Guadalajara, JAL", "4.4", 567, "resma");

            ProveedorProducto sq25 = createSupplierQuote(prov13, prod5, "96", 7500, 2, 3, "web",
                "USD", 3, "0", "1000", "Crédito 30 días,Transferencia,Tarjeta",
                "Mínimo 3 resmas,Suscripción disponible,Envío programado", "rating", true, "Ciudad de México", "4.8", 456, "resma");

            ProveedorProducto sq26 = createSupplierQuote(prov15, prod5, "99", 5000, 1, 2, "web",
                "USD", 1, "50", "800", "Transferencia,Tarjeta,MSI,PayPal",
                "Sin mínimo,Entrega express disponible,App móvil", "delivery", true, "Ciudad de México", "4.6", 892, "resma");

            ProveedorProducto sq27 = createSupplierQuote(prov14, prod5, "82", 12000, 4, 6, "web",
                "USD", 50, "0", null, "Transferencia",
                "Mínimo 50 resmas,Mejor precio del mercado,Solo mayoreo", "price", false, "Puebla, PUE", "4.3", 178, "resma");

            ProveedorProducto sq28 = createSupplierQuote(prov16, prod5, "91", 4000, 2, 4, "web",
                "USD", 8, "100", "2500", "Crédito 30 días,Transferencia,Tarjeta",
                "Mínimo 8 resmas,Facturación automática,Reorden programado", null, true, "Monterrey, NL", "4.4", 234, "resma");

            List<ProveedorProducto> quotes = proveedorProductoRepo.saveAll(
                Arrays.asList(sq1, sq2, sq3, sq4, sq5, sq6, sq7, sq8, sq9, sq10,
                              sq11, sq12, sq13, sq14, sq15, sq16, sq17, sq18,
                              sq19, sq20, sq21, sq22, sq23, sq24, sq25, sq26, sq27, sq28));
            System.out.println(">>> " + quotes.size() + " supplier quotes creados");

            // ==================== ÓRDENES DE COMPRA ====================
            OrdenCompra oc1 = new OrdenCompra();
            oc1.setNumero("OC-2024-001");
            oc1.setFecha(LocalDate.of(2024, 1, 15));
            oc1.setProveedor(prov1);
            oc1.setEstado("recibida");
            oc1.setSubtotal(new BigDecimal("45000.00"));
            oc1.setIva(new BigDecimal("7200.00"));
            oc1.setTotal(new BigDecimal("52200.00"));

            OrdenCompra oc2 = new OrdenCompra();
            oc2.setNumero("OC-2024-002");
            oc2.setFecha(LocalDate.of(2024, 1, 18));
            oc2.setProveedor(prov2);
            oc2.setEstado("aprobada");
            oc2.setSubtotal(new BigDecimal("92500.00"));
            oc2.setIva(new BigDecimal("14800.00"));
            oc2.setTotal(new BigDecimal("107300.00"));

            OrdenCompra oc3 = new OrdenCompra();
            oc3.setNumero("OC-2024-003");
            oc3.setFecha(LocalDate.of(2024, 1, 20));
            oc3.setProveedor(prov3);
            oc3.setEstado("pendiente");
            oc3.setSubtotal(new BigDecimal("12350.00"));
            oc3.setIva(new BigDecimal("1976.00"));
            oc3.setTotal(new BigDecimal("14326.00"));

            OrdenCompra oc4 = new OrdenCompra();
            oc4.setNumero("OC-2024-004");
            oc4.setFecha(LocalDate.of(2024, 1, 22));
            oc4.setProveedor(prov4);
            oc4.setEstado("borrador");
            oc4.setSubtotal(new BigDecimal("8900.00"));
            oc4.setIva(new BigDecimal("1424.00"));
            oc4.setTotal(new BigDecimal("10324.00"));

            OrdenCompra oc5 = new OrdenCompra();
            oc5.setNumero("OC-2024-005");
            oc5.setFecha(LocalDate.of(2024, 1, 25));
            oc5.setProveedor(prov1);
            oc5.setEstado("cancelada");
            oc5.setSubtotal(new BigDecimal("15000.00"));
            oc5.setIva(new BigDecimal("2400.00"));
            oc5.setTotal(new BigDecimal("17400.00"));

            List<OrdenCompra> ordenes = ordenCompraRepo.saveAll(
                Arrays.asList(oc1, oc2, oc3, oc4, oc5));
            System.out.println(">>> " + ordenes.size() + " órdenes de compra creadas");

            // ==================== RECEPCIONES ====================
            Recepcion rec1 = new Recepcion();
            rec1.setNumero("REC-2024-001");
            rec1.setFecha(LocalDate.of(2024, 1, 20));
            rec1.setOrdenCompra(oc1);
            rec1.setEstado("completa");
            rec1.setItemsRecibidos(5);
            rec1.setItemsTotales(5);

            Recepcion rec2 = new Recepcion();
            rec2.setNumero("REC-2024-002");
            rec2.setFecha(LocalDate.of(2024, 1, 25));
            rec2.setOrdenCompra(oc2);
            rec2.setEstado("parcial");
            rec2.setItemsRecibidos(5);
            rec2.setItemsTotales(8);

            List<Recepcion> recepciones = recepcionRepo.saveAll(Arrays.asList(rec1, rec2));
            System.out.println(">>> " + recepciones.size() + " recepciones creadas");

            System.out.println(">>> ¡Datos de prueba cargados exitosamente!");
        };
    }

    private ProveedorProducto createSupplierQuote(
            Proveedor proveedor, Producto producto, String precio, int stock,
            int diasMin, int diasMax, String sourceType, String currency,
            int minQty, String shippingCost, String freeShippingMin,
            String paymentTerms, String conditions, String highlight,
            boolean verified, String supplierCity, String rating, int reviewCount, String unit) {

        ProveedorProducto pp = new ProveedorProducto();
        pp.setProveedor(proveedor);
        pp.setProducto(producto);
        pp.setPrecio(new BigDecimal(precio));
        pp.setStock(stock);
        pp.setDiasEntregaMin(diasMin);
        pp.setDiasEntregaMax(diasMax);
        pp.setSourceType(sourceType);
        pp.setCurrency(currency);
        pp.setMinQuantity(minQty);
        pp.setShippingCost(new BigDecimal(shippingCost));
        pp.setFreeShippingMinimum(freeShippingMin != null ? new BigDecimal(freeShippingMin) : null);
        pp.setPaymentTerms(paymentTerms);
        pp.setConditions(conditions);
        pp.setHighlight(highlight);
        pp.setVerified(verified);
        pp.setSupplierCity(supplierCity);
        pp.setSupplierRating(new BigDecimal(rating));
        pp.setReviewCount(reviewCount);
        pp.setUnit(unit);
        return pp;
    }
}
