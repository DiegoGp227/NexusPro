"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Icon, Button, Title, Text } from "@/components/atoms";

interface OrderData {
  product: {
    id: string;
    name: string;
    description: string;
    unitPrice: number;
    unit: string;
    supplierName: string;
    deliveryDays: { min: number; max: number };
  };
  quantity: number;
  deliveryAddress: string;
  contactName: string;
  contactPhone: string;
  paymentMethod: string;
  subtotal: number;
  shipping: number;
  total: number;
  orderNumber: string;
  createdAt: string;
}

const banks = [
  { id: "bancolombia", name: "Bancolombia" },
  { id: "davivienda", name: "Davivienda" },
  { id: "bbva", name: "BBVA Colombia" },
  { id: "banco-bogota", name: "Banco de Bogotá" },
  { id: "banco-occidente", name: "Banco de Occidente" },
  { id: "banco-popular", name: "Banco Popular" },
  { id: "banco-avvillas", name: "Banco AV Villas" },
  { id: "scotiabank", name: "Scotiabank Colpatria" },
  { id: "itau", name: "Banco Itaú" },
  { id: "gnb-sudameris", name: "GNB Sudameris" },
  { id: "banco-agrario", name: "Banco Agrario" },
  { id: "bancoomeva", name: "Bancoomeva" },
  { id: "nequi", name: "Nequi" },
  { id: "daviplata", name: "Daviplata" },
];

type PaymentStep = "select-bank" | "processing" | "success" | "error";

