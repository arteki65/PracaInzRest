package pl.aptewicz.ftthchecker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import pl.aptewicz.ftthchecker.contorller.ControllerConfigurationPoint;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {ControllerConfigurationPoint.class})
public class WebConfig extends WebMvcConfigurerAdapter {

}
