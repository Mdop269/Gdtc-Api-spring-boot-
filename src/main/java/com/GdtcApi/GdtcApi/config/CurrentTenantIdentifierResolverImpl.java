package com.GdtcApi.GdtcApi.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

// resolve the tenant identifier
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {


    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();
      if(tenant == null || tenant.trim().isEmpty())
        {
            tenant = "mdop";
        }
            System.out.println("Resolver: tenant" + tenant + " thread " + Thread.currentThread().getName());
//        return (tenant != null && !tenant.trim().isEmpty()) ? tenant : "mdop";

        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}


