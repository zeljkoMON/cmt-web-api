package com.zeljkomon.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

import java.util.Map;

public class ServletContainerFactory {
    private final Config config;

    public ServletContainerFactory(Config config) {
        this.config = config;
    }

    public TomcatEmbeddedServletContainerFactory build() {
        if (Boolean.parseBoolean(config.getHttps().get("enabled"))) {
            return this.getHttps();
        } else {
            return this.getHttp();
        }
    }

    private Connector createSslConnector() {
        Map<String, String> https = config.getHttps();
        Connector connector = new Connector(Http11NioProtocol.class.getName());
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();

        connector.setPort(Integer.parseInt(this.config.getPort()));
        connector.setScheme("https");
        connector.setRedirectPort(Integer.parseInt(https.get("port")));
        connector.setSecure(true);

        protocol.setSSLEnabled(Boolean.parseBoolean(https.get("enabled")));
        protocol.setKeyAlias(https.get("keyAlias"));
        protocol.setKeystorePass(https.get("keyStorePassword"));
        protocol.setKeystoreFile(https.get("keyStore"));
        protocol.setSslProtocol("TLS");
        protocol.setPort(Integer.parseInt(https.get("port")));

        return connector;
    }

    private TomcatEmbeddedServletContainerFactory getHttps() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        tomcat.setPort(Integer.parseInt(this.config.getPort()));
        return tomcat;
    }

    private TomcatEmbeddedServletContainerFactory getHttp() {
        return new TomcatEmbeddedServletContainerFactory();
    }
}