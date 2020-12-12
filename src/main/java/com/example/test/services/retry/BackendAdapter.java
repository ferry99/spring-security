package com.example.test.services.retry;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import com.example.test.exception.RemoteServiceNotAvailableException;

 
public interface BackendAdapter {
 
    @Retryable(value = { RemoteServiceNotAvailableException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String getBackendResponse(boolean simulateretry, boolean simulateretryfallback);
 
    @Recover
    public String getBackendResponseFallback(RuntimeException e);
 
}
