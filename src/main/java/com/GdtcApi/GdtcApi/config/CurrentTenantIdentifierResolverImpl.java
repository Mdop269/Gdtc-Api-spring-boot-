package com.GdtcApi.GdtcApi.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();
        return (tenant != null && !tenant.isEmpty()) ? tenant : "mdop";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}


