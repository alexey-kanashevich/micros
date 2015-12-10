package com.mm.base.endpoint;

public interface Endpoint<T extends EndpointOptions> {
    Endpoint configure(T options);
    Endpoint start();
    Endpoint stop();
}
