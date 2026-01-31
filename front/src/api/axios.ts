import axios from "axios";
import { BaseURL } from "@/shared/constants/urls";

const api = axios.create({
  baseURL: BaseURL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor - agregar token de autenticación
api.interceptors.request.use(
  (config) => {
    const token = typeof window !== "undefined"
      ? localStorage.getItem("nexuspro_token")
      : null;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor - manejar errores globalmente
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Token expirado o inválido
      if (typeof window !== "undefined") {
        localStorage.removeItem("nexuspro_token");
        window.location.href = "/login";
      }
    }
    return Promise.reject(error);
  }
);

export default api;
