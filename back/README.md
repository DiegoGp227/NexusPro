# NexusPro Backend - API de Compras

API REST para el módulo de compras del ERP NexusPro.

## Requisitos

- Docker y Docker Compose
- O Java 17+ y Maven 3.9+

## Inicio Rápido

### Con Docker (Recomendado)

```bash
cd back
docker-compose up --build
```

### Sin Docker

```bash
cd back
./mvnw spring-boot:run
```

El servidor estará disponible en `http://localhost:8080`

---

## Endpoints API

### Base URL
```
http://localhost:8080/api
```

---

## 1. Proveedores (Suppliers)

### Listar todos los proveedores
```http
GET /api/suppliers
```

**Response:**
```json
[
  {
    "id": 1,
    "codigo": "PROV-001",
    "nombre": "Distribuidora Industrial del Norte S.A.",
    "rfc": "DIN850101ABC",
    "contacto": "Carlos Mendoza",
    "email": "ventas@dinorte.com",
    "telefono": "+52 81 1234 5678",
    "direccion": "Av. Industrial 1250",
    "ciudad": "Monterrey",
    "estado": "Nuevo León",
    "activo": true,
    "categoria": "Materiales",
    "rating": 4.8,
    "reviewCount": 245,
    "verified": true,
    "logo": null
  }
]
```

### Obtener proveedor por ID
```http
GET /api/suppliers/{id}
```

### Buscar proveedores
```http
GET /api/suppliers/search?query={texto}
```

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| query | string | Busca en nombre, código y contacto |

### Crear proveedor
```http
POST /api/suppliers
Content-Type: application/json
```

**Body:**
```json
{
  "codigo": "PROV-009",
  "nombre": "Nuevo Proveedor S.A.",
  "rfc": "NPR900101ABC",
  "contacto": "Juan Pérez",
  "email": "contacto@nuevoproveedor.com",
  "telefono": "+52 55 1234 5678",
  "direccion": "Calle Principal 100",
  "ciudad": "Ciudad de México",
  "estado": "CDMX",
  "activo": true,
  "categoria": "General"
}
```

### Actualizar proveedor
```http
PUT /api/suppliers/{id}
Content-Type: application/json
```

### Eliminar proveedor
```http
DELETE /api/suppliers/{id}
```

---

## 2. Productos (Products)

### Listar todos los productos
```http
GET /api/products
```

**Response:**
```json
[
  {
    "id": 1,
    "codigo": "MAT-001",
    "nombre": "Acero Inoxidable Calibre 18",
    "descripcion": "Lámina de acero inoxidable calibre 18, 4x8 pies",
    "categoria": "Materiales",
    "unidad": "Pieza",
    "precioUnitario": 2500.00,
    "stock": 45,
    "stockMinimo": 20,
    "proveedor": { "id": 1, "nombre": "..." }
  }
]
```

### Obtener producto por ID
```http
GET /api/products/{id}
```

### Buscar productos
```http
GET /api/products/search?query={texto}
```

### Productos por categoría
```http
GET /api/products/category/{categoria}
```

### Crear producto
```http
POST /api/products
Content-Type: application/json
```

**Body:**
```json
{
  "codigo": "PROD-001",
  "nombre": "Nuevo Producto",
  "descripcion": "Descripción del producto",
  "categoria": "General",
  "unidad": "Pieza",
  "precioUnitario": 100.00,
  "stock": 50,
  "stockMinimo": 10
}
```

### Actualizar producto
```http
PUT /api/products/{id}
Content-Type: application/json
```

### Eliminar producto
```http
DELETE /api/products/{id}
```

---

## 3. Búsqueda de Cotizaciones (Supplier Quotes)

### Buscar cotizaciones con filtros
```http
GET /api/suppliers/quotes/search
```

