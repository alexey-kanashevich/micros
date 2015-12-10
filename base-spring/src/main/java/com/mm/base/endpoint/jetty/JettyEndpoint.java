package com.mm.base.endpoint.jetty;

import com.mm.base.endpoint.Endpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JettyEndpoint implements Endpoint<JettyEndpointOptions> {
    private static final Logger log = LogManager.getLogger();

    private JettyEndpointOptions options;
    private Server server;

    private ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(new ErrorHandler());
        contextHandler.setContextPath(options.contextPath());
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), options.mappingUrl());
        contextHandler.addEventListener(new ContextLoaderListener(context));
        return contextHandler;
    }

    private WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(options.springConfigClass().getCanonicalName());
        context.getEnvironment().setActiveProfiles("dev");
        return context;
    }


    @Override
    public Endpoint configure(JettyEndpointOptions options) {
        this.options = options;
        return this;
    }

    @Override
    public Endpoint start() {
        server = new Server();
        server.setConnectors(getConnectors());
        try {
            server.setHandler(getServletContextHandler(getContext()));
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("Гэты сярвак больш не палiць", e);
            throw new IllegalStateException("Гэты сярвак больш не палiць", e);
        }
        return this;
    }

    private Connector[] getConnectors() {
        List<Connector> connectors = new ArrayList<>();
        if(options.ssl()){
            connectors.add(getSslConnector());
        }
        connectors.add(getHttpConnector());
        return connectors.toArray(new Connector[connectors.size()]);
    }

    private ServerConnector getHttpConnector() {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(options.port());
        return connector;
    }

    private ServerConnector getSslConnector() {
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(this.getClass().getResource(
                options.keystore()).toExternalForm());
        sslContextFactory.setKeyStorePassword(options.keystorePassword());
        sslContextFactory.setKeyManagerPassword(options.keystorePassword());
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(options.sslPort());
        return sslConnector;
    }

    @Override
    public Endpoint stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error("Cannot stop server", e);
        }
        return this;
    }
}