export default function CheckoutPage() {
  const router = useRouter();
  const [orderData, setOrderData] = useState<OrderData | null>(null);
  const [selectedBank, setSelectedBank] = useState("");
  const [personType, setPersonType] = useState<"natural" | "juridica">("natural");
  const [step, setStep] = useState<PaymentStep>("select-bank");
  const [acceptTerms, setAcceptTerms] = useState(false);

  useEffect(() => {
    const stored = localStorage.getItem("nexuspro_order");
    if (stored) {
      setOrderData(JSON.parse(stored));
    } else {
      router.push("/compras");
    }
  }, [router]);

  const handlePayment = () => {
    if (!selectedBank || !acceptTerms) return;

    setStep("processing");

    // Simular procesamiento de pago
    setTimeout(() => {
      // 90% de probabilidad de éxito
      const success = Math.random() > 0.1;

      if (success) {
        const transactionId = `PSE-${Date.now()}`;
        const paidAt = new Date().toISOString();

        // Guardar transacción exitosa y agregar orden al historial
        if (orderData) {
          const transaction = {
            ...orderData,
            paymentStatus: "completed",
            bank: banks.find(b => b.id === selectedBank)?.name,
            personType,
            transactionId,
            paidAt,
          };
          localStorage.setItem("nexuspro_last_transaction", JSON.stringify(transaction));

          // Agregar orden al historial de órdenes
          const newOrder = {
            id: `oc-user-${Date.now()}`,
            numero: orderData.orderNumber,
            fecha: new Date().toISOString().split('T')[0],
            proveedorId: orderData.product.id,
            proveedorNombre: orderData.product.supplierName,
            estado: "pendiente" as const,
            subtotal: orderData.subtotal,
            iva: orderData.subtotal * 0.19, // 19% IVA
            total: orderData.total,
            items: 1,
            producto: orderData.product.name,
            cantidad: orderData.quantity,
            transactionId,
            isUserOrder: true,
          };

          // Obtener órdenes existentes y agregar la nueva
          const existingOrders = JSON.parse(localStorage.getItem("nexuspro_user_orders") || "[]");
          existingOrders.unshift(newOrder);
          localStorage.setItem("nexuspro_user_orders", JSON.stringify(existingOrders));
        }

        setStep("success");
      } else {
        setStep("error");
      }
    }, 3000);
  };

  const handleRetry = () => {
    setStep("select-bank");
    setSelectedBank("");
    setAcceptTerms(false);
  };

  const handleGoToOrders = () => {
    localStorage.removeItem("nexuspro_order");
    router.push("/compras");
  };

  if (!orderData) {
    return (
      <div className="min-h-screen bg-slate-100 dark:bg-slate-900 flex items-center justify-center">
        <div className="animate-spin w-8 h-8 border-4 border-blue-600 border-t-transparent rounded-full"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-100 dark:bg-slate-900">
      {/* Header PSE */}
      <header className="bg-white dark:bg-slate-800 border-b border-slate-200 dark:border-slate-700">
        <div className="max-w-4xl mx-auto px-4 py-4 flex items-center justify-between">
          <div className="flex items-center gap-3">
            {/* Logo PSE */}
            <div className="bg-[#00529B] text-white font-bold text-xl px-3 py-1 rounded">
              PSE
            </div>
            <div>
              <p className="text-sm font-semibold text-slate-900 dark:text-white">Pagos Seguros en Línea</p>
              <p className="text-xs text-slate-500 dark:text-slate-400">ACH Colombia</p>
            </div>
          </div>
          <div className="flex items-center gap-2 text-xs text-slate-500 dark:text-slate-400">
            <Icon name="clock" size="sm" />
            <span>Sesión segura</span>
          </div>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-4 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Panel principal */}
          <div className="lg:col-span-2">
            <div className="bg-white dark:bg-slate-800 rounded-xl shadow-sm border border-slate-200 dark:border-slate-700 overflow-hidden">

              {/* Step: Select Bank */}
              {step === "select-bank" && (
                <>
                  <div className="p-6 border-b border-slate-200 dark:border-slate-700">
                    <h2 className="text-lg font-semibold text-slate-900 dark:text-white mb-1">
                      Selecciona tu banco
                    </h2>
                    <p className="text-sm text-slate-500 dark:text-slate-400">
                      Serás redirigido al portal de tu banco para completar el pago
                    </p>
                  </div>

                  <div className="p-6 space-y-6">
                    {/* Tipo de persona */}
                    <div>
                      <label className="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                        Tipo de persona
                      </label>
                      <div className="flex gap-4">
                        <label className="flex items-center gap-2 cursor-pointer">
                          <input
                            type="radio"
                            name="personType"
                            checked={personType === "natural"}
                            onChange={() => setPersonType("natural")}
                            className="w-4 h-4 text-blue-600"
                          />
                          <span className="text-sm text-slate-700 dark:text-slate-300">Persona Natural</span>
                        </label>
                        <label className="flex items-center gap-2 cursor-pointer">
                          <input
                            type="radio"
                            name="personType"
                            checked={personType === "juridica"}
                            onChange={() => setPersonType("juridica")}
                            className="w-4 h-4 text-blue-600"
                          />
                          <span className="text-sm text-slate-700 dark:text-slate-300">Persona Jurídica</span>
                        </label>
                      </div>
                    </div>

                    {/* Selector de banco */}
                    <div>
                      <label className="block text-sm font-medium text-slate-700 dark:text-slate-300 mb-2">
                        Banco
                      </label>
                      <select
                        value={selectedBank}
                        onChange={(e) => setSelectedBank(e.target.value)}
                        className="block w-full px-4 py-3 text-sm text-slate-900 dark:text-white bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-700 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500/50"
                      >
                        <option value="">Selecciona tu banco</option>
                        {banks.map((bank) => (
                          <option key={bank.id} value={bank.id}>
                            {bank.name}
                          </option>
                        ))}
                      </select>
                    </div>

                    {/* Términos */}
                    <label className="flex items-start gap-3 cursor-pointer">
                      <input
                        type="checkbox"
                        checked={acceptTerms}
                        onChange={(e) => setAcceptTerms(e.target.checked)}
                        className="w-4 h-4 mt-0.5 text-blue-600 border-slate-300 rounded focus:ring-blue-500"
                      />
                      <span className="text-xs text-slate-600 dark:text-slate-400">
                        Autorizo el débito de mi cuenta y acepto los términos y condiciones del servicio PSE.
                        La transacción será procesada por ACH Colombia.
                      </span>
                    </label>

                    {/* Botón de pago */}
                    <Button
                      variant="primary"
                      size="lg"
                      onClick={handlePayment}
                      disabled={!selectedBank || !acceptTerms}
                      className="w-full"
                    >
                      Pagar ${orderData.total.toLocaleString()} USD
                    </Button>

                    {/* Info de seguridad */}
                    <div className="flex items-center justify-center gap-2 text-xs text-slate-500 dark:text-slate-400">
                      <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                      </svg>
                      <span>Conexión segura SSL 256-bit</span>
                    </div>
                  </div>
                </>
              )}

              {/* Step: Processing */}
              {step === "processing" && (
                <div className="p-12 text-center">
                  <div className="w-16 h-16 border-4 border-blue-600 border-t-transparent rounded-full animate-spin mx-auto mb-6"></div>
                  <h2 className="text-xl font-semibold text-slate-900 dark:text-white mb-2">
                    Procesando tu pago
                  </h2>
                  <p className="text-slate-500 dark:text-slate-400 mb-4">
                    Conectando con {banks.find(b => b.id === selectedBank)?.name}...
                  </p>
                  <p className="text-xs text-slate-400">
                    Por favor no cierres esta ventana
                  </p>
                </div>
              )}

              {/* Step: Success */}
              {step === "success" && (
                <div className="p-8 text-center">
                  <div className="w-20 h-20 bg-green-100 dark:bg-green-900/30 rounded-full flex items-center justify-center mx-auto mb-6">
                    <Icon name="check-circle" size="xl" className="text-green-600 dark:text-green-400 w-10 h-10" />
                  </div>
                  <h2 className="text-2xl font-semibold text-slate-900 dark:text-white mb-2">
                    ¡Pago exitoso!
                  </h2>
                  <p className="text-slate-600 dark:text-slate-400 mb-6">
                    Tu transacción ha sido procesada correctamente
                  </p>

                  <div className="bg-slate-50 dark:bg-slate-900 rounded-xl p-6 text-left mb-6 space-y-3">
                    <div className="flex justify-between">
                      <span className="text-sm text-slate-500 dark:text-slate-400">ID Transacción</span>
                      <span className="text-sm font-mono font-medium text-slate-900 dark:text-white">
                        PSE-{Date.now()}
                      </span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-sm text-slate-500 dark:text-slate-400">Orden</span>
                      <span className="text-sm font-medium text-slate-900 dark:text-white">
                        {orderData.orderNumber}
                      </span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-sm text-slate-500 dark:text-slate-400">Banco</span>
                      <span className="text-sm font-medium text-slate-900 dark:text-white">
                        {banks.find(b => b.id === selectedBank)?.name}
                      </span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-sm text-slate-500 dark:text-slate-400">Monto pagado</span>
                      <span className="text-sm font-bold text-green-600 dark:text-green-400">
                        ${orderData.total.toLocaleString()} USD
                      </span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-sm text-slate-500 dark:text-slate-400">Fecha</span>
                      <span className="text-sm font-medium text-slate-900 dark:text-white">
                        {new Date().toLocaleString()}
                      </span>
                    </div>
                  </div>

                  <div className="bg-blue-50 dark:bg-blue-900/20 rounded-lg p-4 mb-6">
                    <p className="text-sm text-blue-800 dark:text-blue-300">
                      Recibirás un correo de confirmación con los detalles de tu pedido.
                      El proveedor procesará tu orden en las próximas horas.
                    </p>
                  </div>

                  <Button variant="primary" onClick={handleGoToOrders} className="w-full">
                    Volver a Compras
                  </Button>
                </div>
              )}

              {/* Step: Error */}
              {step === "error" && (
                <div className="p-8 text-center">
                  <div className="w-20 h-20 bg-red-100 dark:bg-red-900/30 rounded-full flex items-center justify-center mx-auto mb-6">
                    <Icon name="x-mark" size="xl" className="text-red-600 dark:text-red-400 w-10 h-10" />
                  </div>
                  <h2 className="text-2xl font-semibold text-slate-900 dark:text-white mb-2">
                    Pago no procesado
                  </h2>
                  <p className="text-slate-600 dark:text-slate-400 mb-6">
                    No pudimos completar tu transacción. Por favor intenta nuevamente.
                  </p>

                  <div className="bg-red-50 dark:bg-red-900/20 rounded-lg p-4 mb-6">
                    <p className="text-sm text-red-800 dark:text-red-300">
                      Posibles causas: fondos insuficientes, límite de transacción excedido,
                      o problemas de conexión con el banco.
                    </p>
                  </div>

                  <div className="flex gap-3">
                    <Button variant="secondary" onClick={handleGoToOrders} className="flex-1">
                      Cancelar
                    </Button>
                    <Button variant="primary" onClick={handleRetry} className="flex-1">
                      Intentar de nuevo
                    </Button>
                  </div>
                </div>
              )}
            </div>
          </div>

          {/* Resumen del pedido */}
          <div className="lg:col-span-1">
            <div className="bg-white dark:bg-slate-800 rounded-xl shadow-sm border border-slate-200 dark:border-slate-700 p-6 sticky top-6">
              <h3 className="font-semibold text-slate-900 dark:text-white mb-4">
                Resumen del pedido
              </h3>

              <div className="space-y-4">
                <div className="pb-4 border-b border-slate-200 dark:border-slate-700">
                  <p className="font-medium text-slate-900 dark:text-white text-sm">
                    {orderData.product.name}
                  </p>
                  <p className="text-xs text-slate-500 dark:text-slate-400 mt-1">
                    {orderData.product.supplierName}
                  </p>
                  <div className="flex justify-between mt-2 text-sm">
                    <span className="text-slate-500 dark:text-slate-400">
                      {orderData.quantity} {orderData.product.unit}
                    </span>
                    <span className="text-slate-900 dark:text-white">
                      ${orderData.product.unitPrice} c/u
                    </span>
                  </div>
                </div>

                <div className="space-y-2 text-sm">
                  <div className="flex justify-between">
                    <span className="text-slate-500 dark:text-slate-400">Subtotal</span>
                    <span className="text-slate-900 dark:text-white">${orderData.subtotal.toLocaleString()}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-slate-500 dark:text-slate-400">Envío</span>
                    <span className="text-slate-900 dark:text-white">
                      {orderData.shipping === 0 ? (
                        <span className="text-green-600 dark:text-green-400">Gratis</span>
                      ) : (
                        `$${orderData.shipping}`
                      )}
                    </span>
                  </div>
                </div>

                <div className="pt-4 border-t border-slate-200 dark:border-slate-700">
                  <div className="flex justify-between">
                    <span className="font-semibold text-slate-900 dark:text-white">Total</span>
                    <span className="font-bold text-lg text-slate-900 dark:text-white">
                      ${orderData.total.toLocaleString()} USD
                    </span>
                  </div>
                </div>

                <div className="pt-4 border-t border-slate-200 dark:border-slate-700 space-y-2 text-xs text-slate-500 dark:text-slate-400">
                  <div className="flex items-start gap-2">
                    <Icon name="location" size="sm" className="mt-0.5 flex-shrink-0" />
                    <span>{orderData.deliveryAddress}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Icon name="employee" size="sm" className="flex-shrink-0" />
                    <span>{orderData.contactName}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Icon name="truck" size="sm" className="flex-shrink-0" />
                    <span>{orderData.product.deliveryDays.min}-{orderData.product.deliveryDays.max} días hábiles</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Footer */}
        <div className="mt-8 text-center text-xs text-slate-400">
          <p>Transacción procesada por PSE - ACH Colombia</p>
          <p className="mt-1">Este es un ambiente de demostración</p>
        </div>
      </main>
    </div>
  );
}