**Parámetros:**
| Parámetro | Tipo | Default | Descripción |
|-----------|------|---------|-------------|
| query | string | requerido | Texto de búsqueda |
| sourceType | string | "all" | "all", "local", "web" |
| category | string | "all" | Categoría del producto |
| minRating | number | - | Rating mínimo (1-5) |
| maxDeliveryDays | number | - | Días máximos de entrega |
| maxPrice | number | - | Precio máximo |
| verifiedOnly | boolean | false | Solo proveedores verificados |
| freeShippingOnly | boolean | false | Solo con envío gratis |

**Ejemplo:**
```http
GET /api/suppliers/quotes/search?query=laptop&sourceType=local&minRating=4.5&verifiedOnly=true
```

**Response:**
```json
[
  {
    "id": "1",
    "supplierId": "2",
    "supplierName": "TecnoEquipos Empresariales",
    "supplierLogo": null,
    "supplierCity": "Ciudad de México",
    "supplierRating": 4.7,
    "reviewCount": 156,
    "productName": "Laptop HP ProBook 450",
    "productDescription": "Laptop empresarial i5, 16GB RAM, 512GB SSD",
    "productCategory": "Tecnología",
    "unitPrice": 18500.00,
    "currency": "USD",
    "minQuantity": 1,
    "availableStock": 45,
    "unit": "pieza",
    "deliveryDays": {
      "min": 3,
      "max": 5
    },
    "shippingCost": 0,
    "freeShippingMinimum": null,
    "paymentTerms": ["Crédito 30 días", "Transferencia", "Tarjeta"],
    "conditions": ["Garantía 3 años", "Soporte técnico incluido"],
    "verified": true,
    "highlight": "price",
    "sourceType": "local"
  }
]
```

### Listar todas las cotizaciones
```http
GET /api/suppliers/quotes
```

---

## 4. Órdenes de Compra (Purchase Orders)

### Listar todas las órdenes
```http
GET /api/purchase-orders
```

**Response:**
```json
[
  {
    "id": "1",
    "numero": "OC-2024-001",
    "fecha": "2024-01-15",
    "proveedorId": "1",
    "proveedorNombre": "Distribuidora Industrial del Norte S.A.",
    "estado": "recibida",
    "subtotal": 45000.00,
    "iva": 7200.00,
    "total": 52200.00,
    "items": 5
  }
]
```

### Obtener orden por ID
```http
GET /api/purchase-orders/{id}
```

### Buscar órdenes
```http
GET /api/purchase-orders/search?query={texto}
```

### Órdenes por estado
```http
GET /api/purchase-orders/status/{estado}
```

**Estados válidos:** `borrador`, `pendiente`, `aprobada`, `recibida`, `cancelada`

### Crear orden de compra
```http
POST /api/purchase-orders
Content-Type: application/json
```

**Body:**
```json
{
  "proveedor": { "id": 1 },
  "estado": "borrador",
  "items": [
    {
      "producto": { "id": 1 },
      "cantidad": 10,
      "precioUnitario": 2500.00
    },
    {
      "producto": { "id": 2 },
      "cantidad": 5,
      "precioUnitario": 350.00
    }
  ]
}
```

**Nota:** El número, fecha, subtotal, IVA (16%) y total se calculan automáticamente.

### Actualizar orden
```http
PUT /api/purchase-orders/{id}
Content-Type: application/json
```

### Cancelar/Eliminar orden
```http
DELETE /api/purchase-orders/{id}
```

---

## 5. Recepciones (Receptions)

### Listar todas las recepciones
```http
GET /api/receptions
```

**Response:**
```json
[
  {
    "id": "1",
    "numero": "REC-2024-001",
    "fecha": "2024-01-20",
    "ordenCompraId": "1",
    "ordenCompraNumero": "OC-2024-001",
    "proveedorNombre": "Distribuidora Industrial del Norte S.A.",
    "estado": "completa",
    "itemsRecibidos": 5,
    "itemsTotales": 5
  }
]
```

### Obtener recepción por ID
```http
GET /api/receptions/{id}
```

