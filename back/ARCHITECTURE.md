# Arquitectura del Backend - NexusPro Compras

## Visión General

El backend sigue una **arquitectura en capas** (Layered Architecture) típica de aplicaciones Spring Boot empresariales.

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENTE (Frontend)                      │
│                    http://localhost:3000                     │
└─────────────────────────┬───────────────────────────────────┘
                          │ HTTP/REST
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE CONTROLADORES                     │
│  (Controllers - Reciben peticiones HTTP y devuelven JSON)   │
│                                                              │
│  ProveedorController    ProductoController                  │
│  OrdenCompraController  RecepcionController                 │
│  SupplierQuoteController                                    │
└─────────────────────────┬───────────────────────────────────┘
                          │ Llamadas a métodos
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE SERVICIOS                         │
│    (Services - Lógica de negocio y reglas del sistema)      │
│                                                              │
│  ProveedorService       ProductoService                     │
│  OrdenCompraService     RecepcionService                    │
│  ProveedorProductoService                                   │
└─────────────────────────┬───────────────────────────────────┘
                          │ Operaciones CRUD
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE REPOSITORIOS                       │
│     (Repositories - Acceso a datos con Spring Data JPA)     │
│                                                              │
│  IProveedorRepository   IProductoRepository                 │
│  IOrdenCompraRepository IRecepcionRepository                │
│  IProveedorProductoRepository                               │
└─────────────────────────┬───────────────────────────────────┘
                          │ SQL/JPQL
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                    BASE DE DATOS                             │
│              H2 (desarrollo) / MySQL (producción)           │
└─────────────────────────────────────────────────────────────┘
```

---

## Estructura de Carpetas

```
back/src/main/java/com/erp/compras/
│
├── ComprasApplication.java      # Punto de entrada de la aplicación
│
├── config/                      # Configuraciones
│   ├── CorsConfig.java         # Permite peticiones del frontend
│   └── DataSeeder.java         # Carga datos de prueba al iniciar
│
├── controller/                  # Controladores REST
│   ├── ProveedorController.java
│   ├── ProductoController.java
│   ├── SupplierQuoteController.java
│   ├── OrdenCompraController.java
│   ├── RecepcionController.java
│   ├── ProveedorProductoController.java
│   └── PedidoController.java
│
├── service/                     # Lógica de negocio
│   ├── IProveedorService.java          # Interface
│   ├── ProveedorService.java           # Implementación
│   ├── IProductoService.java
│   ├── ProductoService.java
│   ├── IOrdenCompraService.java
│   ├── OrdenCompraService.java
│   ├── IRecepcionService.java
│   ├── RecepcionService.java
│   ├── IProveedorProductoService.java
│   └── ProveedorProductoService.java
│
├── repository/                  # Acceso a datos
│   ├── IProveedorRepository.java
│   ├── IProductoRepository.java
│   ├── IOrdenCompraRepository.java
│   ├── IRecepcionRepository.java
│   └── IProveedorProductoRepository.java
│
├── model/                       # Entidades JPA (tablas)
│   ├── Proveedor.java
│   ├── Producto.java
│   ├── ProveedorProducto.java   # Cotizaciones de proveedores
│   ├── OrdenCompra.java
│   ├── OrdenCompraItem.java
│   ├── Recepcion.java
│   └── Pedido.java
│
└── dto/                         # Data Transfer Objects
    ├── SupplierQuoteDTO.java
    ├── OrdenCompraDTO.java
    ├── RecepcionDTO.java
    ├── ProveedorDTO.java
    └── ProductoDTO.java
```

---

## Explicación de Cada Capa

### 1. **Controllers** (Controladores)

**¿Qué hacen?**
- Reciben peticiones HTTP (GET, POST, PUT, DELETE)
- Validan datos de entrada básicos
- Llaman a los Services
- Devuelven respuestas JSON

**Ejemplo:** `ProveedorController.java`
```java
@RestController
@RequestMapping("/api/suppliers")  // Ruta base
public class ProveedorController {

    @Autowired
    private IProveedorService serv;  // Inyección del servicio

    @GetMapping  // GET /api/suppliers
    public ResponseEntity<List<Proveedor>> getAll() {
        return ResponseEntity.ok(serv.getProveedores());
    }

