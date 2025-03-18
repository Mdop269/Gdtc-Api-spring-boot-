package com.GdtcApi.GdtcApi.config;

// ThreadLocal holder to keep track of the current tenant ( set in jwtfilter)
public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static void setCurrentTenant(String dbName) {
        CURRENT_TENANT.set(dbName);
    }

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }

}