### Buscar recepciones
```http
GET /api/receptions/search?query={texto}
```

### Recepciones por estado
```http
GET /api/receptions/status/{estado}
```

**Estados válidos:** `pendiente`, `parcial`, `completa`

### Recepciones por orden de compra
```http
GET /api/receptions/order/{ordenCompraId}
```

### Crear recepción
```http
POST /api/receptions
Content-Type: application/json
```

**Body:**
```json
{
  "ordenCompra": { "id": 1 },
  "itemsRecibidos": 3,
  "itemsTotales": 5
}
```

**Nota:** El número, fecha y estado se calculan automáticamente.

### Actualizar recepción
```http
PUT /api/receptions/{id}
Content-Type: application/json
```

---

## 6. Catálogo (Legacy)

### Buscar en catálogo
```http
GET /api/catalogo/buscar?nombre={texto}
```

### Listar catálogo completo
```http
GET /api/catalogo
```

---

## Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 204 | No Content - Eliminación exitosa |
| 400 | Bad Request - Datos inválidos |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error |

---

## CORS

El backend permite solicitudes desde:
- `http://localhost:3000`
- `http://127.0.0.1:3000`

---

## Base de Datos

### Desarrollo (H2 In-Memory)
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:comprasdb`
- **Usuario:** `sa`
- **Contraseña:** *(vacía)*

### Producción (MySQL)
Configurar variables de entorno:
```bash
DB_URL=jdbc:mysql://localhost:3306/compras
DB_USER=root
DB_PASSWORD=tu_password
```

---

## Estructura del Proyecto

```
back/
├── src/main/java/com/erp/compras/
│   ├── config/
│   │   ├── CorsConfig.java
│   │   └── DataSeeder.java
│   ├── controller/
│   │   ├── ProveedorController.java
│   │   ├── ProductoController.java
│   │   ├── SupplierQuoteController.java
│   │   ├── OrdenCompraController.java
│   │   └── RecepcionController.java
│   ├── dto/
│   │   ├── SupplierQuoteDTO.java
│   │   ├── OrdenCompraDTO.java
│   │   └── RecepcionDTO.java
│   ├── model/
│   │   ├── Proveedor.java
│   │   ├── Producto.java
│   │   ├── ProveedorProducto.java
│   │   ├── OrdenCompra.java
│   │   ├── OrdenCompraItem.java
│   │   └── Recepcion.java
│   ├── repository/
│   │   └── I*Repository.java
│   ├── service/
│   │   └── *Service.java
│   └── ComprasApplication.java
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## Ejemplos con cURL

### Crear proveedor
```bash
curl -X POST http://localhost:8080/api/suppliers \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "PROV-010",
    "nombre": "Test Proveedor",
    "rfc": "TEST123456",
    "email": "test@test.com",
    "activo": true
  }'
```

### Buscar cotizaciones de tornillos
```bash
curl "http://localhost:8080/api/suppliers/quotes/search?query=tornillos&verifiedOnly=true"
```

### Crear orden de compra
```bash
curl -X POST http://localhost:8080/api/purchase-orders \
  -H "Content-Type: application/json" \
  -d '{
    "proveedor": { "id": 1 },
    "items": [
      { "producto": { "id": 1 }, "cantidad": 10, "precioUnitario": 2500 }
    ]
  }'
```

---

## Integración con Frontend

El frontend en `http://localhost:3000` puede consumir esta API directamente gracias a la configuración CORS.

Ejemplo en TypeScript/React:
```typescript
// Buscar cotizaciones
const response = await fetch(
  'http://localhost:8080/api/suppliers/quotes/search?query=laptop'
);
const quotes: SupplierQuote[] = await response.json();

// Crear orden de compra
const order = await fetch('http://localhost:8080/api/purchase-orders', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    proveedor: { id: 1 },
    items: [{ producto: { id: 1 }, cantidad: 10, precioUnitario: 2500 }]
  })
});
```
