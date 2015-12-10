package com.mm.base.endpoint;

@SuppressWarnings("unchecked")
public class EndpointOptions<T extends EndpointOptions> {
    private int port;
    private String contextPath;
    private Class springConfigClass;
    private String envProfile;

    public int port() {
        return port;
    }

    public String contextPath() {
        return contextPath;
    }

    public Class springConfigClass() {
        return springConfigClass;
    }

    public String envProfile() {
        return envProfile;
    }

    public T port(int port) {
        this.port = port;
        return (T) this;
    }

    public T contextPath(String contextPath) {
        this.contextPath = contextPath;
        return (T) this;
    }

    public T springConfigClass(Class springConfigClass) {
        this.springConfigClass = springConfigClass;
        return (T) this;
    }

    public T envProfile(String profile) {
        this.envProfile = profile;
        return (T) this;
    }
}
