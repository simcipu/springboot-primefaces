package it.laura.palestra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PalestraApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalestraApplication.class, args);
	}


	@Bean
	public WebXmlBridge webXmlBridge() {
		return new WebXmlBridge();
	}

	/*
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000);
		return multipartResolver;
	}*/

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.THEME", "nova-light");
		};
	}


}
