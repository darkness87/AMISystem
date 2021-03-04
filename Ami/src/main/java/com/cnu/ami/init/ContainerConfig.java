package com.cnu.ami.init;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config.properties")
public class ContainerConfig {

	@Value("${tomcat.ajp.protocol}")
	String ajpAddress;

	@Value("${tomcat.ajp.protocol}")
	String ajpProtocol;

	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		Connector ajpConnector = createAjpConnector();
		ajpConnector.setParseBodyMethods("POST,PUT,DELETE");
		tomcat.addAdditionalTomcatConnectors(ajpConnector);
		tomcat.addConnectorCustomizers(connector -> connector.setParseBodyMethods("POST,PUT,DELETE"));

		return tomcat;
	}

	private Connector createAjpConnector() {
		Connector ajpConnector = new Connector(ajpProtocol);
		ajpConnector.setPort(ajpPort);
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme("http");
		ProtocolHandler protocolHandler = ajpConnector.getProtocolHandler();
		((AbstractAjpProtocol<?>) protocolHandler).setSecretRequired(false);

		return ajpConnector;
	}
}