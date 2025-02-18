package com.GdtcApi.GdtcApi.Service.SecurityService;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

// this service is no use of right now
//in this we are exolicitly setting security context in async methods , this ensures that the jwt authentiction is available in the async thread
@Service
public class AsyncService {

    @Async
    public CompletableFuture<String> asyncMethod(){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return CompletableFuture.supplyAsync(() -> {
            SecurityContextHolder.setContext(securityContext);
            return "Async Task Completed";
        });
    }
}
