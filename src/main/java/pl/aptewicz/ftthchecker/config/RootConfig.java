package pl.aptewicz.ftthchecker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.aptewicz.ftthchecker.domain.DomainConfigurationPoint;
import pl.aptewicz.ftthchecker.service.ServiceConfigurationPoint;

@Configuration
@ComponentScan(basePackageClasses = {DomainConfigurationPoint.class,
		ServiceConfigurationPoint.class})
public class RootConfig {

}
