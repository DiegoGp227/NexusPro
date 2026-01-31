export const BaseURL =
  process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080/api";

// Auth
export const SignUpURL = new URL("signup", BaseURL);
export const LoginURL = new URL("login", BaseURL);

// Categories
export const CategoriesURL = new URL("categories", BaseURL);

// Suppliers (Proveedores)
export const SuppliersURL = `${BaseURL}/suppliers`;
export const SuppliersSearchURL = `${BaseURL}/suppliers/search`;
export const SuppliersQuotesURL = `${BaseURL}/suppliers/quotes`;
export const SuppliersQuotesSearchURL = `${BaseURL}/suppliers/quotes/search`;

// Products (Productos)
export const ProductsURL = `${BaseURL}/products`;
export const ProductsSearchURL = `${BaseURL}/products/search`;
export const ProductsCategoryURL = `${BaseURL}/products/category`;

// Purchase Orders (Órdenes de Compra)
export const PurchaseOrdersURL = `${BaseURL}/purchase-orders`;
export const PurchaseOrdersSearchURL = `${BaseURL}/purchase-orders/search`;
export const PurchaseOrdersStatusURL = `${BaseURL}/purchase-orders/status`;

// Receptions (Recepciones)
export const ReceptionsURL = `${BaseURL}/receptions`;
export const ReceptionsSearchURL = `${BaseURL}/receptions/search`;
export const ReceptionsStatusURL = `${BaseURL}/receptions/status`;

// Catalogo (Legacy)
export const CatalogoURL = `${BaseURL}/catalogo`;
export const CatalogoBuscarURL = `${BaseURL}/catalogo/buscar`;
