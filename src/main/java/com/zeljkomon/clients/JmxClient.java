package com.zeljkomon.clients;

import com.zeljkomon.App;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class JmxClient {
    private JMXConnector jmxConnector;
    private static final Logger logger = LogManager.getLogger(App.class.getName());

    private JmxClient(ClientBuilder clientBuilder) {
        Map<String, Object> env = clientBuilder.getEnv();
        JMXServiceURL _jmxServiceURL = clientBuilder.getJmxServiceURL();
        try {
            //TODO fix timeout
            jmxConnector = JMXConnectorFactory.connect(_jmxServiceURL, env);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public MBeanServerConnection getConnection() {
        try {
            return jmxConnector.getMBeanServerConnection();
        } catch (IOException | NullPointerException e) {
            logger.log(Level.ERROR, e.getMessage());
            return null;
        }
    }

    public static class ClientBuilder {
        private JMXServiceURL _jmxServiceURL;
        private Map<String, Object> _env = new HashMap<>();
        private String _user;
        private String _password;
        private boolean _ssl;

        public ClientBuilder(String url) {
            String formattedUrl = "service:jmx:rmi:///jndi/rmi://".concat(url).concat("/jmxrmi");
            try {
                _jmxServiceURL = new JMXServiceURL(formattedUrl);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        public ClientBuilder user(String user) {
            _user = user;
            return this;
        }

        public ClientBuilder password(String password) {
            _password = password;
            return this;
        }

        public ClientBuilder ssl(boolean ssl) {
            _ssl = ssl;
            return this;
        }

        public JmxClient build() {

            if (!_user.isEmpty() && !_password.isEmpty()) {
                _env.put(JMXConnector.CREDENTIALS, new String[]{_user, _password});
            }

            if (_ssl) {
                _env.put("com.sun.jndi.rmi.factory.socket", new SslRMIClientSocketFactory());
            }

            return new JmxClient(this);
        }

        Map<String, Object> getEnv() {
            return _env;
        }

        JMXServiceURL getJmxServiceURL() {
            return _jmxServiceURL;
        }
    }
}