    @PostMapping  // POST /api/suppliers
    public ResponseEntity<?> create(@RequestBody Proveedor proveedor) {
        Proveedor saved = serv.saveProveedor(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
```

**Anotaciones clave:**
| Anotación | Significado |
|-----------|-------------|
| `@RestController` | Esta clase maneja peticiones REST |
| `@RequestMapping` | Ruta base para todos los endpoints |
| `@GetMapping` | Responde a peticiones GET |
| `@PostMapping` | Responde a peticiones POST |
| `@PathVariable` | Captura variables de la URL (`/api/suppliers/{id}`) |
| `@RequestParam` | Captura parámetros de query (`?query=texto`) |
| `@RequestBody` | Convierte el JSON del body a objeto Java |

---

### 2. **Services** (Servicios)

**¿Qué hacen?**
- Contienen la **lógica de negocio**
- Validan reglas del sistema
- Coordinan operaciones entre múltiples repositorios
- Son reutilizables por diferentes controllers

**Ejemplo:** `OrdenCompraService.java`
```java
@Service  // Marca como servicio de Spring
public class OrdenCompraService implements IOrdenCompraService {

    @Autowired
    private IOrdenCompraRepository repo;

    private static final BigDecimal IVA_RATE = new BigDecimal("0.16");

    @Override
    public OrdenCompra save(OrdenCompra orden) {
        // LÓGICA DE NEGOCIO: Calcular totales automáticamente
        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrdenCompraItem item : orden.getItems()) {
            BigDecimal itemTotal = item.getPrecioUnitario()
                .multiply(new BigDecimal(item.getCantidad()));
            item.setSubtotal(itemTotal);
            subtotal = subtotal.add(itemTotal);
        }

        orden.setSubtotal(subtotal);
        orden.setIva(subtotal.multiply(IVA_RATE));  // 16% IVA
        orden.setTotal(subtotal.add(orden.getIva()));

        return repo.save(orden);
    }
}
```

**¿Por qué usar interfaces?**
```java
public interface IOrdenCompraService {
    OrdenCompra save(OrdenCompra orden);
    List<OrdenCompra> getAll();
}
```
- Permite cambiar implementaciones fácilmente
- Facilita testing con mocks
- Desacopla el código

---

### 3. **Repositories** (Repositorios)

**¿Qué hacen?**
- Acceden a la base de datos
- Spring Data JPA genera las implementaciones automáticamente
- Solo defines la interface con métodos de consulta

**Ejemplo:** `IProveedorProductoRepository.java`
```java
@Repository
public interface IProveedorProductoRepository
    extends JpaRepository<ProveedorProducto, Long> {

    // Spring genera automáticamente el SQL basándose en el nombre del método
    List<ProveedorProducto> findByProductoNombreContainingIgnoreCase(String nombre);

    List<ProveedorProducto> findByVerified(Boolean verified);

    // Para consultas complejas, usamos @Query con JPQL
    @Query("SELECT pp FROM ProveedorProducto pp WHERE " +
           "LOWER(pp.producto.nombre) LIKE LOWER(CONCAT('%', :query, '%')) AND " +
           "(:sourceType IS NULL OR pp.sourceType = :sourceType)")
    List<ProveedorProducto> searchWithFilters(
        @Param("query") String query,
        @Param("sourceType") String sourceType);
}
```

**Métodos automáticos de JpaRepository:**
| Método | Descripción |
|--------|-------------|
| `findAll()` | Obtener todos los registros |
| `findById(id)` | Buscar por ID |
| `save(entity)` | Guardar o actualizar |
| `deleteById(id)` | Eliminar por ID |
| `count()` | Contar registros |

**Convención de nombres para queries:**
| Nombre del método | SQL generado |
|-------------------|--------------|
| `findByNombre(String n)` | `WHERE nombre = n` |
| `findByNombreContaining(String n)` | `WHERE nombre LIKE '%n%'` |
| `findByNombreIgnoreCase(String n)` | `WHERE LOWER(nombre) = LOWER(n)` |
| `findByActivoTrue()` | `WHERE activo = true` |
| `findByPrecioGreaterThan(BigDecimal p)` | `WHERE precio > p` |

---

### 4. **Models** (Entidades)

**¿Qué hacen?**
- Representan las tablas de la base de datos
- Definen campos, tipos y relaciones
- JPA/Hibernate genera las tablas automáticamente

**Ejemplo:** `Proveedor.java`
```java
@Entity                           // Esta clase es una tabla
@Table(name = "proveedores")      // Nombre de la tabla
public class Proveedor {

    @Id                                              // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment
    private Long id;

    @NotBlank(message = "El código es obligatorio")  // Validación
    @Column(unique = true)                           // Único en BD
    private String codigo;

    @Email                        // Validación de formato email
    private String email;

    @OneToMany(mappedBy = "proveedor")  // Relación 1 a muchos
    @JsonIgnore                         // No serializar en JSON (evita loops)
    private List<ProveedorProducto> productos;
}
```

**Relaciones entre entidades:**

```
┌─────────────┐       ┌──────────────────┐       ┌─────────────┐
│  Proveedor  │──1:N──│ ProveedorProducto│──N:1──│  Producto   │
└─────────────┘       │ (Cotizaciones)   │       └─────────────┘
                      └──────────────────┘
       │                                                │
       │                                                │
      1:N                                              N:1
       │                                                │
       ▼                                                ▼
┌─────────────┐       ┌──────────────────┐       ┌─────────────┐
│ OrdenCompra │──1:N──│ OrdenCompraItem  │──N:1──│  Producto   │
└─────────────┘       └──────────────────┘       └─────────────┘
       │
      1:N
       │
       ▼
┌─────────────┐
│  Recepcion  │
└─────────────┘
```

---

### 5. **DTOs** (Data Transfer Objects)

**¿Qué hacen?**
- Transforman datos para el frontend
- Evitan exponer la estructura interna de las entidades
- Permiten combinar datos de múltiples entidades

**Ejemplo:** `SupplierQuoteDTO.java`
```java
public class SupplierQuoteDTO {
    private String id;
    private String supplierId;
    private String supplierName;      // Viene de Proveedor
    private String productName;        // Viene de Producto
    private BigDecimal unitPrice;      // Viene de ProveedorProducto
    private List<String> paymentTerms; // Parseado de String a List

    // El Controller convierte ProveedorProducto → SupplierQuoteDTO
}
```

**¿Por qué usar DTOs?**
1. **Seguridad**: No expones campos internos (IDs reales, passwords)
2. **Flexibilidad**: El frontend recibe exactamente lo que necesita
3. **Desacoplamiento**: Cambios en el modelo no afectan la API

---

### 6. **Config** (Configuraciones)

#### `CorsConfig.java`
Permite que el frontend (localhost:3000) haga peticiones al backend (localhost:8080).

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

#### `DataSeeder.java`
Carga datos de prueba al iniciar la aplicación (solo en perfil "dev").

```java
@Configuration
public class DataSeeder {
    @Bean
    @Profile("dev")  // Solo ejecuta en desarrollo
    CommandLineRunner initDatabase(IProveedorRepository repo) {
        return args -> {
            if (repo.count() == 0) {  // Solo si está vacía
                // Crear datos de prueba...
            }
        };
    }
}
```

---

## Flujo de una Petición

**Ejemplo: Buscar cotizaciones de laptops**

```
1. Frontend hace petición:
   GET http://localhost:8080/api/suppliers/quotes/search?query=laptop

2. SupplierQuoteController recibe la petición:
   @GetMapping("/search")
   public ResponseEntity<List<SupplierQuoteDTO>> search(@RequestParam String query)

3. Controller llama al Service:
   List<ProveedorProducto> results = serv.searchWithFilters(query, ...);

4. Service llama al Repository:
   return repo.searchWithFilters(query, ...);

5. Repository ejecuta la query SQL:
   SELECT * FROM proveedor_producto pp
   JOIN productos p ON pp.producto_id = p.id
   WHERE LOWER(p.nombre) LIKE '%laptop%'

6. Repository devuelve List<ProveedorProducto>

7. Service devuelve la lista al Controller

8. Controller convierte a DTOs:
   List<SupplierQuoteDTO> dtos = results.stream()
       .map(this::toDTO)
       .collect(Collectors.toList());

9. Controller devuelve JSON:
   return ResponseEntity.ok(dtos);

10. Frontend recibe:
    [{ "id": "1", "supplierName": "...", "productName": "Laptop HP...", ... }]
```

---

## Tecnologías Utilizadas

| Tecnología | Propósito |
|------------|-----------|
| **Spring Boot 3.2** | Framework principal |
| **Spring Data JPA** | Acceso a datos |
| **Hibernate** | ORM (mapeo objeto-relacional) |
| **H2 Database** | Base de datos en memoria (desarrollo) |
| **MySQL** | Base de datos (producción) |
| **Lombok** | Reduce código boilerplate (getters, setters) |
| **Jakarta Validation** | Validación de datos |
| **Docker** | Contenedorización |

---

## Anotaciones Importantes

| Anotación | Capa | Propósito |
|-----------|------|-----------|
| `@SpringBootApplication` | Main | Punto de entrada |
| `@Configuration` | Config | Clase de configuración |
| `@Bean` | Config | Define un bean de Spring |
| `@RestController` | Controller | Controlador REST |
| `@Service` | Service | Servicio de negocio |
| `@Repository` | Repository | Acceso a datos |
| `@Entity` | Model | Entidad JPA |
| `@Autowired` | Todas | Inyección de dependencias |
