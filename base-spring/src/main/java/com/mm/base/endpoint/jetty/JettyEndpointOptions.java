package com.mm.base.endpoint.jetty;

import com.mm.base.endpoint.EndpointOptions;

public class JettyEndpointOptions extends EndpointOptions<JettyEndpointOptions> {

    private String mappingUrl;
    private boolean ssl;
    private String keystorePassword;
    private int sslPort;
    private String keystore;

    public JettyEndpointOptions mappingUrl(String mappingUrl) {
        this.mappingUrl = mappingUrl;
        return this;
    }

    public String mappingUrl() {
        return mappingUrl;
    }

    public String keystore() {
        return keystore;
    }

    public JettyEndpointOptions keystore(String keystore) {
        this.keystore = keystore;
        return this;
    }

    public boolean ssl() {
        return ssl;
    }

    public JettyEndpointOptions ssl(boolean ssl) {
        this.ssl = ssl;
        return this;
    }

    public String keystorePassword() {
        return keystorePassword;
    }

    public JettyEndpointOptions keystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
        return this;
    }

    public int sslPort() {
        return sslPort;
    }

    public JettyEndpointOptions sslPort(int sslPort) {
        this.sslPort = sslPort;
        return this;
    }
}
