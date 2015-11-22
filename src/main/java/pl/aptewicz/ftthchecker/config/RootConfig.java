package pl.aptewicz.ftthchecker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.aptewicz.ftthchecker.domain.DomainConfigurationPoint;

@Configuration
@ComponentScan(basePackageClasses = {DomainConfigurationPoint.class})
public class RootConfig {

}
