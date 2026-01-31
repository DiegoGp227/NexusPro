"use client";

import { store } from "@/store";
import { StoreProvider } from "easy-peasy";
import { ReactNode, useEffect, useState } from "react";

export const EasyPeasyProvider = ({ children }: { children: ReactNode }) => {
  const [isClient, setIsClient] = useState(false);

  // Ensure StoreProvider is only used in the client-side
  useEffect(() => {
    setIsClient(true);
  }, []);

  if (!isClient) {
    return null;
  }

  return <StoreProvider store={store}>{children}</StoreProvider>;
};
