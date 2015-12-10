package com.mm.base.endpoint;

import com.mm.base.endpoint.jetty.JettyEndpoint;
import com.mm.base.endpoint.jetty.JettyEndpointOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class MMApp {

    public static void run(String appName, Class springConfigClass) {
        try {
            new JettyEndpoint().configure(readEndpointOptions(appName, springConfigClass)).start();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static JettyEndpointOptions readEndpointOptions(String appName, Class springConfigClass) throws IOException {
        Properties props = new Properties();
        Properties fileProps = PropertiesLoaderUtils.loadProperties(getPropertiesResource(appName));
        Properties systemProps = System.getProperties();
        props.putAll(fileProps);
        props.putAll(systemProps);
        boolean ssl = Boolean.parseBoolean(props.getProperty("ssl", "false"));
        JettyEndpointOptions jettyEndpointOptions = new JettyEndpointOptions()
                .springConfigClass(springConfigClass)
                .contextPath(props.getProperty("context.path", "/"))
                .port(Integer.parseInt(props.getProperty("http.port", "8081")))
                .envProfile(props.getProperty("env.profile", "dev"))
                .mappingUrl(props.getProperty("mapping.url", "/*"))
                .ssl(ssl);
        if(ssl){
            jettyEndpointOptions = jettyEndpointOptions
                    .keystore(props.getProperty("keystore"))
                    .keystorePassword(props.getProperty("keystore.password"))
                    .sslPort(Integer.parseInt(props.getProperty("ssl.port")));
        }
        return jettyEndpointOptions;
    }

    public static void main(String[] args) {
        if (args.length <2) {
            System.out.println("Please specify appName as 1st argument and config class canonical name as 2nd");
            return;
        }
        try {
            run(args[0], Class.forName(args[1]));
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot find spring configuration class "+ args[1]);
        }

    }

    private static ClassPathResource getPropertiesResource(String appName) {
        return new ClassPathResource(String.format("%s.properties", appName));
    }
}
